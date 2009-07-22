package org.rejuse.java.collections.test;
import java.util.HashSet;
import java.util.Set;

import org.rejuse.java.SafeAccumulator;
import org.rejuse.java.collections.Collections;
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

public class TestAccumulator extends JutilTest {
  
  public TestAccumulator(String name) {
    super(name, new CVSRevision("1.14"));
  }
  
  private Set $set;
  private Set $cloneSet;
  
  public void setUp() {
    $set = new HashSet();
    $cloneSet = new HashSet();    
    for(int i=1; i <=100;i++) {
      Integer integer = new Integer(i);
      $set.add(integer);
      $cloneSet.add(integer);
    }
    // sum is n*(n+1)/2 = 5050
  }
  public void testAccumulateSet() {
    Set clone = new HashSet($set);
    Integer sum = (Integer) new SafeAccumulator() {
                              public Object initialAccumulator() {
                                return new Integer(0);
                              }

                              public Object accumulate(Object element, Object acc) {
                                return new Integer( ((Integer) element).intValue() +
                                                    ((Integer) acc).intValue());
                              }
                          }.accumulate($set);
    assertEquals(sum, new Integer(5050));
    assertEquals($set,$cloneSet);
    // Check whether $set has changed. 
   assertTrue(Collections.identical($set,$cloneSet));
   assertTrue(Collections.identical($set,clone));
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
