package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.NotNull;

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
