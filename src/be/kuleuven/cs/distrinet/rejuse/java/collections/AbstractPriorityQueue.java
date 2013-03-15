package be.kuleuven.cs.distrinet.rejuse.java.collections;

/**
 * AbstractPriorityQueue provides an implementation for 
 * getNext and removeNext.
 * 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author Tom Schrijvers
 * @release $Name$
 */
public abstract class AbstractPriorityQueue extends AbstractDispenser implements PriorityQueue {

 /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";
    
  /**
	 * See superclass.
	 */
	public /*@ pure @*/ Object getNext() {
		return min();
	}

	/**
	 * See superclass.
	 */
	public void removeNext() {
		pop();
	}
	
}

