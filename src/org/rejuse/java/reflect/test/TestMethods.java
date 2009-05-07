package org.rejuse.java.reflect.test;

import java.util.Set;

import org.rejuse.java.reflect.Methods;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestMethods extends JutilTest {
  
  public TestMethods(String name) {
    super(name, new CVSRevision("1.8"));
  }

  public void setUp() {
  }
  
  
  public void testMethods() {
    Set set = Methods.getAllApplicableMethods(Object.class);
    assertTrue(set.size() == 12);
    set = Methods.getAllApplicableMethods(Methods.class);
    assertTrue(set.size() == 13);
//     set = Methods.getAllApplicableMethods(A.class);
//     new Visitor() {
//       public void visit(Object element) {
//         System.out.println(element);
//       }
//     }.applyTo(set);
//     assertTrue(set.size() == 17);
  }

  class A {
    public void a() {}
    public void b() {}
    private void c() {}
    void d() {}
    protected void e() {}
  }
    
}

/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
