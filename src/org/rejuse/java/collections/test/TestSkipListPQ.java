package org.rejuse.java.collections.test;

import org.rejuse.java.collections.SkipListPQ;
import org.rejuse.java.comparator.ComparableComparator;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
/**
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 *
 */
public class TestSkipListPQ extends JutilTest {

	public TestSkipListPQ(String name) {
		super(name, new CVSRevision("1.4"));
	}

	public void testAdd() {
		SkipListPQ pq = new SkipListPQ(new ComparableComparator());
		assertTrue(pq.size() == 0);
		Integer five = new Integer(5);
		for(int i=0; i<7; i++) {
			pq.add(five);
		}
		assertTrue(pq.nbExplicitOccurrences(five) == 7);
		assertTrue(pq.size() == 7);
		for(int i=0; i<3932; i++) {
			pq.add(new Integer(i));
		}
		// The other 5 is another object.
		assertTrue(pq.nbExplicitOccurrences(five) == 7);
		assertTrue(pq.size() == 3939);
		pq.clear();
		assertTrue(pq.size() == 0);
		assertTrue(pq.isEmpty());
	}

	public void testPop() {
		Integer one = new Integer(1);
		Integer two = new Integer(2);
		Integer three = new Integer(3);
		SkipListPQ pq = new SkipListPQ(new ComparableComparator());
		pq.add(two);
		pq.add(one);
		pq.add(three);
		assertTrue(pq.pop() == one);
		assertTrue(pq.pop() == two);
		assertTrue(pq.pop() == three);
	}

}

