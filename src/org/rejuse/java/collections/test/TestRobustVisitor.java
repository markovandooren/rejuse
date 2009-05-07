package org.rejuse.java.collections.test;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import org.rejuse.java.collections.Collections;
import org.rejuse.java.collections.RobustVisitor;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestRobustVisitor extends JutilTest {
  
  public TestRobustVisitor(String name) {
    super(name, new CVSRevision("1.16"));
  }
  
  private Vector $intVector;
  
  private Vector $origIntVector;  
  
  private Vector $otherIntVector;
  
  private MyInt[][] $intArray;
  
  private MyInt[][] $origIntArray;  
  
  private MyInt[][] $otherIntArray;
  
  
  public void setUp() {
    $intVector = new Vector();
    $origIntVector = new Vector();
    $otherIntVector = new Vector();
    $intArray = new MyInt[10][10];
    $origIntArray = new MyInt[10][10];
    $otherIntArray = new MyInt[10][10];
    for(int i=0; i<100; i++) {
      $intVector.add(new MyInt(i));
      $origIntVector.add(new MyInt(i));
      $otherIntVector.add(new MyInt(i + 1));
    }
    for(int i=0; i<10; i++) {
      for(int j=0; j<10; j++) {
        $intArray[i][j] = new MyInt(i*10 + j);
        $origIntArray[i][j] = new MyInt(i*10 + j);
        $otherIntArray[i][j] = new MyInt(i*10 + j + 1);
      }
    }
    
  }
  
  
  public void testApplyToCollection() {
    // test in case of an exception
    // throw an exception at the 37th element
    // and check whether the end result
    // is equal to $origIntVector.
    Collection clone = null;
    try {
      clone = new HashSet($intVector);
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          if(integer.getInt() == 37) {
            throw new Exception();
          }
          integer.setInt(integer.getInt() + 1);
          return null;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() - 1);
        }
      }.applyTo($intVector);
      // An exception should be thrown
      assertTrue(false);
    }
    catch(Exception exc) {
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    assertEquals($intVector,$origIntVector);    
    assertTrue(Collections.identical($intVector, clone));
    

    
    // now using the undo data.
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          Integer undo = new Integer(integer.getInt());
          if(integer.getInt() == 37) {
            throw new Exception();
          }
          integer.setInt(integer.getInt() + 1);
          return undo;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo($intVector);
      // An exception should be thrown
      assertTrue(false);
    }
    catch(Exception exc) {
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    assertEquals($intVector,$origIntVector);    
    
    // check the normal behaviour
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          Integer undo = new Integer(integer.getInt());
          integer.setInt(integer.getInt() + 1);
          return undo;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo($intVector);
    }
    catch(Exception exc) {
      assertTrue(false);
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    assertEquals($intVector,$otherIntVector);
    Vector nullVector = null;
    // check null as an argument  
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          Integer undo = new Integer(integer.getInt());
          integer.setInt(integer.getInt() + 1);
          return undo;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo(nullVector);
    }
    catch(Exception exc) {
      assertTrue(false);
    }
    catch(Throwable th) {
      assertTrue(false);
    }

    // pass an empty collection as argument
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          Integer undo = new Integer(integer.getInt());
          integer.setInt(integer.getInt() + 1);
          return undo;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo(new HashSet());
    }
    catch(Exception exc) {
      assertTrue(false);
    }
    catch(Throwable th) {
      assertTrue(false);
    }

    Vector stupidVector=new Vector();
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }    
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          return null;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo(stupidVector);
    }
    catch(Exception exc) {
      assertTrue(false);
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    
        
    
//         
//     for(int i=0;i<100;i++) {
//       MyInt first = (MyInt) $intVector.elementAt(i);
//       MyInt second = (MyInt) $origIntVector.elementAt(i);
//       System.out.println(first.getInt()+" -> "+second.getInt());
//     }
  }

  
