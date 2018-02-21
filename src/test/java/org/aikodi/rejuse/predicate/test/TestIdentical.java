package org.aikodi.rejuse.predicate.test;
import org.aikodi.rejuse.predicate.Identical;

import junit.framework.TestCase;

public class TestIdentical extends TestCase {

  public TestIdentical(String name) {
    super(name);
  }

  public void setup() {
  }

  public void testIdentical() {
    for (int i=-10; i<15; i++) {
      assertTrue(! new Identical(new Integer(i)).eval(new Integer(i)));
    } 
    for (int i=-10; i<15; i++) {
      Integer integer = new Integer(i);
      assertTrue(new Identical(integer).eval(integer));
    } 
    Object o = new Object();
    assertTrue(new Identical(o).eval(o));
  }
}
