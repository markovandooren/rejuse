package org.aikodi.rejuse.java.reflect.test;

import java.util.Set;

import org.aikodi.rejuse.java.reflect.Methods;
import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;

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


