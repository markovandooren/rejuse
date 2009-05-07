package org.rejuse.java.collections.test;

import java.util.HashMap;
import java.util.Map;

import org.rejuse.java.collections.MapVisitor;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

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
