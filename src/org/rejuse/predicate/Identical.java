package org.rejuse.predicate;

/**
 * <p>A class of predicate that check whether or not the given argument
 * is the same as some object.</p>
 *
 * <center><img src="doc-files/Identical.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Identical<T> extends SafePredicate<T> {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>Initialize a new Identical with the given object</p>
   *
   * @param object
   *        The object that will be used for the identity check.
   */
 /*@
	 @ public behavior
	 @
   @ post getObject() == object;
   @*/
  public Identical(T object) {
    _object = object;
  }

	/**
	 * Return the object of this Identical.
	 */
  public /*@ pure @*/ T getObject() {
    return _object;
  }

 /*@
	 @ also public behavior
	 @
	 @ post \result == (getObject() == object);
	 @*/
	public /*@ pure @*/ boolean eval(T object) {
		return _object == object;
	}

	/**
	 * The object to be used for the identity test.
	 */
  private T _object;
}