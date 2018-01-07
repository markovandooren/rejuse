package org.aikodi.rejuse.predicate.test;
import org.aikodi.rejuse.predicate.Equal;

import junit.framework.TestCase;

public class TestEqual extends TestCase {

  public TestEqual(String name) {
    super(name);
  }

  public void setup() {
  }

  public void testEqual() {
    for (int i=-10; i<15; i++) {
      assertTrue(new Equal(new Integer(i)).eval(new Integer(i)));
    } 
    for (int i=-10; i<15; i++) {
      assertTrue(! new Equal(new Integer(i+1)).eval(new Integer(i)));
    } 
  }
}
