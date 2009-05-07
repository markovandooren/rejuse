package org.rejuse.predicate.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.predicate.False;

public class TestFalse extends JutilTest {

  public TestFalse(String name) {
    super(name, new CVSRevision("1.2"));
  }

  public void setup() {
  }

  public void testFalse() {
    for (int i=-10; i<15; i++) {
      assertTrue(! new False().eval(new Integer(i)));
    } 
    assertTrue(! new False().eval(null));
    assertTrue(! new False().eval(new Object()));
  }
}
