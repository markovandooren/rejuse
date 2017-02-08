package org.aikodi.rejuse.java.collections;

import java.util.Comparator;

/**
 * <p>A BinomialHeap is a Heap with most of
 *    its operations <b>O(1)</b> or <b>O(log(1))</b>.<p>
 * 
 * @author Tom Schrijvers
 * @release $Name$
 */
public class BinomialHeap extends AbstractPriorityQueue {

	private final BasicBinomialHeap _rest;
	private Object _root = null;
	
	/**
	 * Initialize a new BinomialHeap with the given comparator.
	 *
	 * @param comparator
	 *        The comparator that is used to determine the order
	 *        of the elements.
	 */
 /*@
   @ public behavior
   @
	 @ pre comparator != null;
	 @
	 @ post getComparator() == comparator;
	 @ post size() == 0;
	 @*/
	public BinomialHeap(Comparator comparator) {
		_rest = new BasicBinomialHeap(comparator);
	}

	/**
	 * Initialize a new BinomialHeap with the given comparator
	 * and element.
	 *
	 * @param comparator
	 *        The comparator that is used to determine the order
	 *        of the elements.
	 * @param root
	 *        The initial element in the new BinomialHeap.
	 */
 /*@
   @ public behavior
	 @
	 @ pre comparator != null;
	 @ pre root != null;
	 @
	 @ post getComparator() == comparator;
	 @ post nbExplicitOccurrences(root) == 1;
	 @ post size() == 1;
	 @*/
	public BinomialHeap(Comparator comparator, Object root) {
		this(comparator);
		_root = root;
	}

	/**
	 * <p>See superclass.</p>
	 */
	public /*@ pure @*/ Comparator getComparator() {
		return _rest.getComparator();
	}
	
	/**
	 * <p>See superclass.</p>
	 *
	 * <p><b>O(log(n))</b></p>
	 */
	protected void addImpl(Object value) {
		if ( _root == null ) {
			_root = value;
			return;
		}
		if ( getComparator().compare(_root, value) < 0) {
			_rest.add(value);
		} else {
      _rest.add(_root);
			_root = value;
		}
	}

	/**
	 * <p>See superclass.</p>
	 *
	 * <p><b>O(1)</b></p>
	 */
	public /*@ pure @*/ Object min() {
		return _root;
	}

	/**
	 * <p>See superclass.</p>
	 * 
	 * <p><b>O(log(n))</b></p>
	 */
	public Object pop() {
		Object result = _root;
		if ( ! _rest.isEmpty() ) {
			_root = _rest.pop();
		} else {
			_root = null;
		}
		return result;
	}

	/**
	 * <p>Add all elements in the given BinomialHeap to this heap
	 * and empty that heap.</p>
	 * 
	 * @param heap
	 *        The heap of which the elements are to be added to this heap.
	 *        
	 * <p><b>O(log((n))</b></p>
	 */
 /*@
   @ public behavior
	 @
	 @ pre heap != null;
	 @ pre (\forall Object a,b; heap.nbExplicitOccurrences(a) > 0 &&
	 @                          heap.nbExplicitOccurrences(b) > 0 
	 @                        ; getComparator().compare(a, b) == 0    ||
	 @                          heap.getComparator().compare(a, b) == 0 ||
	 @                          getComparator().compare(a, b) * heap.getComparator().compare(a, b) > 0 );
	 @                           
	 @ 
	 @ post size() == \old(size()) + \old(heap.size());
	 @ post (\forall Object element; ;
	 @               nbExplicitOccurrences(element) == \old(nbExplicitOccurrences(element)) + 
	 @                                                 \old(heap.nbExplicitOccurrences(element)));
	 @ post (\forall Object element; ;
	 @               heap.nbExplicitOccurrences(element) == 0);
	 @*/
	public void merge(BinomialHeap heap) {
		_rest.merge(heap._rest);
		if ( getComparator().compare(_root, heap._root) > 0) {
			_rest.add(_root);
			_root = heap._root;
		} else {
			_rest.add(heap._root);
		}
	}

	
	/**
	 * <p>See superclass</p>
	 */
	public void clear() {
		_root = null;
		_rest.clear();
	}

	/**
	 * <p>See superclass.</p>
	 *
	 * <p><b>O(n)</b></p>
	 */
	public /*@ pure @*/ int nbExplicitOccurrences(Object element) {
		if ( element == null ) {
			return 0;
		}
		return _rest.nbExplicitOccurrences(element) + (_root == element ? 1 : 0);
	}

	/**
	 * <p>See superclass.</p>
	 */
	public /*@ pure @*/ int size() {
		return (_root == null ? 0 : 1 ) + _rest.size();
	}

}

