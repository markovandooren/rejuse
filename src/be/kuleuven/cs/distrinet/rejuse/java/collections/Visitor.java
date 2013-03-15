package be.kuleuven.cs.distrinet.rejuse.java.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.ConcurrentModificationException;

/**
 * <p>A class of collection operators that can perform a certain action on all
 * element of a collection.</p>
 *
 * <center> <img src="doc-files/Visitor.png"/> </center>
 * 
 * <h2><a href="Visitor.html">Visitor</a></h2>
 * 
 * <p>The Visitor class is a
 * replacement for the <code>java.util.Iterator</code> class that allows a
 * programmer to perform a certain action on all elements of a collection. An
 * iterator already provides a convenient way to iterate over all elements of a
 * collection without having to know anything about its inner structure, but the
 * programmer still needs to write the control statements for the iteration.</p>
 * 
 * <p><b><underline>Traditional use of an iterator.</underline></b></p>
 *
 * <pre><code>
 * Iterator iter = collection.iterator();
 * while(iter.hasNext()) {
 *   MyClass element = (MyClass) iter.next();
 *   // action code
 * }
 * </code></pre>
 *
 * <p>The <code>Visitor</code> class provides a more declarative way of
 * performing an action on all elements of a collection. Instead of writing an
 * iteration loop, a class is created, most often as an anonymous inner class,
 * which overwrites the <code><a
 * href="Visitor.html#visit(java.lang.Object)">visit</a></code> method of the
 * Visitor class. The <code><a
 * href="Visitor.html#visit(java.lang.Object)">visit</a></code> method will
 * perform the action on the given element of the collection. To perform the
 * visit, the <code><a
 * href="Visitor.html#applyTo(java.util.Collection)">applyTo</a></code> method
 * is invoked with the collection which has to be visited as an argument. The
 * typical use of such an inner class is shown below.</p>
 * 
 * <p><b><underline>Typical use of a <code>Visitor</code>.</underline></b></p>
 * <pre><code>
 * new Visitor() {
 *   public void visit(Object element) {
 *     MyClass object = (MyClass) element;
 *     // action code
 *   }
 * }.applyTo(collection);
 * </code></pre>
 * 
 * <p>Notice that all control statements have disappeared. Only the code that
 * actually performs the action is present, along with the invokation of the
 * <code><a href="Visitor.html#applyTo(java.util.Collection)">applyTo</a></code>
 * method. The code in <code>Visitor<code> takes care of the iteration. Using a
 * visitor does not make the code shorter, but it is easier to read and write
 * than the code with the iterator.</p>
 * 
 * <p>When the action to be performed needs information from elements that were
 * visited before, that information can be stored in instance variables of the 
 * inner class instead of a local variable of the current method.</p>
 * 
 * <p>In addition to collections, a <code>Visitor</code> class can also visit
 * iterators, enumerations and multi-dimensional arrays of objects. Being able
 * to visit iterators and enumerations is usefull in case you can't get a
 * reference to a collection but only a reference to an <code>Iterator</code> or
 * an <code>Enumeration</code> for that collection. The <code><a
 * href="Visitor.html#applyTo(java.util.Collection)">applyTo</a></code> method
 * has been overloaded in order to provide a single notation for all visits.</p>
 * <p>When you need to save information between the visit of two elements,
 * you can create instance variables for the inner class instead of using
 * local variables.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class Visitor<T> implements CollectionOperator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * The code to be applied to all elements of a collection.
   *
   * @param  element
   *         The object the code should be applied to.
   */
 /*@
   @ public behavior
   @
   @ pre isValidElement(element);
   @*/
  public abstract void visit(T element);
  
  /**
   * <p>Perform the visit defined in <code>public void visit(Object)</code>
   * on <collection>. The contents of <collection> is not changed.</p>
   * <p>The collection is returned, so that further operations can be 
   * applied to it inline.</p>
   *
   * @param  collection
   *         The collection to perform this visit on. This can be null.
   */
 /*@
   @ public behavior
   @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
   @
   @ // The changes are applied to the given collection,
   @ // which is returned afterwards
   @ post \result == collection;
   @
   @ // <code>public void visit(Object)</code> is called for all elements.
   @ post (* for all e in collection: visit(e) *);
   @
	 @ signals (ConcurrentModificationException) 
	 @         (* The collection was modified while calculating the tranitive closure. *);
   @*/
  public final Collection applyTo(Collection<T> collection) throws ConcurrentModificationException {
    if (collection != null) {
      Iterator<T> iter = collection.iterator();
      while (iter.hasNext()) {
        visit(iter.next());
      }
    }
    return collection;
  }

  /**
   * <p>Perform the visit defined in <code>public void visit(Object)</code>
   * on the multi-dimensional object array array. The contents of array is not changed.</p>
   * <p>The array is returned, so
   * that further operations can be applied to it inline.</p>
   * <p>The elements are visited in depth-first order.</p>
   * <p><b>NOTE: For now it doesn't work when subarrays at the same level have different sizes.
   * for example:</b></p>
   * <pre><code>
   * [
   *  [o1, o2],
   *  [o3, o4, o5]
   * ]
   * </code></pre>
   *
   * @param  array
   *         The array to perform this visit on. This can be null.
   * @result <code>public void visit(Object)</code> is called for all elements of
   *         <collection>.
   *       | for all e in array: visit(e)
   */
 /*@
   @ public behavior
   @
   @ // The changes are applied to the given object array,
   @ // which is returned afterwards
   @ pre (* All elements in the array are of type T *);
   @ post \result == array;
   @*/
  public final Object[] applyTo(Object[] array) {
                //MvDMvDMvD : PRECONDITION !!!
    if (array != null) {
      try {
        ObjectArrayIterator<T> iter = new ObjectArrayIterator<T>(array);
        // The while loop will visit all elements but the last one.
        // since the constructor of ObjectArrayIterator did not throw an
        // exception, there is at least one element to visit.
        while (! iter.atEnd()) {
          visit((T) iter.getElement());
          iter.next();
        }
        visit((T) iter.getElement());
      }
      catch(ZeroDimensionException exc) {
          //NOP
          //The array has some dimension equal to zero
          //so we don't have to do anything because the array
          //can not contain any elements.
      }
    }
    return array;
  }
  
  /**
   * <p>Perform the visit defined in <code>public void visit(Object)</code>
   * on <iterator>. The contents of collection of the iterator is not changed.</p>
   *
   * @param  iterator
   *         The iterator used to perform this visit. This can be null.
   * @result <code>public void visit(Object)</code> is called for all elements of
   *         can be reached using the iterator.
   *       | for all e in iterator: visit(e)
   */
 /*@
   @ public behavior
   @
   @ pre (* The iterator must be at the beginning of its iteration. *);
   @
   @ // The iterator will be at the end
   @ post ! iterator.hasNext();
   @
	 @ signals (ConcurrentModificationException) 
	 @         (* The collection was modified while calculating the tranitive closure. *);
   @*/
  public final void applyTo(Iterator<T> iterator) throws ConcurrentModificationException {
                //MVDMVDMVD PRECONDITION !!!
    if (iterator != null) {
      while (iterator.hasNext()) {
        visit(iterator.next());
      }
    }
  }

  /**
   * <p>Perform the visit defined in <code>public void visit(Object)</code>
   * on <enumeration>. The contents of collection of the enumeration is not changed.</p>
   *
   * @param  enumeration
   *         The enumeration used to perform this visit. This can be null.
   * @result <code>public void visit(Object)</code> is called for all elements of
   *         can be reached using the enumeration.
   *       | for all e in enumeration: visit(e)
   */
 /*@
   @ public behavior
   @
   @ pre (* The enumeration must be at the beginning of its enumeration. *);
   @
   @ // The enumeration will be at the end
   @ post ! enumeration.hasMoreElements();
   @
	 @ signals (ConcurrentModificationException) 
	 @         (* The collection was modified while calculating the tranitive closure. *);
   @*/
  public final void applyTo(Enumeration<T> enumeration) throws ConcurrentModificationException {
                //MVDMVDMVD PRECONDITION !!!
    if (enumeration != null) {
      while (enumeration.hasMoreElements()) {
        visit(enumeration.nextElement());
      }
    }
  }
}