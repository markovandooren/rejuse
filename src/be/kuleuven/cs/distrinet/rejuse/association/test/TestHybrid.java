package be.kuleuven.cs.distrinet.rejuse.association.test;
import be.kuleuven.cs.distrinet.rejuse.association.Association;
import be.kuleuven.cs.distrinet.rejuse.association.MultiAssociation;
import be.kuleuven.cs.distrinet.rejuse.association.SingleAssociation;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

import java.util.List;

public class TestHybrid extends JutilTest {

  public TestHybrid(String name) {
    super(name, be.kuleuven.cs.distrinet.rejuse.association.MultiAssociation.class, new CVSRevision("1.9"));
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

  abstract Association getBLink();

  private String _name;
}

private class A extends T {
  public A(String name) {
    super(name);
    _a = new SingleAssociation(this);
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

  Association getBLink() {
    return _a;
  }

  private SingleAssociation _a;
}

private class C extends T {
  public C(String name) {
    super(name);
    _c = new MultiAssociation(this);
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

  Association getBLink() {
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

  private MultiAssociation _c;
}

private class B {
  public B(String name) {
    _b = new MultiAssociation(this);
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

  MultiAssociation getTLink() {
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

  private MultiAssociation _b;
  private String _name;
}
}

