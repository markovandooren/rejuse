package org.aikodi.rejuse.java.collections;

import java.util.Comparator;

/**
 * <p>A BasicBinomialHeap is a Heap that consists
 * of a forest of Binomial Trees.<p>
 *
 * <p>Most operations are <b>O(log(n))</b>.</p>
 * 
 * @author Tom Schrijvers
 * @release $Name$
 */
class BasicBinomialHeap extends AbstractPriorityQueue {

  /**
   * The forest of BinomialTrees.
   */
  private BinomialTree _trees;

  /**
   * The comparator.
   */
 /*@
   @ private invariant _comparator != null;
   @*/
  private final Comparator _comparator;

 /*@
   @ private invariant _size >= 0;
   @*/
  private int _size = 0;

  /**
   * Initialize a new BasicBinomialHeap with the given comparator.
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
  public BasicBinomialHeap(Comparator comparator) {
    _comparator = comparator;
  }

  /**
   * Initialize a new BasicBinomialHeap with the given comparator
   * and element.
   *
   * @param comparator
   *        The comparator that is used to determine the order
   *        of the elements.
   * @param element
   *        The initial element in the new BasicBinomialHeap.
   */
 /*@
   @ public behavior
   @
   @ pre comparator != null;
   @ pre element != null;
   @
   @ post getComparator() == comparator;
   @ post nbExplicitOccurrences(element) == 1;
   @ post size() == 1;
   @*/
  public BasicBinomialHeap(Comparator comparator, Object element) {
    _comparator = comparator;
    _trees = new BinomialTree(element);
    _size = 1;
  }

  /**
   * <p>See superclass.</p>
   */
  public /*@ pure @*/ Comparator getComparator() {
    return _comparator;
  }
  
  /**
   * <p>See superclass.</p>
   *
   * <p><b>O(log(n))</b></p>
   */
  protected void addImpl(Object value) {
    addMerge(new BinomialTree(value));
  }

  private void addMerge(BinomialTree otherTree) {
    if ( (_size & 1) == 0 ) {
      _size++;
      otherTree.setNext(_trees);
      _trees = otherTree;
      return;
    }
    _size++;
    int theSize = _size >> 1;
  
    BinomialTree carry_over = _trees;
    BinomialTree currentTree = _trees.getNext();
    carry_over.merge(otherTree, _comparator);

    while ( (theSize & 1) == 0 ) {
      theSize >>= 1;
      otherTree = currentTree.getNext();
      carry_over.merge(currentTree, _comparator);
      currentTree = otherTree;
    }
    carry_over.setNext(currentTree);
    _trees = carry_over;
  }

  /**
   * <p>See superclass.</p>
   *
   * <p><b>O(log(n))</b></p>
   */
  public /*@ pure @*/ Object min() {
    BinomialTree current = _trees;
    Object result = current.getRoot();
    while ( current.getNext() != null ) {
       current = current.getNext(); 
       Object value = current.getRoot();
       if ( _comparator.compare(result, value) > 0 ) {
         result = value;
       }
    }
    return result;
  }

  /**
   * <p>See superclass.</p>
   * 
   * <p><b>O(log(n))</b></p>
   */
  public Object pop() {
    BinomialTree previousRTree = null;
    BinomialTree currentRTree = _trees;
    BinomialTree previousTree = null;
    BinomialTree currentTree = _trees;
    Object result = currentTree.getRoot();
    while ( currentTree.getNext() != null ) {
       previousTree = currentTree; 
       currentTree  = currentTree.getNext(); 
       Object value = currentTree.getRoot();
       if ( _comparator.compare(result, value) > 0 ) {
         result = value;
         previousRTree = previousTree;
         currentRTree = currentTree;
       }
    }
    if ( previousRTree != null ) {
      previousRTree.setNext(currentRTree.getNext());
    } else {
      _trees = currentRTree.getNext();
    }
    _size = _size - currentRTree.size();
    if ( currentRTree.children() != null ) {
      merge(currentRTree.children().reverse(),currentRTree.size() - 1);
    }
    return result;
  }

  /**
   * <p>Add all elements in the given BasicBinomialHeap to this heap
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
   @ 
   @ post size() == \old(size()) + \old(heap.size());
   @ post (\forall Object element; ;
   @               nbExplicitOccurrences(element) == \old(nbExplicitOccurrences(element)) + 
   @                                                 \old(heap.nbExplicitOccurrences(element)));
   @ post (\forall Object element; ;
   @               heap.nbExplicitOccurrences(element) == 0);
   @*/
  public void merge(BasicBinomialHeap heap) {
    merge(heap._trees, heap.size());
    heap.clear();
  }

