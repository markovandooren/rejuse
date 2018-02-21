package org.aikodi.rejuse.predicate.test;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.predicate.Predicate;

import junit.framework.TestCase;

public class TestAnd extends TestCase {

  public TestAnd(String name) {
    super(name);
  }

  public void setup() {
  }

  private Predicate<Integer,Nothing> _greaterThan5 = o -> o.intValue() > 5;

  private Predicate<Integer,Nothing> _smallerThan5 = o -> o.intValue() < 5;

  private Predicate<Integer,Nothing> _equalTo5 = o -> o.intValue() == 5;

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
