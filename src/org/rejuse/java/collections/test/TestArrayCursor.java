package org.rejuse.java.collections.test;
import java.util.NoSuchElementException;

import org.rejuse.java.collections.ArrayCursor;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
 
/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestArrayCursor extends JutilTest {
  
  public TestArrayCursor(String name) {
    super(name, new CVSRevision("1.7"));
  }
  
  public void setUp() {
  }
  
  public void testArrayCursor() {
    Integer[][][] array= new Integer[3][4][5];
    array[1][2][3] = new Integer(7);
    ArrayCursor iter = new ArrayCursor(array);
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
