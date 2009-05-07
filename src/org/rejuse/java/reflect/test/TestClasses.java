package org.rejuse.java.reflect.test;

import java.util.Set;
import java.util.Vector;

import org.rejuse.java.reflect.Classes;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestClasses extends JutilTest {
  
  public TestClasses(String name) {
    super(name, new CVSRevision("1.10"));
  }

  public void setUp() {
  }
  
  
  public void testGetImmediateSuperTypes() {
    Set set = Classes.getImmediateSuperTypes(I.class);
    Vector vec = new Vector(set);
//     new Visitor() {
//       public void visit(Object element) {
//         System.out.println(element);
//       }
//     }.applyTo(set);
//    System.out.println(set);
    assertTrue(set.size() == 0);
    set = Classes.getImmediateSuperTypes(K.class);
    vec = new Vector(set);
    assertTrue(set.size() == 1);
    assertTrue(vec.elementAt(0) == J.class);

    set = Classes.getImmediateSuperTypes(A.class);
    assertTrue(set.size() == 2);
    assertTrue(set.contains(Object.class));
    assertTrue(set.contains(I.class));

    set = Classes.getImmediateSuperTypes(B.class);
    assertTrue(set.size() == 3);
    assertTrue(set.contains(I.class));
    assertTrue(set.contains(K.class));
    assertTrue(set.contains(A.class));
    
    set = Classes.getImmediateSuperTypes(C.class);
    assertTrue(set.size() == 1);
    assertTrue(set.contains(B.class));
    
  }

  public void testGetSuperTypes() {
    Set set = Classes.getSuperTypes(I.class);
     assertTrue(set.size() == 1);
    assertTrue(set.contains(I.class));
    
    set = Classes.getSuperTypes(K.class);
    assertTrue(set.size() == 2);
    assertTrue(set.contains(J.class));
    assertTrue(set.contains(K.class));

    set = Classes.getSuperTypes(A.class);
    assertTrue(set.size() == 3);
    assertTrue(set.contains(Object.class));
    assertTrue(set.contains(I.class));
    assertTrue(set.contains(A.class));

    set = Classes.getSuperTypes(B.class);
    assertTrue(set.size() == 6);
    assertTrue(set.contains(I.class));
    assertTrue(set.contains(K.class));
    assertTrue(set.contains(A.class));
    assertTrue(set.contains(B.class));
    assertTrue(set.contains(J.class));
    assertTrue(set.contains(Object.class));
    
    set = Classes.getSuperTypes(C.class);
    assertTrue(set.size() == 7);
    assertTrue(set.contains(I.class));
    assertTrue(set.contains(K.class));
    assertTrue(set.contains(A.class));
    assertTrue(set.contains(J.class));
    assertTrue(set.contains(Object.class));
    assertTrue(set.contains(B.class));
    assertTrue(set.contains(C.class));
    
  }
  
  
  public void testGetSuperClasses() {
    Set set;
    set = Classes.getSuperClasses(Object.class);
    assertTrue(set.size() == 1);
    assertTrue(set.contains(Object.class));
    
    set = Classes.getSuperClasses(A.class);
    assertTrue(set.size() == 2);
    assertTrue(set.contains(Object.class));
    assertTrue(set.contains(A.class));

    set = Classes.getSuperClasses(B.class);
    assertTrue(set.size() == 3);
    assertTrue(set.contains(Object.class));
    assertTrue(set.contains(A.class));
    assertTrue(set.contains(B.class));

    set = Classes.getSuperClasses(C.class);
    assertTrue(set.size() == 4);
    assertTrue(set.contains(Object.class));
    assertTrue(set.contains(A.class));
    assertTrue(set.contains(B.class));
    assertTrue(set.contains(C.class));
  }
  
  public void testAreInSamePackage() {
    assertTrue(! Classes.areInSamePackage(I.class,K.class));
    assertTrue(! Classes.areInSamePackage(I.class,A.class));
    assertTrue(! Classes.areInSamePackage(B.class,A.class));
    assertTrue(! Classes.areInSamePackage(Object.class,K.class));
    assertTrue(! Classes.areInSamePackage(I.class,TestClasses.class));
    assertTrue(Classes.areInSamePackage(Object.class,Integer.class));
  }
  
  public void testPackageName() {
    assertEquals(Classes.packageName(Object.class),"java.lang");
  }
  
  interface I {
  }
  
  interface J {
  }
  
  interface K extends J {
  }
  
  class A implements I{
  }
  
  class B extends A implements I,K{
  }
  
  
  class C extends B {
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
For more information, please see http://org-jutil.sourceforge.net/</copyright>
*/
