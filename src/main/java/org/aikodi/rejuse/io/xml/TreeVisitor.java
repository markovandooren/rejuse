package org.aikodi.rejuse.io.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.aikodi.contract.Contract;
import org.aikodi.rejuse.action.Action;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.function.Consumer;
import org.aikodi.rejuse.map.StringMap;
import static org.aikodi.contract.Contract.*;

/**
 * A class of visitors for XML trees.
 * 
 * @param <E> The type of the exception that can be thrown.
 */
public class TreeVisitor<E extends Exception> {

	/**
	 * Create a new tree visitor builder.
	 * 
	 * @return A non-null tree visitor builder with an empty configuration. 
	 */
    public static <E extends Exception> Builder<E> builder() {
        return new Builder<>();
    }

    private Map<String, ElementVisitorBuilder<E>> nodeBuilders;
    private Map<String, ElementVisitorBuilder<E>> leafBuilders;

    private TreeVisitor(Map<String, ElementVisitorBuilder<E>> nodeBuilders,
                        Map<String, ElementVisitorBuilder<E>> leafBuilders) {
        this.nodeBuilders = new HashMap<>(nodeBuilders);
        this.leafBuilders = new HashMap<>(leafBuilders);
    }

    /**
     * Visit the given XML stream.
     * 
     * @param reader The reader of the XML stream.
     *               The reader cannot be null.
     *               
     * @throws E The visitor code threw an exception.
     * @throws XMLStreamException The reader threw an exception.
     */
    public void visit(XMLStreamReader reader) throws E, XMLStreamException {
    	requireNotNull(reader);
    	
        visit(reader, true, null, null);
    }

    /**
     * Execute all actions in the given list.
     * The actions are exectuted in the order in which they are in the list.
     * 
     * @param actions The actions to be executed.
     *                The list cannot be null.
     *                The list cannot contain null.
     *                
     * @throws E One of the actions threw an exception.
     */
    private void consume(List<Action<E>> actions) throws E {
        for (Action<E> action : actions) {
            action.apply();
        }
    }

    private void consume(StringMap map, List<Consumer<StringMap, E>> consumers)
            throws E {
        for (Consumer<StringMap, E> consumer : consumers) {
            consumer.accept(map);
        }
    }

    private void consumeAttributes(StringMap map, List<AttributeVisitorBuilder<E>> attributeVisitorBuilders)
            throws E {
        for (AttributeVisitorBuilder<E> visitorBuilder : attributeVisitorBuilders) {
            if (map.containsKey(visitorBuilder.name)) {
                for (Consumer<String, E> consumer : visitorBuilder.action) {
                    consumer.accept(map.get(visitorBuilder.name));
                }
            }
        }
    }

    private StringMap attributes(XMLStreamReader reader) {
        int attributeCount = reader.getAttributeCount();
        StringMap.Builder builder = StringMap.builder();
        for (int i = 0; i < attributeCount; i++) {
            builder.with(reader.getAttributeName(i).getLocalPart())
                    .as(reader.getAttributeValue(i));
        }
        return builder.build();
    }

