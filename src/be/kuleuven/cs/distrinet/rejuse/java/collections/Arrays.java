package be.kuleuven.cs.distrinet.rejuse.java.collections;
import java.lang.reflect.Array;

/**
 * A class with static methods for arrays
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @author  Jan Dockx
 * @release $Name$
 */

public abstract class Arrays {

  //MvDMvDMvD: not all multi-dimensional arrays are cubical
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /**
   * Return a new array of the same type and dimension
   * as the given array.
   *
   * @param array
   *        an array that will be "cloned"
   *
   * @excep ZeroDimensionException
   *        (* The array has one dimension equal to 0 *)
   */
 /*@
   @ public behavior
   @
   @ // array may not be null
   @ pre array != null;
   @
   @ // The returned array is not null
   @ post \result != null;
   @ // The returned array has the same type as array.
   @ post getArrayType(\result) == getArrayType(array);
   @ // The returned array has the same dimensions as array.
   @ post (\forall int i; i >= 0 && i < getArrayDimensions(array).length;
   @         getArrayDimensions(\result)[i] == getArrayDimensions(array)[i]);
   @*/
  public static /*@ pure @*/ Object[] newArray(Object[] array) throws ZeroDimensionException {
    return (Object[]) Array.newInstance(getArrayType(array),getArrayDimensions(array));
  }


  /**
   * Return the dimensions of the given array
   *
   * @param array 
   *        The array of which the dimensions are requested
   *
   * @return The dimensions of the given array.
   *         The size of the i-th dimension will be
   *         at the (i-1)-th position in the returned
   *         array.
   *
   * @excep ZeroDimensionException
   *        (* The array has one dimension equal to 0 *)
   */
 /*@
   @ public behavior
   @
   @ // array may not be null.
   @ pre array != null;
   @*/
  public static /*@ pure @*/ int[] getArrayDimensions(Object[] array) throws ZeroDimensionException {
    // Parse the classname of the given array.
    String name=array.getClass().getName();
    int nbDimensions=name.lastIndexOf("[")+1;
    int dimension =1;
    int[] dimensions = new int[nbDimensions];
    Object myArray = array;
    dimensions[dimension-1] = Array.getLength(myArray);
    dimension++;
    while (dimension<=nbDimensions){
      try {
        myArray=Array.get(myArray,0);
        dimensions[dimension-1]=Array.getLength(myArray);
        dimension++;
      }
      catch(ArrayIndexOutOfBoundsException exc) {
        // slow in case of an exception but faster
        // when everything is ok
        throw new ZeroDimensionException(array);
      }
    }
    return dimensions;
  }


  /**
   * Return the type of an array
   *
   * @param array 
   *        The array of which the type must be determined
   *
   * @return The type of the given array. In case of a primitive 
   *         type, the wrapper class will be returned
   */
 /*@
   @ public behavior
   @
   @ // array may not be null.
   @ pre array != null;
   @ // array must be an array.
   @ pre array.getClass().isArray();
   @*/
  public static /*@ pure @*/ Class getArrayType(Object array){
    try{
      if((array==null)||(! array.getClass().isArray())){
        throw new IllegalArgumentException();
      }
      String name=array.getClass().getName();
      int pos = name.lastIndexOf("[");
      char type = name.charAt(pos+1);
      Class clazz=null;
      switch(type){
        case 'B' : clazz=Byte.class;break;
        case 'C' : clazz=Character.class;break;
        case 'D' : clazz=Double.class;break;
        case 'F' : clazz=Float.class;break;
        case 'I' : clazz=Integer.class;break;
        case 'J' : clazz=Long.class;break;
        case 'S' : clazz=Short.class;break;
        case 'Z' : clazz=Boolean.class;break;
        case 'L' : 
        String classname = name.substring(pos+2,name.length()-1);
        clazz=Class.forName(classname);
        break;
      }
      return clazz;
    }
    catch(ClassNotFoundException exc){
      // this really shouldn't happen
      exc.printStackTrace();
      return null;
    }
  }

  /**
   * Check whether the given class is an array of objects
   *
   * @return clazz 
   *         The Class to be checked.
   */
 /*@
   @ public behavior
   @
   @ // False is <clazz> is null.
   @ post (clazz == null) ==> (\result == false);
   @ //Return true if <clazz> is an array of objects.
   @ post \result == clazz.isArray() && 
   @                 (clazz.getName().indexOf("[L") != -1);
   @*/
  public static /*@ pure @*/ boolean isObjectArray(Class clazz){
    if((clazz==null)||(! clazz.isArray())){
      return false;
    }
    String name = clazz.getName();
    // See java docs of Class for stupid name convention
    if(name.indexOf("[L")==-1){
      return false;
    }
    return true;
  }

  /**
   * Check whether the given object is an array of objects
   *
   * @param clazz 
   *        The object to be checked
   *
   * Result
   *
   *     True if the given object is an array of objects.
   */
 /*@
   @ public behavior
   @
   @ // False is <object> is null.
   @ post (object == null) ==> (\result == false);
   @ // True if <object> is an array of objects, false otherwise.
   @ post isObjectArray(object.getClass());
   @*/
  public static /*@ pure @*/ boolean isObjectArray(Object object){
    if(object == null) {
      return false;
    }
    return isObjectArray(object.getClass());
  }
}

