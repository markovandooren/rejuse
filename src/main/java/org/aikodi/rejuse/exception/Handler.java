/**
 * 
 */
package org.aikodi.rejuse.exception;

import java.io.PrintStream;

import org.aikodi.rejuse.action.Nothing;

/**
 * @author Marko van Dooren
 *
 */
public interface Handler<I extends Exception, O extends Exception> {

  public interface Executor<E extends Exception> {
    public void execute() throws E;
  }
  
  public interface ExecutorWithTwoExceptions<E extends Exception, N extends Exception> {
  	public void execute() throws E, N;
  }
  
  public void handle(I exception) throws O;
  
  public <E extends I> void execute(Executor<E> e) throws O;
  
  public <E extends I, N extends Exception> void execute(ExecutorWithTwoExceptions<E, N> e) throws O, N;
  
  public static <I extends Exception> Handler<I,Nothing> resume() {
    Handler<I, Nothing> handler = new Handler<I, Nothing>() {

      @Override
      public void handle(I exception) throws Nothing {
      }

      @Override
      public <E extends I> void execute(Executor<E> e) throws Nothing {
        try {
          e.execute();
        }catch(Exception exception) {
          
        }
      }

//			@Override
			public <E extends I, N extends Exception> void execute(ExecutorWithTwoExceptions<E, N> e) {
        try {
          e.execute();
        }catch(Exception exception) {
          
        }
			}
    };
    return handler;
  }
  
  public static <I extends Exception> Handler<I,Nothing> printer(final PrintStream stream) {
    Handler<I, Nothing> handler = new Handler<I, Nothing>() {

      @Override
      public void handle(I exception) throws Nothing {
      }

      @Override
      public <E extends I> void execute(Executor<E> e) throws Nothing {
        try {
          e.execute();
        }catch(Exception exception) {
          exception.printStackTrace(stream);
        }
      }

//			@Override
			public <E extends I, N extends Exception> void execute(ExecutorWithTwoExceptions<E, N> e) {
        try {
          e.execute();
        }catch(Exception exception) {
          exception.printStackTrace(stream);
        }
			}
    };
    return handler;
  }
  
  
  
  public static <I extends Exception> Handler<I,I> propagate() {
    Handler<I, I> handler = new Handler<I,I>() {

      @Override
      public void handle(I exception) throws I {
        throw exception;
      }

      @Override
      public <E extends I> void execute(Executor<E> e) throws E {
        e.execute();
      }

//			@Override
			public <E extends I, N extends Exception> void execute(
					org.aikodi.rejuse.exception.Handler.ExecutorWithTwoExceptions<E, N> e) throws I, N {
				e.execute();
			}
      
    };
    return handler;
  }
  
}
