package org.aikodi.rejuse.association;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.aikodi.rejuse.action.Action;
import org.aikodi.rejuse.java.collections.Visitor;
import org.aikodi.rejuse.predicate.SafePredicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

/**
 * <p>A class of Relation components for implementing a binding in which the object of
 * the Reference has a relation with N other objects. This component behaves as a set.</p>
 *
 * <center><img src="doc-files/ReferenceSet.png"/></center>
 *
 * <p>In UML this class is used for implementing multiplicity n:</p>
 *
 * <center><img src="doc-files/referenceset_uml.png"/></center>
 *
 * <p>In Java, you get the following situation.</p>
 * <center><img src="doc-files/referenceset_java.png"/></center>
 *
 * <p>Note that the question mark is represented by a <code>Relation</code> object since
 * we don't know its multiplicity. The double binding between the <code>ReferenceSet</code> 
 * object and <code>A</code> is made by passing <code>this</code> to the constructor of 
 * ReferenceSet.</p>
 *
 * <p>This is actually a lightweight version of the APSet class.
 * of the <a href="http://www.beedra.org">Beedra</a> framework of Jan Dockx.</p>
 *
 * <p>This class is typically using in the following way.
 * <pre><code>
 *public class A {
 *
 *  public A() {
 *    _b= new ReferenceSet(this);
 *  }
 *
 *  public List getBs() {
 *    return _b.getOtherEnds();
 *  }
 *
 *  // public Set getBs() {
 *  //   return new TreeSet(_b.getOtherEnds());
 *  // }
 *
 *  public void addB(B b) {
 *    _b.add(b.getALink());
 *  }
 *
 *  public void removeB(B b) {
 *    _b.remove(b.getALink());
 *  }
 *
 *  private ReferenceSet _b;
 *}
 * </code></pre>
 *
 * <p>The other class must have a method <code>getALink()</code>, which returns a 
 * <a href="Relation.html"><code>Relation</code></a> object that represents the other side
 * of the bi-directional binding. In case the two classes are not in the same package that means
 * that <code>getALink()</code> must be <code>public</code> there, but that is also the case when
 * not working with these components. Note that if the binding should not be mutable from class 
 * <code>A</code> the <code>addB()</code> may be removed. Similarly, <code>getBLink()</code> may 
 * be removed if the binding is not mutable from class <code>B</code>.</p>
 *
 * <p>If <code>getBs()</code> must return a Set, you should add the result of <code>_b.getOtherEnds()</code>
 * to a new Set (e.g. TreeSet). The general method <code>getOtherEnds()</code> must return a <code>List</code>
 * because in some bindings the order may be important.</p>
 *
 * @author  Marko van Dooren
 */
public class OrderedMultiAssociation<FROM,TO> extends AbstractOrderedMultiAssociation<FROM,TO> {

  /**
   * Initialize an empty OrderedMultiAssociation for the given object.
   *
   * @param object
   *        The object on this side of the binding
   */
  /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObject() == object;
   @ post (\forall Relation r;; !contains(r));
   @*/
  public OrderedMultiAssociation(FROM object) {
    super(object);
  }

  /**
   * Initialize an empty OrderedMultiAssociation for the given object with the
   * given capacity. Note that the capacity is not the size. This constructor
   * is useful to avoid internal copying when adding elements if you already
   * know roughly how many elements will be added. 
   *
   * @param object The object on this side of the binding
   * @param initialCapacity The initial internal capacity.
   */
  /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObject() == object;
   @ post (\forall Relation r;; !contains(r));
   @*/
  public OrderedMultiAssociation(FROM object, int initialCapacity) {
    super(object, initialCapacity);
  }

  /**
   * Add the given Relation to this ReferenceSet.
   *
   * @param element
   *        The Relation to be added.
   */
 /*@
   @ public behavior
   @
   @ pre element != null;
   @
   @ post registered(\old(getOtherRelations()), element);
   @ post element.registered(\old(element.getOtherRelations()),this);
   @*/  
  public void add(Association<? extends TO,? super FROM> element) {
    checkLock();
    checkLock(element);
    if(element != null) {
      boolean added = element.register(this);
      // Skip a redundant contains check.
      if(added) {
        registerPrivate(element);
      }
    }
  }

  
  /**
   * Remove the given Relation from this ReferenceSet.
   *
   * @param element
   *        The Relation to be removed.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post unregistered(\old(getOtherRelations()), other);
   @ post other.unregistered(\old(other.getOtherRelations()), this);
   @*/  
  public void remove(Association<? extends TO,? super FROM> other) {
    checkLock();
    checkLock(other);
    if (contains(other)) {
      other.unregister(this);
      // Skip a redundant contains check.
      unregisterInternal(other);
    }
  }

  /**
   * {@inheritDoc} 
   */
  public void clear() {
    checkLock();
    if(isStored()) {
      Collection<Association<? extends TO,? super FROM>> rels = new ArrayList<>(internalAssociations());
      for(Association<? extends TO,? super FROM> rel : rels) {
        checkLock(rel);
      }
      for(Association<? extends TO,? super FROM> rel : rels) {
        remove(rel);
      }
    }
  }

  /**
   * The set containing the StructureElements at the n side of the 1-n binding.
   */
  /*@
   @ private invariant ! _elements.contains(null);
   @ private invariant (\forall Object o; _elements.contains(o);
   @                      o instanceof Relation);
   @*/
  private ArrayList<Association<? extends TO,? super FROM>> _elements;
  
  public Association<? extends TO,? super FROM> associationAt(int index) {
    if(index < 0 || index >= size()) {
      throw new IllegalArgumentException();
    }
  	return _elements.get(index);
  }
  
  protected void setAssociationAt(int index, Association<? extends TO,? super FROM> association) {
    _elements.set(index, association);
  }
  
  public int indexOfAssociation(Association<? extends TO,? super FROM> association) {
  	return _elements.indexOf(association);
  }

  protected boolean isStored() {
  	return _elements != null;
  }

  @Override
  protected List<Association<? extends TO, ? super FROM>> internalAssociations() {
    return _elements;
  }

  protected void initStorage() {
    _elements = new ArrayList<Association<? extends TO,? super FROM>>(initialCapacity());  	
  }

  protected boolean removeAssociation(Association<? extends TO,? super FROM> association) {
  	return _elements.remove(association);
  }
}