//#############################################
  public void testApplyToArray() {
    // test in case of an exception
    // throw an exception at the 37th element
    // and check whether the end result
    // is equal to $origIntVector.
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          if(integer.getInt() == 37) {
            throw new Exception();
          }
          integer.setInt(integer.getInt() + 1);
          return null;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() - 1);
        }
      }.applyTo($intArray);
      // An exception should be thrown
      assertTrue(false);
    }
    catch(Exception exc) {
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    for(int i=0;i<10;i++) {
      for(int j=0;j<10;j++) {
        assertEquals($intArray[i][j], $origIntArray[i][j]);
      }
    }    

    
    // now using the undo data.
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          Integer undo = new Integer(integer.getInt());
          if(integer.getInt() == 37) {
            throw new Exception();
          }
          integer.setInt(integer.getInt() + 1);
          return undo;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo($intArray);
      // An exception should be thrown
      assertTrue(false);
    }
    catch(Exception exc) {
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    for(int i=0;i<10;i++) {
      for(int j=0;j<10;j++) {
        assertEquals($intArray[i][j], $origIntArray[i][j]);
      }
    }    
    // check the normal behaviour
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          Integer undo = new Integer(integer.getInt());
          integer.setInt(integer.getInt() + 1);
          return undo;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo($intArray);
    }
    catch(Exception exc) {
      assertTrue(false);
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    for(int i=0;i<10;i++) {
      for(int j=0;j<10;j++) {
        assertEquals($intArray[i][j], $otherIntArray[i][j]);
      }
    }
    Object[] nullArray = null;
    // check null as an argument  
    try {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception {
          MyInt integer = (MyInt) element;
          Integer undo = new Integer(integer.getInt());
          integer.setInt(integer.getInt() + 1);
          return undo;
        }
        
        public void unvisit(Object element,Object undo) {
          MyInt integer = (MyInt) element;
          Integer undoInt = (Integer) undo;
          integer.setInt(undoInt.intValue());
        }
      }.applyTo(nullArray);
    }
    catch(Exception exc) {
      assertTrue(false);
    }
    catch(Throwable th) {
      assertTrue(false);
    }
//         
//     for(int i=0;i<100;i++) {
//       MyInt first = (MyInt) $intVector.elementAt(i);
//       MyInt second = (MyInt) $origIntVector.elementAt(i);
//       System.out.println(first.getInt()+" -> "+second.getInt());
//     }
  }
  
  
  
//########################################  
    
public void testApplyToIterator() {
  // reset the vectors.
  setUp();
  // test in case of an exception
  // throw an exception at the 37th element
  // and check whether the end result
  // is equal to $origIntVector.
  Iterator iter = $intVector.iterator();
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        if(integer.getInt() == 37) {
          throw new Exception();
        }
        integer.setInt(integer.getInt() + 1);
        return null;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        integer.setInt(integer.getInt() - 1);
      }
    }.applyTo(iter);
    // An exception should be thrown
    assertTrue(false);
  }
  catch(Exception exc) {
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  assertEquals($intVector,$origIntVector);    
  
  
  iter = $intVector.iterator();
  // now using the undo data.
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        Integer undo = new Integer(integer.getInt());
        if(integer.getInt() == 37) {
          throw new Exception();
        }
        integer.setInt(integer.getInt() + 1);
        return undo;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        Integer undoInt = (Integer) undo;
        integer.setInt(undoInt.intValue());
      }
    }.applyTo(iter);
    // An exception should be thrown
    assertTrue(false);
  }
  catch(Exception exc) {
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  assertEquals($intVector,$origIntVector);    
  
  iter=$intVector.iterator();
  // check the normal behaviour
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        Integer undo = new Integer(integer.getInt());
        integer.setInt(integer.getInt() + 1);
        return undo;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        Integer undoInt = (Integer) undo;
        integer.setInt(undoInt.intValue());
      }
    }.applyTo(iter);
  }
  catch(Exception exc) {
    assertTrue(false);
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  assertTrue(! iter.hasNext());
  assertEquals($intVector,$otherIntVector);
  Iterator nullIterator = null;
  // check null as an argument  
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        Integer undo = new Integer(integer.getInt());
        integer.setInt(integer.getInt() + 1);
        return undo;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        Integer undoInt = (Integer) undo;
        integer.setInt(undoInt.intValue());
      }
    }.applyTo(nullIterator);
  }
  catch(Exception exc) {
    assertTrue(false);
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  //         
  //     for(int i=0;i<100;i++) {
    //       MyInt first = (MyInt) $intVector.elementAt(i);
    //       MyInt second = (MyInt) $origIntVector.elementAt(i);
    //       System.out.println(first.getInt()+" -> "+second.getInt());
    //     }
}

  //############################################################

  