    public void visit(XMLStreamReader reader, boolean descend,
                      ElementVisitorBuilder<E> parentBuilder, StringMap parentAttributes)
            throws E, XMLStreamException {
        int nestingCounter = 0;
        while (reader.hasNext()) {
            int eventCode = reader.next();
            switch (eventCode) {
                case XMLStreamConstants.START_ELEMENT:
                    if (descend == false) {
                        nestingCounter++;
                    } else {
                        String tagName = reader.getLocalName();
                        StringMap attributes = attributes(reader);

                        boolean recurse = true;
                        ElementVisitorBuilder<E> childBuilder = nodeBuilders.get(tagName);
                        if (childBuilder == null) {
                            recurse = false;
                            childBuilder = leafBuilders.get(tagName);
                        }
                        if (childBuilder != null && descend) {
                            consume(childBuilder.open);
                            consume(attributes, childBuilder.openWithAttributes);
                            consumeAttributes(attributes, childBuilder.attribute);

                            visit(reader, recurse, childBuilder, attributes);
                        } else {
                            nestingCounter++;
                        }
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText();
                    if (parentBuilder != null) {
                        for (Consumer<String, E> consumer : parentBuilder.text) {
                            consumer.accept(text);
                        }
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (nestingCounter != 0) {
                        nestingCounter--;
                    } else {
                        if (parentBuilder != null) {
                            consume(parentBuilder.close);
                            consume(parentAttributes, parentBuilder.closeWithAttributes);
                        }
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static class Builder<E extends Exception> {

        private Map<String, ElementVisitorBuilder<E>> nodeBuilders = new HashMap<>();
        private Map<String, ElementVisitorBuilder<E>> leafBuilders = new HashMap<>();

        private Builder() {
        }

        public ElementVisitorBuilder<E> node(String name) {
            ElementVisitorBuilder<E> result = new ElementVisitorBuilder<>(name, this);
            nodeBuilders.put(name, result);
            return result;
        }

        public ElementVisitorBuilder<E> leaf(String name) {
            ElementVisitorBuilder result = new ElementVisitorBuilder(name, this);
            leafBuilders.put(name, result);
            return result;
        }

        public TreeVisitor<E> build() {
            return new TreeVisitor<E>(nodeBuilders, leafBuilders);
        }

    }

    public static class ElementVisitorBuilder<E extends Exception> {

        private String name;
        private Builder<E> parent;
        private List<Action<E>> open = new ArrayList<>();
        private List<Action<E>> close = new ArrayList<>();
        private List<Consumer<StringMap, E>> openWithAttributes = new ArrayList<>();
        private List<Consumer<StringMap, E>> closeWithAttributes = new ArrayList<>();
        private List<Consumer<String, E>> text = new ArrayList<>();
        private List<AttributeVisitorBuilder<E>> attribute = new ArrayList<>();

        private ElementVisitorBuilder(String name, Builder<E> parent) {
            Contract.requireNotNull(name);
            Contract.requireNotNull(parent);

            this.name = name;
            this.parent = parent;
        }

        public ElementVisitorBuilder<E> node(String name) {
            return parent.node(name);
        }

        public ElementVisitorBuilder<E> leaf(String name) {
            return parent.leaf(name);
        }

        public TreeVisitor<E> build() {
            return parent.build();
        }

        private <T> ElementVisitorBuilder<E> add(List<T> list, T element) {
            Contract.requireNotNull(element);
            Contract.requireNotNull(list);

            list.add(element);
            return this;
        }

        public ElementVisitorBuilder<E> open(Action<E> action) {
            return add(open, action);
        }

        public ElementVisitorBuilder<E> close(Action<E> action) {
            return add(close, action);
        }

        public ElementVisitorBuilder<E> open(Consumer<StringMap, E> action) {
            return add(openWithAttributes, action);
        }

        public ElementVisitorBuilder<E> close(Consumer<StringMap, E> action) {
            return add(closeWithAttributes, action);
        }

        public AttributeVisitorBuilder<E> attribute(String name) {
            AttributeVisitorBuilder<E> result = new AttributeVisitorBuilder(name,
                    this);
            attribute.add(result);
            return result;
        }

        public ElementVisitorBuilder<E> text(Consumer<String, E> action) {
            return add(text, action);
        }

    }

    public static class AttributeVisitorBuilder<E extends Exception> {

        private String name;
        private ElementVisitorBuilder<E> parent;
        private List<Consumer<String, E>> action = new ArrayList<>();

        private AttributeVisitorBuilder(String name,
                                        ElementVisitorBuilder<E> parent) {
            Contract.requireNotNull(name);
            Contract.requireNotNull(parent);

            this.name = name;
            this.parent = parent;
        }

        public ElementVisitorBuilder<E> open(Action<E> action) {
            return parent.open(action);
        }

        public ElementVisitorBuilder<E> open(Consumer<StringMap, E> action) {
            return parent.open(action);
        }

        public ElementVisitorBuilder<E> close(Action<E> action) {
            return parent.close(action);
        }

        public ElementVisitorBuilder<E> close(Consumer<StringMap, E> action) {
            return parent.close(action);
        }

        public AttributeVisitorBuilder<E> attribute(String name) {
            return parent.attribute(name);
        }

        public ElementVisitorBuilder<E> text(Consumer<String, E> action) {
            return parent.text(action);
        }

        public ElementVisitorBuilder<E> node(String name) {
            return parent.node(name);
        }

        public ElementVisitorBuilder<E> leaf(String name) {
            return parent.node(name);
        }

        public TreeVisitor<E> build() {
            return parent.build();
        }

        public AttributeVisitorBuilder<E> action(Consumer<String, E> action) {
            Contract.requireNotNull(action);
            this.action.add(action);
            return this;
        }

    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        TreeVisitor<Nothing> visitor = TreeVisitor.<Nothing>builder()
                .node("source").open(() -> print("source"))
                .node("class")
                    .open(() -> print("class"))
                    .attribute("name").action(n -> print(n))
                .build();
        XMLStreamReader reader = XMLInputFactory.newInstance()
                .createXMLStreamReader(new BufferedInputStream(new FileInputStream(new File("/home/marko/git/heimdall/demo/project/project-1.xml"))));
        visitor.visit(reader);
    }
}
