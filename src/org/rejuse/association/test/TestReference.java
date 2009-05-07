package org.rejuse.association.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.association.Reference;

public class TestReference extends JutilTest {

  public TestReference(String name) {
    super(name, new CVSRevision("1.6"));
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
    a1.setB(b1);
    a2.setB(b2);
    a3.setB(b3);
    a4.setB(b4);
    assertTrue(a1 == b1.getA());
    assertTrue(b1 == a1.getB());
    assertTrue(a2 == b2.getA());
    assertTrue(b2 == a2.getB());
    assertTrue(a3 == b3.getA());
    assertTrue(b3 == a3.getB());
    assertTrue(a4 == b4.getA());
    assertTrue(b4 == a4.getB());
    a2.setB(b3);
    assertTrue(a2 == b3.getA());
    assertTrue(b3 == a2.getB());
    assertTrue(b2.getA() == null);
    assertTrue(a3.getB() == null);
    a4.setB(null);
    assertTrue(b4.getA() == null);
    assertTrue(a4.getB() == null);
    b1.setA(null);
    assertTrue(b1.getA() == null);
    assertTrue(a1.getB() == null);

  }

private class A {
  public A(String name) {
    _a = new Reference(this);
    _name = name;
  }

  public B getB() {
    return (B) _a.getOtherEnd();
  }

  public void setB(B other) {
    if(other != null) {
      _a.connectTo(other.getALink());
    }
    else {
      _a.connectTo(null);
    }
  }

  Reference getBLink() {
    return _a;
  }

  public String toString() {
    return _name;
  }

  private Reference _a;
  private String _name;
}

private class B {
  public B(String name) {
    _b = new Reference(this);
    _name = name;
  }

  public A getA() {
    return (A) _b.getOtherEnd();
  }

  public void setA(A other) {
    if(other != null) {
      _b.connectTo(other.getBLink());
    }
    else {
      _b.connectTo(null);
    }
  }

  Reference getALink() {
    return _b;
  }

  public String toString() {
    return _name;
  }

  private Reference _b;
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
