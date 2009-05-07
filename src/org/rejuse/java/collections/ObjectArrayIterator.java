package org.rejuse.java.collections;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import org.rejuse.java.collections.Arrays;
/**
 * A class of iterators for multi-dimensional arrays of objects.
 * The traversal of the array is depth-first.
 *
 * You can, but should not use this class in your code. Using the Visitor class
 * is a better way of visiting all elements of a multi-dimensional array.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class ObjectArrayIterator<T> {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  // MvDMvDMvD:
  // This class does not implements the iterator interface because
  // you can't remove an element from array
  // The Enumeration interface isn't implemented either:
  // I don't like the next() method, and you should use
  // the Visitor class anyway.

 /*@
   @ // The array of an ObjectArrayIterator is not null.
   @ public invariant getArray() != null;
   @*/
  
  /**
   * Initialize a new ObjectArrayIterator with the given array of objects
   *
   * @param array
   *        an array of objects
   *
   * @excep ZeroDimensionException
   *        The given array has some dimension equal to 0.
   *        (\exists int i; i >= 0 && i < Arrays.getArrayDimensions(array).length; 
   *           Arrays.getArrayDimensions(array)[i] == 0);
   */
 /*@
   @ // <arra> may not be null.
   @ pre array != null;
   @
   @ // The array of this ObjectArrayIterator will be set to <array>
   @ post getArray() == array;
   @ // The iterator will be positioned at the start of the array.
   @ post atStart() == true;
   @*/
  public ObjectArrayIterator(Object[] array) throws ZeroDimensionException {
    _array=array;
    _cursor=new ArrayCursor(array);
  }
  
  /**
   * Check whether this iterator is positioned at the beginning
   * of its array
   *
   * Result
   *
   *     True if this iterator is positioned at the beginning of
   *     its array, false otherwise.
   */
  public /*@ pure @*/ boolean atStart(){
    return _cursor.atStart();
  }

  /**
   * Return the array of this ObjectArrayIterator.
   */
  public /*@ pure @*/ Object[] getArray() {
    return _array;
  }
    
  /**
   * Check whether this iterator is positioned at the end
   * of its array
   *
   * Result
   *
   *     True if this iterator is positioned at the end of
   *     its array, false otherwise.
   */
  public /*@ pure @*/ boolean atEnd(){
    return _cursor.atEnd();
  }
  
  /**
   * Go to the next element of the array
   *
   * Effects
   *
   *    If the iterator wasn't at the end of the array,
   *    the iterator will be positioned at the next element.
   *    First the deepest dimension will be increased, the first dimension
   *    will be increased as last (depth-first).
   *
   * Exceptions
   *
   *    NoSuchElementException
   *       The iterator is at the end of the iteration
   *          atEnd()
   */
  public void next() throws NoSuchElementException {
    _cursor.next();
  }
  
  /**
   * Go to the previous element of the array
   *
   * Effects
   *
   *    If the iterator wasn't at the start of the array,
   *    the iterator will be positioned at the previous element.
   *    First the deepest dimension will be decreased, the first dimension
   *    will be decreased as last.
   *
   * Exceptions
   *
   *    NoSuchElementException
   *       The iterator is at the start of the iteration
   *          atStart()
   */
  public void previous() throws NoSuchElementException {
    _cursor.previous();
  }
  
  /**
   * Set this iterator to the beginning of the array.
   *
   * Effects
   *
   *    The iterator will be positioned at the beginning of the array.
   *       atStart()==true
   */
  public void toStart(){
    _cursor.toStart();
  }
  
  /**
   * Set this iterator to the end of the array.
   *
   * Effects
   *
   *    The iterator will be positioned at the end of the array.
   *       atEnd()==true
   */
  public void toEnd(){
    _cursor.toEnd();
  }

  /**
   * Return the element of the array at the current
   * position of the iterator.
   *
   * Result
   *
   *    The element of the array at the current
   *    position of the iterator.
   */
  public /*@ pure @*/ T getElement() {
    Object array=_array;
    try{
      int[] cursor=_cursor.getCursor();
      for(int i=0;i<cursor.length;i++){
        array=Array.get(array,cursor[i]);
      }
    }
    catch(ArrayIndexOutOfBoundsException exc){
      System.out.println("Array error");
      System.out.println("Array type : "+Arrays.getArrayType(_array));
      System.out.println("tostring : "+_array.toString());
      System.out.println("number of elements"+_array.length);
      throw exc;
    }
    // array is the object.
    return (T) array;
  }
  
  /**
   * Set the element of the array at the current
   * position of the iterator to the given element.
   *
   * @param element 
   *        the new element that will be placed
   *        at the current position.
   *
   * Effects
   *
   *    The element of the array at the current
   *    position of the iterator will be set to
   *    the given element.
   */
  public void setElement(Object element) {
    // first retrieve the deepest array
    Object array=_array;
    int[] cursor=_cursor.getCursor();
    for(int i=0;i<cursor.length-1;i++){
      array=Array.get(array,cursor[i]);
    }
    Array.set(array,cursor[cursor.length-1],element);
  }
  
  
 /*@
   @ //The current cursor of this iterator
   @
   @ // The array represented by the cursor is the array
   @ // iterated over by this ObjectArrayIterator.
   @ private invariant _cursor.array == _array;
   @*/
  private ArrayCursor _cursor;
  
  
  /**
   * The array of the ObjectArrayIterator
   */
 /*@
   @ private invariant _array != null;
   @*/
  private Object[] _array;
}
/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
