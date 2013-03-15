package be.kuleuven.cs.distrinet.rejuse.java.collections;


import java.util.Set;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.lang.reflect.Array;

/**
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @release $Name$
 */
final public class Singleton extends AbstractList implements Set {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * The only element contained in this Singleton.
	 */
  private Object _onlyElement;

	 /**
		* Initialize a new Singleton containing the given element.
		*
		* @param onlyElement
		*        The only element of this Singleton.
		*/
  /*@
	  @ public behavior
		@
    @ post getOnlyElement() == onlyElement;
    @*/
  public /*@ pure @*/ Singleton(Object onlyElement) {
    _onlyElement = onlyElement;
  }

	/**
	 * Return the only element in this Singleton.
	 */
  public /*@ pure @*/ Object getOnlyElement() {
    return _onlyElement;
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post \result == 1;
    @*/
  public /*@ pure @*/ int size() {
    return 1;
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post \result == false;
    @*/
  public /*@ pure @*/ boolean isEmpty() {
    return false;
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post \result ==
    @         ((getOnlyElement() == null) && (o == null)) ||
    @         getOnlyElement().equals(o);
    @*/
  public /*@ pure @*/ boolean contains(Object o) {
    return ((_onlyElement == null) && (o == null)) || _onlyElement.equals(o);
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post \result instanceof SingletonIterator;
    @ post \result !=  null;
    @*/
  public /*@ pure @*/ Iterator iterator() {
    return new SingletonIterator();
  }

  public class SingletonIterator implements Iterator {
  
    /*
       public model boolean onlyElementNotReturnedYet;
       initially onlyElementNotReturnedYet == true;
 depends onlyElementNotReturnedYet <- _calledOnce;
			 represents onlyElementNotReturnedYet <- ! _calledOnce;
      */
  
		/**
		 * A boolean indicating whether or not the only element in an Singleton
		 * has been visited.
		 */
    private /*@ spec_public @*/ boolean _calledOnce = false;
    
		 /**
	  	* See superclass
			*/
    /*@
	  	@ also public behavior
			@
      @ post \result == onlyElementNotReturnedYet;
      @*/
    public /*@ pure @*/ boolean hasNext() {
      return (! _calledOnce);
    }

		 /**
	  	* See superclass
			*/
    /*@
	  	@ also public behavior
			@
      @ post \result == getOnlyElement();
      @ post onlyElementNotReturnedYet == false;
      @ signals (NoSuchElementException) (\old(! onlyElementNotReturnedYet));
      @*/
    public Object next() {
      if (_calledOnce) {
        throw new NoSuchElementException();
      }
      _calledOnce = true;
      return _onlyElement;
    }

		 /**
	  	* See superclass
			*/
    /*@
	  	@ also public behavior
			@
      @ post false;
      @ signals (UnsupportedOperationException) true;
      @*/
    public void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
    }

  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post \result.length == 1;
    @ post \result[0] == getOnlyElement();
    @*/
  public /*@ pure @*/ Object[] toArray() {
    Object[] result = {_onlyElement};
    return result;
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post \result.getClass().getComponentType() == a.getClass().getComponentType();
    @ post \result[0] == getOnlyElement();
    @ post (a.length >= 1) ==> (\result == a);
    @ post (a.length < 1) ==> (\result.length == 1);
    @ signals (NullPointerException) a == null;
    @ signals (ArrayStoreException)
    @           (getOnlyElement() != null) &&
    @             (! a.getClass().isInstance(getOnlyElement()));
    @*/
  public /*@ pure @*/ Object[] toArray(Object[] a) {
    if (a.length < 1) { // NullPointerException, right were we want it
      a = (Object[]) Array.newInstance(a.getClass().getComponentType(), 1);
    }
    a[0] = _onlyElement; // ArrayStoreException, right were we want it
    return a;
  }


  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ pre index == 1;
   @
   @ post \result == getOnlyElement(); 
   @*/
  public /*@ pure @*/ Object get(int index) {
    return _onlyElement;
  }

  // Modification Operations

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post false;
    @ signals (UnsupportedOperationException) true;
    @*/
  public boolean add(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post false;
    @ signals (UnsupportedOperationException) true;
    @*/
  public boolean remove(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }


  // Bulk Operations

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post false;
    @ signals (UnsupportedOperationException) true;
    @*/
  public boolean addAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post false;
    @ signals (UnsupportedOperationException) true;
    @*/
  public boolean retainAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post false;
    @ signals (UnsupportedOperationException) true;
    @*/
  public boolean removeAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post false;
    @ signals (UnsupportedOperationException) true;
    @*/
  public void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

	 /**
	  * See superclass
		*/
  /*@
	  @ also public behavior
		@
    @ post false;
    @ signals (UnsupportedOperationException) true;
    @*/
  public Object set(int index, Object object) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
}

