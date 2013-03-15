package be.kuleuven.cs.distrinet.rejuse.association.test;
import be.kuleuven.cs.distrinet.rejuse.association.SingleAssociation;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

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

  public String toString() {
    return _name;
  }

  private SingleAssociation _a;
  private String _name;
}

private class B {
  public B(String name) {
    _b = new SingleAssociation(this);
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

  SingleAssociation getALink() {
    return _b;
  }

  public String toString() {
    return _name;
  }

  private SingleAssociation _b;
  private String _name;
}
}

