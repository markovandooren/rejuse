package org.aikodi.rejuse.tree.walker;

import org.aikodi.rejuse.tree.TreeStructure;

/**
 * A tree walker that applies two walkers in sequence to a tree node.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the elements in the tree.
 * @param <E> The type of exceptions that can be thrown during the traversal.
 */
public class Sequence<T,E extends Exception> implements TreeWalker<T,E> {
	
	/**
	 * Create a new sequence from the two given tree walkers.
	 * 
	 * @param first The walker that is applied first.
	 *              The walker cannot be null.
	 * @param second The walker that is applied second.
	 *               The walker cannot be null.
	 */
	public Sequence(TreeWalker<T, ? extends E> first, TreeWalker<T, ? extends E> second) {
		_first = first;
		_second = second;
	}
	
	/**
	 * A constructor for subclasses that allows the walkers
	 * to be null during construction such that recursive
	 * structures can be created.
	 */
	protected Sequence() {
	}

	/**
	 * The walker that is applied first to a node.
	 * The walker is not null.
	 */
	private TreeWalker<T, ? extends E> _first;
	
	/**
	 * The walker that is applied first to a node.
	 * The walker is not null.
	 */
	private TreeWalker<T, ? extends E> _second;
	
	/**
	 * @return The walker that is applied first to a node.
	 *         The result is not null
	 */
	public TreeWalker<T, ? extends E> first() {
	  return _first;
	}

	/**
	 * Set the first walker.
	 * 
	 * @param first The walker that will be applied first to a node.
	 */
	protected void setFirst(TreeWalker<T, ? extends E> first) {
		if (first == null) {
			throw new IllegalArgumentException("The first walker cannot be null.");
		}
		_first = first;
	}

  /**
   * @return The walker that is applied second to a node.
	 *         The result is not null
   */
	public TreeWalker<T, ? extends E> second() {
		return _second;
	}

  /**
   * Set the second walker.
   * 
   * @param second The walker that will be applied second to a node.
   */
	protected void setSecond(TreeWalker<T, ? extends E> second) {
		if (second == null) {
			throw new IllegalArgumentException("The second walker cannot be null.");
		}
		_second = second;
	}

	/**
	 * The following actions are performed in order:
	 * <ol>
	 *   <li>The first walker enters the element.</li>
	 *   <li>The first walker traverses the element.</li>
	 *   <li>The first walker exists the element.</li>
	 *   <li>The second walker enters the element.</li>
	 *   <li>The second walker traverses the element.</li>
	 *   <li></li>
	 * </ol>
	 * 
	 * {@inheritDoc}
	 * @throws N 
	 */
	@Override
	public <X extends T, N extends Exception> void traverse(TreeStructure<X, N> element) throws E, N {
		first().enter(element);
		first().traverse(element);
		second().enter(element);
		second().traverse(element);
		second().exit(element);
		first().exit(element);
	}
	
}
