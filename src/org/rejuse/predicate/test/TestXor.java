package org.rejuse.predicate.test;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
import org.rejuse.predicate.NegationAsFailure;
import org.rejuse.predicate.Predicate;
import org.rejuse.predicate.SafePredicate;
import org.rejuse.predicate.Xor;

public class TestXor extends JutilTest {

  public TestXor(String name) {
    super(name, new CVSRevision("1.2"));
  }

  public void setup() {
  }

  private Predicate _greaterThan5 = new SafePredicate() {
    public boolean eval(Object o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() > 5;
      }
    }
  };

  private Predicate _smallerThan5 = new SafePredicate() {
    public boolean eval(Object o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() < 5;
      }
    }
  };

  private Predicate _equalTo5 = new SafePredicate() {
    public boolean eval(Object o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() == 5;
      }
    }
  };

  private SafePredicate wrap(Predicate predicate) {
    return new NegationAsFailure(predicate);
  }

  public void testXor() {
    for (int i=-10; i<5; i++) {
      assertTrue(wrap(new Xor(_equalTo5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Xor(_smallerThan5,_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(new Xor(_greaterThan5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Xor(_smallerThan5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Xor(_equalTo5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Xor(_greaterThan5,_equalTo5)).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(! wrap(new Xor(_equalTo5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Xor(_smallerThan5,_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(new Xor(_greaterThan5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Xor(_smallerThan5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Xor(_equalTo5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Xor(_greaterThan5,_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=0;i<11;i++) {
      assertTrue(! wrap(new Xor(_greaterThan5,_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(! wrap(new Xor(_smallerThan5,_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(! wrap(new Xor(_equalTo5,_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(! wrap(new Xor(_equalTo5,_equalTo5)).eval(new Integer(5)));

  }
}
