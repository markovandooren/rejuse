package org.aikodi.rejuse.io.xml;

import static org.aikodi.contract.Contract.require;
import static org.aikodi.contract.Contract.requireNotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.aikodi.contract.Contract;
import org.aikodi.rejuse.action.Action;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.data.path.Path;
import org.aikodi.rejuse.function.BiConsumer;
import org.aikodi.rejuse.function.BiFunction;
import org.aikodi.rejuse.function.Consumer;
import org.aikodi.rejuse.function.Function;
import org.aikodi.rejuse.function.Producer;
import org.aikodi.rejuse.map.StringMap;
import org.aikodi.rejuse.predicate.Predicate;

/**
 * A class of tree readers that can be constructor using a builder pattern.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the element that is parsed by the tree reader.
 * @param <E> The type of checked exceptions that can be thrown by the construction code.
 *            Use {@link Nothing} if the custom construction code cannot throw checked exceptions.
 */
public abstract class TreeReader<T, E extends Exception> {

	/**
	 * Read the element defined by the input in the given reader.
	 * 
	 * @param reader The reader from which the element will be parsed.
	 *               The reader cannot be null.
	 * @return A non-null element if the input contained a definition according
	 *         to the configuration of the reader. Null if no such element could be parsed.
	 *         
	 * @throws E An exception occurred in the custom construction code.
	 * @throws XMLStreamException An exception occurred while parsing the XML data.
	 */
	public abstract T read(XMLStreamReader reader) throws E, XMLStreamException;
	
