package org.rejuse.association.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.association.ReferenceSet;
import org.rejuse.association.Reference;
import org.rejuse.association.Relation;
import org.rejuse.java.collections.Visitor;
import java.util.List;

public class TestHybrid extends JutilTest {

  public TestHybrid(String name) {
    super(name, org.rejuse.association.ReferenceSet.class, new CVSRevision("1.9"));
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
    C c1 = new C("c1");
    C c2 = new C("c2");
    C c3 = new C("c3");
    C c4 = new C("c4");

    a1.setB(b1);
    a2.setB(b1);
    c1.addB(b1);
    c1.addB(b2);
    c1.addB(b3);
    a3.setB(b3);
    assertTrue(a1.getB() == b1);
    assertTrue(a2.getB() == b1);
    assertTrue(c1.getB().size() == 3);
    assertTrue(c1.getB().contains(b1));
    assertTrue(c1.getB().contains(b2));
    assertTrue(c1.getB().contains(b3));
    assertTrue(b3.getT().size() == 2);
    assertTrue(b3.getT().contains(c1));
    assertTrue(b3.getT().contains(a3));
    b1.removeT(a2);
    assertTrue(a2.getB() == null);
    assertTrue(b1.getT().size() == 2);
    b3.removeT(c1);
    assertTrue(b3.getT().size() == 1);
    assertTrue(c1.getB().size() == 2);
    assertTrue(! c1.getB().contains(b3));
    assertTrue(! b3.getT().contains(c1));


  }

private abstract class T {

  public T(String name) {
    _name = name;
  }
  public String getName() {
    return _name;
  }

  public String toString() {
    return _name;
  }

  abstract Relation getBLink();

  private String _name;
}

private class A extends T {
  public A(String name) {
    super(name);
    _a = new Reference(this);
  }

  public B getB() {
    return (B) _a.getOtherEnd();
  }

  public void setB(B other) {
    if(other != null) {
      _a.connectTo(other.getTLink());
    }
    else {
      _a.connectTo(null);
    }
  }

  Relation getBLink() {
    return _a;
  }

  private Reference _a;
}

private class C extends T {
  public C(String name) {
    super(name);
    _c = new ReferenceSet(this);
  }

  public List getB() {
    return _c.getOtherEnds();
  }

  public void addB(B other) {
    _c.add(other.getTLink());
  }

  public void removeB(B other) {
    _c.remove(other.getTLink());
  }

  Relation getBLink() {
    return _c;
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

  private ReferenceSet _c;
}

private class B {
  public B(String name) {
    _b = new ReferenceSet(this);
    _name = name;
  }

  public List getT() {
    return _b.getOtherEnds();
  }

  public void addT(T other) {
    _b.add(other.getBLink());
  }

  public void removeT(T other) {
    _b.remove(other.getBLink());
  }

  ReferenceSet getTLink() {
    return _b;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    final StringBuffer result = new StringBuffer();
    new Visitor() {
      public void visit(Object o) {
        result.append(((T)o).getName());
      }
    }.applyTo(getT());
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
