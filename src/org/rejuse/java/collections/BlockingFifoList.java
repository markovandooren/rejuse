package org.rejuse.java.collections;


/**
 * <p>Synchronized fifo list that will block the request to pop the first object
 * until a first object is present.</p>
 *
 * <center>
 *   <img src="doc-files/BlockingFifoList.png"/>
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
public class BlockingFifoList	extends FifoList {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new empty blocking fifo list.
	 */
 /*@
	 @ public behavior
	 @
	 @ post size() == 0;
	 @*/
  public BlockingFifoList(){
  }

  /**
	 * See superclass.
	 */
	public synchronized void push(Object object) {
	  super.push(object);
	  notify();
  }

  /**
   * See superclass.
   * Will block until the list is non-empty.
   */
 /*@
	 @ also public behavior
	 @
	 @ pre true;
	 @*/
  public synchronized Object pop() {
		// While loop in case an InterruptedException is thrown
		// while there is no object available yet.
		// MVDMVDMVD: Shouldn't the exception be thrown to the
		// caller ?
	  while(isEmpty()) {
	    try {
		    wait();
	    } 
			catch (InterruptedException ie) {
	    }
	  }
	  return super.pop();
  }

  /**
    * See superclass.
    */
  public synchronized /*@ pure @*/ Object getFirst() {
	  while (isEmpty()) {
		try {
			wait();
		} catch (InterruptedException ie) {
		}
	  }
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

