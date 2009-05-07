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
