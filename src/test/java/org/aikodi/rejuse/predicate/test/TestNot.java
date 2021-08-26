package org.aikodi.rejuse.predicate.test;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.predicate.Predicate;

import junit.framework.TestCase;

public class TestNot extends TestCase {

  public TestNot(String name) {
    super(name);
  }

  public void setup() {
  }

  private Predicate<Integer, Nothing> _greaterThan5 = o -> o.intValue() > 5;

  private Predicate<Integer, Nothing> _smallerThan5 = o -> o.intValue() < 5;

  private Predicate<Integer, Nothing> _equalTo5 = o -> o.intValue() == 5;

  public void testNot() {
		assertTrue(!_equalTo5.negation().eval(5));
		assertTrue(!_greaterThan5.negation().eval(6));
		assertTrue(!_smallerThan5.negation().eval(4));
		assertTrue(_equalTo5.negation().eval(3));
		assertTrue(_greaterThan5.negation().eval(4));
		assertTrue(_smallerThan5.negation().eval(6));
		for (int i = 0; i < 11; i++) {
			assertTrue(_equalTo5.negation().eval(i) != _equalTo5.eval(i));
			assertTrue(_smallerThan5.negation().eval(i) != _smallerThan5.eval(i));
			assertTrue(_greaterThan5.negation().eval(i) != _greaterThan5.eval(i));
		}
	  }
}
