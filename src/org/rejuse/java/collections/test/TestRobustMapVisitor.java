package org.rejuse.java.collections.test;
import java.util.HashMap;
import java.util.Map;

import org.rejuse.java.collections.Collections;
import org.rejuse.java.collections.MapVisitor;
import org.rejuse.java.collections.RobustMapVisitor;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestRobustMapVisitor extends JutilTest {
  
  public TestRobustMapVisitor(String name) {
    super(name, new CVSRevision("1.9"));
  }
  
  private Map $map;
  
  public void setUp() {
    $map = new HashMap();
    for(int i=0; i<37;i++) {
      $map.put(new MyInt(i), new MyInt(i+1));
    }
  }
  
  
  public void testMapping() {
    Map clone = new HashMap($map);
    try {
      new RobustMapVisitor() {
        public Object visit(Object key, Object value) throws Exception {
          if(((MyInt)value).getInt() == 20) {
            throw new IllegalArgumentException();
          }
          MyInt myint = ((MyInt)value);
          myint.setInt(myint.getInt() + 1);
          return value;
        }
        public void unvisit(Object key, Object value, Object undoData) {
          MyInt myint = ((MyInt)value);
          myint.setInt(myint.getInt() - 1);
        }
      }.applyTo($map);
    }
    catch(IllegalArgumentException exc) {
    }
    catch(Throwable th) {
      th.printStackTrace();
      assertTrue(false);
    }
    assertTrue(Collections.identical($map,clone));    

    try {
      new RobustMapVisitor() {
        public Object visit(Object key, Object value) throws Exception {
          if(((MyInt)value).getInt() == 20) {
            throw new IllegalArgumentException();
          }
          $map.remove(key);
          $map.put(key,new Integer(((Integer)value).intValue() + 1));
          return value;
        }
        public void unvisit(Object key, Object value, Object undoData) {
          $map.remove(key);
          $map.put(key,undoData);
        }
      }.applyTo(null);
    }

    catch(IllegalArgumentException exc) {
      assertTrue(false);
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    assertTrue(Collections.identical($map,clone));            
    
    try {
      new RobustMapVisitor() {
        public Object visit(Object key, Object value) throws Exception {
          MyInt myint = ((MyInt)value);
          myint.setInt(myint.getInt() + 1);
          return value;
        }
        public void unvisit(Object key, Object value, Object undoData) {
        }
      }.applyTo($map);
    }

    catch(IllegalArgumentException exc) {
      assertTrue(false);      
    }
    catch(Throwable th) {
      assertTrue(false);
    }
    new MapVisitor() {
      public void visit(Object key, Object value) {
        MyInt int1 = (MyInt) key;
        MyInt int2 = (MyInt) value;
        if(int1.getInt() != int2.getInt() - 2) {
          TestRobustMapVisitor.this.assertTrue(false);
        }
      }
    }.applyTo($map);
    
    
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
