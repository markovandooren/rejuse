package be.kuleuven.cs.distrinet.rejuse.java.collections;

/**
 * AbstractFifo provides an implementation for several Dispenser
 * methods, delegating them to the corresponding Fifo methods.
 * 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author Tom Schrijvers
 * @release $Name$
 */
public abstract class AbstractFifo extends AbstractDispenser implements Fifo {

 /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";
    
  /**
   * See superclass
   */
	protected void addImpl(Object item) {
		push(item);
	}

  /**
   * See superclass
   */
	public void removeNext() {
		pop();
	}

  /**
   * See superclass
   */
	public /*@ pure @*/ Object getNext() {
		return getFirst();
	}
	
}


