package org.rejuse.java.collections.test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.rejuse.java.collections.Collections;
import org.rejuse.java.collections.Exists;
import org.rejuse.java.collections.Filter;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
 
/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestFilter extends JutilTest {
  public TestFilter(String name) {
    super(name, new CVSRevision("1.13"));
  }
  
  private Vector $intVector;
  public void setUp() {
    $intVector = new Vector();
    for(int i=0; i<37; i++) {
      $intVector.add(new Integer(i));
    }
  }
  
  public void testFilter() {
    Collection clone = new ArrayList($intVector);
    new Filter() {
      public boolean criterion(Object element) {
        return true;
      }
    }.retain($intVector);
    assertTrue(Collections.identical($intVector,clone));
  

    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.discard($intVector);
    assertTrue(Collections.identical($intVector,clone));

    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()<30;
      }
    }.retain($intVector);
    assertTrue($intVector.size()==30);
    boolean bool = new Exists() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()>=30;
      }
    }.in($intVector);
    assertTrue(! bool);
    
    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() < 20;
      }
    }.discard($intVector);
    
    assertTrue($intVector.size() == 10);
    bool = new Exists() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()<20;
      }
    }.in($intVector);
    assertTrue(! bool);    
    
    
    // null as argument
    Collection nullCollection = null;    
    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.retain(nullCollection);

    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.discard(nullCollection);
    
    // empty collections
    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() >= 20;
      }
    }.retain(new HashSet());

    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() >= 20;
      }
    }.discard(new Vector());
    
    // collection containing only null references
    Vector stupidVector=new Vector();
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }
    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.retain(stupidVector);
    assertTrue(stupidVector.size() == 0);
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }
    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.discard(stupidVector);
    assertTrue(stupidVector.size() == 100);

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
