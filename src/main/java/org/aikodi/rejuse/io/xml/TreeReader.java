package org.aikodi.rejuse.io.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
import static org.aikodi.contract.Contract.require;

public abstract class TreeReader<C, E extends Exception> {


	public abstract C read(XMLStreamReader reader) throws E, XMLStreamException;
	
	private static class GenericTreeReader<TYPE, PARENTTYPE, E extends Exception> {
		
		private Map<String, GenericTreeReader<?, ? super TYPE, ? extends E>> childReaders;
		
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
		
		

		public GenericTreeReader(String tagName, 
				Producer<TYPE, E> defaultConstructor,
				Function<TreeNode, ? extends TYPE, ? extends E> constructorWithAttributes, 
				BiFunction<PARENTTYPE, TreeNode, ? extends TYPE, ? extends E> constructorWithParent,
				BiConsumer<TYPE, String, E> textProcessor,
				BiConsumer<PARENTTYPE, TYPE, E> closerWithParent,
				boolean descend, List<GenericTreeReader<?, ? super TYPE, ? extends E>> childReaders) {
			super();
			this.childReaders = new HashMap<>();
			childReaders.forEach(reader -> this.childReaders.put(reader.tagName(), reader));
			this.descend = descend;
			this.tagName = tagName;
			this.defaultConstructor = defaultConstructor;
			this.constructorWithParent = constructorWithParent;
			this.constructorWithAttributes = constructorWithAttributes;
			this.textProcessor = textProcessor;
			this.closerWithParent = closerWithParent;
		}


//		/**
//		 * Create a generic tree reader the create the node before adding it to the parent.
//		 * 
//		 * @param configurator The configuration of the reader.
//		 *                     The configuration cannot be null.
//		 * @param descend Determines if this reader is a leaf reader or not.
//		 *                If true, the reader will process descendants.
//		 *                If false, the reader will not process descendants.
//		 */
//		GenericTreeReader(NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E, ?> configurator, boolean descend) {
//			requireNotNull(configurator);
//			
//			tagName = configurator.tagName();
//			this.descend = descend;
//			
//			defaultConstructor = configurator.defaultConstructor();
//			constructorWithAttributes = configurator.constructorWithNode();
//			closerWithParent = configurator.closer();
//		}
//		
//		
//		GenericTreeReader(RootNodeFirstConfigurator<TYPE, E> configurator, boolean descend) {
//			requireNotNull(configurator);
//			
//			tagName = configurator.tagName();
//			this.descend = descend;
//			defaultConstructor = configurator.defaultConstructor();
//			constructorWithAttributes = configurator.constructorWithNode();
//		}

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

