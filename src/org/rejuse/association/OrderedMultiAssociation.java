package org.rejuse.association;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rejuse.java.collections.Visitor;
import org.rejuse.predicate.SafePredicate;

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
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class OrderedMultiAssociation<FROM,TO> extends Association<FROM,TO> {
  
    /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

 //@ public invariant contains(null) == false;
 //@ public invariant getObject() != null;
 
 /*@ 
   @ public invariant (\forall Relation e; contains(e);
   @                    e.contains(this));
   @*/

  /**
   * Initialize an empty ReferenceSet for the given object.
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
    _elements = new ArrayList<Association<? extends TO,? super FROM>>();
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
      unregisterPrivate(other);
    }
  }
  
  public void clear() {
  	checkLock();
  	Collection<Association<? extends TO,? super FROM>> rels = new ArrayList<Association<? extends TO,? super FROM>>(_elements);
  	for(Association<? extends TO,? super FROM> rel : rels) {
  		checkLock(rel);
  	}
  	for(Association<? extends TO,? super FROM> rel : rels) {
  		remove(rel);
  	}
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
    if(! contains(element)) {
	  	checkLock();
	  	checkLock(element);
      element.register(this);
      // Skip a redundant contains check.
      registerPrivate(element);
    }
  }
  
  /**
   * Replace the given element with a new element
   */
 /*@
   @ public behavior
   @
   @ pre element != null;
   @ pre newElement != null;
   @
   @ post replaced(\old(getOtherRelations()), oldAssociation, newAssociation);
   @ post oldAssociation.unregistered(\old(other.getOtherAssociations()), this);
   @ post newAssociation.registered(\old(oldAssociation.getOtherAssociations()),this);
   @*/
  public void replace(Association<? extends TO,? super FROM> oldAssociation, Association<? extends TO,? super FROM> newAssociation) {
    int index = _elements.indexOf(oldAssociation);
    if(index != -1) {
	  	checkLock();
	  	checkLock(oldAssociation);
	  	checkLock(newAssociation);
      _elements.set(index, newAssociation);
      newAssociation.register(this);
      oldAssociation.unregister(this);
      fireElementReplaced(oldAssociation.getObject(), newAssociation.getObject());
    }
  }
  
    
  /**
   * Return a set containing the objects at the
   * n side of the 1-n binding.
   */
 /*@
   @ also public behavior
   @
   @ post (\forall Object o;;
   @        \result.contains(o) <==> 
   @        (\exists Relation r; contains(r);
   @          r.getObject().equals(o)));
   @ post \result != null;
   @*/
  public /*@ pure @*/ List<TO> getOtherEnds() {
    final List<TO> result = new ArrayList<TO>();
    new Visitor<Association<? extends TO,? super FROM>>() {
      public void visit(Association<? extends TO,? super FROM> element) {
        result.add(element.getObject());
      }
    }.applyTo(_elements);
    return result;
  }
  
  
  /**
   * Return a set containing the Relations at the
   * other side of this binding.
   */
 /*@
   @ also public behavior
   @
   @ post (\forall Relation s;;
   @       contains(s) <==> \result.contains(s));
   @ post \result != null;
   @*/
  public /*@ pure @*/ List<Association<? extends TO,? super FROM>> getOtherAssociations() {
    return new ArrayList<Association<? extends TO,? super FROM>>(_elements);
  }
  
  @Override
  protected void unregister(Association<? extends TO,? super FROM> association) {
    if(! contains(association)) {
      unregisterPrivate(association);
    }
  }

	private void unregisterPrivate(Association<? extends TO, ? super FROM> association) {
		_elements.remove(association);
		fireElementRemoved(association.getObject());
	}
    
  
  @Override
  protected void register(Association<? extends TO,? super FROM> association) {
    if(! contains(association)) {
      registerPrivate(association);
    }
  }

	private void registerPrivate(Association<? extends TO, ? super FROM> association) {
		_elements.add(association);
		fireElementAdded(association.getObject());
	}
  

 /*@
   @ also public behavior
   @
   @ post \result == (contains(registered)) &&
   @                 (\forall Relation r; r != registered;
   @                   oldConnections.contains(r) == contains(r)
   @                 );
   @ //TODO: order
   @*/
  public /*@ pure @*/ boolean registered(List<Association<? extends TO,? super FROM>> oldConnections, Association<? extends TO,? super FROM> registered) {
    return (oldConnections != null) &&
           (contains(registered)) &&
           new SafePredicate<Association<? extends TO,? super FROM>>() {
             public boolean eval(Association<? extends TO,? super FROM> o) {
               return OrderedMultiAssociation.this.contains(o);
             }
           }.forAll(_elements);
  }

 /*@
   @ also public behavior
   @
   @ post \result == (oldConnections.contains(unregistered)) &&
   @                 (! contains(unregistered)) &&
   @                 (\forall Relation r; r != unregistered;
   @                   oldConnections.contains(r) == contains(r));
   @ // TODO: order
   @*/
  public /*@ pure @*/ boolean unregistered(List<Association<? extends TO,? super FROM>> oldConnections, final Association<? extends TO,? super FROM> unregistered) {
    // FIXME : implementation is not correct
   return (oldConnections != null) &&
          (oldConnections.contains(unregistered)) &&
          (! contains(unregistered)) &&
          new SafePredicate<Association<? extends TO,? super FROM>>() {
            public boolean eval(Association<? extends TO,? super FROM> o) {
              return (o == unregistered) || contains(o);
            }
          }.forAll(oldConnections);
  }

 /*@
   @ also protected behavior
   @
   @ post \result == (relation != null);
   @*/
  protected /*@ pure @*/ boolean isValidElement(Association<? extends TO,? super FROM> relation) {
    return (relation != null);
  }

    /**
     * Return the size of the ReferenceSet
     */
 /*@
     @ public behavior
     @
     @ post \result == getOtherRelations().size();
     @*/
    public /*@ pure @*/ int size() {
        return _elements.size();
  }
    
  /**
   * Check whether or not the given element is connected to
   * this ReferenceSet.
   *
   * @param element
   *        The element of which one wants to know if it is in
   *        this ReferenceSet.
   */
  public /*@ pure @*/ boolean contains(Association<? extends TO,? super FROM> element) {
    return _elements.contains(element);
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
}
/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
