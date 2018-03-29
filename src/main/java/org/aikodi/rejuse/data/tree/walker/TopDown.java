package org.aikodi.rejuse.data.tree.walker;

/**
 * A walker for top down tree traversal.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the elements in the tree.
 * @param <E> The type of exceptions that can be thrown by this walker.
 */
public class TopDown<T,E extends Exception> extends Sequence<T,E> {

	/**
	 * Create a new top down walker for the given walker.
	 * 
	 * The first walker will be set to the given walker.
	 * The second walker will be a {@link ChildWalker} of this walker.
	 * 
	 * @param walker The walker that will be applied top down
	 *               to traversed trees.
	 *               The walker cannot be null.
	 */
	public TopDown(TreeWalker<T, ? extends E> walker) {
		setFirst(walker);
		setSecond(new ChildWalker<T,E>(this));
	}

}
