package org.aikodi.rejuse.java.collections.test;
import org.aikodi.rejuse.java.collections.Arrays;
import org.aikodi.rejuse.java.collections.ZeroDimensionException;
import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;
 
/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestArrays extends JutilTest {
  
  public TestArrays(String name) {
    super(name, new CVSRevision("1.8"));
  }
  
  private Integer[][][][] $integers;
  private Integer[][][][] $zeroDimIntegers;
    
  public void setUp() {
    $integers = new Integer[3][4][4][35];
    $zeroDimIntegers = new Integer[3][4][0][35];
  }
  
  public void testNewArray() {
    Object[] cloneArray = Arrays.newArray($integers);
    assertEquals($integers.getClass().getName(),cloneArray.getClass().getName());
    // array with 0 as dimension.
    try {
      cloneArray = Arrays.newArray($zeroDimIntegers);
      assertTrue(false);
    }
    catch(ZeroDimensionException exc) {
    }
  }
  
  public void testGetArrayDimensions() {
    int[] dims = Arrays.getArrayDimensions($integers);
    assertTrue(dims[0] == 3);
    assertTrue(dims[1] == 4);
    assertTrue(dims[2] == 4);
    assertTrue(dims[3] == 35);
    assertTrue(dims.length == 4);
    // array with zero as dimension.
    try {
      dims = Arrays.getArrayDimensions($zeroDimIntegers);
    }
    catch(ZeroDimensionException exc) {
    }    
  }
  
  public void testGetArrayType() {
    Class type=Arrays.getArrayType($integers);
    assertTrue(type == Integer.class);
    int[] ints = new int[45];
    type=Arrays.getArrayType(ints);
    assertTrue(type == Integer.class);
    
  }
  
  public void getIsObjectArray() {
    assertTrue(Arrays.isObjectArray($integers));
    int[] ints = new int[45];
    assertTrue(! Arrays.isObjectArray(ints));
    assertTrue(! Arrays.isObjectArray(new Object()));
    assertTrue(Arrays.isObjectArray(Arrays.getArrayType($integers)));
    Class nullClass = null;
    Object nullObject = null;
    assertTrue(! Arrays.isObjectArray(nullClass));
    assertTrue(! Arrays.isObjectArray(nullObject));
  }
} 

