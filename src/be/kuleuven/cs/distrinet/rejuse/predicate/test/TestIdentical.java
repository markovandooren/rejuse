package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.Identical;

public class TestIdentical extends JutilTest {

  public TestIdentical(String name) {
    super(name, new CVSRevision("1.2"));
  }

  public void setup() {
  }

  public void testIdentical() {
    for (int i=-10; i<15; i++) {
      assertTrue(! new Identical(new Integer(i)).eval(new Integer(i)));
    } 
    for (int i=-10; i<15; i++) {
      Integer integer = new Integer(i);
      assertTrue(new Identical(integer).eval(integer));
    } 
    Object o = new Object();
    assertTrue(new Identical(o).eval(o));
  }
}
