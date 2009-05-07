package org.rejuse.predicate.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.predicate.True;

public class TestTrue extends JutilTest {

  public TestTrue(String name) {
    super(name, new CVSRevision("1.2"));
  }

  public void setup() {
  }

  public void testTrue() {
    for (int i=-10; i<15; i++) {
      assertTrue(new True().eval(new Integer(i)));
    } 
    assertTrue(new True().eval(null));
    assertTrue(new True().eval(new Object()));
  }
}
