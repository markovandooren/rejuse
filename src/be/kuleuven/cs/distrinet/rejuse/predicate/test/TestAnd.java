package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.And;
import be.kuleuven.cs.distrinet.rejuse.predicate.NegationAsFailure;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class TestAnd extends JutilTest {

  public TestAnd(String name) {
    super(name, new CVSRevision("1.3"));
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

  public void testAnd() {
    for (int i=-10; i<11; i++) {
      assertTrue(! wrap(new And(_equalTo5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new And(_smallerThan5,_equalTo5)).eval(new Integer(i)));
      assertTrue(! wrap(new And(_greaterThan5,_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new And(_smallerThan5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new And(_equalTo5,_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(new And(_greaterThan5,_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(new And(_greaterThan5,_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(new And(_greaterThan5,_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(new And(_smallerThan5,_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(new And(_smallerThan5,_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(new And(_equalTo5,_equalTo5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(new And(_equalTo5,_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(new And(_equalTo5,_equalTo5)).eval(new Integer(5)));

    for (int i=-10; i<11; i++) {
      assertTrue(! wrap(new And(new Predicate[]{_equalTo5,_smallerThan5})).eval(new Integer(i)));
      assertTrue(! wrap(new And(new Predicate[]{_smallerThan5,_equalTo5})).eval(new Integer(i)));
      assertTrue(! wrap(new And(new Predicate[]{_greaterThan5,_smallerThan5})).eval(new Integer(i)));
      assertTrue(! wrap(new And(new Predicate[]{_smallerThan5,_greaterThan5})).eval(new Integer(i)));
      assertTrue(! wrap(new And(new Predicate[]{_equalTo5,_greaterThan5})).eval(new Integer(i)));
      assertTrue(! wrap(new And(new Predicate[]{_greaterThan5,_equalTo5})).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(new And(new Predicate[]{_greaterThan5,_greaterThan5})).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(new And(new Predicate[]{_greaterThan5,_greaterThan5})).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(new And(new Predicate[]{_smallerThan5,_smallerThan5})).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(new And(new Predicate[]{_smallerThan5,_smallerThan5})).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(new And(new Predicate[]{_equalTo5,_equalTo5})).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(new And(new Predicate[]{_equalTo5,_equalTo5})).eval(new Integer(i)));
    }
    assertTrue(wrap(new And(new Predicate[]{_equalTo5,_equalTo5})).eval(new Integer(5)));
    assertTrue(wrap(new And()).eval(new Object()));
  }
}
