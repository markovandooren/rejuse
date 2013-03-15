package be.kuleuven.cs.distrinet.rejuse.java.collections;

import java.util.NoSuchElementException;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Arrays;

/**
 * A class of objects that point to a certain index in
 * a multi-dimensional array.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class ArrayCursor {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
   @ // An ArrayCursor can not have null as dimensions.
   @ public invariant getDimensions() != null;
   @
   @ // Dimensions can only have positive sizes.
   @ public invariant (\forall int i; i >= 0 && i <getDimensions().length;
   @                     getDimensions()[i] > 0);
   @
   @ // The dimensions of a cursor are the dimension of its array.
   @ public invariant getDimensions().equals(Arrays.getArrayDimensions(array));
   @
   @ // An ArrayCursor can not have null as cursor.
   @ public invariant getCursor() != null;
   @
   @ // The cursor of an ArrayCursor has the same number of elements as
   @ // there are dimensions
   @ public invariant getCursor().length == getDimensions().length;
   @
   @ // An ArrayCursor can only point to a valid index for the
   @ // represented array
   @ public invariant (\forall int i; i >= 0 && i <getCursor().length;
   @                     (getCursor()[i] >= 0) && (getCursor()[i] < getDimensions()[i]));
   @*/  
  
 /*@
   @ public model Object[] array;
   @*/
  
  /**
   * Initialize a new ArrayCursor for a given array of objects.
   *
   * @excep ZeroDimensionException
   *        The given array has some dimension equal to 0.
   *        (\exists int i; i >= 0 && i < Arrays.getArrayDimensions(array).length; 
   *           Arrays.getArrayDimensions(array)[i] == 0);
   */
 /*@
   @ // array may not be null.
   @ pre array != null;
   @
   @ // The dimensions of the new ArrayCursor are set to the
   @ // dimensions of <theArray>.
   @ post getDimensions().equals(Arrays.getArrayDimensions(theArray));
   @ // The array of this ArrayCursor is set to array
   @ post array == theArray;
   @*/
  public ArrayCursor(Object[] theArray) throws ZeroDimensionException {
    _dimensions=Arrays.getArrayDimensions(theArray);
    for(int i=0; i<_dimensions.length; i++) {
      if(_dimensions[i] == 0) {
        throw new ZeroDimensionException(theArray,"Trying to construct an ArrayCursor with an array that has one dimension equal to 0.");
      }
    }
    _cursor = new int[_dimensions.length];
  }
  
  /**
   * Return the number of dimensions of the array
   */
 /*@
   @ // Returns the number of dimensions of the represented array.
   @ post \result == getDimensions().length;
   @*/
  public /*@ pure @*/ int getNbDimensions() {
    return _dimensions.length;
  }
  
  /**
   * Return the dimensions of the array of this cursor.
   */
  public /*@ pure @*/ int[] getDimensions() {
    int[] result = new int[_dimensions.length];
    for(int i=0; i < _dimensions.length; i++) {
      result[i]=_dimensions[i];
    }
    return result;
  }
  
  /**
   * Return the index this ArrayCursor is pointing at.
   */
  public /*@ pure @*/ int[] getCursor() {
    int[] result = new int[_cursor.length];
    for(int i=0; i < _cursor.length; i++) {
      result[i]=_cursor[i];
    }
    return result;
  }
  
  /**
   * Check whether this cursor points to the beginning of the array.
   */
 /*@
   @ // True if the cursor only contains 0's.
   @ post \result == (\forall int i; i>=0 && i < getCursor().length;
   @                   getCursor()[i]==0);
   @*/
  public /*@ pure @*/ boolean atStart() {
    boolean result=true;
    for(int i=0;i<_cursor.length;i++) {
      if(_cursor[i] != 0) {
        result=false;
      }
    }
    return result;
  }

  /**
   * Check whether this cursor points to the end of the array.
   */
 /*@
   @ // True if the elements in the cursor are equal to
   @ // the maximum size of their dimension - 1 or if
   @ // at least 1 dimension has size 0
   @ post \result == (\forall int i; i>=0 && i < getCursor().length;
   @                   getCursor()[i]==getDimensions()[i]-1) ||
   @                 (\exists int i; i>=0 && i < getDimensions().length;
   @                   getDimensions()[i]==0);
   @*/
  public /*@ pure @*/ boolean atEnd() {
    // if the dimensions contain a 0, the result is true.
    for(int i=0;i<_dimensions.length;i++) {
      if(_dimensions[i] == 0) {
        return true;
      }
    }
    boolean result=true;
    for(int i=0;i<_cursor.length;i++) {
      if(_cursor[i] != _dimensions[i] - 1) {
        result=false;
      }
    }
    return result;
  }
  
  /**
   * Set this cursor to the next element in the array.
   *
   * @excep NoSuchElementException
   *        The cursor already points to the end of the array.
   *      | atEnd()
   */
 /*@
   @ // The cursor is set to the next element.
   @*/ 
  public void next() throws NoSuchElementException {
    if(atEnd()){
      throw new NoSuchElementException();
    }
    int dimensions = getNbDimensions();
    boolean ok=false;
    // dim goes from dimensions - 1 to 0
    for(int dim=dimensions-1; (dim>=0) && (! ok); dim--) {
      if ((_cursor[dim]+1) < _dimensions[dim]){
        _cursor[dim]=_cursor[dim]+1;
        ok=true;
      }
      else {
        // If the index of this dimension cannot be increased,
        // it has to be set to zero.
        _cursor[dim]=0;
      }
    }
    // MvDMvDMvD : remove the following code.
    if(! ok) {
        System.out.println("Error in ArrayCursor.next()");
        throw new Error();
    }
  }

  /**
   * Set this cursor to the previous element in the array.
   *
   * @excep NoSuchElementException
   *        The cursor already points to the start of the array.
   *      | atStart()
   */
 /*@
   @ // The cursor is set to the previous element.
   @*/ 
  public void previous() throws NoSuchElementException {
    if(atStart()){
      throw new NoSuchElementException();
    }
    int dimensions = getNbDimensions();
    boolean ok=false;
    for(int dim=dimensions-1; (dim>=0) && (! ok); dim--) {
      if ((_cursor[dim]-1) >= 0){
        _cursor[dim]=_cursor[dim]-1;
        ok=true;
      }
      else {
        _cursor[dim]=_dimensions[dim]-1;
      }
    }
  }  

  /**
   * Set this ArrayCursor to the beginning of the array.
   */
 /*@
   @ // The iterator will be positioned at the beginning of the array.
   @ post atStart()==true;
   @*/
  public void toStart(){
    /*<jdk>*/
    /*<jdk1.1.8>
     for(int i=0; i<_cursor.length;i++) {
       _cursor[i]=0;
     }
     </jdk1.1.8>*/
    /*<jdk1.3>*/
    java.util.Arrays.fill(_cursor,0);  
    /*</jdk1.3>*/    
    /*</jdk>*/
  }  

  /**
   * Set this ArrayCursor to the end of the array.
   */
 /*@
   @ // The iterator will be positioned at the end of the array.
   @ post atEnd()==true;
   @*/
  public void toEnd(){
    for(int i=0; i<_cursor.length;i++) {
      _cursor[i]=_dimensions[i]-1;
    }
  }  
  
 /*@
   @ // The dimensions of the array of this ArrayCursor.
   @
   @ // The array is not null.
   @ private invariant _dimensions != null;
   @
   @ // The length of the array equals the number of dimensions of the array.
   @ private invariant _dimensions.length==Arrays.getArrayDimensions(array).length;
   @
   @ // The array contains the dimensions of array
   @ private invariant (\forall int i; i >=0 && i < array.length;
   @          _dimensions[i]==Arrays.getArrayDimensions(array)[i]);
   @*/
  private int[] _dimensions;
  
 /*@
   @ // The array representing the cursor of this ArrayCursor.
   @
   @ // The array is not null.
   @ private invariant _cursor != null;
   @
   @ // An ArrayCursor can only point to a valid index for the
   @ // represented array
   @ private invariant (\forall int i; i >= 0 && i <_cursor.length;
   @                     (_cursor[i] >= 0) && (_cursor[i] <= _dimensions[i]));  
   @*/
  private int[] _cursor;
}


