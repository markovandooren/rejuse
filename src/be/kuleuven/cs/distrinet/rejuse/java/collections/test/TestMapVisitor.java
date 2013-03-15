package be.kuleuven.cs.distrinet.rejuse.java.collections.test;

import java.util.HashMap;
import java.util.Map;

import be.kuleuven.cs.distrinet.rejuse.java.collections.MapVisitor;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestMapVisitor extends JutilTest{
  
  public TestMapVisitor(String name) {
    super(name, new CVSRevision("1.10"));
  }
  
  private Map $map;
  
  public void setUp() {
    $map = new HashMap();
    for(int i=0; i<37; i++) {
      $map.put(new Integer(i), new Integer(i*i));
    }
  }
  
  
  public void testApplyTo() {
    
    // Add all entries in $map to a new Map and
    // check whether both are equal.
    final Map otherMap = new HashMap();
    new MapVisitor() {
      public void visit(Object key, Object value) {
        otherMap.put(key,value);
      }
    }.applyTo($map);
    assertEquals($map,otherMap);
    
    // Check whether the map has been altered.
    // assertTrue(Collections.identical($integers,otherIntegerSet));
    
    // passing null as an argument should do nothing
    Map nullMap = null;
    try {
      new MapVisitor() {
        public void visit(Object key, Object value) {
          TestMapVisitor.this.assertTrue(false);
        }
      }.applyTo(nullMap);
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    
    //pass an empty collection
    try {
      new MapVisitor() {
        public void visit(Object key, Object value) {
          TestMapVisitor.this.assertTrue(false);
        }
      }.applyTo(new HashMap());
    }
    catch(Exception exc) {
        assertTrue(false);
    }
    
    //pass a collection containing only null references
//     Vector stupidVector=new Vector();
//     for(int i=0; i<100;i++) {
//       stupidVector.add(null);
//     }
//     
//     try {
//       new Visitor() {
//         public void visit(Object element) {
//           otherIntegerSet.add(element);
//         }
//       }.applyTo(stupidVector);
//     }
//     catch(Exception exc) {
//         assertTrue(false);
//     }
    
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

