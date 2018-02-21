package org.aikodi.rejuse.predicate.test;
import org.aikodi.rejuse.predicate.NotNull;

import junit.framework.TestCase;

public class TestNotNull extends TestCase {

  public TestNotNull(String name) {
    super(name);
  }

  public void setup() {
  }

  public void testNotNull() {
    assertTrue(! new NotNull().eval(new Integer(7)));
    assertTrue(new NotNull().eval(null));
    assertTrue(! new NotNull().eval(new Object()));
  }
}
