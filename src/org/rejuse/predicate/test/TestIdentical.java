package org.rejuse.predicate.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.predicate.Identical;

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
