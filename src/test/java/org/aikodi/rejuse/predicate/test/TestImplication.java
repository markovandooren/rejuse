package org.aikodi.rejuse.predicate.test;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.predicate.Predicate;
import org.aikodi.rejuse.predicate.SafePredicate;

import junit.framework.TestCase;

public class TestImplication extends TestCase {

  public TestImplication(String name) {
    super(name);
  }

  public void setup() {
  }

  private SafePredicate<Integer> _greaterThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
        return o.intValue() > 5;
    }
  };

  private SafePredicate<Integer> _smallerThan5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
        return o.intValue() < 5;
    }
  };

  private SafePredicate<Integer> _equalTo5 = new SafePredicate<Integer>() {
    public boolean eval(Integer o) {
        return o.intValue() == 5;
    }
  };

  private <T> Predicate<T,Nothing> wrap(Predicate<T,?> predicate) {
    return predicate.guard(false);
  }

  public void testImplication() {
    for (int i=-10; i<5; i++) {
      assertTrue(wrap(_equalTo5.implies(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.implies(_equalTo5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.implies(_smallerThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_smallerThan5.implies(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_equalTo5.implies(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_greaterThan5.implies(_equalTo5)).eval(new Integer(i)));
    } 
    for (int i=6; i<15; i++) {
      assertTrue(wrap(_equalTo5.implies(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.implies(_equalTo5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.implies(_smallerThan5)).eval(new Integer(i)));
      assertTrue(wrap(_smallerThan5.implies(_greaterThan5)).eval(new Integer(i)));
      assertTrue(wrap(_equalTo5.implies(_greaterThan5)).eval(new Integer(i)));
      assertTrue(! wrap(_greaterThan5.implies(_equalTo5)).eval(new Integer(i)));
    } 
    for(int i=0;i<11;i++) {
      assertTrue(wrap(_greaterThan5.implies(_greaterThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(wrap(_smallerThan5.implies(_smallerThan5)).eval(new Integer(i)));
    }
    for(int i=0;i<11;i++) {
      assertTrue(wrap(_equalTo5.implies(_equalTo5)).eval(new Integer(i)));
    }
    assertTrue(wrap(_equalTo5.implies(_equalTo5)).eval(new Integer(5)));

  }
}
