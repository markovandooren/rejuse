package org.aikodi.rejuse.java.collections;


import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;


/**
 * <p>A collection which is actually a sorted list.</p>
 *
 * <p>A skiplist is a sorted list with an <b>average</b> <code>O(log(n))</code>
 * behavior for 
 * <a href="SkipList.html#add(java.lang.Object)"><code>add()</code></a> and
 * <a href="SkipList.html#remove(java.lang.Object)">remove()<code></code></a>. 
 * The worst case time complexity is <code>O(n)</code>.</p>
 * <p>A skiplist looks like a linked list, but the difference is that the cells
 * of a skiplist have a number of forward pointers. The number of forward 
 * pointers of a cell is called the <em>level</em> of that cell. 
 * The <a href="SkipList.html#leve()"><code>level</code></a> of a SkipList is 
 * the maximum of the levels of its cells. The level of a SkipList can not 
 * exceed its <a href="SkipList.html#getMaxLevel()">maximum level</a>.</p>
 *
 * <p>The i'th forward pointer of a cell points to the next cell of level 
 * &gt;= i, thus <em>skipping</em> a number of elements with level &lt;i. 
 * An example a skiplist is shown in the figure below.
 * The left cell is the header of the skiplist which contains 
 * <a href="SkipList.html#getMaxLevel()"><code>getMaxLevel()</code></a> forward
 * pointers (but is not included in the level count for the list). 
 * The crossed arrows point to the end of the list, indicating there is no 
 * next cell of level i. The light grey field at the bottom of each cell is a 
 * reference to the element that is represented by that cell. As you can see, 
 * multiple cells can reference the same object  because we are dealing with a
 * list.</p>
 *
 * <center>
 * 	<img src="doc-files/skiplist-example.png"/>
 * </center>
 *
 * <p>The elements of a SkipList are sorted using a 
 * <a href="http://java.sun.com/j2se/1.4/docs/api/index.html"><code>Comparator</code></a>,
 * which is given to the list at the time of construction.</p>
 *
 * <p>The level of a cell is a random number between 1 and 
 * <a href="SkipList.html#getMaxLevel()"><code>getMaxLevel()</code></a>. 
 * There is a fixed 
 * <a href="SkipList.html#getProbability()">probability<code></code></a> 
 * that a cell with a level  &gt;=i has a level &gt;=i+1 (unless of course 
 * i==<a href="SkipList.html#getMaxLevel()"><code>getMaxLevel()</code></a>).
 * It is the randomness that makes that a skiplist has an average 
 * <code>O(log(n))</code> 
 * <a href="SkipList.html#add(java.lang.Object)"><code>add()</code></a> and 
 * <a href="SkipList.html#remove(java.lang.Object)"><code>remove()</code></a> 
 * because only a fixed number of cells must be changed to insert of remove a 
 * cell (no structure has to be maintained).</p>
 * 
 * <p>With probability 0.25 the average number of forward pointers per element
 * is 1.333. A maximum level L will be efficient for a SkipList containing about
 * 4<sup>L</sup> elements. If you add much more elements than that, the number
 * of cell with the maximum level will increase, so searching an element 
 * will take longer. The default level is 8.</p>
 *
 * <p>For more information, go to 
 * <a href="ftp://ftp.cs.umd.edu/pub/skipLists">ftp://ftp.cs.umd.edu/pub/skipLists</a>.</p>
 *
 * @author  Marko van Dooren
 * @author  Tom Schrijvers
 */
public class SkipList<E> extends AbstractCollection<E> {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@ // FIXME
   @ // private behavior
	 @
 	 @ // post level() == 1;
	 @*/

	/**
	 * Initialize a new SkipList with the given comparator.
	 *
	 * @param comparator
	 *        The Comparator used to determine the order of the elements
	 *        in this SkipList.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre comparator != null;
	 @
	 @ post getMaxLevel() == 8;
	 @ post size() == 0;
	 @ post getProbability() == 0.25;
	 @
	 @*/
	public /*@ pure @*/ SkipList(Comparator<E> comparator) {
		this(8,comparator, 0.25f);
  }

