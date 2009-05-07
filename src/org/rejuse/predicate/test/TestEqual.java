package org.rejuse.predicate.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.predicate.Equal;

public class TestEqual extends JutilTest {

  public TestEqual(String name) {
    super(name, new CVSRevision("1.2"));
  }

  public void setup() {
  }

  public void testEqual() {
    for (int i=-10; i<15; i++) {
      assertTrue(new Equal(new Integer(i)).eval(new Integer(i)));
    } 
    for (int i=-10; i<15; i++) {
      assertTrue(! new Equal(new Integer(i+1)).eval(new Integer(i)));
    } 
  }
}
