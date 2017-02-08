package org.aikodi.rejuse.java.collections;

import java.util.Comparator;
import java.util.Iterator;

/**
 * <p>A {@link org.aikodi.rejuse.java.collections.SkipList SkipList} based priority queue</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public class SkipListPQ extends AbstractPriorityQueue {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";
		
	/**
	 * Initialize a new SkipListPQ with the given comparator.
	 *
	 * @param comparator
	 *        The comparator that is used to determine the order
	 *        of the added elements.
	 */
 /*@
	 @ public behavior
	 @
	 @ post size() == 0;
	 @ post getComparator() == comparator;
	 @*/
	public SkipListPQ(Comparator comparator) {
		_list = new SkipList(10,comparator,0.25f);
	}

	/**
	 * <p>See superclass.</p>
	 *
	 * <p>average: <b>O(log(n))</b></p>
	 * <p>worst case: <b>O(n)</b></p>
	 */
	protected void addImpl(Object element) {
		_list.add(element);
	}

	/**
	 * <p>See superclass.</p>
	 *
	 * <p><b>O(1)</b></p>
	 */
	public /*@ pure @*/ Object min() {
		return _list.first();
	}

	/**
	 * <p>See superclass.</p>
	 *
	 * <p><b>O(1)</b></p>
	 */
	public Object pop() {
		Object result = _list.first();
		_list.removeFirst();
		return result;
	}

	/**
	 * <p>See superclass.</p>
	 */
	public /*@ pure @*/ int nbExplicitOccurrences(Object element) {
		return Collections.nbExplicitOccurrences(element, _list);
	}

	/**
	 * <p>See superclass.</p>
	 */
	public /*@ pure @*/ int size() {
		return _list.size();
	}

	/**
	 * <p>See superclass.</p>
	 */
	public /*@ pure @*/ Comparator getComparator() {
		return _list.getComparator();
	}

	/**
	 * <p>See superclass.</p>
	 */
	public void clear() {
		_list.clear();
	}

	/**
	 * See superclass.
	 */
	public Iterator iterator() {
		return _list.iterator();
	}

	/**
	 * The SkipList that will hold all elements.
	 */
 /*@
	 @ private invariant _list != null;
	 @*/
	private final SkipList _list;
}

