package org.aikodi.rejuse.tree.walker;

/**
 * A walker for depth first tree traversal.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the elements in the tree.
 * @param <E> The type of exceptions that can be thrown by this walker.
 */
public class DepthFirst<T, E extends Exception> extends Sequence<T,E> {

  /**
   * Create a new walker that applies the given walker to the tree
   * using a depth first strategy.
   * 
   * The first element of this walker (which is a sequence) is a
   * {@link ChildWalker} of this walker. The second element is the given walker.
   *  
   * @param walker The walker to be executed for every node.
   */
	public DepthFirst(TreeWalker<T, ? extends E> walker) {
		setFirst(new ChildWalker<T,E>(this));
		setSecond(walker);
	}
	
}
