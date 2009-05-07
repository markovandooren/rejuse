package org.rejuse.java.collections.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.rejuse.java.collections.Collections;
import org.rejuse.java.collections.ForAll;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestForAll extends JutilTest {

  private HashSet $hashSet;
  
  public TestForAll(String name) {
    super(name,new CVSRevision("1.19"));
  }
  
  public void setUp() {
    $hashSet = new HashSet();
    for(int i=0; i<10; i++) {
      $hashSet.add(new Integer(i));
    }
    
  }
  
  public void testForAll() {
    Collection clone = new Vector($hashSet);
    boolean bool = new ForAll() {
                     public boolean criterion(Object element) {
                        return ((Integer)element).intValue() == 7;
                     }
                   }.in($hashSet);
    assertTrue(! bool);
    assertTrue(Collections.identical($hashSet,clone));
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() <= 10;
             }
           }.in($hashSet);
    assertTrue(bool);
    assertTrue(Collections.identical($hashSet,clone));
    Collection nullCollection = null;
    // shouldn't throw an exception
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() == 17;
             }
    }.in(nullCollection);
    Vector stupidVector = new Vector();
    for(int i=0;i<37;i++) {
      stupidVector.add(null);
    }
    // just for fun a vector containing only null elements.
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return element == new Object();
             }
    }.in(stupidVector);
    assertTrue(! bool);
    // an empty collection
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() == 17;
             }
    }.in(new ArrayList());
    assertTrue(bool);
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
