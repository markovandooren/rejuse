package org.aikodi.rejuse.java.collections;


/**
 * AbstractDispenser provides an implementation for the add method
 * that always returns true and delegates to an abstract method
 * for the actual adding.
 * 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author Tom Schrijvers
 * @release $Name$
 */
public abstract class AbstractDispenser /* extends AbstractCollection */ implements Dispenser {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

  /**
   * See superclass.
   * FIXME
   */
  public final boolean add(Object item) {
    addImpl(item);
    return true;
  }

  /**
   * Add the given item to this Dispenser.
   *
   * @param item
   *        the item to be added
   */
 /*@
   @ protected behavior
   @
   @ pre item != null;
   @
   @ post nbExplicitOccurrences(item) ==
   @        \old(nbExplicitOccurrences(item)) + 1;
   @*/
  protected abstract void addImpl(Object item);


  /**
   * Temporary substitue
   */
  public /*@ pure @*/ boolean isEmpty() {
    return size() == 0;
  }
}

