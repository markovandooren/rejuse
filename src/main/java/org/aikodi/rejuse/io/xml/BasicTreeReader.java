package org.aikodi.rejuse.io.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class BasicTreeReader<I extends T, T> implements XMLReader<T> {

   private final DefaultNodeConfigurator<I, T> defaultNodeConfigurator;
   private final Map<String, NodeConfigurator<I, T>> internalNodeMap;
   private final Map<String, LeafConfigurator<I, T>> leafNodeMap;
   private final DefaultLeafConfigurator<I, T> defaultLeafConfigurator;

   public static <I extends T, T> Builder<I, T> createBuilder() {
      return new BuilderImpl<>();
   }

   public BasicTreeReader(DefaultNodeConfigurator<I, T> defaultNodeConfigurator, DefaultLeafConfigurator<I, T> defaultLeafConfigurator, List<NodeConfigurator<I, T>> nodeConfigurators, List<LeafConfigurator<I, T>> leafConfigurators) {
      if(nodeConfigurators == null) {
         throw new IllegalArgumentException();
      }
      if(leafConfigurators == null) {
         throw new IllegalArgumentException();
      }
      this.defaultNodeConfigurator = defaultNodeConfigurator;
      this.defaultLeafConfigurator = defaultLeafConfigurator;
      internalNodeMap = new HashMap<>();
      leafNodeMap = new HashMap<>();
      nodeConfigurators.forEach(c -> internalNodeMap.put(c.tagName(), c));
      leafConfigurators.forEach(c -> leafNodeMap.put(c.tagName(), c));
   }

   private AttributeMap attributes(XMLStreamReader reader) {
      int attributeCount = reader.getAttributeCount();
      Map<String, String> map = new HashMap<>();
      for(int i = 0; i < attributeCount; i++) {
         map.put(reader.getAttributeName(i).getLocalPart(), reader.getAttributeValue(i));
      }
      return new AttributeMap(map);
   }

   private InternalNode<I, T> readNode(XMLStreamReader reader) {
      String elementType = reader.getLocalName();
      AttributeMap attributes = attributes(reader);
      if(internalNodeMap.containsKey(elementType)) {
         NodeConfigurator<I, T> nodeConfigurator = internalNodeMap.get(elementType);
         BiFunction<String, AttributeMap, I> creator;
         if (nodeConfigurator.creator() != null) {
            creator = (ignored, attributeMap) -> nodeConfigurator.creator().apply(attributeMap);
         } else if(defaultNodeConfigurator != null) {
            creator = defaultNodeConfigurator.creator();
         } else {
            return null;
         }
         BiConsumer<I, T> collector;
         if (nodeConfigurator.collector() != null) {
            collector = nodeConfigurator.collector();
         } else if(defaultNodeConfigurator != null) {
            collector = defaultNodeConfigurator.collector();
         } else {
            return null;
         }
         return new InternalNode<I, T>(creator.apply(elementType, attributes), collector);
      } else {
         return null;
      }
   }

   private T readLeaf(XMLStreamReader reader) {
      String elementType = reader.getLocalName();
      AttributeMap attributes = attributes(reader);
      if(leafNodeMap.containsKey(elementType)) {
         LeafConfigurator<I, T> leafConfigurator = leafNodeMap.get(elementType);
         if (leafConfigurator.creator() != null) {
            return leafConfigurator.creator().apply(attributes);
         } else if(defaultLeafConfigurator != null){
            return defaultLeafConfigurator.creator().apply(elementType, attributes);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }


   private static class InternalNode<I, T> {

      private I element;
      private BiConsumer<I, T> consumer;

      public InternalNode(I element, BiConsumer<I, T> consumer) {
         if(consumer == null || element == null) {
            throw new IllegalArgumentException();
         }
         this.element = element;
         this.consumer = consumer;
      }

      public I element() {
         return element;
      }

      public BiConsumer<I, T> consumer() {
         return consumer;
      }
   }

   @Override
   public T read(XMLStreamReader reader) throws XMLStreamException {
      List<T> result = new ArrayList<T>(1);
      read(reader, c -> result.add(c));
      return result.get(0);
   }

   public void read(final XMLStreamReader reader, Consumer<T> consumer) throws XMLStreamException {
      int nestingCounter = 0;
      while (reader.hasNext()) {
         int eventCode = reader.next();
         switch (eventCode) {
            case XMLStreamConstants.START_ELEMENT :
               if(consumer == null) {
                  nestingCounter++;
               } else {
                  InternalNode<I, T> node = readNode(reader);
                  if (node != null) {
//                     System.out.println("created node " + node.node().getClass().getSimpleName() + " " + node.node().toString());
                     consumer.accept(node.element());
                     read(reader, child -> {
                        node.consumer().accept(node.element(), child);
//                        System.out.println("added " + child.getClass().getSimpleName() + " " + child.toString() + " to " + node.node().getClass().getSimpleName() + " " + node.node().toString());
                     });
                  } else {
                     T leaf = readLeaf(reader);
                     if (leaf != null) {
//                        System.out.println("created leaf " + leaf.getClass().getSimpleName() + " " + leaf.toString());
                        consumer.accept(leaf);
                        read(reader, ignored -> {});
                     } else {
                        nestingCounter++;
                     }
                  }
               }
               break;
            case XMLStreamConstants.END_ELEMENT :
               if (nestingCounter != 0) {
                  nestingCounter--;
               } else {
                  return;
               }
               break;
            default:
               break;
         }
      }
   }

   public static interface Builder<I extends T, T> {
      DefaultNodeConfigurator<I, T> nodes();
      DefaultLeafConfigurator<I, T> leaves();
      NodeConfigurator<I, T> node(String tagName);
      LeafConfigurator<I, T> leaf(String tagName);
      BasicTreeReader<I, T> build();
   }

   public static class BuilderImpl<I extends T, T> implements Builder<I, T> {
      private DefaultNodeConfigurator<I, T> defaultNodeConfigurator;
      private DefaultLeafConfigurator<I, T> defaultLeafConfigurator;
      private List<NodeConfigurator<I, T>> nodeConfigurators = new ArrayList<>();
      private List<LeafConfigurator<I, T>> leafConfigurators = new ArrayList<>();

      @Override
      public DefaultNodeConfigurator<I, T> nodes() {
         if (defaultNodeConfigurator != null) {
            throw new IllegalStateException("The node configuration is already set.");
         }
         defaultNodeConfigurator = new DefaultNodeConfigurator<>(this);
         return defaultNodeConfigurator;
      }

      @Override
      public DefaultLeafConfigurator<I, T> leaves() {
         if (defaultLeafConfigurator != null) {
            throw new IllegalStateException("The node configuration is already set.");
         }
         defaultLeafConfigurator = new DefaultLeafConfigurator<>(this);
         return defaultLeafConfigurator;
      }

      @Override
      public NodeConfigurator<I, T> node(String tagName) {
         NodeConfigurator<I, T> result = new NodeConfigurator<>(this, tagName);
         nodeConfigurators.add(result);
         return result;
      }

      @Override
      public LeafConfigurator<I, T> leaf(String tagName) {
         LeafConfigurator<I, T> result = new LeafConfigurator<>(this, tagName);
         leafConfigurators.add(result);
         return result;
      }

      @Override
      public BasicTreeReader<I, T> build() {
         return new BasicTreeReader<I, T>(defaultNodeConfigurator, defaultLeafConfigurator, nodeConfigurators, leafConfigurators);
      }
   }

   public static class Configurator<I extends T, T> implements Builder<I, T> {
      private Builder<I, T> parent;

      public Configurator(Builder<I, T> parent) {
         if (parent == null) {
            throw new IllegalArgumentException();
         }
         this.parent = parent;
      }

      public Builder<I, T> parent() {
         return parent;
      }

      @Override
      public DefaultNodeConfigurator<I, T> nodes() {
         return parent().nodes();
      }

      @Override
      public DefaultLeafConfigurator<I, T> leaves() {
         return parent().leaves();
      }

      @Override
      public NodeConfigurator<I, T> node(String tagName) {
         return parent().node(tagName);
      }

      @Override
      public LeafConfigurator<I, T> leaf(String tagName) {
         return parent().leaf(tagName);
      }

      @Override
      public BasicTreeReader<I, T> build() {
         return parent().build();
      }
   }

   public static class TagConfigurator<I extends T, T, S extends TagConfigurator<I, T,S>> extends Configurator<I, T> {
      private String tagName;
      private Function<AttributeMap, I> creator;

      public TagConfigurator(Builder<I, T> parent, String tagName) {
         super(parent);
         if (tagName == null || parent == null) {
            throw new IllegalArgumentException();
         }
         this.tagName = tagName;
      }

      public String tagName() {
         return tagName;
      }

      public S constructor(Function<AttributeMap, I> creator) {
         this.creator = creator;
         return (S)this;
      }

      public Function<AttributeMap, I> creator() {
         return creator;
      }

   }

   public static class LeafConfigurator<I extends T, T> extends Configurator<I, T> {

      private String tagName;
      private Function<AttributeMap, T> creator;

      public String tagName() {
         return tagName;
      }

      public LeafConfigurator<I,T> constructor(Function<AttributeMap, T> creator) {
         this.creator = creator;
         return this;
      }

      public Function<AttributeMap, T> creator() {
         return creator;
      }

      public LeafConfigurator(Builder<I, T> parent, String tagName) {
         super(parent);
         if (tagName == null || parent == null) {
            throw new IllegalArgumentException();
         }
         this.tagName = tagName;
      }
   }

   public static class DefaultLeafConfigurator<I extends T, T> extends Configurator<I, T> {

      private BiFunction<String, AttributeMap, T> creator;

      public DefaultLeafConfigurator(Builder<I, T> parent) {
         super(parent);
      }

      public DefaultLeafConfigurator<I, T> constructor(BiFunction<String, AttributeMap, T> creator) {
         this.creator = creator;
         return this;
      }

      public BiFunction<String, AttributeMap, T> creator() {
         return creator;
      }
   }


   public static class NodeConfigurator<I extends T, T> extends TagConfigurator<I, T, NodeConfigurator<I, T>> {

      private BiConsumer<I, T> collector;
      public NodeConfigurator(Builder<I, T> parent, String tagName) {
         super(parent, tagName);
      }

      public NodeConfigurator<I, T> collector(BiConsumer<I, T> collector) {
         if(collector == null) {
            throw new IllegalArgumentException();
         }
         this.collector = collector;
         return this;
      }

      public BiConsumer<I, T> collector() {
         return collector;
      }
   }

   public static class DefaultNodeConfigurator<I extends T, T> extends Configurator<I, T> {

      private BiFunction<String, AttributeMap, I> creator;
      private BiConsumer<I, T> collector;

      public DefaultNodeConfigurator(Builder<I, T> parent) {
         super(parent);
      }

      public DefaultNodeConfigurator<I, T> constructor(BiFunction<String, AttributeMap, I> creator) {
         this.creator = creator;
         return this;
      }

      public DefaultNodeConfigurator<I, T> collector(BiConsumer<I, T> collector) {
         this.collector = collector;
         return this;
      }

      public BiFunction<String, AttributeMap, I> creator() {
         return creator;
      }

      public BiConsumer<I, T> collector() {
         return collector;
      }
   }
}
