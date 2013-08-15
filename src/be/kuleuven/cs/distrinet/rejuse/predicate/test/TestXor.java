package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class TestXor extends JutilTest {

  public TestXor(String name) {
    super(name, new CVSRevision("1.2"));
  }

  public void setup() {
  }

  private Predicate<Integer,Nothing> _greaterThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() > 5;
      }
    }
  };

  private Predicate<Integer,Nothing> _smallerThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() < 5;
      }
    }
  };

  private Predicate<Integer,Nothing> _equalTo5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
      if(!(o instanceof Integer)) {
        return false;
      }
      else {
        return ((Integer)o).intValue() == 5;
      }
    }
  };

  private <T> Predicate<T,Nothing> wrap(Predicate<T,?> predicate) {
    return predicate.guard(false);
  }

  public void testXor() {
    for (int i=-10; i<5; i++) {
      assertTrue(wrap(_equalTo5.xor(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.xor(_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.xor(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.xor(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_equalTo5.xor(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.xor(_equalTo5)).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(! wrap(_equalTo5.xor(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.xor(_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.xor(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.xor(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_equalTo5.xor(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.xor(_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=0;i<11;i++) {
      assertTrue(! wrap(_greaterThan5.xor(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(! wrap(_smallerThan5.xor(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(! wrap(_equalTo5.xor(_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(! wrap(_equalTo5.xor(_equalTo5)).eval(new Integer(5)));

  }
}
