package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.NegationAsFailure;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class TestNegationAsFailure extends JutilTest {

  public TestNegationAsFailure(String name) {
    super(name, new CVSRevision("1.3"));
  }

  public void setup() {
  }

  private Predicate _greaterThan5 = new AbstractPredicate() {
    public boolean eval(Object o) {
      return ((Integer)o).intValue() > 5;
    }
  };

  private Predicate _smallerThan5 = new AbstractPredicate() {
    public boolean eval(Object o) {
      return ((Integer)o).intValue() < 5;
    }
  };

  private Predicate _equalTo5 = new AbstractPredicate() {
    public boolean eval(Object o) {
      return ((Integer)o).intValue() == 5;
    }
  };

  private SafePredicate wrap(Predicate predicate) {
    return new NegationAsFailure(predicate);
  }

  public void testNegationAsFailure() {
    assertTrue(! wrap(_equalTo5).eval(new Object()));
    assertTrue(! wrap(_greaterThan5).eval(new Object()));
    assertTrue(! wrap(_smallerThan5).eval(new Object()));
    assertTrue(wrap(_equalTo5).eval(new Integer(5)));
    assertTrue(wrap(_greaterThan5).eval(new Integer(6)));
    assertTrue(wrap(_smallerThan5).eval(new Integer(4)));
    assertTrue(! wrap(_equalTo5).eval(new Integer(3)));
    assertTrue(! wrap(_greaterThan5).eval(new Integer(5)));
    assertTrue(! wrap(_smallerThan5).eval(new Integer(5)));
  }
}
