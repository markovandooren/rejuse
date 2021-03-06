package org.aikodi.rejuse.association.test;
import java.util.Set;

import org.aikodi.rejuse.association.MultiAssociation;

import junit.framework.TestCase;

public class TestReferenceSet extends TestCase {

  public TestReferenceSet(String name) {
    super(name);
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
    _a = new MultiAssociation(this);
    _name = name;
  }

  public Set<B> getB() {
    return _a.getOtherEnds();
  }

  public void addB(B other) {
    _a.add(other.getALink());
  }

  public void removeB(B other) {
    _a.remove(other.getALink());
  }

  MultiAssociation getBLink() {
    return _a;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    final StringBuffer result = new StringBuffer();
    getB().forEach(o -> result.append(((B)o).getName()));
    return result.toString();
  }

  private MultiAssociation<A, B> _a;
  private String _name;
}

private class B {
  public B(String name) {
    _b = new MultiAssociation(this);
    _name = name;
  }

  public Set<A> getA() {
    return _b.getOtherEnds();
  }

  public void addA(A other) {
    _b.add(other.getBLink());
  }

  public void removeA(A other) {
    _b.remove(other.getBLink());
  }

  MultiAssociation getALink() {
    return _b;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    final StringBuffer result = new StringBuffer();
    getA().forEach(o -> result.append(o.getName()));
    return result.toString();
  }

  private MultiAssociation<B, A> _b;
  private String _name;
}
}

