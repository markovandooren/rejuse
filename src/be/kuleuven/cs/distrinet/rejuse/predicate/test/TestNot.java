package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class TestNot extends JutilTest {

  public TestNot(String name) {
    super(name, new CVSRevision("1.3"));
  }

  public void setup() {
  }

  private SafePredicate<Integer> _greaterThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
      return ((Integer)o).intValue() > 5;
    }
  };

  private SafePredicate<Integer> _smallerThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
      return ((Integer)o).intValue() < 5;
    }
  };

  private SafePredicate<Integer> _equalTo5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
      return ((Integer)o).intValue() == 5;
    }
  };

  public void testNot() {
    try{
      assertTrue(! _equalTo5.negation().eval(new Integer(5)));
      assertTrue(! _greaterThan5.negation().eval(new Integer(6)));
      assertTrue(! _smallerThan5.negation().eval(new Integer(4)));
      assertTrue(_equalTo5.negation().eval(new Integer(3)));
      assertTrue(_greaterThan5.negation().eval(new Integer(4)));
      assertTrue(_smallerThan5.negation().eval(new Integer(6)));
      for(int i=0;i<11;i++) {
        assertTrue(_equalTo5.negation().eval(new Integer(i))  == _equalTo5.eval(new Integer(i)));
        assertTrue(_smallerThan5.negation().eval(new Integer(i)) == _smallerThan5.eval(new Integer(i)));
        assertTrue(_greaterThan5.negation().eval(new Integer(i)) == _greaterThan5.eval(new Integer(i)));
      }
    }
    catch (Exception exc) {
      assertTrue(false);
    }
  }
}
