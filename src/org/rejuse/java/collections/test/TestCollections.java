package org.rejuse.java.collections.test;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.rejuse.java.collections.Collections;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestCollections extends JutilTest {
  
  public TestCollections(String name) {
    super(name, new CVSRevision("1.18"));
  }
  
  private Vector $integers;
  private Vector $otherIntegers;
  private Map $map;
  private Map $otherMap;
      
  public void setUp() {
    $integers = new Vector();
    $otherIntegers = new Vector();
    for(int i=0;i<37;i++) {
      $integers.add(new Integer(i));
      $otherIntegers.add(new Integer(i));
    }
    $map = new HashMap();
    $otherMap = new HashMap();
    for(int i=0;i<37;i++) {
      $map.put(new Integer(i), new Integer(i+1));
      $otherMap.put(new Integer(i),new Integer(i+1));
    }
  }
  
  public void testIdenticalCollection() {
    Vector vek = new Vector($integers);
    Collection nullCollection = null;
    assertTrue(! Collections.identical($integers,$otherIntegers));
    assertTrue(! Collections.identical($integers,null));
    assertTrue(! Collections.identical(null,$otherIntegers));
    assertTrue(Collections.identical(nullCollection,nullCollection));
    assertTrue(Collections.identical($integers,$integers));
    assertTrue(Collections.identical($integers,vek));
  }
  
  public void testIdenticalMap() {
    Map map = new HashMap($map);
    Map nullMap = null;
    assertTrue(! Collections.identical($map,$otherMap));
    assertTrue(! Collections.identical($map,null));
    assertTrue(! Collections.identical(null,$otherMap));
    assertTrue(Collections.identical(nullMap,nullMap));
    assertTrue(Collections.identical($map,$map));
    assertTrue(Collections.identical($map,map));
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
