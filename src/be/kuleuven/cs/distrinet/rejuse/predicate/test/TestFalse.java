package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.False;

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
