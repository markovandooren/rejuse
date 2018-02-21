package org.aikodi.rejuse.predicate.test;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.predicate.Predicate;
import org.aikodi.rejuse.predicate.SafePredicate;

import junit.framework.TestCase;

public class TestOr extends TestCase {

  public TestOr(String name) {
    super(name);
  }

  public void setup() {
  }

  private Predicate<Integer,Nothing> _greaterThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
    	return ((Integer)o).intValue() > 5;
    }
  };

  private Predicate<Integer,Nothing> _smallerThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
    	return ((Integer)o).intValue() < 5;
    }
  };

  private Predicate<Integer,Nothing> _equalTo5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
    	return ((Integer)o).intValue() == 5;
    }
  };

  private <T> Predicate<T,Nothing> wrap(Predicate<T,?> predicate) {
    return predicate.guard(false);
  }

  public void testOr() {
    for (int i=-10; i<5; i++) {
      assertTrue(wrap(_equalTo5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.or(_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_equalTo5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.or(_equalTo5)).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(! wrap(_equalTo5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.or(_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_equalTo5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.or(_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(_greaterThan5.or(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(_greaterThan5.or(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(_smallerThan5.or(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(_smallerThan5.or(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(_equalTo5.or(_equalTo5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(_equalTo5.or(_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(_equalTo5.or(_equalTo5)).eval(new Integer(5)));

    for (int i=-10; i<5; i++) {
      assertTrue(wrap(_equalTo5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.or(_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_equalTo5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.or(_equalTo5)).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(! wrap(_equalTo5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.or(_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.or(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_equalTo5.or(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.or(_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=6;i<11;i++) {
      assertTrue(wrap(_greaterThan5.or(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<6;i++) {
      assertTrue(! wrap(_greaterThan5.or(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=5;i<11;i++) {
      assertTrue(! wrap(_smallerThan5.or(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(wrap(_smallerThan5.or(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=6;i<11;i++) {
      assertTrue(! wrap(_equalTo5.or(_equalTo5)).eval(new Integer(i)));
    }
    for(int i=0;i<5;i++) {
      assertTrue(! wrap(_equalTo5.or(_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(_equalTo5.or(_equalTo5)).eval(new Integer(5)));
  }
}
