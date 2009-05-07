package org.rejuse.java.collections.test;
import java.util.Collection;
import java.util.HashSet;

import org.rejuse.java.collections.Collections;
import org.rejuse.java.collections.TypeFilter;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestTypeFilter extends JutilTest {
  
  public TestTypeFilter(String name) {
    super(name, new CVSRevision("1.11"));
  }
  
  Collection $col1;
//   Collection $col2;
//   Collection $col3;
  
  public void setUp() {
    $col1 = new HashSet();
//     $col2 = new Vector();
//     $col3 = new ArrayList();
    $col1.add(new Object());
    $col1.add(new Integer(2));
    $col1.add(new Boolean(false));
    
  }
  
  
  public void testTypeFilter() {
    TypeFilter filter = new TypeFilter(Integer.class);
    filter.discard($col1);
    //System.out.println($col1);
    assertTrue($col1.size() == 2);
    assertTrue($col1.contains(new Boolean(false)));
    assertTrue(! $col1.contains(new Integer(2)));
    filter.discard($col1);
    filter.retain($col1);
    assertTrue($col1.size() == 0);
    // null as argument
    Collection nullCollection = null;
    filter.discard(nullCollection);
    filter.retain(nullCollection);
    setUp();
    filter = new TypeFilter(Object.class);
    Collection clone = new HashSet($col1);
    filter.retain($col1);
    assertTrue(Collections.identical($col1,clone));
    filter.discard($col1);
    assertTrue($col1.size() == 0);
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