public void testApplyToEnumeration() {
  // reset the vectors.
  setUp();
  // test in case of an exception
  // throw an exception at the 37th element
  // and check whether the end result
  // is equal to $origIntVector.
  Enumeration enumerator = $intVector.elements();
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        if(integer.getInt() == 37) {
          throw new Exception();
        }
        integer.setInt(integer.getInt() + 1);
        return null;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        integer.setInt(integer.getInt() - 1);
      }
    }.applyTo(enumerator);
    // An exception should be thrown
    assertTrue(false);
  }
  catch(Exception exc) {
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  assertEquals($intVector,$origIntVector);    
  
  
  enumerator = $intVector.elements();
  // now using the undo data.
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        Integer undo = new Integer(integer.getInt());
        if(integer.getInt() == 37) {
          throw new Exception();
        }
        integer.setInt(integer.getInt() + 1);
        return undo;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        Integer undoInt = (Integer) undo;
        integer.setInt(undoInt.intValue());
      }
    }.applyTo(enumerator);
    // An exception should be thrown
    assertTrue(false);
  }
  catch(Exception exc) {
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  assertEquals($intVector,$origIntVector);    
  
  enumerator=$intVector.elements();
  // check the normal behaviour
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        Integer undo = new Integer(integer.getInt());
        integer.setInt(integer.getInt() + 1);
        return undo;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        Integer undoInt = (Integer) undo;
        integer.setInt(undoInt.intValue());
      }
    }.applyTo(enumerator);
  }
  catch(Exception exc) {
    assertTrue(false);
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  assertTrue(! enumerator.hasMoreElements());
  assertEquals($intVector,$otherIntVector);
  Enumeration nullEnumeration = null;
  // check null as an argument  
  try {
    new RobustVisitor() {
      public Object visit(Object element) throws Exception {
        MyInt integer = (MyInt) element;
        Integer undo = new Integer(integer.getInt());
        integer.setInt(integer.getInt() + 1);
        return undo;
      }
      
      public void unvisit(Object element,Object undo) {
        MyInt integer = (MyInt) element;
        Integer undoInt = (Integer) undo;
        integer.setInt(undoInt.intValue());
      }
    }.applyTo(nullEnumeration);
  }
  catch(Exception exc) {
    assertTrue(false);
  }
  catch(Throwable th) {
    assertTrue(false);
  }
  //         
  //     for(int i=0;i<100;i++) {
    //       MyInt first = (MyInt) $intVector.elementAt(i);
    //       MyInt second = (MyInt) $origIntVector.elementAt(i);
    //       System.out.println(first.getInt()+" -> "+second.getInt());
    //     }
}


//############################  
  
      
  class MyInt {
    public MyInt(int integer){
      setInt(integer);
    }
    
    public int getInt(){
      return $int;
    }
    
    public void setInt(int integer){
      $int=integer;
    }
    
    public boolean equals(Object other){
      if(other instanceof MyInt){
        return getInt() == ((MyInt) other).getInt();
      }
      return false;
    }
    
    private int $int;
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
