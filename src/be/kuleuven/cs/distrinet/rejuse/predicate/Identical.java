package be.kuleuven.cs.distrinet.rejuse.predicate;

import be.kuleuven.cs.distrinet.rejuse.action.Nothing;

/**
 * <p>A class of predicate that check whether or not the given argument
 * is the same as some object.</p>
 *
 * <center><img src="doc-files/Identical.png"/></center>
 */
public class Identical extends AbstractPredicate<Object, Nothing> {

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
  public Identical(Object object) {
    _object = object;
  }

	/**
	 * Return the object of this Identical.
	 */
  public /*@ pure @*/ Object getObject() {
    return _object;
  }

 /*@
	 @ also public behavior
	 @
	 @ post \result == (getObject() == object);
	 @*/
	public /*@ pure @*/ boolean eval(Object object) {
		return _object == object;
	}

	/**
	 * The object to be used for the identity test.
	 */
  private Object _object;
}