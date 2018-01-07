package org.aikodi.rejuse.io.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.aikodi.contract.Contract;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.function.BiConsumer;
import org.aikodi.rejuse.function.BiFunction;
import org.aikodi.rejuse.function.Consumer;
import org.aikodi.rejuse.function.Function;
import org.aikodi.rejuse.function.Producer;
import org.aikodi.rejuse.map.StringMap;
import static org.aikodi.contract.Contract.requireNotNull;

public abstract class TreeReader<C, E extends Exception> {

	/**
	 * The tag of the elements that are read by this reader.
	 * 
	 * @return The result is not null.
	 *         The result is not the empty string.
	 */
	protected abstract String tagName();

	private static class GenericTreeReader<TYPE, PARENTTYPE, E extends Exception> extends TreeReader<TYPE, E> {
		
		private Map<String, GenericTreeReader<?, TYPE, ? extends E>> childReaders;
		
		private boolean descend = true;
		
		private String tagName;

		// OPEN
		
		/**
		 * 
		 */
		private Producer<TYPE, E> defaultConstructor;
		/**
		 * The function to constructor the child (C) given the parent (P) and the attributes.
		 */
		private BiFunction<PARENTTYPE, TreeNode, ? extends TYPE, ? extends E> constructorWithParent;
		
		private Function<TreeNode, ? extends TYPE, ? extends E> constructorWithAttributes;

		// TEXT
		
		private BiConsumer<TYPE, String, E> textProcessor;


		// CLOSE
		
		private BiConsumer<PARENTTYPE, TYPE, E> closerWithParent;
		

		/**
		 * Create a generic tree reader the create the node before adding it to the parent.
		 * 
		 * @param configurator The configuration of the reader.
		 *                     The configuration cannot be null.
		 * @param descend Determines if this reader is a leaf reader or not.
		 *                If true, the reader will process descendants.
		 *                If false, the reader will not process descendants.
		 */
		GenericTreeReader(NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E, ?> configurator, boolean descend) {
			requireNotNull(configurator);
			
			tagName = configurator.tagName();
			this.descend = descend;
			
			defaultConstructor = configurator.defaultConstructor();
			constructorWithAttributes = configurator.constructorWithNode();
			closerWithParent = configurator.closer();
		}

		protected String tagName() {
			return tagName;
		}

		private TreeNode attributes(String name, XMLStreamReader reader) {
			int attributeCount = reader.getAttributeCount();
			StringMap.Builder builder = StringMap.builder();
			for (int i = 0; i < attributeCount; i++) {
				builder.with(reader.getAttributeName(i).getLocalPart()).as(reader.getAttributeValue(i));
			}
			return new TreeNode(name, builder.build());
		}

		protected void read(XMLStreamReader reader, PARENTTYPE parent) throws E, XMLStreamException {
			// Open
			String tagName = reader.getLocalName();
			TYPE currentElement;
			if (defaultConstructor != null) {
				currentElement = defaultConstructor.produce();
			} else if (constructorWithParent != null) {
				requireNotNull(parent, "The constructor for " + tagName + " expects a parent object, but received null.");
				currentElement = constructorWithParent.apply(parent, attributes(tagName, reader));
			} else if (constructorWithAttributes != null) {
				currentElement = constructorWithAttributes.apply(attributes(tagName, reader));
			} else {
				throw new IllegalStateException("No element could be constructed.");
			}

			// Content
			readChildren(reader, currentElement, parent);

			// Close
			if (closerWithParent != null) {
				closerWithParent.accept(parent, currentElement);
			}
		}