		protected void read(XMLStreamReader reader) throws E, XMLStreamException {
			
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
			readChildren(reader, currentElement);

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
		protected void readChildren(XMLStreamReader reader, TYPE currentElement)
				throws XMLStreamException, E {

			int nestingCounter = 0;
			while (reader.hasNext()) {
				int eventCode = reader.next();
				switch (eventCode) {
				case XMLStreamConstants.START_ELEMENT:
					if (descend == false) {
						nestingCounter++;
					} else if (nestingCounter == 0) {
							GenericTreeReader<?, ? super TYPE, ? extends E> childReader = childReaders.get(reader.getLocalName());
							if (childReader != null) {
								childReader.read(reader, currentElement);
							} else {
								nestingCounter++;
							}
					} else {
						// Here go the descendant readers.
						// But let's finish the strict parsers first.
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					// Any text that we encounter when the nesting is zero belongs to the current element.
					if (nestingCounter == 0) {
						String text = reader.getText();
						if (textProcessor != null) {
							textProcessor.accept(currentElement, text);
						}
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (nestingCounter != 0) {
						// If the nesting counter is not zero, it means that we closed a tag
						// for which no reader was defined. We simply decrease the counter
						// to go backup outwards in the nesting and continue. 
						nestingCounter--;
					} else {
						// If the nesting counter is zero, we have finished parsing the current element.
						// A child is parsed by recursive call. That call will process the closing
						// tag of that child. This means that the next closing tag for which the nesting
						// count is zero is the closing tag of the current element.
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

	public static class Builder<TYPE, E extends Exception> implements Consumer<GenericTreeReader<?, List<? super TYPE>, ? extends E>, Nothing> {
		
		private List<GenericTreeReader<?, ? super List<TYPE>, ? extends E>> _childReaders = new ArrayList<>();
		
		public RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>> open(String tagName, Producer<TYPE, E> producer) {
			return new RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>>(this, tagName, producer);
		}
		
		public RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>> open(String tagName, Function<TreeNode, TYPE, E> producer) {
			return new RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>>(this, tagName, producer);
		}
		

		@Override
		public void accept(GenericTreeReader<?, List<? super TYPE>, ? extends E> reader) throws Nothing {
			_childReaders.add(reader);
		}
		
		public TreeReader<TYPE, E> build() {
			GenericTreeReader<List<TYPE>, Object, E> internalReader = new GenericTreeReader<>(null, null, null, null, null, (p,c) -> {}, true, _childReaders);
			return new TreeReader<TYPE, E>() {

				@Override
				public TYPE read(XMLStreamReader reader) throws E, XMLStreamException {
					List<TYPE> result = new ArrayList<TYPE>();
					internalReader.readChildren(reader, result);
					if (! result.isEmpty()) {
					    return result.get(0);
					} else {
						return null;
					}
				}
				
			};
		}
	}

	
	
	public static abstract class NodeConfigurator<TYPE, E extends Exception, SELF extends NodeConfigurator<TYPE, E, SELF>>
			implements Consumer<GenericTreeReader<?, ? super TYPE, ? extends E>, Nothing> {

		private String _tagName;
		private List<GenericTreeReader<?, ? super TYPE, ? extends E>> _childReaders = new ArrayList<>();

		protected NodeConfigurator(String tagName) {
			this._tagName = tagName;
		}

		protected String tagName() {
			return _tagName;
		}

		@Override
		public void accept(GenericTreeReader<?, ? super TYPE, ? extends E> reader) {
			Contract.requireNotNull(reader);
			_childReaders.add(reader);
		}

		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> open(TreeReader<X, E> type) {
			return null;
		}

		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> open(String tagName, Producer<X, E> defaultConstructor) {
			return new NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF>((SELF) this, tagName, defaultConstructor);
		}

		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> open(String tagName,
				Function<TreeNode, X, E> constructorWithNode) {
			return new NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF>((SELF) this, tagName, constructorWithNode);
		}
		
		protected  List<GenericTreeReader<?, ? super TYPE, ? extends E>> childReaders() {
			return new ArrayList<>(_childReaders);
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

	public static class RootNodeFirstConfigurator<TYPE, E extends Exception, P extends Consumer<GenericTreeReader<?, List<? super TYPE>, ? extends E>, Nothing>> // implements Consumer<TreeReader<?, E>,
			extends NodeFirstConfigurator<TYPE, List<? super TYPE>, E, P, RootNodeFirstConfigurator<TYPE, E, P>> {

		private Producer<TYPE, E> defaultConstructor;
		
		private Function<TreeNode, TYPE, E> constructorWithNode;

		protected RootNodeFirstConfigurator(P parent, String tagName, Producer<TYPE, E> defaultConstructor) {
			super(parent, tagName);
			requireNotNull(defaultConstructor);
			this.defaultConstructor = defaultConstructor;
		}

		protected RootNodeFirstConfigurator(P parent, String tagName, Function<TreeNode, TYPE, E> constructorWithNode) {
			super(parent, tagName);
			requireNotNull(constructorWithNode);
			this.constructorWithNode = constructorWithNode;
		}

		
		public Producer<TYPE, E> defaultConstructor() {
			return defaultConstructor;
		}

		public Function<TreeNode, TYPE, E> constructorWithNode() {
			return constructorWithNode;
		}

		public P close() {
			parent().accept(new GenericTreeReader<TYPE, List<? super TYPE>, E>(tagName(), defaultConstructor, constructorWithNode, null, null, (p,c) -> {p.add(c);}, true, childReaders()));
			return parent();
		}

	}

	public static class NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E extends Exception, P extends Consumer<GenericTreeReader<?, ? super PARENTTYPE, ? extends E>, Nothing>>
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
			List<GenericTreeReader<?, ? super TYPE, ? extends E>> childReaders = childReaders();
			parent().accept(new GenericTreeReader<TYPE, PARENTTYPE, E>(tagName(), defaultConstructor, constructorWithNode, null, null, closer(), true, childReaders)); //childReaders()
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

	public static class NodeFirstAddOnOpenConfigurator<TYPE, PARENTTYPE, E extends Exception, PARENT extends Consumer<GenericTreeReader<?, ? super PARENTTYPE, ? extends E>, Nothing>>
			extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, PARENT, NodeFirstAddOnOpenConfigurator<TYPE, PARENTTYPE, E, PARENT>> {

		private NodeFirstAddOnOpenConfigurator(PARENT parent, String tagName) {
			super(parent, tagName);
		}

		public PARENT close() {
			return parent();
		}

	}

//	public static class ActionNodeConfigurator<T, PT, E extends Exception, P extends NodeFirstConfigurator<PT, ?, E, ?, ?>>
//			extends NodeFirstConfigurator<T, PT, E, P, NodeFirstAddOnCloseConfigurator<T, PT, E, P>> {
//
//		private ActionNodeConfigurator(P parent, String tagName) {
//			super(parent, tagName);
//		}
//
//		public P close() {
//			return parent();
//		}
//
//	}

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
				.open("package", () -> new Package("Kuzo 4"))
					.open("class", (n) -> new Clas(n.attribute("name")))
//						.open("method", () -> new Integer(3))
//						.close((p, c) -> p.add(c))
					.close((p, c) -> p.add(c))
				.close()
				.build();

//		TreeReader<Package, Nothing> other = TreeReader.<Package, Nothing>builder()
//				.open("package", () -> new Package("name"))
//					.open("class", () -> new Clas("klz"))
//				        .open("method", () -> new Integer(3))
//				        .close((p, c) -> p.add(c))
//				    .close((p, c) -> p.add(c))
//				    .open(first)
//				    .close((p, c) -> p.add(c))
//				.close()
//				.build();
		
		String test = "<package><class name=\"sjinkie\"><method></method></class></package>";
		InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8.name()));
		XMLStreamReader reader = javax.xml.stream.XMLInputFactory.newInstance().createXMLStreamReader(stream);
		Package read = first.read(reader);
		System.out.println(read);
	}

	private static class Package {
		private String name;
		private List<Clas> classes = new ArrayList<>();
		private List<Package> packages = new ArrayList<>();

		public Package(String name) {
			this.name = name;
		}

		public void add(Clas klas) {
			classes.add(klas);
		}

		public void add(Package klas) {
			packages.add(klas);
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append(name);
			classes.forEach(c -> result.append(c));
			packages.forEach(p -> result.append(p));
			return result.toString();
		}
	}

	private static class Clas {
		private String name;

		private Clas(String name) {
			this.name = name;
		}

		public void add(int x) {

		}
		
		@Override
		public String toString() {
			return name;
		}
	}

}
