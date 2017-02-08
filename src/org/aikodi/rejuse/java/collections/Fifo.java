package org.aikodi.rejuse.java.collections;

/*@ model import java.util.List; @*/

/**
 * <p>Interface for first-in first-out like datastructures.</p>
 *
 * <center>
 *   <img src="doc-files/Fifo.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface Fifo extends Dispenser {
  
  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

 /*@
   @ // A model field representing the elements in this Fifo. Necessary
   @ // in order to write full specifications, but we don't want to provide
   @ // a getElements() method.
   @ public model List elements;
   @ public invariant elements != null;
   @*/

  /**
   * See superclass.
   */
 /*@
   @ also public behavior
   @
   @ post \result == elements.size();
   @*/
  public /*@ pure @*/ int size();

  /**
   * Push a new object in the Fifo.
   *
   * @param item
   *        The object to be put in the fifo.
   */
 /*@
   @ public behavior
   @
   @ pre size() < Integer.MAX_VALUE;
   @
   @ post elements.get(elements.size() - 1) == item;
   @ post nbExplicitOccurrences(item) == \old(nbExplicitOccurrences(item)) + 1;
   @*/
  public void push(Object item);

 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post \result == elements.get(0);
   @*/
  public /*@ pure @*/ Object getFirst();

  /**
   * See superclass.
   */
 /*@
   @ also public behavior
   @
   @ post \result == getFirst();
   @*/
  public /*@ pure @*/ Object getNext();
  
  /**
   * Pop the first object from this fifo.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post \result == \old(getFirst());
   @ post nbExplicitOccurrences(\result) ==
   @        \old(nbExplicitOccurrences(getNext())) - 1;
   @ post (\forall int index; index >= 0 && index < size();
   @          elements.get(index) == \old(elements.get(index + 1)));
   @*/
  public Object pop();

}

