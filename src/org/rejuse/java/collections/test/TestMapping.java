package org.rejuse.java.collections.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import org.rejuse.java.collections.Collections;
import org.rejuse.java.collections.Mapping;
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
public class TestMapping extends JutilTest {

  private Vector $integers;
  
  
  public TestMapping(String name) {
    super(name, new CVSRevision("1.13"));
  }
  
  public void setUp() {
    $integers = new Vector();
    for(int i=0;i < 37; i++) {
      $integers.add(new Integer(i));
    }
  }
  
  public void testApplyTo() {
    new Mapping() {
      public Object mapping(Object element) {
        return new Integer(((Integer) element).intValue() + 1);
      }
    }.applyTo($integers);
    new Visitor() {
      private int $number=1;
      public void visit(Object element) {
        TestMapping.this.assertTrue(((Integer)element).intValue() == $number);
        $number++;
      }
    }.applyTo($integers);
  }
  
  public void testApplyFromTo() {
    setUp();
    ArrayList list = new ArrayList($integers);
    ArrayList newIntegers = new ArrayList();
    Collection col = new Mapping() {
      public Object mapping(Object element) {
        return new Integer(((Integer) element).intValue() + 1);
      }
    }.applyFromTo($integers,newIntegers);
    assertTrue(Collections.identical($integers,list));
    assertTrue(col==newIntegers);
    new Visitor() {
      private int $number=1;
      public void visit(Object element) {
        TestMapping.this.assertTrue(((Integer)element).intValue() == $number);
        $number++;
      }
    }.applyTo(newIntegers);
    assertTrue($integers.size() == newIntegers.size());
    
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
