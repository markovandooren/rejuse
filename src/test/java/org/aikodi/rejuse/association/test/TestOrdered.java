package org.aikodi.rejuse.association.test;
import java.util.List;

import org.aikodi.rejuse.association.OrderedMultiAssociation;
import org.aikodi.rejuse.association.SingleAssociation;

import junit.framework.TestCase;

public class TestOrdered extends TestCase {

  public TestOrdered(String name) {
    super(name);
  }

  public void test() {
    A a1 = new A("a1");
    A a2 = new A("a2");
    A a3 = new A("a3");
    A a4 = new A("a4");
    B b1 = new B("b1");
    B b2 = new B("b2");

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
    _a = new SingleAssociation<>(this);
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

  SingleAssociation<A, B> getBLink() {
    return _a;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    return getName();
  }

  private SingleAssociation<A, B> _a;
  private String _name;
}

private class B {
  public B(String name) {
    _b = new OrderedMultiAssociation<>(this);
    _name = name;
  }

  public List<A> getA() {
    return _b.getOtherEnds();
  }

  public void addA(A other) {
    _b.add(other.getBLink());
  }

  OrderedMultiAssociation<B, A> getALink() {
    return _b;
  }

  public String toString() {
    return getName();
  }
  
  public String getName() {
  	return _name;
  }

  private OrderedMultiAssociation<B, A> _b;
  private String _name;
}
}

