package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;

public class TestAnd extends JutilTest {

  public TestAnd(String name) {
    super(name, new CVSRevision("1.3"));
  }

  public void setup() {
  }

  private Predicate<Integer,Nothing> _greaterThan5 = new AbstractPredicate<Integer,Nothing>() {
    public boolean eval(Integer o) {
        return ((Integer)o).intValue() > 5;
    }
  };

  private Predicate<Integer,Nothing> _smallerThan5 = new AbstractPredicate<Integer,Nothing>() {
    public boolean eval(Integer o) {
        return ((Integer)o).intValue() < 5;
    }
  };

  private Predicate<Integer,Nothing> _equalTo5 = new AbstractPredicate<Integer,Nothing>() {
    public boolean eval(Integer o) {
        return ((Integer)o).intValue() == 5;
    }
  };

  private <T> Predicate<T,Nothing> wrap(Predicate<T,?> predicate) {
    return predicate.guard(false);
  }

  public void testAnd() {
    for (int i=-10; i<11; i++) {
      assertTrue(! wrap(_equalTo5.and(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.and(_equalTo5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.and(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.and(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_equalTo5.and(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.and(_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(_greaterThan5.and(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(_greaterThan5.and(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(_smallerThan5.and(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(_smallerThan5.and(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(_equalTo5.and(_equalTo5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(_equalTo5.and(_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(_equalTo5.and(_equalTo5)).eval(new Integer(5)));

    for (int i=-10; i<11; i++) {
      assertTrue(! wrap(_equalTo5.and(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.and(_equalTo5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.and(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.and(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_equalTo5.and(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.and(_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(_greaterThan5.and(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(_greaterThan5.and(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(_smallerThan5.and(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(_smallerThan5.and(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(_equalTo5.and(_equalTo5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(_equalTo5.and(_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(_equalTo5.and(_equalTo5)).eval(new Integer(5)));
  }
}