	/**
	 * Read the given string.
	 * 
	 * @param input The string to read.
	 *              The string cannot be null.
	 *              The string must be valid XML.
	 *              
	 * @return The result of reading the input.
	 * 
	 * @throws IllegalArgumentException The input is null.
	 * @throws E An exception occurred when constructing an object of the output.
	 * @throws XMLStreamException The XML is invalid.
	 */
	public T read(String input) throws E, XMLStreamException {
		requireNotNull(input);
		try {
			InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8.name()));
			XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
			return read(reader);
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		} catch (XMLStreamException e) {
			throw new Error(e);
		} catch (FactoryConfigurationError e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Read the top element of the given XML reader as a child.
	 * 
	 * @param reader The reader to read from.
	 * @param closer The consumer of the produced element.
	 * 
	 * @throws E An exception occurred when constructing an object of the input.
	 * @throws XMLStreamException The XML in the reader is invalid.
	 */
	protected abstract void readAsChild(XMLStreamReader reader, Consumer<? super T, Nothing> closer) throws E, XMLStreamException;
	
	/**
	 * Return the object that determines if a path in the tree is selected or not.
	 * 
	 * @return The matcher is not null.
	 */
	protected abstract PathMatcher<TreeNode> matcher();

	/**
	 * A reader that transforms the output of another reader.
	 *
	 * @param <T> The output type of the transformation.
	 * @param <X> The input type of the transformation.
	 * @param <E> The type of exception that can be thrown. Use {@link Nothing} if no checked exception can occur.
	 */
	private static class TransformingReader<T, X, E extends Exception> extends TreeReader<T, E> {

		/**
		 * The transformation to apply.
		 * The transformation cannot be null.
		 * The transformation cannot throw exceptions.
		 */
		private Function<X, T, Nothing> _transformation;
		
		/**
		 * The reader that reads the input and provides the object to be transformed.
		 * The reader cannot be null.
		 */
		private TreeReader<X, E> _wrappedReader;
		
		/**
		 * Create a new transforming reader that uses the given reader to reader the tree, and then
		 * applies the given function to produce the final result.
		 * 
		 * @param wrappedReader The reader that reads the input.
		 *                      The reader cannot be null.
		 * @param function A function that transforms
		 *                 The function cannot be null.
		 */
		public TransformingReader(TreeReader<X, E> wrappedReader, Function<X, T, Nothing> function) {
			requireNotNull(wrappedReader);
			requireNotNull(function);
			
			_wrappedReader = wrappedReader;
			_transformation = function;
		}
		
		/**
		 * Read the given reader using the wrapped reader and apply the
		 * conversion function to produce the final result.
		 */
		@Override
		public T read(XMLStreamReader reader) throws E, XMLStreamException {
			return _transformation.apply(_wrappedReader.read(reader));
		}

		@Override
		protected void readAsChild(XMLStreamReader reader, Consumer<? super T, Nothing> closer) throws E, XMLStreamException {
			_wrappedReader.readAsChild(reader, internal -> closer.accept(_transformation.apply(internal)));
		}

		/**
		 * @return The matcher of the wrapped reader.
		 */
		@Override
		protected PathMatcher<TreeNode> matcher() {
			return _wrappedReader.matcher();
		}
		
	}
	
	public <N> TreeReader<N, E> map(Function<T, N, Nothing> function) {
		return new TransformingReader<N, T, E>(this, function);
	}
	
	static abstract class InternalTreeReader<TYPE, PARENTTYPE, E extends Exception> {
		
		protected abstract void readAsChild(XMLStreamReader reader, Consumer<? super TYPE, E> closer) throws E, XMLStreamException;

		protected abstract void read(XMLStreamReader reader, PARENTTYPE parent, Consumer<? super TYPE, E> closer) throws E, XMLStreamException;

		protected abstract PathMatcher<TreeNode> matcher();
	}

	interface TreeConsumer<T, E extends Exception> {

		void addChildReader(InternalTreeReader<?, ? super T, ? extends E> reader);

	}

	static class GlobMatcher<T> implements PathMatcher<T> {

		@Override
		public boolean matches(List<T> path, int start, int stop) {
			return true;
		}

		@Override
		public boolean canMatchExtensionOf(List<T> path) {
			return true;
		}
		
		@Override
		public String toString() {
			return "**";
		}
	}
	
	private static class StarPredicate<T> implements PathMatcher<T> {

		@Override
		public boolean matches(List<T> path, int start, int stop) {
			return (stop - start) == 1;
		}

		@Override
		public boolean canMatchExtensionOf(List<T> path) {
			return path.size() == 0;
		}
		
		@Override
		public String toString() {
			return "*";
		}
	}
	
 
	/**
	 * A matcher that matches a sequences of paths matcher by two given matchers.
	 */
	static class Sequence<T> implements PathMatcher<T> {
		private PathMatcher<T> _first;
		private PathMatcher<T> _second;
		public Sequence(PathMatcher<T> first, PathMatcher<T> second) {
			requireNotNull(first);
			requireNotNull(second);
			
			_first = first;
			_second = second;
		}
		
		@Override
		public boolean matches(List<T> path, int startInclusive, int stopExclusive) {
			boolean result = false;
			if (path != null) {
				for (int endOfFirstExclusive = startInclusive; ! result && endOfFirstExclusive < stopExclusive; endOfFirstExclusive++) {
					result = _first.matches(path, startInclusive, endOfFirstExclusive) &&
							_second.matches(path, endOfFirstExclusive, stopExclusive);
				}
			}
			return result;
		}
		
		@Override
		public boolean canMatchExtensionOf(List<T> path) {
			return true;
		}
		
		@Override
		public String toString() {
			return _first + "/" + _second;
		}
		
	}

	/**
	 * An internal tree reader.
	 * 
	 * @author Marko van Dooren
	 *
	 * @param <TYPE>
	 * @param <PARENTTYPE>
	 * @param <E>
	 */
	private static class GenericTreeReader<TYPE, PARENTTYPE, E extends Exception> 
			extends InternalTreeReader<TYPE, PARENTTYPE, E> {
		
		private List<InternalTreeReader<?, ? super TYPE, ? extends E>> descendantReaders;

		private PathMatcher<TreeNode> matcher;

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
		
		private BiConsumer<PARENTTYPE, TYPE, E> _closerWithParent;
		
		

		private GenericTreeReader(PathMatcher<TreeNode> matcher,
								 Producer<TYPE, E> defaultConstructor,
								 Function<TreeNode, ? extends TYPE, ? extends E> constructorWithAttributes,
								 BiFunction<PARENTTYPE, TreeNode, ? extends TYPE, ? extends E> constructorWithParent,
								 BiConsumer<TYPE, String, E> textProcessor,
								 BiConsumer<PARENTTYPE, TYPE, E> closerWithParent,
								 List<InternalTreeReader<?, ? super TYPE, ? extends E>> descendantReaders
		) {
			super();
			this.descendantReaders = new ArrayList<>(descendantReaders);
			this.matcher = matcher;
			this.defaultConstructor = defaultConstructor;
			this.constructorWithParent = constructorWithParent;
			this.constructorWithAttributes = constructorWithAttributes;
			this.textProcessor = textProcessor;
			this._closerWithParent = closerWithParent;
		}

		protected PathMatcher<TreeNode> matcher() {
			return matcher;
		}

		protected TreeNode treeNode(XMLStreamReader reader) {
			String tagName = reader.getLocalName();
			int attributeCount = reader.getAttributeCount();
			StringMap.Builder builder = StringMap.builder();
			for (int i = 0; i < attributeCount; i++) {
				builder.with(reader.getAttributeName(i).getLocalPart()).as(reader.getAttributeValue(i));
			}
			return new TreeNode(tagName, builder.build());
		}

		@Override
		protected void readAsChild(XMLStreamReader reader, Consumer<? super TYPE, E> closer) throws E, XMLStreamException {
			read(reader, null, closer);
		}
		
		@Override
		protected void read(XMLStreamReader reader, PARENTTYPE parent, Consumer<? super TYPE, E> closer) throws E, XMLStreamException {
			TYPE currentElement;
			TreeNode node = treeNode(reader); //treeNode(reader)
			if (defaultConstructor != null) {
				currentElement = defaultConstructor.produce();
			} else if (constructorWithParent != null) {
				requireNotNull(parent, "The constructor for " + node.name() + " expects a parent object, but received null.");
				currentElement = constructorWithParent.apply(parent, node);
			} else if (constructorWithAttributes != null) {
				currentElement = constructorWithAttributes.apply(node);
			} else {
				throw new IllegalStateException("No element could be constructed.");
			}

			// Content
			readChildren(reader, currentElement);

			// Close
			if (closer != null && currentElement != null) {
				closer.accept(currentElement);
			} else if (_closerWithParent != null && currentElement != null) {
				_closerWithParent.accept(parent, currentElement);
			}
		}
		
		private InternalTreeReader<?, ? super TYPE, ? extends E> descendantReader(List<TreeNode> path) {
			return descendantReaders.stream().filter(r -> r.matcher().matches(path)).findFirst().orElse(null);
		}

		/**
		 * Read the children of the given element.
		 * 
		 * @param reader The reader from which the children are read.
		 *               The reader is not null.
		 *               The reader is set directly after the starting tag
		 *               of the current element.
		 * @throws XMLStreamException
		 * @throws E
		 */
		protected void readChildren(XMLStreamReader reader, TYPE currentElement)
				throws XMLStreamException, E {
			List<TreeNode> path = new ArrayList<>();
			int nestingCounter = 0;
			while (reader.hasNext()) {
				int eventCode = reader.next();
				switch (eventCode) {
				case XMLStreamConstants.START_ELEMENT:
					TreeNode child = treeNode(reader);
					
					// Push the new element.
					path.add(child);
					InternalTreeReader<?, ? super TYPE, ? extends E> descendantReader = descendantReader(path);
					if (descendantReader != null) {
						descendantReader.read(reader, currentElement, null);
						// Pop after reading.
						// For the list, we always add the element.
						// To restore the invariant nestingCounter == path.size(), we need to pop it again.
						path.remove(path.size() - 1);
					} else {
						nestingCounter++;
					}
					//					}
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
					// Pop the last element.
					if (nestingCounter != 0) {
						path.remove(path.size() - 1);
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

	public static class RootConstructorSelector<TYPE, E extends Exception> {
		
		private Builder<TYPE,E> _self;
		private PathMatcher<TreeNode> _matcher;
		private Consumer<TreeNode, E> _consumer;
		
		public RootConstructorSelector(PathMatcher<TreeNode> matcher, Builder<TYPE,E> self) {
			requireNotNull(matcher);
			requireNotNull(self);

			_self = self;
			_matcher = matcher;
		}
		
		public RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>> construct(Producer<TYPE, E> defaultConstructor) {
			return new RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>>(_self, _matcher, defaultConstructor);
		}

		public RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>> construct(Function<TreeNode, TYPE, E> constructorWithNode) {
			return new RootNodeFirstConfigurator<TYPE, E, Builder<TYPE,E>>(_self, _matcher, constructorWithNode);
		}
		
//		public Void action(Action<E> action) {
//			action(n -> action.apply());
//		}
		
		public void action(Consumer<TreeNode, E> action) {
			requireNotNull(action);
			require(_consumer == null, "An action can be set only for element with matcher: " + _matcher);
			
			_consumer = action;
		}
	}
	
	public static class Builder<TYPE, E extends Exception> implements Consumer<InternalTreeReader<?, ? super List<? super TYPE>, ? extends E>, E> {
		
		private List<InternalTreeReader<?, ? super List<? super TYPE>, ? extends E>> _descendantReaders = new ArrayList<>();

		/**
		 * Select the child of the tree root. If the reader will be used
		 * standalone, the tree root is empty, and the child is the top-most node
		 * in the tree.
		 * 
		 * @param tagName The name of the element to match.
		 *                The name cannot be empty or null.
		 * @return
		 */
		public RootConstructorSelector<TYPE, E> child(String tagName) {
			return new RootConstructorSelector<>(new ChildMatcher(tagName), this);
		}
		
		public RootConstructorSelector<TYPE, E> descendant(String tagName) {
			return new RootConstructorSelector<>(new Sequence<>(new GlobMatcher<>(),new ChildMatcher(tagName)), this);
		}
		
		@Override
		public void accept(InternalTreeReader<?, ? super List<? super TYPE>, ? extends E> reader) throws Nothing {
			_descendantReaders.add(reader);
		}
		
		public TreeReader<TYPE, E> build() {
			GenericTreeReader<? super List<? super TYPE>, Object, E> internalReader = new GenericTreeReader<>(null, null, null, null, null, (p,c) -> {}, _descendantReaders);
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
				
				@Override
				protected PathMatcher<TreeNode> matcher() {
					return _descendantReaders.get(0).matcher();
				}

				@Override
				protected void readAsChild(XMLStreamReader reader, Consumer<? super TYPE, Nothing> closer) throws E, XMLStreamException {
					_descendantReaders.get(0).readAsChild(reader, l -> closer.accept((TYPE)l));
				}
				
			};
		}
	}

	public static class ConstructorSelector<TYPE, E extends Exception, SELF extends ReaderBuilder<TYPE, E, SELF>> {
		
		private SELF _self;
		private PathMatcher<TreeNode> _matcher;
		
		public ConstructorSelector(PathMatcher<TreeNode> matcher, SELF self) {
			requireNotNull(matcher);
			requireNotNull(self);

			_self = self;
			_matcher = matcher;
		}
		
		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> construct(Producer<X, E> defaultConstructor) {
			return new NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF>(_self, _matcher, defaultConstructor);
		}

		public <X> NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF> construct(Function<TreeNode, X, E> constructorWithNode) {
			return new NodeFirstAddOnCloseConfigurator<X, TYPE, E, SELF>(_self, _matcher, constructorWithNode);
		}
		
//		public ActionConfigurator<E, SELF> action(Consumer<TreeNode, E> action) {
////			return new ActionConfigurator<>(_self, _matcher, action);
//			throw new Error();
//		}
	}
	
	public static abstract class NodeFirstConfigurator<TYPE, PARENTTYPE, E extends Exception, PARENTCONFIGURATOR, SELF extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, PARENTCONFIGURATOR, SELF>>
			extends ReaderBuilder<TYPE, E, SELF> {

		private PARENTCONFIGURATOR parent;
		
		private NodeFirstConfigurator(PARENTCONFIGURATOR parent, PathMatcher<TreeNode> matcher) {
			super(matcher);
			this.parent = parent;
		}
		
		public PARENTCONFIGURATOR parent() {
			return parent;
		}
		
	}
	
	public static class ActionConfigurator<E extends Exception, PARENT extends Consumer<InternalTreeReader<?, ?, ? extends E>, E>> {
		
		private PARENT _parent;
		private Consumer<TreeNode, E> _action;
		private PathMatcher<TreeNode> _matcher;
		
		public ActionConfigurator(PARENT parent, PathMatcher<TreeNode> matcher, Consumer<TreeNode, E> action) {
			requireNotNull(parent);
			requireNotNull(matcher);
			
			_parent = parent;
			_action = action;
			_matcher = matcher;
		}
		
		public PARENT close() throws E {
			Function<TreeNode, ? extends Object, ? extends E> constructorWithNode = treenode -> {_action.accept(treenode); return new Object();};
			ArrayList<InternalTreeReader<?, ? super Object, ? extends E>> descendantReaders = new ArrayList<>();
			_parent.accept(new GenericTreeReader<Object, List<Object>, E>(_matcher, null, constructorWithNode, null, null, (p,c)->{}, descendantReaders));
			return _parent;
		}
	}

	public static class RootNodeFirstConfigurator<TYPE, E extends Exception, P extends Consumer<InternalTreeReader<?, ? super List<? super TYPE>, ? extends E>, E>>
			extends NodeFirstConfigurator<TYPE, List<? super TYPE>, E, P, RootNodeFirstConfigurator<TYPE, E, P>> {

		private Producer<TYPE, E> defaultConstructor;
		
		private Function<TreeNode, TYPE, E> constructorWithNode;

		protected RootNodeFirstConfigurator(P parent, PathMatcher<TreeNode> matcher, Producer<TYPE, E> defaultConstructor) {
			super(parent, matcher);
			requireNotNull(defaultConstructor);
			this.defaultConstructor = defaultConstructor;
		}

		protected RootNodeFirstConfigurator(P parent, PathMatcher<TreeNode> matcher, Function<TreeNode, TYPE, E> constructorWithNode) {
			super(parent, matcher);
			requireNotNull(constructorWithNode);
			this.constructorWithNode = constructorWithNode;
		}

		
		public Producer<TYPE, E> defaultConstructor() {
			return defaultConstructor;
		}

		public Function<TreeNode, TYPE, E> constructorWithNode() {
			return constructorWithNode;
		}

		public P close() throws E {
			parent().accept(new GenericTreeReader<TYPE, List<? super TYPE>, E>(matcher(), defaultConstructor, constructorWithNode, null, textProcessor(), (p,c) -> {p.add(c);}, descendantReaders()));
			return parent();
		}
		
	}

	public static class PredefinedAddOnCloseConfigurator<TYPE, PARENTTYPE, E extends Exception, P extends TreeConsumer<PARENTTYPE, E>>
		extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, P, NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E, P>> {

		private BiConsumer<PARENTTYPE, TYPE, E> _closer;
		private TreeReader<TYPE, E> _reader;
	
		PredefinedAddOnCloseConfigurator(P parent, TreeReader<TYPE, E> reader) {
			super(parent, reader.matcher());
			requireNotNull(reader);
			
			this._reader = reader;
		}
		
		public P close(BiConsumer<PARENTTYPE, TYPE, E> closer) {
			requireNotNull(closer);
			_closer = closer;
			parent().addChildReader(new InternalTreeReader<TYPE, PARENTTYPE, E>() {

				@Override
				protected void read(XMLStreamReader reader, PARENTTYPE parent, Consumer<? super TYPE, E> closer) throws E, XMLStreamException {
					List<TYPE> element = new ArrayList<>();
					_reader.readAsChild(reader, e -> element.add(e));
					_closer.accept(parent, element.get(0));
				}

				@Override
				protected PathMatcher<TreeNode> matcher() {
					return _reader.matcher();
				}

				@Override
				protected void readAsChild(XMLStreamReader reader, Consumer<? super TYPE, E> closer)
						throws E, XMLStreamException {
					throw new Error();
				}
				
			});
			return parent();
		}	
	}
	public static class NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E extends Exception, PARENT extends TreeConsumer<PARENTTYPE, E>>
			extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, PARENT, NodeFirstAddOnCloseConfigurator<TYPE, PARENTTYPE, E, PARENT>> {

		private BiConsumer<PARENTTYPE, TYPE, E> _closer;
		
		private Producer<TYPE, E> defaultConstructor;
		
		private Function<TreeNode, TYPE, E> constructorWithNode;
		
		private NodeFirstAddOnCloseConfigurator(PARENT parent, PathMatcher<TreeNode> matcher, Producer<TYPE, E> defaultConstructor) {
			super(parent, matcher);
			requireNotNull(defaultConstructor);
			
			this.defaultConstructor = defaultConstructor;
		}

		private NodeFirstAddOnCloseConfigurator(PARENT parent, PathMatcher<TreeNode> matcher, Function<TreeNode, TYPE, E> constructorWithNode) {
			super(parent, matcher);
			requireNotNull(constructorWithNode);
			
			this.constructorWithNode = constructorWithNode;
		}

		public PARENT close(BiConsumer<PARENTTYPE, TYPE, E> closer) {
			requireNotNull(closer);
			_closer = closer;
			parent().addChildReader(new GenericTreeReader<TYPE, PARENTTYPE, E>(matcher(), defaultConstructor, constructorWithNode, null, textProcessor(), closer(), descendantReaders()));
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

	public static class NodeFirstAddOnOpenConfigurator<TYPE, PARENTTYPE, E extends Exception, PARENT extends TreeConsumer<PARENTTYPE, E>>
			extends NodeFirstConfigurator<TYPE, PARENTTYPE, E, PARENT, NodeFirstAddOnOpenConfigurator<TYPE, PARENTTYPE, E, PARENT>> {

		private NodeFirstAddOnOpenConfigurator(PARENT parent, PathMatcher<TreeNode> matcher) {
			super(parent, matcher);
		}

		public PARENT close() {
			return parent();
		}

	}

}
