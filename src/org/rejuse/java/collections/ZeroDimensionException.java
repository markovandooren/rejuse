package org.rejuse.java.collections;
/**
 * A class of exceptions indicating the attempt to use
 * a class in this package with an object array that
 * has at least one dimension equal to zero.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class ZeroDimensionException extends RuntimeException {

  //MvDMvDMvD: should this class be public ? It does make sense
  //           in the case of the ArrayCursor, since you simply
  //           can't point to an index of an array that can't 
  //           contain any elements. Or can we work around that
  //           in an elegant way ?

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * Initialize a new ZeroDimensionException with the given array.
   */
 /*@
   @ // array may not be null
   @ pre array != null;
   @
   @ // The array of this ZeroDimensionException is set to array.
   @ post getArray() == array;
   @*/  
  public ZeroDimensionException(Object[] array) {
    _array=array;
  }

  /**
   * Initialize a new ZeroDimensionException with the given array
   * and message.
   */
 /*@
   @ // array may not be null
   @ pre array != null;
   @
   @ // The array of this ZeroDimensionException is set to array.
   @ post getArray() == array;
   @ // The message of this ZeroDimensionException is set to <msg>.
   @ post getMessage() == msg;
   @*/  
  public ZeroDimensionException(Object[] array, String msg) {
    super(msg);
    _array=array;
  }

  /**
   * Return the array that caused this ZeroDimensionException.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ Object[] getArray() {
    return _array;
  }  
  
  /*@
    @ private invariant _array != null;
    @*/
  private Object[] _array;
} 