 /*@ // FIXME
	 @ // private behavior
	 @
	 @ // post level() == 1;
	 @*/
	/**
	 * Initialize a new SkipList with the given maximum level, comparator and
	 * probability.
	 *
	 * @param maxLevel
	 *        The maximum level of the nodes used internally by
	 *        this SkipList.
	 * @param comparator
	 *        The Comparator used to determine the order of the elements
	 *        in this SkipList.
	 * @param probability
	 *        The fraction of nodes of level >= i that has level >= i + 1
	 */
 /*@
	 @ public behavior
	 @
	 @ pre maxLevel >= 1;
	 @ pre comparator != null;
	 @ pre probability >= 0;
	 @ pre probability <= 1;
	 @
	 @ post getMaxLevel() == maxLevel;
	 @ post size() == 0;
	 @ post getProbability() == probability;
	 @*/
	public /*@ pure @*/ SkipList(int maxLevel, Comparator<E> comparator, float probability) {
		_maxLevel = maxLevel;
		_head = new Object[maxLevel+1];
		_comparator = comparator;
		_probability = probability;
		_prng = new Random();
		_level = 1;
		_update = new Object[maxLevel][];
		_size = 0;
  }

	/**
	 * See superclass
	 */
	public /*@ pure @*/ String toString() {
		StringBuffer result = new StringBuffer();
		Iterator iter = iterator();
		while(iter.hasNext()) {
			result.append(iter.next().toString());
    }
		return result.toString();
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ int size() {
		return _size;
  }

	/**
	 * See superclass
	 */
	public void clear() {
		// Clear the Head node, so all other nodes will be garbage collected.
		Arrays.fill(_head,0,_maxLevel,null);
		_level = 1;
		_size = 0;
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ boolean contains(Object o) {
		try {
			Object[] dummy = search(o);
			return true;
		}
		catch(NoSuchElementException exc) {
			return false;
    }
	}

	/**
	 * Return the first object of this SkipList.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre ! isEmpty();
	 @
	 @ post contains(\result);
	 @ post (\forall Object o; contains(o); 
	 @       getComparator().compare(\result,o)<=0);
	 @*/
	public /*@ pure @*/ E first() {
		return (E) ((Object[]) _head[1])[0];
	}
	
	/**
	 * Return the level of this skiplist
	 */
 /*@
	 @ private behavior
	 @
	 @ post \result >= 1;
	 @ post \result <= getMaxLevel();
	 @*/
	private /*@ pure @*/ int level() {
		return _level;
  }

	/**
	 * Return the maximum level of this SkipList
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result >= 1;
	 @*/
	public /*@ pure @*/ int getMaxLevel() {
		return _maxLevel;
  }

	/**
	 * Return the Comparator used to determine the order in which the
	 * elements are added to this SkipList.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Comparator<E> getComparator() {
		return _comparator;
  }
	
	/**
	 * Return the probability that a node of level >= i is a node
	 * of level >= i + 1
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result >= 0;
	 @ post \result <= 1;
	 @*/
	public /*@ pure @*/ float getProbability() {
		return _probability;
  }

	
	/**
	 * Return the node starting this SkipList.
	 */
 /*@
	 @ private behavior
	 @
	 @ post \result != null;
	 @*/
	private /*@ pure @*/ Object[] getHead() {
		return _head;
  }

	/**
	 * Search for the InternalSkipListNode containing the given value
	 *
	 * @param o
	 *        The value to be looked for.
	 */
 /*@
	 @ private behavior
	 @
	 @ post getComparator().compare(\result[0], object) == 0;
	 @
	 @ signals (NoSuchElementException) (* The object is not in this SkipList *);
	 @*/
	private /*@ pure @*/ Object[] search(final Object object) throws NoSuchElementException {
		Object[] x = _head;
		final Comparator comparator = _comparator;
		int i=_level;
		// level = i
		// the 0-th element is reserved for the value
		do {
			Object[] node = (Object[])x[i];
			while ((node != null) && (comparator.compare(node[0],object) < 0)) {
				x = node;
				node = (Object[])x[i];
			}
			i--;
		} while (i>0);
		
		x = (Object[])x[1];
		if ((x == null) || (comparator.compare(x[0], object) != 0)) {
			throw new NoSuchElementException();
    }
		else {
			return x;
    }
  }
	
  public E higher(E element){
    Object[] next = (Object[])search(element)[1];
    if(next != null) {
      return (E)next[0];
    } else {
  	  return null;
    }
  }

	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Iterator<E> iterator() {
		return new SkipListIterator();
  }
	
	/**
	 * See superclass
	 **/
	public boolean add(final Object object) {
		Object[] x = _head;
		// Cache some frequently used instance variables as local
		// variables to increase speed.
		final Comparator comparator = _comparator;
		final Random prng = _prng;
		final int thisLevel = _level;
		int i = thisLevel;

		int level = 1;
		// @variant _maxLevel - level
		// @invariant level < _maxLevel
		// @invariant level >= 1;
		while ((prng.nextFloat() < _probability) && (level < _maxLevel)) {
			level++;
		}
		
		// Create a new node to insert.
		// Since this is not a set, we can do it in advance.
		Object[] newNode = new Object[level + 1];
		// Set to value of the new node to the given object.
		newNode[0]=object;
		
		// Check whether or not we've exceeded the current level of the list
		if(level > thisLevel) {
			// We did exceed it, so we must set the previously unused forward references
			// to the new node and adjust the level of the list.
			Arrays.fill(_head, thisLevel+1, level+1, newNode);
			_level = level;
		}
		else if (thisLevel>level) {
			// In this case level was smaller than the list, we need to descend without
			// updating anything until we've reached level "level". From there we must
			// start updating (next loop).
			do {
				Object[] node = (Object[])x[i];
				while ((node != null) && (comparator.compare(node[0],object) < 0)) {
					x = node;
					node = (Object[])x[i];
				}
				i--;
			} while (i>level);
		}
		while(i>0) {
			// Descend while updating.
			Object[] node = (Object[])x[i];
			while ((node != null) && (comparator.compare(node[0],object) < 0)) {
				x = node;
				node = (Object[])x[i];
			}
			newNode[i]=node;
			x[i]=newNode;
			i--;
		}
		_modCount++;
		_size++;
		return true;
	}

	/**
	 * Remove the first element of this SkipList.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre ! isEmpty();
	 @
	 @ post (! \old(isEmpty())) ==> (size() == \old(size()) - 1);
	 @ post (! \old(isEmpty())) ==> (Collections.nbExplicitOccurrences(\old(getFirst()),this) ==
	 @                               \old(Collections.nbExplicitOccurrences(getFirst(),this)) - 1);
	 @*/
	public void removeFirst() {
		Object[] first = (Object[]) _head[1];
		// do nothing if this SkipList is empty.
		if(first != null) {
			_size--;
			System.arraycopy(first,1,_head,1,first.length-1);
		}
	}
	
	private boolean remove(final Object object, final boolean removeAll) {
		boolean result = false;
		Object[] x = _head;
		// Cache some frequently used instance variables as local
		// variables to increase speed.
		final Comparator comparator = _comparator;
		final int thisLevel = _level;
		int i = thisLevel;
		//Object[][] update = new Object[thisLevel][];
		final Object[][] update = _update;
		Object[] node = null;
		
		do {
			node = (Object[])x[i];
			while ((node != null) && (comparator.compare(node[0],object) < 0)) {
				x = node;
				node = (Object[])x[i];
			}
			i--;
			update[i]=x;
		} while (i>0);
		// remove the first node that equals the given object. Since
		// comparator.compare() == 0 must be consistent with equals.
		
		// if node == null , the given object wasn't found.
		if ((node != null) && (comparator.compare(node[0],object) == 0)) {
			// If we have to remove all occurrences, we need to search for 
			// the first node with a different value
			if(removeAll) {
				do {
					x = node;
					node = (Object[])x[i];
				} while ((node != null) && (comparator.compare(node[0],object) == 0));
				// At this point node is either null, or greater than object.
				// This means that x is the last node to be removed.
				node = x;
      }
			
			i = node.length-1;
			do {
				Object temp = node[i];
				x = update[i-1];
				x[i] = temp;
				i--;
				if((temp == null) && (x == _head)) {
					_level--;
				}
			} while(i > 0);
			result = true;
			_size--;
		}
		_modCount++;
		return result;
  }
	
	/**
	 * See superclass
	 */
	public boolean remove(Object object) {
		return remove(object, false);
  }
	
	/**
	 * See superclass
	 */
	public boolean removeAll(final Object object) {
		return remove(object, true);
  }
	
	/**
	 * Return a random level between 1 and getMaxLevel(). The fraction
	 * of levels > = i that has level >= i+1 is getProbability().
	 */
  /*@
	 @ private behavior
	 @
	 @ post \result >= 1;
	 @ post \result <= getMaxLevel();
	 @*/
	private /*@ pure @*/ int randomLevel() {
		int level = 1;
		while ((_prng.nextFloat() < _probability) && (level < _maxLevel)) {
			level++;
    }
		return level;
	}


	/**
    * <p>The first node of this SkipList.</p>
    * 
 	 * <p>All elements in a SkipList are kept as nodes. Nodes are represented
	 * as object arrays for efficiency reasons. I tried an object-oriented
	 * cell design, but it was too slow.</p>
	 *
	 * <p>The nodes have the following structure :</p>
	 * <ul>
	 * 	<li>node[0] is a reference to the element contained in the node.</li>
	 *  <li>node[i] is the i'th forward pointer. It points to the next
	 * node of level >= i, or null if such a node does not exist (that
	 * means that the node itself is the last one of level >= i).</li>
	 * </ul>
	 *
	 * <p>The number of forward pointer is the level of the node, and is
	 * restricted by _maxLevel.</p>
	 */
 /*@
	 @ private invariant _head != null;
	 @*/
	private Object[] _head;

	/**
	 * <p>The maximum level an object can have in this SkipList.</p>
	 */
 /*@
	 @ private invariant _maxLevel >= 1;
	 @*/
	private int _maxLevel;
	
	/**
	 * <p>The Comparator that will determine the order of the elements
	 * in this SkipList.</p>
	 */
 /*@
	 @ private invariant _comparator != null;
	 @*/
	private Comparator<E> _comparator;
	
	/**
	 * <p>The fraction of node of level i that have a level >= i.</p>
	 */
 /*@
	 @ private invariant _probability >= 0;
	 @ private invariant _probability <= 1;
	 @*/
	private float _probability;

	/**
	 * <p>The level of this node, being the maximum of the levels of all nodes.</p>
	 */
 /*@
	 @ private invariant _level >= 0;
	 @ private invariant _level <= _maxLevel;
	 @*/
	private int _level;
	
	/**
	 * </p>The update vector of the skiplist algorithm. It is kept as an 
	 * instance variable to prevent the construction of lots of array 
	 * which are thrown away immediately. It may of course only be used 
	 * by a single thread at a time. So either provide a locking mechanism, 
	 * or synchronize the entire methods that use it.</p>
	 * <p>Locking it, and only constructing a new array if _update is already
	 * in use is still a lot faster than creating a new one by default.</p>
	 *
	 * MVDMVDMVD: put this in a general optimization page or so
	 */
 /*@
	 @ private invariant _update != null;
	 @ private invariant _update.length == _level;
	 @*/
	private Object[][] _update;
	
	/**
	 * <p>The pseudo random number generator used to determine the level of a node.</p>
	 */
 /*@
	 @ private invariant _prng != null;
	 @*/
	private Random _prng;
	
	/**
	 * <p>The modcount of this SkipList. It is used by
	 * SkipListIterators in order to check whether or not
	 * concurrent modifications have happened.</p>
	 */
	private int _modCount = 0;
	
	/**
	 * <p>The size of this SkipList.</p>
	 */
 /*@
	 @ private invariant _size >= 0;
	 @*/
	private int _size;

	/**
	 * An iterator class for SkipLists
	 */
	private class SkipListIterator implements Iterator<E> {
		
		/**
		 * Initialize a new SkipListIterator which is set to the start of the SkipList.
		 */
		public /*@ pure @*/ SkipListIterator() {
			_node = (Object[])SkipList.this._head[1];
			_lastRet = null;
    }
		
		/**
		 * See superclass
		 */
		public /*@ pure @*/ boolean hasNext() {
			return _node != null;
    }
		
		/**
		 * Check whether or not the SkipList was modified by someone else
		 * and throw a ConcurrentModificationException if that is the case.
		 */
	 /*@
		 @ private behavior
		 @
		 @ // Make sure that an exception must be thrown when a modification
		 @ // has happened.
		 @ post _expectedModCount == SkipList.this._modCount;
		 @
		 @ signals (ConcurrentModificationException) _expectedModCount != SkipList.this._modCount;
		 @*/
		private void checkForModification() throws ConcurrentModificationException {
			if (_expectedModCount != SkipList.this._modCount) {
				throw new ConcurrentModificationException();
      }
    }
		
		/**
		 * See superclass
		 */
		public E next() {
			checkForModification();
			try {
				E result = (E) _node[0];
				// advance
				_lastRet=_node;
				_node = (Object[]) _node[1];
				return result;
			}
			catch(NullPointerException exc) {
				// first check to see if the exception originated because the next element
				// was removed between the check and the array access.
				// Sun does it in AbstractList, so we'll do it too.
				checkForModification();
				throw new NoSuchElementException();
      }
    }
		
		/**
		 * See superclass
		 */
		public void remove() {
			if(_lastRet == null) {
				throw new IllegalStateException();
      }
			checkForModification();
			SkipList.this.remove(_lastRet[0]);
			_lastRet = null;
			_expectedModCount = SkipList.this._modCount;
    }
		
		/**
		 * The node that will be returned on the next next() call.
		 */
		private Object[] _node;
		
		/**
		 * The node that has been returned by the last next() call.
		 */
		private Object[] _lastRet;

		/**
		 * The modcount of the SkipList.
		 */
		private int _expectedModCount = SkipList.this._modCount;
  }

}