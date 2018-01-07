package org.aikodi.rejuse.tree.walker;

import org.aikodi.rejuse.tree.TreeStructure;

/**
 * A walker that applies the given walker to the children of a tree node.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the elements in the trees.
 * @param <E> The type of exceptions that can be thrown by this walker.
 */
public class ChildWalker<T, E extends Exception> implements TreeWalker<T, E> {

  /**
   * The walker that will process the children of the top
   * of the traversed trees.
   * The walker is not null.
   */
  private TreeWalker<T, ? extends E> _walker;

	/**
	 * Create a new recurse walker that applies the given walker
	 * to the children of the top of the traversed tree.
	 * 
	 * @param walker The walker that will process the children of the top
   *               of the traversed trees.
   *               The walker cannot be null.
	 */
  public ChildWalker(TreeWalker<T, ? extends E> walker) {
  	if (walker == null) {
  		throw new IllegalArgumentException("The walker cannot be null.");
  	}
    _walker = walker;
  }

  /**
   * Return the walker that will process the children of the top
   * of the traversed trees.
   * 
   * @return The walker that will process the children of the top
   *         of the traversed trees.
   *         The result is not null.
   */
  public TreeWalker<T, ? extends E> walker() {
    return _walker;
  }

  /**
   * Let the walker of this child walker traverse the children of
   * the top of the given tree.
   */
  @Override
  public <X extends T, N extends Exception> void traverse(TreeStructure<X, N> tree) throws E, N {
  	if (tree == null) {
  		throw new IllegalArgumentException("The tree cannot be null.");
  	}
    for (TreeStructure<? extends X, N> child : tree.branches()) {
      walker().enter(child);
      walker().traverse(child);
      walker().exit(child);
    }
  }

}
