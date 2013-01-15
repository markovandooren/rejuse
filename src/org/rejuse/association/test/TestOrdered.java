package org.rejuse.association.test;
import java.util.List;

import org.rejuse.association.MultiAssociation;
import org.rejuse.association.OrderedMultiAssociation;
import org.rejuse.association.SingleAssociation;
import org.rejuse.java.collections.Visitor;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

public class TestOrdered extends JutilTest {

  public TestOrdered(String name) {
    super(name, org.rejuse.association.MultiAssociation.class, new CVSRevision("1.9"));
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
    a2.setB(b1);
    a3.setB(b1);
    a4.setB(b1);
    assertTrue(a1.getB() == b1);
    assertTrue(a2.getB() == b1);
    assertTrue(a3.getB() == b1);
    assertTrue(a4.getB() == b1);
    assertTrue(b1.getA().size() == 4);
    assertTrue(b1.getA().contains(a1));
    assertTrue(b1.getA().contains(a2));
    assertTrue(b1.getA().contains(a3));
    assertTrue(b1.getA().contains(a4));

    b2.addA(a2);
    assertTrue(b2.getA().contains(a2));
    assertTrue(a2.getB() == b2);
    assertTrue( ! b1.getA().contains(a2));
    assertTrue(b1.getA().size() == 3);
    assertTrue(b2.getA().size() == 1);

    a3.setB(null);
    assertTrue(a3.getB() == null);
    assertTrue( ! b1.getA().contains(a3));
    assertTrue(b1.getA().size() == 2);


  }

private class A {
  public A(String name) {
    _a = new SingleAssociation(this);
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

  SingleAssociation getBLink() {
    return _a;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    return _name;
  }

  private SingleAssociation _a;
  private String _name;
}

private class B {
  public B(String name) {
    _b = new OrderedMultiAssociation(this);
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

  OrderedMultiAssociation getALink() {
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

  private OrderedMultiAssociation _b;
  private String _name;
}
}

