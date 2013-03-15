package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.Implication;
import be.kuleuven.cs.distrinet.rejuse.predicate.NegationAsFailure;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class TestImplication extends JutilTest {

  public TestImplication(String name) {
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

  public void testImplication() {
    for (int i=-10; i<5; i++) {
      assertTrue(wrap(new Implication(_equalTo5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Implication(_smallerThan5,_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(new Implication(_greaterThan5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Implication(_smallerThan5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Implication(_equalTo5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Implication(_greaterThan5,_equalTo5)).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(wrap(new Implication(_equalTo5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Implication(_smallerThan5,_equalTo5)).eval(new Integer(i)));
      assertTrue(! wrap(new Implication(_greaterThan5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Implication(_smallerThan5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(new Implication(_equalTo5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new Implication(_greaterThan5,_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=0;i<11;i++) {
      assertTrue(wrap(new Implication(_greaterThan5,_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(wrap(new Implication(_smallerThan5,_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(wrap(new Implication(_equalTo5,_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(new Implication(_equalTo5,_equalTo5)).eval(new Integer(5)));

  }
}
