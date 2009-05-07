package org.rejuse.predicate.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.predicate.Or;
import org.rejuse.predicate.Predicate;
import org.rejuse.predicate.PrimitiveTotalPredicate;
import org.rejuse.predicate.TotalPredicate;
import org.rejuse.predicate.NegationAsFailure;

public class TestOr extends JutilTest {

  public TestOr(String name) {
    super(name, new CVSRevision("1.3"));
  }

  public void setup() {
  }

  private Predicate _greaterThan5 = new PrimitiveTotalPredicate() {
    public boolean eval(Object o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() > 5;
      }
    }
  };

  private Predicate _smallerThan5 = new PrimitiveTotalPredicate() {
    public boolean eval(Object o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() < 5;
      }
    }
  };

  private Predicate _equalTo5 = new PrimitiveTotalPredicate() {
    public boolean eval(Object o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() == 5;
      }
    }
  };

  private TotalPredicate wrap(Predicate predicate) {
    return new NegationAsFailure(predicate);
  }

  public void testOr() {
    for (int i=-10; i<5; i++) {
      assertTrue(wrap(new Or(_equalTo5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Or(_smallerThan5,_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(new Or(_greaterThan5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Or(_smallerThan5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Or(_equalTo5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Or(_greaterThan5,_equalTo5)).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(! wrap(new Or(_equalTo5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Or(_smallerThan5,_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(new Or(_greaterThan5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Or(_smallerThan5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Or(_equalTo5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Or(_greaterThan5,_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(new Or(_greaterThan5,_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(new Or(_greaterThan5,_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(new Or(_smallerThan5,_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(new Or(_smallerThan5,_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(new Or(_equalTo5,_equalTo5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(new Or(_equalTo5,_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(new Or(_equalTo5,_equalTo5)).eval(new Integer(5)));

    for (int i=-10; i<5; i++) {
      assertTrue(wrap(new Or(new Predicate[]{_equalTo5,_smallerThan5})).eval(new Integer(i)));
      assertTrue(wrap(new Or(new Predicate[]{_smallerThan5,_equalTo5})).eval(new Integer(i)));
      assertTrue(wrap(new Or(new Predicate[]{_greaterThan5,_smallerThan5})).eval(new Integer(i)));
      assertTrue(wrap(new Or(new Predicate[]{_smallerThan5,_greaterThan5})).eval(new Integer(i)));
      assertTrue(! wrap(new Or(new Predicate[]{_equalTo5,_greaterThan5})).eval(new Integer(i)));
      assertTrue(! wrap(new Or(new Predicate[]{_greaterThan5,_equalTo5})).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(! wrap(new Or(new Predicate[]{_equalTo5,_smallerThan5})).eval(new Integer(i)));
      assertTrue(! wrap(new Or(new Predicate[]{_smallerThan5,_equalTo5})).eval(new Integer(i)));
      assertTrue(wrap(new Or(new Predicate[]{_greaterThan5,_smallerThan5})).eval(new Integer(i)));
      assertTrue(wrap(new Or(new Predicate[]{_smallerThan5,_greaterThan5})).eval(new Integer(i)));
      assertTrue(wrap(new Or(new Predicate[]{_equalTo5,_greaterThan5})).eval(new Integer(i)));
      assertTrue(wrap(new Or(new Predicate[]{_greaterThan5,_equalTo5})).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(new Or(new Predicate[]{_greaterThan5,_greaterThan5})).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(new Or(new Predicate[]{_greaterThan5,_greaterThan5})).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(new Or(new Predicate[]{_smallerThan5,_smallerThan5})).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(new Or(new Predicate[]{_smallerThan5,_smallerThan5})).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(new Or(new Predicate[]{_equalTo5,_equalTo5})).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(new Or(new Predicate[]{_equalTo5,_equalTo5})).eval(new Integer(i)));
    }
    assertTrue(wrap(new Or(new Predicate[]{_equalTo5,_equalTo5})).eval(new Integer(5)));
    assertTrue(! wrap(new Or()).eval(new Object()));
  }
}
