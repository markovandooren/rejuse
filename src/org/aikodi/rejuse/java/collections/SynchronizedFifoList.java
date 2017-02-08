package org.aikodi.rejuse.java.collections;

/**
 * <p>Synchronized version of a FifoList.</p>
 *
 * <center>
 *   <img src="doc-files/SynchronizedFifoList.png"/>
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
public class SynchronizedFifoList extends FifoList{
  
  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

  /**
   * See superclass.
   */
  public synchronized void push(Object obj) {
    super.push(obj);
  }
  
  /**
   * See superclass.
   */
  public synchronized Object pop() {
    return super.pop();
  }

  /**
   * See superclass.
   */
  public synchronized /*@ pure @*/ Object getFirst() {
    return super.getFirst();
  }
  
  /**
   * See superclass/
   */
  public synchronized void clear() {
    super.clear();
  }

  /**
   * See superclass.
   */
  public synchronized /*@ pure @*/ int size() {
    return super.size();
  }

  /**
   * See superclass.
   */
  public synchronized /*@ pure @*/ int nbExplicitOccurrences(Object item) {
    return super.nbExplicitOccurrences(item);
  }
}

