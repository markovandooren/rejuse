package org.rejuse.predicate.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.predicate.NotNull;

public class TestNotNull extends JutilTest {

  public TestNotNull(String name) {
    super(name, new CVSRevision("1.2"));
  }

  public void setup() {
  }

  public void testNotNull() {
    assertTrue(! new NotNull().eval(new Integer(7)));
    assertTrue(new NotNull().eval(null));
    assertTrue(! new NotNull().eval(new Object()));
  }
}
