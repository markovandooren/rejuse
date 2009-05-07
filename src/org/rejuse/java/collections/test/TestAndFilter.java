package org.rejuse.java.collections.test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.rejuse.java.collections.AndFilter;
import org.rejuse.java.collections.Exists;
import org.rejuse.java.collections.Filter;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
 
/* 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestAndFilter extends JutilTest {
  public TestAndFilter(String name) {
    super(name, new CVSRevision("1.11"));
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
    Filter trueFilter = new Filter() {
      public boolean criterion(Object element) {
        return true;
      }
    };
  

    Filter falseFilter = new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    };

    Filter smallerThan30 = new Filter() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()<30;
      }
    };
    Filter biggerThan19 = new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() >= 20;
      }
    };

    Filter[] filters = new Filter[2];
    filters[0] = smallerThan30;
    filters[1] = biggerThan19;
    
    new AndFilter(filters).retain($intVector);
    
    boolean bool = new Exists() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()>=30 || ((Integer)element).intValue()<20;
      }
    }.in($intVector);
    assertTrue(! bool);
    assertTrue($intVector.size() == 10);    
    
    // reset the set
    setUp();
    new AndFilter(filters).discard($intVector);
    assertTrue($intVector.size() == 27);        
    
    // empty collections
    new AndFilter(filters).retain(new HashSet());

    new AndFilter(filters).discard(new Vector());
    
    // collection containing only null references
    Vector stupidVector=new Vector();
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }
    Filter[] otherFilters = new Filter[2];
    otherFilters[0]=falseFilter;
    otherFilters[1]=trueFilter;
    
    new AndFilter(otherFilters).retain(stupidVector);
    
    assertTrue(stupidVector.size() == 0);
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }
    otherFilters[1]=falseFilter;
    new AndFilter(otherFilters).discard(stupidVector);
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
