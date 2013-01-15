package org.rejuse.java.collections;

import java.util.Vector;
import java.util.List;
import java.util.ListIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.ConcurrentModificationException;

/**
 * <p>A robust visitor of collections. The code in visit is performed for each element
 * in the visited collection.</p>
 * <center><img src="doc-files/RobustVisitor.png"/></center>
 *
 * <p>Of course, sometimes exceptions can be thrown during a visit. The default
 * behaviour when an exception occurs, is to undo all changes that were made and
 * throw an exception to the caller of the method. This means that the actions
 * that were performed on elements visited before the element that caused the
 * exception, have to be undone. The RobustVisitor class adds support for
 * handling exceptions to the Visitor class.</p> 
 * 
 * <p>If an exception occurs while visiting the collection, the changes made to
 * the elements visited before the element that caused the exception, are
 * undone. To accomplish this, a method <code><a
 * href="RobustVisitor.html#unvisit(java.lang.Object, java.lang.Object)">void
 * unvisit(Object element, Object undoData)</a></code> is introduced, which
 * takes a visited element and data to undo the changes as arguments. The method
 * must undo the changes made to the given element. The undoData object is the
 * object that is returned by the <code><a
 * href="RobustVisitor.html#visit(java.lang.Object)">void visit(Object element)
 * throws Exception</a></code> method. If no undo data is necessary to undo the
 * changes, <code>null</code> can be returned. The changes are undone in the
 * opposite order as the changes were done. The changes of the visit that caused
 * the exception should be undone by the visit method itself, the other visits
 * are undone using the unvisit method. After all changes are undone, the
 * exception is propagated to the caller of the <code><a
 * href="RobustVisitor.html#applyTo(java.util.Collection)">applyTo</a></code>
 * method.</p>
 *
 * <p>The <code><a
 * href="RobustVisitor.html#visit(java.lang.Object)">visit</a></code> and
 * <code><a
 * href="RobustVisitor.html#applyTo(java.util.Collection)">applyTo</a></code>
 * methods throw <code>Exceptions</code> to keep the class as general as
 * possible. The <code>Exception</code> of the <code><a
 * href="RobustVisitor.html#visit(java.lang.Object)">visit</a></code> method can
 * be strengthened to one specific exception when overwriting the method, but
 * the <code>Exception</code> of the applyTo method can't because that method is
 * made final. That means that in the code you have to catch
 * <code>Exception</code>, which is a disadvantage of using a
 * <code>RobustVisitor</code>. Most methods we didn't think could be overwritten
 * in a useful way are made final. Even if we would allow to overwrite the
 * <code><a
 * href="RobustVisitor.html#applyTo(java.util.Collection)">applyTo</a></code>
 * method, the only way to strengthen the exception there is to insert a
 * try-catch block and call the super method, or copy the code from the
 * <codel>RobustVisitor</code: class, which both aren't any better. The only
 * elegant solution to the problem are generic classes, <a
 * href="http://developer.java.sun.com/developer/earlyAccess/adding_generics/">which
 * aren't supported yet in Java</a>.</p>
 *
 * <p><code>RobustVisitor</code> doesn't inherit from <code>Visitor</code>
 * because adding an exception to the throws clausule of the visit method would
 * violate LSP (Liskov Substitution Principle) and because the visit methods of
 * both classes have different return types. The last reason also prevents us
 * from doing the opposite, making <code>Visitor</code> inherit from
 * <code>RobustVistor</code> (which would also introduce a useless unvisit
 * method in <code>Visitor</code>).</p>
 *
 * <p><b><underline>Undoing changes the traditional way.</underline></b></p>
 * <pre><code>
 * Iterator iter = collection.iterator();
 * Vector changed = new Vector();
 * Vector undo = new Vector();
 * try{
 *   while(iter.hasNext()) {
 *     MyClass object = (MyClass) iter.next();
 *     // action code
 *     changed.add(object);
 * 		undo.add(undoData);
 *   }
 * }
 * catch(MyException exc) {
 *   for(int i=changed.size(); i>= 0; i--) {
 *     // undo action code;
 *   }
 *   throw exc;
 * }
 * </code></pre>
 *
 * <p><b><underline>Handling exceptions with the RobustVisitor.</underline></b></p>
 * <pre><code>
 * new RobustVisitor() {
 *   public Object visit(Object element) throws MyException {
 *     MyClass object = (MyClass) element;
 *     // action code;
 * 		 return undoData;
 *   }
 * 	
 *   public void unvisit(Object element, Object undoData) {
 *     // undo action code;
 *   }
 * }.applyTo(collection);
 * </code></pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class RobustVisitor<T> implements CollectionOperator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * The code to be applied to all elements of a collection.
   * If this method throws a Exception, unvisit will be called
   * on all elements that are already visited with the data they returned.
   *
   * @param  element
   *         The object the code should be applied to.
   */
 /*@
   @ public behavior
   @
   @ pre isValidElement(element);
   @
   @ post (* Data which enables to undo what visit has done
   @         in unvisit is returned. *);
   @
   @ signals (Exception) 
   @         (* Something went wrong while visiting <element>. *);
   @*/
  public abstract Object visit(T element) throws Exception;
  
  /**
   * This method will be called when the visit method has raised an
   * exception for some element which was visited after <element>.
   * The implementation should undo whatever visit did on <element>.
   * For that, <unvisitData> can be used.
   */  
 /*@
   @ public behavior
   @
   @ pre isValidElement(element);
	 @
   @ // unvisitData is the data returned by the visit method.
   @ pre (* unvisitData == visit(element) *);
   @
   @ post (* the changes on <element> done by visit are undone *);
   @*/
  public abstract void unvisit(T element, Object unvisitData);

  /**
   * <p>Perform the visitation defined in <code>public void visit(Object)</code>
   * on <collection>. The contents of <collection> is not changed.</p>
   * <p>The collection is returned, so
   * that further operations can be applied to it inline.</p>
   *
   * @param  collection
   *         The collection to perform this visitation on. This can be null.
   */
 /*@
   @ public behavior
   @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
	 @
   @ // The changes are applied to the given collection,
   @ // which is returned afterwards
   @ post \result == collection;
   @ // <code>public void visit(Object)</code> is called for all
   @ // elements of <collection>.
   @ post (* for all e in collection: visit(e) *);
   @
	 @ signals (ConcurrentModificationException) (* The collection was modified while visiting. *);
   @ signals (Exception)
   @         (* Something has gone wrong during the visit *);
   @*/
  public final Collection applyTo(Collection<T> collection) throws Exception, ConcurrentModificationException {
    if ((collection != null) && (! collection.isEmpty())) {
      List<Entry<T>> undoDataList = new Vector(collection.size());
      try {
        Iterator<T> iter = collection.iterator();
        while (iter.hasNext()) {
          T element = iter.next();
          Object undoData = visit(element);
          undoDataList.add(new Entry<T>(element, undoData));
        }
      }
      catch(Exception exc) {
        // Only entries for elements that were succesfully visited are 
        // in the list, so we have to call unvisit on all elements.
        ListIterator<Entry<T>> iter = undoDataList.listIterator();
        // moving the iterator to the end.
        // thx Sun, for this wonderful interface
        while (iter.hasNext()) {
          iter.next();
        }
        while (iter.hasPrevious()) {
            // The value is the undo data.
            Entry<T> entry = iter.previous();
            unvisit(entry.getKey(),entry.getValue());
          }
        throw exc;
      }
    }    
    return collection;
  }
  
  /**
   * <p>Perform the visitation defined in <code>public void visit(Object)</code>
   * on array. The contents of array is not changed.</p>
   * <p>The array is returned, so that further operations
   * can be applied to it inline.</p>
   *
   * @param  array
   *         The array to perform this visitation on.
   *         This can be null.
   */
 /*@
   @ // The changes are applied to the given array,
   @ // which is returned afterwards
   @ post \result == array;
   @ // public void visit(Object) is called for all
   @ // elements of array.
   @ post (* for all e in collection: visit(e) *);
   @
   @ signals (Exception)
   @         (* Something has gone wrong during the visit *);
   @*/
  public final Object[] applyTo(Object[] array) throws Exception {
		//MvDMvDMvD PRECONDITION !!!
    if (array != null) {
      try {
        int[] dimensions = Arrays.getArrayDimensions(array);
        // Calculate the number of elements in the array.
        int nbElements=1;
        for(int i=0;i<dimensions.length;i++) {
          nbElements = nbElements * dimensions[i];
        }
        List undoDataList = new Vector(nbElements);
        try {
          ObjectArrayIterator<T> iter = new ObjectArrayIterator<T>(array);
          while (! iter.atEnd()) {
            T element = iter.getElement();
            Object undoData = visit(element);
            undoDataList.add(new Entry(element, undoData));
            iter.next();            
          }
          T element = iter.getElement();
          Object undoData = visit(element);
          undoDataList.add(new Entry(element, undoData));
        }
        catch(Exception exc) {
          // Only entries for elements that were succesfully visited are 
          // in the list, so we have to call unvisit on all elements.
          ListIterator<Entry<T>> iter = undoDataList.listIterator();
          // moving the iterator to the end.
          while (iter.hasNext()) {
            iter.next();
          }
          while (iter.hasPrevious()) {
            // The value is the undo data.
            Entry<T> entry = iter.previous();
            unvisit(entry.getKey(),entry.getValue());
          }
          throw exc;
        }
      }
      catch(ZeroDimensionException exc) {
        // do nothing
      }
    }    
    return array;
  }
  
  /**
   * <p>Perform the visitation defined in <code>public void visit(Object)</code>
   * on all elements reachable from <iterator>. The contents of the underlying collection
   * is not changed.</p>
   *
   * @param  iterator
   *         The iterator to perform this visitation on. This can be null.
   * @result <code>public void visit(Object)</code> is called for all elements of
   *         <iterator>.
   *       | for all e in iterator: visit(e)
   */
 /*@
   @ pre (* The iterator must be at the beginning of its iteration. *);
   @
   @ // The iterator will be at the end
   @ post ! iterator.hasNext();
   @
	 @ signals (ConcurrentModificationException) (* The collection was modified while visiting. *);
   @ signals (Exception)
   @         (* Something has gone wrong during the visit *);
   @*/
  public final void applyTo(Iterator<T> iterator) throws Exception, ConcurrentModificationException {
		//MvDMvDMvD PRECONDITION !!!
    if (iterator != null) {
      // not effective, but we can't get the size.
      List<Entry<T>> undoDataList = new Vector();
      try {
        while (iterator.hasNext()) {
          T element = iterator.next();
          Object undoData = visit(element);
          undoDataList.add(new Entry<T>(element, undoData));
        }
      }
      catch(Exception exc) {
        // Only entries for elements that were succesfully visited are 
        // in the list, so we have to call unvisit on all elements.
        ListIterator<Entry<T>> iter = undoDataList.listIterator();
        // moving the iterator to the end :(
        while (iter.hasNext()) {
          iter.next();
        }
        while (iter.hasPrevious()) {
            // The value is the undo data.
            Entry<T> entry = iter.previous();
            unvisit(entry.getKey(),entry.getValue());
          }
        throw exc;
      }
    }    
  }  

  /**
   * <p>Perform the visitation defined in <code>public void visit(Object)</code>
   * on all elements reachable from <enumeration>. The contents of the underlying collection
   * is not changed.</p>
   *
   * @param  enumeration
   *         The enumeration to perform this visitation on. This can be null.
   * @result <code>public void visit(Object)</code> is called for all elements of
   *         <iterator>.
   *       | for all e in enumeration: visit(e)
   * @excep  Throwable
   *         Something has gone wrong during the visit.
   */
 /*@
   @ pre (* The enumeration must be at the beginning of its enumeration. *);
   @
   @ // The enumeration will be at the end
   @ post ! enumeration.hasMoreElements();
   @
	 @ signals (ConcurrentModificationException) (* The collection was modified while visiting. *);
   @ signals (Exception)
   @         (* Something has gone wrong during the visit *);
   @*/
  public final void applyTo(Enumeration<T> enumeration) throws Exception, ConcurrentModificationException {
		//MvDMvDMvD PRECONDITION !!!
    if (enumeration != null) {
      // not efficient, but we can't get the size.
      List<Entry<T>> undoDataList = new Vector();
      try {
        while (enumeration.hasMoreElements()) {
          T element = enumeration.nextElement();
          Object undoData = visit(element);
          undoDataList.add(new Entry<T>(element, undoData));
        }
      }
      catch(Exception exc) {
        // Only entries for elements that were succesfully visited are 
        // in the list, so we have to call unvisit on all elements.
        ListIterator<Entry<T>> iter = undoDataList.listIterator();
        // moving the iterator to the end.
        // thx Sun, for this wonderful interface
        while (iter.hasNext()) {
          iter.next();
        }
        while (iter.hasPrevious()) {
            // The value is the undo data.
            Entry<T> entry = iter.previous();
            unvisit(entry.getKey(),entry.getValue());
          }
        throw exc;
      }
    }    
  }  
  
  class Entry<T> {
    /**
     * Initialize a new Entry with the given key and value.
     */
   /*@
     @ // The key of this Entry is set to <key>.
     @ post getKey() == key;
     @ // The value of this Entry is set to <value>.
     @ post getValue() == value;
     @*/
    public /*@ pure @*/ Entry(T key, Object value) {
      _key=key;
      _value=value;
    }
    
    /**
     * Return the key of this Entry.
     */
    public /*@ pure @*/ T getKey() {
      return _key;
    }

    /**
     * Return the value of this Entry.
     */
    public /*@ pure @*/ Object getValue() {
      return _value;
    }

    /**
     * The key of this Entry.
     */
    private T _key;
    
    /**
     * The value of this Entry.
     */
    private Object _value;
  }
}


