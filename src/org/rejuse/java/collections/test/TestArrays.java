package org.rejuse.java.collections.test;
import org.rejuse.java.collections.Arrays;
import org.rejuse.java.collections.ZeroDimensionException;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
 
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
/*
 * <copyright>Copyright (C) 1997-2001. This software is copyrighted by 
 * the people and entities mentioned after the "@author" tags above, on 
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