  private void merge(BinomialTree otherTree, int otherSize) {

    if ( otherTree == null ) {
      return;
    }
    
    if ( _trees == null ) {
      _trees = otherTree;
      _size = otherSize;
      return;
    }

    if ( otherSize == 1 ) {
      addMerge(otherTree);
      return;
    }
    
    // At least one cell in each chain
    
    BinomialTree tmp = null;
  
    int oldSize = 0;

    // ensure trees contains 'longest' chain
    if ( _size < otherSize ) {
      // swap sizes
      oldSize   = otherSize;
      otherSize = _size;
      _size = oldSize;
      // swap cells
      tmp = _trees;
      _trees = otherTree;
      otherTree = tmp;
    }
  
    oldSize = _size;
    _size += otherSize;

    BinomialTree previousTree = null;
    BinomialTree currentTree  = _trees;
    BinomialTree carry_over   = null;
    
    int bits = 0;
    oldSize <<= 2;
    otherSize <<= 1;
    
    do {
    
      // Invariant previousTree == null ||
      //           previousTree.getNext() == currentTree
      bits = bits | (oldSize & 4) | (otherSize & 2);
      oldSize >>= 1;
      otherSize >>= 1;
      
      // bits t o c
      switch (bits) {
        case 0: // 0 0 0
          break;
        case 1: // 0 0 1
          if ( previousTree != null ) { 
            previousTree.setNext(carry_over); 
          } else {
            _trees = carry_over;
          }
          previousTree = carry_over;
          previousTree.setNext(currentTree);
          bits = 0;
          break;
        case 2: // 0 1 0
          if ( previousTree != null ) {
            previousTree.setNext(otherTree); 
          } else {
            _trees = otherTree;
          }
          previousTree = otherTree;
          otherTree = otherTree.getNext();
          previousTree.setNext(currentTree);
          bits = 0;
          break;
        case 3: // 0 1 1
          tmp = otherTree.getNext();
          carry_over.merge(otherTree,_comparator);
          otherTree = tmp;
          bits = 1;
          break;
        case 4: // 1 0 0
          previousTree = currentTree;
          currentTree = currentTree.getNext();
          bits = 0;
          break;
        case 5: // 1 0 1
          if ( previousTree != null ) {
            previousTree.setNext(currentTree.getNext());
          }
          tmp = currentTree.getNext();
          carry_over.merge(currentTree, _comparator);
          currentTree = tmp;
          bits = 1;
          break;
        case 6: // 1 1 0
          if ( previousTree != null ) { 
            previousTree.setNext(currentTree.getNext()); 
          } 
          carry_over = currentTree;
          currentTree = currentTree.getNext();
          tmp = otherTree.getNext();
          carry_over.merge(otherTree, _comparator);
          otherTree = tmp;
          bits = 1;
          break;
        case 7: // 1 1 1
          if ( previousTree == null ) {
            _trees = currentTree;
          }
          previousTree = currentTree;
          currentTree = currentTree.getNext();
          tmp = otherTree.getNext();
          carry_over.merge(otherTree, _comparator);
          otherTree = tmp;
          bits = 1;
          break;
      } 
    } while ( otherSize > 1 ) ;


    if ( bits > 0 ) { // bits == 1
      if (previousTree != null ) {
        previousTree.setNext(carry_over);
      } else {
        _trees = carry_over;
      }
      while ( (oldSize & 4) > 0 ) {
        oldSize >>= 1;
        tmp = currentTree.getNext();
        carry_over.merge(currentTree, _comparator);
        currentTree = tmp;
      }
      carry_over.setNext(currentTree);
    }
  }


  
  /**
   * <p>See superclass</p>
   */
  public void clear() {
    _trees = null;
    _size = 0;
  }

  /**
   * <p>See superclass.</p>
   *
   * <p><b>O(n)</b></p>
   */
  public /*@ pure @*/ int nbExplicitOccurrences(Object element) {
    int count = 0;
    BinomialTree current = _trees;
    while ( current != null ) {
      count += current.nbExplicitOccurrences(element);
      current = current.getNext();
    }
    return count;
  }

  /**
   * <p>See superclass.</p>
   */
  public /*@ pure @*/ int size() {
    return _size;
  }


  private static class BinomialTree {
    
   /*@
     @ private invariant _root != null;
     @*/
    private Object _root;

    private BinomialTree _next;

   /*@
     @ private invariant _size > 0;
     @*/
    private int _size = 1;
  
    private BinomialTree _children;

   /*@  
     @ public behavior
     @
     @ pre value != null;
     @
     @ post size() == 1;
     @ post getRoot() == value;
     @ post nbExplicitOccurrences(value) == 1;
     @ post children() == null;
     @*/
    public BinomialTree(Object value) {
      _root = value;
    }
  
   /*@
     @ public behavior
     @
     @ post \result != null;
     @*/
    public /*@ pure @*/ Object getRoot() {
      return _root;
    }
   /*@
     @ public behavior
     @
     @ post getNext() == next;
     @*/
    public void setNext(BinomialTree next) {
      _next = next;
    }

    public /*@ pure @*/ BinomialTree getNext() {
      return _next;
    }

    public void merge(BinomialTree other, Comparator comp) {
      if (comp.compare(this.getRoot(), other.getRoot()) <= 0) {
        mergeImpl(other);
      } else {
        BinomialTree children = _children;
        Object root = _root;
        _root = other._root;
        _children = other._children;
        other._root = root;
        other._children = _children;
        mergeImpl(other);
      }
    }

    private void mergeImpl(BinomialTree other) {
      other.setNext(_children);
      _children = other;
      _size <<= 1;
    }
    
   /*@
     @ public behavior
     @
     @ post \result == (\sum Object item; ; nbExplicitOccurrences(item));
     @ post \result > 0;
     @*/
    public /*@ pure @*/ int size() {
      return _size;
    }
   /*@
     @ public behavior
     @
     @ post \result >= 0;
     @*/
    public /*@ pure @*/ int nbExplicitOccurrences(Object element) {
      int count = (_root == element ? 1 : 0);
      BinomialTree current = _children;
      while ( current != null ) {
        count += current.nbExplicitOccurrences(element);
        current = current.getNext();
      }
      return count;
    }

    public /*@ pure @*/ BinomialTree children() {
      return _children;
    }

    public BinomialTree reverse() {
      BinomialTree previous = null;
      BinomialTree current = this;
      BinomialTree temp = null;
      do {
        temp = current.getNext();
        current.setNext(previous);
        previous = current;
              current = temp;  
      } while ( current != null );
      return previous;
    }
  }
}

