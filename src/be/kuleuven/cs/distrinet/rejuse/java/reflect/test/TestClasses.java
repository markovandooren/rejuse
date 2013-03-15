package be.kuleuven.cs.distrinet.rejuse.java.reflect.test;

import java.util.Set;
import java.util.Vector;

import be.kuleuven.cs.distrinet.rejuse.java.reflect.Classes;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

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
