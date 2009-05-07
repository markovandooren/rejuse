package org.rejuse.predicate.test;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
import org.rejuse.predicate.Not;
import org.rejuse.predicate.Predicate;
import org.rejuse.predicate.PrimitivePredicate;

public class TestNot extends JutilTest {

  public TestNot(String name) {
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

  public void testNot() {
    try{
      assertTrue(! new Not(_equalTo5).eval(new Integer(5)));
      assertTrue(! new Not(_greaterThan5).eval(new Integer(6)));
      assertTrue(! new Not(_smallerThan5).eval(new Integer(4)));
      assertTrue(new Not(_equalTo5).eval(new Integer(3)));
      assertTrue(new Not(_greaterThan5).eval(new Integer(4)));
      assertTrue(new Not(_smallerThan5).eval(new Integer(6)));
      for(int i=0;i<11;i++) {
        assertTrue(new Not(new Not(_equalTo5)).eval(new Integer(i)) ==
            _equalTo5.eval(new Integer(i)));
        assertTrue(new Not(new Not(_smallerThan5)).eval(new Integer(i)) ==
            _smallerThan5.eval(new Integer(i)));
        assertTrue(new Not(new Not(_greaterThan5)).eval(new Integer(i)) ==
            _greaterThan5.eval(new Integer(i)));
      }
    }
    catch (Exception exc) {
      assertTrue(false);
    }
  }
}
