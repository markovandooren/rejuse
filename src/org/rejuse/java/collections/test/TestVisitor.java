package org.rejuse.java.collections.test;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import org.rejuse.java.collections.Collections;
import org.rejuse.java.collections.Visitor;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestVisitor extends JutilTest{
  
  public TestVisitor(String name) {
    super(name, new CVSRevision("1.19"));
  }
  
  private Vector $integers;

  private Vector $intVector;
  
  private Vector $doubleIntVector;  
  
  private Vector $otherIntVector;  

  private MyInt[][] $intArray;
  
   private MyInt[][] $otherIntArray;  
  
//   private MyInt[][] getIntArray() {
//     return $intArray;
//   }
   
  public void setUp() {
    $integers=new Vector();
    for(int i = 0; i<10; i++) {
      $integers.add(new Integer(i));
    }
    
    $intVector = new Vector();
    $intArray = new MyInt[10][10];
    for(int i = 1; i<=10; i++) {
      for(int j = 1; j<=10; j++) {
        $intArray[i-1][j-1]=new MyInt((i-1)*10 + j);
        $intVector.add(new MyInt((i-1)*10 + j));
      }
    }
    
    $otherIntVector = new Vector();
    $doubleIntVector = new Vector();
    $otherIntArray = new MyInt[10][10];
    for(int i = 1; i<=10; i++) {
      for(int j = 1; j<=10; j++) {
        $otherIntArray[i-1][j-1]=new MyInt((i-1)*10 + j -1);
        $otherIntVector.add(new MyInt((i-1)*10 + j -1));
        $doubleIntVector.add(new MyInt(2 * ((i-1)*10 + j)));
      }
    }
    // $otherIntArray[i][j] == $intArray[i][j] - 1;
  }
  
  public void testApplyTo() {
    // Add all integerts in $integerSet to a new Vector and
    // check whether both vectors are equal.
    final Vector otherIntegerSet = new Vector();
    Collection clone = new HashSet($integers);
    new Visitor() {
      public void visit(Object element) {
        otherIntegerSet.add(element);
      }
    }.applyTo($integers);
    assertEquals($integers,otherIntegerSet);
    // Check whether the collection has been altered.
    assertTrue(Collections.identical($integers,otherIntegerSet));
    
    // passing null as an argument should do nothing
    Collection nullCollection = null;
    try {
      new Visitor() {
        public void visit(Object element) {
          otherIntegerSet.add(element);
        }
      }.applyTo(nullCollection);
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    
    //pass an empty collection
    try {
      new Visitor() {
        public void visit(Object element) {
          otherIntegerSet.add(element);
        }
      }.applyTo(new HashSet());
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    
    //pass a collection containing only null references
    Vector stupidVector=new Vector();
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }
    
    try {
      new Visitor() {
        public void visit(Object element) {
          otherIntegerSet.add(element);
        }
      }.applyTo(stupidVector);
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    
  }
  
  public void testApplyToArray() {
    new Visitor() {
      public void visit(Object element) {  
        MyInt integer = (MyInt) element;
        integer.setInt(integer.getInt() - 1);
      }
    }.applyTo($intArray);
    for(int i=0;i<10;i++) {
      for(int j=0;j<10;j++) {
        assertEquals($intArray[i][j], $otherIntArray[i][j]);
      }
    }
    // passing null as an argument should do nothing
    Object[] nullArray = null;
    try {
      new Visitor() {
        public void visit(Object element) {  
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() - 1);
        }
      }.applyTo(nullArray);
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    // empty array
    Object[][] emptyArray = new Object[13][45];
    try {
      new Visitor() {
        public void visit(Object element) {  
          //NOP since elements are null
        }
      }.applyTo(emptyArray);
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    
    emptyArray = new Object[13][0];
    try {
      new Visitor() {
        public void visit(Object element) {  
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() - 1);
        }
      }.applyTo(emptyArray);
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    
  }

  public void testApplyToIterator() {
    Iterator iter = $intVector.iterator();
    new Visitor() {
      public void visit(Object element){
        MyInt integer = (MyInt) element;
        integer.setInt(integer.getInt() - 1);
      }
    }.applyTo(iter);
    assertTrue(! iter.hasNext());
    assertEquals($intVector,$otherIntVector);
    Iterator nullIterator = null;
    try {
      new Visitor() {
        public void visit(Object element){
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() - 1);
        }
      }.applyTo(nullIterator);
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    // pass an iterator for an empty collection.
    try {
      new Visitor() {
        public void visit(Object element){
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() - 1);
        }
      }.applyTo(new HashSet().iterator());
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    

    // pass an iterator for a collection with only
    // null references.
    Vector stupidVector=new Vector();
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }  
    try {
      new Visitor() {
        public void visit(Object element){
          //NOP of course
        }
      }.applyTo(stupidVector.iterator());
    }
    catch(Exception exc) {
        assertTrue(false);
    }            
  }
  
  public void testApplyToEnumeration() {
    Enumeration enumerator = $intVector.elements();
    new Visitor() {
      public void visit(Object element){
        MyInt integer = (MyInt) element;
        integer.setInt(integer.getInt() * 2);
      }
    }.applyTo(enumerator);    
    assertTrue(! enumerator.hasMoreElements());
    assertEquals($intVector,$doubleIntVector);
    // null as an argument should do nothing
    try {
      new Visitor() {
        public void visit(Object element){
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() * 2);
        }
      }.applyTo($intVector.elements());        
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    // pass an enumeration for an empty collection.
    try {
      new Visitor() {
        public void visit(Object element){
          MyInt integer = (MyInt) element;
          integer.setInt(integer.getInt() - 1);
        }
      }.applyTo(new Vector().elements());
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    

    // pass an iterator for a collection with only
    // null references.
    Vector stupidVector=new Vector();
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }  
    try {
      new Visitor() {
        public void visit(Object element){
          //NOP of course
        }
      }.applyTo(stupidVector.elements());
    }
    catch(Exception exc) {
        assertTrue(false);
    }              
  }
    
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
  
  public void tearDown() {
    //nothing actually
  }
}

