package org.aikodi.rejuse.tree.walker;

import org.aikodi.rejuse.tree.TreeStructure;

/**
 * A class of objects for traversing a tree structure. In addition
 * to performing an action on a node, a tree action can also perform
 * action before and after visiting a node.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of objects in the data structure being traversed.
 * @param <E> The type of exceptions that can be thrown by this walker.
 */
public interface TreeWalker<T, E extends Exception> {

  /**
   * Perform the action on the given object. First,
   * the type of the object is checked. If it
   * is of type T, the action is applied. Otherwise,
   * nothing is done.
   * 
   * @param tree The object to which the action should
   *               be applied.
   * @throws E
   */
  public abstract <X extends T, N extends Exception> void traverse(TreeStructure<X, N> tree) throws E, N;

  /**
   * This method is called when a tree action arrives at a node.
   * 
   * @param node The data structure node that has just been entered.
   */
  default <N extends Exception> void enter(TreeStructure<?, N> node) throws E {
  }

  /**
   * This method is called when a tree action exits a node.
   *
   * @param node The data structure node that has just been exited.
   */
  default <N extends Exception> void exit(TreeStructure<?, N> node) throws E {
  }
}