		/**
		 * Reader the root for this tree reader.
		 * 
		 * @param reader
		 * @throws XMLStreamException
		 * @throws E
		 */
		private void readChildren(XMLStreamReader reader, TYPE currentElement, PARENTTYPE parentOfCurrentElement)
				throws XMLStreamException, E {

			int nestingCounter = 0;
			while (reader.hasNext()) {
				int eventCode = reader.next();
				switch (eventCode) {
				case XMLStreamConstants.START_ELEMENT:
					if (descend == false) {
						nestingCounter++;
					} else {
						{
							GenericTreeReader<?, TYPE, ? extends E> childReader = childReaders.get(reader.getLocalName());
							if (childReader != null) {
								childReader.read(reader, currentElement);
							} else {
								nestingCounter++;
							}
						}
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					String text = reader.getText();
					if (textProcessor != null) {
						textProcessor.accept(currentElement, text);
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
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
	}

	/**
	 * TreeReader.<T1>build() .child("T1") .create((name,attributes) -> new T1(...))
	 * .collect(t1 -> {}) .child("T2") .create((n,a) -> new T2(...)) .close((t1, t2)
	 * -> {}) .done() .children(S3.class) .collect((t1, t3) -> {});
	 * .child("T3A").create((n,a) -> new T3(...)) .done() .done()
	 */

	/**
	 * Composition.
	 * 
	 * TreeReader<Tx> txReader = ... TreeReader<T1> t1Reader = new
	 * TreeReader.Builder<T1>() .child("T1") .create((n,a) -> new T1(...))
	 * .child("T2") .create((n,a) -> new T2(...)) .close((t1, t2) -> {...})
	 * .child(txReader) .close((t1, tx) -> {...}) .children(S3.class) // These child
	 * configurators don't have a collect method themselves
	 * .child("T3A").create((n,a) -> new T3A(...)) .child("T3B").create((n,a) -> new
	 * T3B(...)) .child("T3C") .create((n,a) -> new T3C(...)) .child("T4")
	 * .create((n,a) -> new T4(...)) .collect((t3,t4) -> {...}) .child("T5")
	 * .create((n,a) -> new T5(...)) .child("T6") .create((n,a) -> new T6(...))
	 * .collect((t5,t6) -> {...}) .collect((t3,t5) -> {...}) .collect((t1, t3) ->
	 * {...}); .collect(t1 -> {...}) .build();
	 */

	/**
	 * What about recursion?
	 * 
	 * <a> <b> <a> </a> </b> </a>
	 * 
	 * Box<TreeReader<A>> a = new Box<>(null); Box<TreeReader<B>> b = new
	 * Box<>(null);
	 * 
	 * TreeReader<A> aReader = TreeReader.<A>builder() .child("A") .create((n, a) ->
	 * new A(...)) .child(n -> b) .collect((a, b) -> {...}) .build();
	 * 
	 * TreeReader<B> bReader = TreeReader.<B>builder() .child("B") .create((n, a) ->
	 * new B(...)) .child(a::get()) .collect((b, a) -> {...}) .build();
	 */

	public static <T, E extends Exception> Builder<T, E> builder() {
		return new Builder<T, E>();
	}

	public static class Builder<T, E extends Exception> {
		public RootNodeFirstConfigurator<T, E> open(String tagName, Producer<T, E> type) {
			return new RootNodeFirstConfigurator<>(tagName);
		}
	}

	public static class RootNodeFirstConfigurator<R, E extends Exception> // implements Consumer<TreeReader<?, E>,
																			// Nothing> {
			extends NodeConfigurator<R, E, RootNodeFirstConfigurator<R, E>> {

		protected RootNodeFirstConfigurator(String tagName) {
			super(tagName);
		}

		public TreeReader<R, E> close() {
			return null;
		}

	}

	public static abstract class NodeConfigurator<TYPE, E extends Exception, SELF extends NodeConfigurator<TYPE, E, SELF>>
			implements Consumer<TreeReader<?, E>, Nothing> {

		private String tagName;
		private List<TreeReader<?, E>> childReaders = new ArrayList<>();

		protected NodeConfigurator(String tagName) {
			this.tagName = tagName;
		}

		protected String tagName() {
			return tagName;
		}

		@Override
		public void accept(TreeReader<?, E> reader) {
			Contract.requireNotNull(reader);
			childReaders.add(reader);
		}

		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> open(TreeReader<X, E> type) {
			return null;
		}

		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> open(String tagName, Producer<X, E> type) {
			return null;
		}

		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> open(String tagName,
				Function<StringMap, X, E> type) {
			return null;
		}

	}

	public static abstract class NodeFirstConfigurator<TYPE, PARENTTYPE, E extends Exception, PARENTCONFIGURATOR, SELF extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, PARENTCONFIGURATOR, SELF>>
			extends NodeConfigurator<TYPE, E, SELF> {

		private PARENTCONFIGURATOR parent;
		
		private NodeFirstConfigurator(PARENTCONFIGURATOR parent, String tagName) {
			super(tagName);
			this.parent = parent;
		}
		
		public <X> NodeFirstAddOnOpenConfigurator<X, TYPE, E, SELF> open(String tagName,
				BiFunction<PARENTTYPE, TreeNode, X, E> constructor) {
			return null;
		}

		public PARENTCONFIGURATOR parent() {
			return parent;
		}
		
	}

	public static class NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E extends Exception, P extends Consumer<TreeReader<?, E>, Nothing>>
			extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, P, NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E, P>> {

		private BiConsumer<PARENTTYPE, TYPE, E> _closer;
		
		private Producer<TYPE, E> defaultConstructor;
		
		private Function<TreeNode, TYPE, E> constructorWithNode;

		private NodeFirstAddOnCloseConfigurator(P parent, String tagName, Producer<TYPE, E> defaultConstructor) {
			super(parent, tagName);
			requireNotNull(defaultConstructor);
			
			this.defaultConstructor = defaultConstructor;
		}

		private NodeFirstAddOnCloseConfigurator(P parent, String tagName, Function<TreeNode, TYPE, E> constructorWithNode) {
			super(parent, tagName);
			requireNotNull(constructorWithNode);
			
			this.constructorWithNode = constructorWithNode;
		}

		public P close(BiConsumer<PARENTTYPE, TYPE, E> closer) {
			requireNotNull(closer);
			
			_closer = closer;
			parent().accept(new GenericTreeReader<TYPE, PARENTTYPE, E>(this, true));
			return parent();
		}
		
		public BiConsumer<PARENTTYPE, TYPE, E> closer() {
			return _closer;
		}

		public Producer<TYPE, E> defaultConstructor() {
			return defaultConstructor;
		}
		
		public Function<TreeNode, TYPE, E> constructorWithNode() {
			return constructorWithNode;
		}

	}

	public static class NodeFirstAddOnOpenConfigurator<TYPE, PARENTTYPE, E extends Exception, PARENT extends Consumer<TreeReader<?, E>, Nothing>>
			extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, PARENT, NodeFirstAddOnOpenConfigurator<TYPE, PARENTTYPE, E, PARENT>> {

		private NodeFirstAddOnOpenConfigurator(PARENT parent, String tagName) {
			super(parent, tagName);
		}

		public PARENT close() {
			return parent();
		}

	}

	public static class ActionNodeConfigurator<T, PT, E extends Exception, P extends NodeFirstConfigurator<PT, ?, E, ?, ?>>
			extends NodeFirstConfigurator<T, PT, E, P, NodeFirstAddOnCloseConfigurator<T, PT, E, P>> {

		private ActionNodeConfigurator(P parent, String tagName) {
			super(parent, tagName);
		}

		public P close() {
			return parent();
		}

	}

	// public static class ParentLastNodeConfigurator<T, PT, E extends Exception,
	// P extends NodeConfigurator<PT, ?, E, ?, R, ?>, R> {
	//
	// private Producer<TreeReader<R, E>, Nothing> builder;
	// private P parent;
	//
	// private ParentLastNodeConfigurator(P parent, Producer<TreeReader<R, E>,
	// Nothing> builder) {
	// this.parent = parent;
	// this.builder = builder;
	// }
	//
	// public <X> ParentFirstNodeConfigurator<X, P, E, T, R> open(String tagName,
	// Producer<X, E> type) {
	// return null;
	// }
	//
	// public <X> ParentFirstNodeConfigurator<X, P, E, T, R> open(String tagName,
	// Function<StringMap, X, E> type) {
	// return null;
	// }
	//
	// public ParentFirstNodeConfigurator<T, P, E, T, R> open(String tagName) {
	// return null;
	// }
	//
	// public ParentFirstNodeConfigurator<T, P, E, PT, R> create(Consumer<PT, E>
	// function) {
	// return null;
	// }
	//
	// public ParentFirstNodeConfigurator<T, P, E, PT, R> create(BiConsumer<PT,
	// StringMap, E> function) {
	// return null;
	// }
	//
	// public TreeReader<R, E> build() {
	// return builder.produce();
	// }
	//
	// public P close(BiConsumer<PT, T, E> collector) {
	// return parent;
	// }
	//
	// }

	public static void main(String[] args) throws UnsupportedEncodingException, XMLStreamException, FactoryConfigurationError {
		TreeReader<Package, Nothing> first = TreeReader.<Package, Nothing>builder()
				.open("package", () -> new Package("name"))
					.open("class", () -> new Clas("klz"))
						.open("method", () -> new Integer(3))
						.close((p, c) -> p.add(c))
					.close((p, c) -> p.add(c))
				.close();

		TreeReader<Package, Nothing> other = TreeReader.<Package, Nothing>builder()
				.open("package", () -> new Package("name"))
					.open("class", () -> new Clas("klz"))
				        .open("method", () -> new Integer(3))
				        .close((p, c) -> p.add(c))
				    .close((p, c) -> p.add(c))
				    .open(first)
				    .close((p, c) -> p.add(c))
				.close();
		String test = "<package><class><method></method></class></package>";
		InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8.name()));
		XMLStreamReader reader = javax.xml.stream.XMLInputFactory.newInstance().createXMLStreamReader(stream);
		
	}

	private static class Package {
		private String name;

		public Package(String name) {
			this.name = name;
		}

		public void add(Clas klas) {

		}

		public void add(Package klas) {

		}
	}

	private static class Clas {
		private String name;

		private Clas(String name) {
			this.name = name;
		}

		public void add(int x) {

		}
	}

}
