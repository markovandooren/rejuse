package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.Equal;

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
