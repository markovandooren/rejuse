package org.rejuse.java.collections.test;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rejuse.java.collections.SkipList;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestSkipList extends JutilTest {
  
	public static void main(String[] args) {
		TestSkipList blah = new TestSkipList("test");
		blah.setUp();
    // overwrite the default $number from setUp()
    blah.$number = new Integer(args[0]).intValue();
    System.out.println(blah.$number);
		blah.test(false);
		blah.test(false);
		blah.test(false);
		blah.test(true);			
  }
	
  public TestSkipList(String name) {
    super(name, new CVSRevision("1.7.8.2"));
  }

	public void setUp() {
		$reps=5;
		$reps_iter=40;
		$number=8000;
		//$p=0.368f;
		$p=0.25f;
		$comparator = new Comparator() {
			public boolean equals(Object other) {
				return other == this;
      }
			
			public int compare(Object o1, Object o2) {
				if (((Integer) o1).intValue() < ((Integer) o2).intValue()) {
					return -1;
        }
				else if (((Integer) o1).intValue() == ((Integer) o2).intValue()) {
					return 0;
        }
				else {
					return 1;
        }
      }
    };
  }
	
	public Comparator getComparator() {
		return $comparator;
  }
	
	private int $number;
	private int $reps;
	private int $reps_iter;
	private float $p;
	
	private Comparator $comparator;

	public void test() {
		test(false);
	}
	
	public void test(boolean print) {
		testAdd(print);
		testRemove(print);
		testIteration(print);
  }
	
	
	
	public void testAdd(boolean print) {
		if(print) {
			System.out.println("Testing add");
    }
		SkipList myList = new SkipList(16, getComparator(), $p);
		SortedSet sortedSet = new TreeSet(getComparator());
		Integer[] ints = new Integer[$number];
		for(int i=0; i <$number; i++) {
			ints[i] = new Integer((int)(Math.random()*Integer.MAX_VALUE));
    }
		
		System.gc();
		
		// TreeSet
		long startSortedSet = System.currentTimeMillis();
		for(int k=1; k<= $reps; k++) {
			sortedSet = new TreeSet(getComparator());
			for(int i=0; i <$number; i++) {
				sortedSet.add(ints[i]);
			}
		}
		long stopSortedSet = System.currentTimeMillis();
		sortedSet = null;
		
		//Clean Up
		System.gc();
		
		// SkipList
		long startSkipList = System.currentTimeMillis();
		for(int k=1; k<= $reps; k++) {
			myList = new SkipList(20, getComparator(), $p);
			for(int i=0; i <$number; i++) {
				myList.add(ints[i]);
			}
		}
		long stopSkipList = System.currentTimeMillis();
// 		if (print) {
// 			System.out.println("Level of SkipList : "+myList.level());
//     }
		myList = null;
		ints = null;
		System.gc();

				
		if(print) {
			System.out.println("<<<<<<<<<<");
			System.out.println("SkipList : "+(stopSkipList - startSkipList));
			System.out.println("TreeSet : "+(stopSortedSet - startSortedSet));
			System.out.println(">>>>>>>>>>");
		}		
  }

	public void testIteration(boolean print) {
		if(print) {
			System.out.println("Testing iteration");
    }
		SkipList myList = new SkipList(16, getComparator(), $p);
		SortedSet sortedSet = new TreeSet(getComparator());
		for(int i=0; i <$number; i++) {
			Integer to_add = new Integer((int)(Math.random()*Integer.MAX_VALUE));
			sortedSet.add(to_add);
			myList.add(to_add);
		}
		
		System.gc();
		
		// TreeSet
		long startSortedSet = System.currentTimeMillis();
		for(int k=1; k<= $reps_iter; k++) {
			Iterator iter = sortedSet.iterator();
			while(iter.hasNext()) {
				Object o = iter.next();
      }
		}
		long stopSortedSet = System.currentTimeMillis();
		if(print) {
			System.out.println("Size of sortedSet : "+sortedSet.size());
    }
		//sortedSet = null;
		
		//Clean Up
		System.gc();
		
		// SkipList
		long startSkipList = System.currentTimeMillis();
		for(int k=1; k<= $reps_iter; k++) {
			Iterator iter = myList.iterator();
			while(iter.hasNext()) {
				Object o = iter.next();
      }
		}
		long stopSkipList = System.currentTimeMillis();
		myList = null;

		System.gc();
				
		if(print) {
			System.out.println("<<<<<<<<<<");
			System.out.println("SkipList : "+(stopSkipList - startSkipList));
			System.out.println("TreeSet : "+(stopSortedSet - startSortedSet));
			System.out.println(">>>>>>>>>>");
		}		
  }
	
	public void testRemove(boolean print) {
		if(print) {
			System.out.println("Testing remove");
    }
		int reps = $reps;
		int number = $number;
		//SkipList myList = new SkipList(16, getComparator(), $p);
		//SortedSet sortedSet = new TreeSet(getComparator());
		Integer[][] ints = new Integer[reps][number];
		
		SortedSet[] sets = new SortedSet[reps];
		SkipList[] lists = new SkipList[reps];
		
		for(int i=reps; --i >= 0;) {
			sets[i] = new TreeSet(getComparator());
			lists[i] = new SkipList(16, getComparator(), $p);
    }
		
		for(int i=reps; --i >= 0;) {
			for(int j=number; --j >= 0;) {
				Integer to_add = new Integer((int)(Math.random()*Integer.MAX_VALUE));
				ints[i][j] = to_add;
				sets[i].add(to_add);
				lists[i].add(to_add);
			}
		}
		
		System.gc();
		
		// TreeSet
		long startSortedSet = System.currentTimeMillis();
		for(int k=reps; --k >= 0;) {
			for(int i = number; --i>=0;) {
				sets[k].remove(ints[k][i]);
      }
		}
		long stopSortedSet = System.currentTimeMillis();
		
		//Clean Up
		//System.gc();
		
		// SkipList
		long startSkipList = System.currentTimeMillis();
		for(int k=reps; --k >= 0;) {
			for(int i = number; --i>=0;) {
				lists[k].remove(ints[k][i]);
      }
		}
		long stopSkipList = System.currentTimeMillis();
		lists = null;
		sets = null;
		ints = null;
		
		System.gc();
				
		if(print) {
			System.out.println("<<<<<<<<<<");
			System.out.println("SkipList : "+(stopSkipList - startSkipList));
			System.out.println("TreeSet : "+(stopSortedSet - startSortedSet));
			System.out.println(">>>>>>>>>>");
		}		
  }

	public void testRemoveFirst() {
		SkipList list = new SkipList(getComparator());
		Integer min = new Integer(-93432);
		Integer a = new Integer(-93431);
		Integer b = new Integer(-84);
		Integer c = new Integer(-3);
		Integer d = new Integer(5);
		Integer e = new Integer(45);
		Integer f = new Integer(8457);
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
		list.add(f);
		list.add(min);
		assertTrue(list.getFirst() == min);
		list.removeFirst();
		assertTrue(list.getFirst() == a);
		list.removeFirst();
		assertTrue(list.getFirst() == b);
		list.removeFirst();
		assertTrue(list.getFirst() == c);
		list.removeFirst();
		assertTrue(list.getFirst() == d);
		list.removeFirst();
		assertTrue(list.getFirst() == e);
		list.removeFirst();
		assertTrue(list.getFirst() == f);
	}
	public void testGetFirst() {
		SkipList list = new SkipList(getComparator());
		Integer min = new Integer(-93432);
		list.add(new Integer(5));
		list.add(new Integer(-3));
		list.add(new Integer(45));
		list.add(new Integer(-84));
		list.add(new Integer(-93431));
		list.add(new Integer(8457));
		list.add(min);
		assertTrue(list.getFirst() == min);
	}
}
/*
 * <copyright>Copyright (C) 1997-2001. This software is copyrighted by 
 * the people and entities mentioned after the "@author" tags above, on 
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
