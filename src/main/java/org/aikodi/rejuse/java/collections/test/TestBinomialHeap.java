package org.aikodi.rejuse.java.collections.test;

import org.aikodi.rejuse.java.collections.BinomialHeap;
import org.aikodi.rejuse.java.comparator.ComparableComparator;
import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;

public class TestBinomialHeap extends JutilTest {

	public TestBinomialHeap(String name) {
		super(name, new CVSRevision("1.2"));
	}

	public void testAdd() {
		BinomialHeap heap = new BinomialHeap(new ComparableComparator());
		assertTrue(heap.size() == 0);
		Integer five = new Integer(5);
		for(int i=0; i<7; i++) {
			heap.add(five);
		}
		assertTrue(heap.nbExplicitOccurrences(five) == 7);
		assertTrue(heap.size() == 7);
		int nb=67000;
		for(int i=0; i<nb; i++) {
			heap.add(new Integer(i));
		}
		// The other 5 is another object.
		assertTrue(heap.nbExplicitOccurrences(five) == 7);
		assertTrue(heap.size() == nb + 7);
		heap.clear();
		assertTrue(heap.size() == 0);
	}

	public void testPop() {
		Integer one = new Integer(1);
		Integer two = new Integer(2);
		Integer three = new Integer(3);
		BinomialHeap heap = new BinomialHeap(new ComparableComparator());
		heap.add(two);
		heap.add(one);
		heap.add(three);
		assertTrue(heap.pop() == one);
		assertTrue(heap.pop() == two);
		assertTrue(heap.pop() == three);
		assertTrue(heap.size() == 0);
		assertTrue(heap.isEmpty());
		int nb=67000;
		for(int i=0; i<nb; i++) {
			heap.add(new Integer(i));
		}
		assertTrue(heap.size() == nb);
		while(heap.size() > 0) {
			heap.pop();
		}
		assertTrue(heap.size() == 0);
		assertTrue(heap.isEmpty());
	}

}
