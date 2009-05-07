package org.rejuse.java.collections.test;

import java.util.NoSuchElementException;

import org.rejuse.java.collections.ObjectArrayIterator;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestObjectArrayIterator extends JutilTest {
  
  public TestObjectArrayIterator(String name) {
    super(name, new CVSRevision("1.6"));
  }
  
  public void setUp() {
  }
  
  public void tearDown() {
  }  
  
  public static void test(){
    Integer[][][] array= new Integer[3][4][5];
    array[1][2][3] = new Integer(7);
    ObjectArrayIterator iter = new ObjectArrayIterator(array);
    if(! iter.atStart()){
      assertTrue(false);
    }
    try{
      iter.previous();
      assertTrue(false);
    }
    catch(NoSuchElementException exc){
    }

    iter.toStart();
    
    if(! iter.atStart()){
      assertTrue(false);
    }
    
    for(int i=1; i<60;i++){
      iter.next();
    }
    
    if(! iter.atEnd()){
      assertTrue(false);
    }
    
    try{
      iter.next();
      assertTrue(false);
    }
    catch(NoSuchElementException exc){
    }
    
    for(int i=1; i<60;i++){
      iter.previous();
    }
    if(! iter.atStart()){
      assertTrue(false);
    }
    iter.toStart();
    int count=0;
    while(! iter.atEnd()){
      iter.setElement(new Integer(60-count));
      iter.next();
      count++;
    }
    iter.setElement(new Integer(60-count));
    iter.toStart();
    
    count=60;
    while(! iter.atEnd()){
      Integer test = (Integer) iter.getElement();      
      assertEquals(new Integer(count),test);
      iter.next();
      count--;
    }
    assertEquals(new Integer(count),(Integer) iter.getElement());
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
