package org.rejuse.association.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.association.ReferenceSet;
import org.rejuse.java.collections.Visitor;
import java.util.List;

public class TestReferenceSet extends JutilTest {

  public TestReferenceSet(String name) {
    super(name, new CVSRevision("1.9"));
  }

  public void test() {
    A a1 = new A("a1");
    A a2 = new A("a2");
    A a3 = new A("a3");
    A a4 = new A("a4");
    B b1 = new B("b1");
    B b2 = new B("b2");
    B b3 = new B("b3");
    B b4 = new B("b4");

    a1.addB(b1);
    a1.addB(b2);
    a1.addB(b3);
    a1.addB(b4);
    assertTrue(a1.getB().size() == 4);
    assertTrue(a1.getB().contains(b1));
    assertTrue(a1.getB().contains(b2));
    assertTrue(a1.getB().contains(b3));
    assertTrue(a1.getB().contains(b4));
    assertTrue(b1.getA().contains(a1));
    assertTrue(b2.getA().contains(a1));
    assertTrue(b3.getA().contains(a1));
    assertTrue(b4.getA().contains(a1));

    a2.addB(b2);
    a2.addB(b3);
    a2.addB(b4);
    assertTrue(a2.getB().contains(b2));
    assertTrue(a2.getB().contains(b3));
    assertTrue(a2.getB().contains(b4));
    assertTrue(b2.getA().contains(a2));
    assertTrue(b3.getA().contains(a2));
    assertTrue(b4.getA().contains(a2));

    a3.addB(b3);
    a3.addB(b4);
    assertTrue(a3.getB().contains(b3));
    assertTrue(a3.getB().contains(b4));
    assertTrue(b3.getA().contains(a3));
    assertTrue(b4.getA().contains(a3));

    a4.addB(b4);
    assertTrue(a4.getB().contains(b4));
    assertTrue(b4.getA().contains(a4));

    b4.removeA(a1);
    assertTrue(! b4.getA().contains(a1));
    assertTrue(! a1.getB().contains(b4));

    b4.removeA(a1);
    assertTrue(! b4.getA().contains(a1));
    assertTrue(! a1.getB().contains(b4));
  }

private class A {
  public A(String name) {
    _a = new ReferenceSet(this);
    _name = name;
  }

  public List getB() {
    return _a.getOtherEnds();
  }

  public void addB(B other) {
    _a.add(other.getALink());
  }

  public void removeB(B other) {
    _a.remove(other.getALink());
  }

  ReferenceSet getBLink() {
    return _a;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    final StringBuffer result = new StringBuffer();
    new Visitor() {
      public void visit(Object o) {
        result.append(((B)o).getName());
      }
    }.applyTo(getB());
    return result.toString();
  }

  private ReferenceSet _a;
  private String _name;
}

private class B {
  public B(String name) {
    _b = new ReferenceSet(this);
    _name = name;
  }

  public List getA() {
    return _b.getOtherEnds();
  }

  public void addA(A other) {
    _b.add(other.getBLink());
  }

  public void removeA(A other) {
    _b.remove(other.getBLink());
  }

  ReferenceSet getALink() {
    return _b;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    final StringBuffer result = new StringBuffer();
    new Visitor() {
      public void visit(Object o) {
        result.append(((A)o).getName());
      }
    }.applyTo(getA());
    return result.toString();
  }

  private ReferenceSet _b;
  private String _name;
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
