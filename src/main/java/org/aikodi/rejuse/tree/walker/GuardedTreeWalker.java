package org.aikodi.rejuse.tree.walker;

import org.aikodi.contract.Contract;
import org.aikodi.rejuse.exception.Handler;
import org.aikodi.rejuse.exception.Handler.Executor;
import org.aikodi.rejuse.exception.Handler.ExecutorWithTwoExceptions;
import org.aikodi.rejuse.tree.TreeStructure;

/**
 * A class of treewalkers that wrap another walker and use a handler to deal with exceptions.
 * 
 * @author Marko van Dooren
 * 
 * @param <T> The type of the elements in the tree.
 * @param <I> The type of exceptions that are thrown by the wrapped walker.
 * @param <O> The type of exceptions that can be thrown by this walker.           
 */
public class GuardedTreeWalker<T, I extends Exception, O extends Exception> implements TreeWalker<T, O> {

	/**
	 * The tree walker that will traverse the traversed trees.
	 * The wrapped tree walker is not null.
	 */
  private final TreeWalker<T, I> _wrapped;
  
  /**
   * The exception handler that will process exception when
   * traversing trees with the wrapped walker.
   * The exception handler is not null.
   */
  private final Handler<? super I,O> _handler;
  
  /**
   * Create a new guarded tree walker that uses the given handler
   * to handle exception when traversing trees with the wrapped tree walker.
   * 
   * @param wrapped The tree walker that will be used to traverse trees.
   *                The tree walker cannot be null.
   * @param handler The exception handler that will be used to handle exception
   *                that are thrown when traversing trees with the wrapped
   *                tree walker.
   *                The exception handler cannot be null.
   */
  public GuardedTreeWalker(TreeWalker<T, I> wrapped, Handler<? super I,O> handler) {
    Contract.requireNotNull(wrapped, handler);
    this._wrapped = wrapped;
    this._handler = handler;
  }



  /**
   * Traverse the given tree with the wrapped tree walker and handle the
   * exceptions with the handler of this guarded tree walker.
   * 
   * @throws N 
   * @{inheritDoc}
   */
  @Override
  public <X extends T, N extends Exception> void traverse(TreeStructure<X, N> tree) throws O, N {
    Contract.requireNotNull(tree);
  	ExecutorWithTwoExceptions<I, N> executor = () -> _wrapped.traverse(tree); 
    _handler.execute(executor);
  }
  
  /**
   * Let the wrapped tree walker exit the given tree.
   */
  @Override
  public <N extends Exception> void exit(TreeStructure<?, N> tree) throws O {
    Contract.requireNotNull(tree);
  	_handler.<I>execute(() -> _wrapped.exit(tree));
  }

  /**
   * Let the wrapped tree walker enter the given tree.
   */
  @Override
  public <N extends Exception> void enter(TreeStructure<?, N> tree) throws O {
    Contract.requireNotNull(tree);
  	_handler.<I>execute(() -> _wrapped.enter(tree));
  }

}
