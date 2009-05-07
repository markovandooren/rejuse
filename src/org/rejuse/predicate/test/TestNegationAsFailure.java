package org.rejuse.predicate.test;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
import org.rejuse.predicate.NegationAsFailure;
import org.rejuse.predicate.Predicate;
import org.rejuse.predicate.PrimitivePredicate;
import org.rejuse.predicate.TotalPredicate;

public class TestNegationAsFailure extends JutilTest {

  public TestNegationAsFailure(String name) {
    super(name, new CVSRevision("1.3"));
  }

  public void setup() {
  }

  private Predicate _greaterThan5 = new PrimitivePredicate() {
    public boolean eval(Object o) {
      return ((Integer)o).intValue() > 5;
    }
  };

  private Predicate _smallerThan5 = new PrimitivePredicate() {
    public boolean eval(Object o) {
      return ((Integer)o).intValue() < 5;
    }
  };

  private Predicate _equalTo5 = new PrimitivePredicate() {
    public boolean eval(Object o) {
      return ((Integer)o).intValue() == 5;
    }
  };

  private TotalPredicate wrap(Predicate predicate) {
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
