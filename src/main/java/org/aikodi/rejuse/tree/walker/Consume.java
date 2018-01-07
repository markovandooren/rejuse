package org.aikodi.rejuse.tree.walker;

import org.aikodi.rejuse.action.UniversalConsumer;
import org.aikodi.rejuse.tree.TreeStructure;

/**
 * A class of treewalkers that consume the
 * <b>root element</b> of a tree.
 * 
 * Objects of this class are typically used in combination
 * with other treewalkers to consume multiple elements in the tree.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the nodes of the tree.
 * @param <E> The type of exceptions that can be thrown by this walker.
 */
public class Consume<T,E extends Exception> implements TreeWalker<T, E> {

	/**
	 * Create a new consume walker with the given consumer.
	 * 
	 * @param consumer The consumer to process the top of trees.
	 *                 The consumer cannot be null.
	 */
  public Consume(UniversalConsumer<T, E> consumer) {
    if(consumer == null) {
      throw new IllegalArgumentException("The action to apply cannot be null.");
    }
    this._consumer = consumer;
  }

  /**
   * The consumer that is used to process trees.
   * The consumer cannot be null.
   */
  private UniversalConsumer<T,E> _consumer;
  
  /**
   * Return the consumer that will process the top of the traversed trees.
   * @return The consumer that will process the top of the traversed trees.
   *         The result is not null.
   */
  public UniversalConsumer<T,E> consumer() {
    return _consumer;
  }
  
  /**
   * Let the consumer accept the root node of the given tree.
   */
  @Override
  public <X extends T, N extends Exception> void traverse(TreeStructure<X, N> tree) throws E {
  	if (tree == null) {
  		throw new IllegalArgumentException("The tree cannot be null.");
  	}
    consumer().perform(tree.node());
  }

}
