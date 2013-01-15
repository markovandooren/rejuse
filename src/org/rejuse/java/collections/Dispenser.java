package org.rejuse.java.collections;


/**
 * <p>A Dispenser is a Collection with an
 * inherent order of removal of its objects,
 * e.g. last-in first-out, first-in first-out,
 * priority based, ...</p>
 *
 * An object can occur more than once in a Dispenser.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author Tom Schrijvers
 * @release $Name$
 */
public interface Dispenser /* extends Collection */ {

 /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

  /**
   * See superclass.
   *
   * The item is always added.
   */
 /*@
   @ public behavior
   @
   @ pre item != null;
   @
   @ post nbExplicitOccurrences(item) ==
   @        \old(nbExplicitOccurrences(item)) + 1;
   @
   @ post \result == true;
   @*/
  public boolean add(Object item);

  /**
   * Remove the next item in the
   * removal ordering.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post nbExplicitOccurrences(\old(getNext())) ==
   @      \old(nbExplicitOccurrences(getNext())) - 1;
   @*/
  public void removeNext();

  /**
   * Return the object that is next to be removed.
   */
 /*@
   @ public behavior
   @ 
   @ pre ! isEmpty();
   @
   @ post nbExplicitOccurrences(\result) > 0;
   @*/
  public /*@ pure @*/ Object getNext();

  /**
   * See superclass.
   */
 /*@
   @ public behavior
   @
   @ post \result == (\sum Object item; ; nbExplicitOccurrences(item));
   @*/
  public /*@ pure @*/ int size();

  /**
   * Return the nummer of explicit occurrences of the given
   * object.
   *
   * @param item
   *        the object
   */
 /*@
   @ public behavior
   @
   @ post \result >= 0;
   @*/
  public /*@ pure @*/ int nbExplicitOccurrences(Object item);

  /**
   * Temporary substitute
   */
  public /*@ pure @*/ boolean isEmpty();
//  /**
//   * Return an iterator over the elements of the Dispenser,
//   * in order of removal.
//   */
//  public Iterator iterator();
  
}

