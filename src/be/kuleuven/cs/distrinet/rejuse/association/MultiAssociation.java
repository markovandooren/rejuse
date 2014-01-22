package be.kuleuven.cs.distrinet.rejuse.association;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.action.Action;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

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
public class MultiAssociation<FROM,TO> extends AbstractMultiAssociation<FROM,TO> {
  
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
  public MultiAssociation(FROM object) {
    super(object);
    _elements = new HashSet<Association<? extends TO,? super FROM>>();
  }
  
  /**
   * {@inheritDoc}
   */  
  public void remove(Association<? extends TO,? super FROM> other) {
  	checkLock();
  	checkLock(other);
    if (contains(other)) {
      other.unregister(this);
      unregister(other);
    }
  }

  /**
   * {@inheritDoc}
   */  
  public void add(Association<? extends TO,? super FROM> element) {
  	checkLock();
  	checkLock(element);
  	if(element != null) {
      element.register(this);
      register(element);
  	}
  }
  
  public void addOtherEndsTo(Collection<? super TO> collection) {
	  for(Association<? extends TO,? super FROM> element: _elements) {
          collection.add(element.getObject());
	  }
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
  protected Collection<Association<? extends TO, ? super FROM>> internalAssociations() {
  	return _elements;
  }
  
	@Override
	public /*@ pure @*/ Set<TO> getOtherEnds() {
		if(isCaching()) {
			if(_cache == null) {
				_cache = Collections.unmodifiableSet(doGetOtherEnds());
			}
			return _cache;
		} else {
			return doGetOtherEnds();
		}
	}

	public /*@ pure @*/ Set<TO> doGetOtherEnds() {
		Set<TO> result = new HashSet<TO>();
	  addOtherEndsTo(result);
//	  increase();
	  return result;
  }

  /**
   * Remove the given Relation from this ReferenceSet
   *
   * @param element
   *        The element to be removed.
   */
 /*@
   @ also protected behavior
   @
   @ pre element != null;
   @
   @ post ! contains(element);
   @*/
  @Override
  protected void unregister(Association<? extends TO,? super FROM> element) {
    boolean removed = _elements.remove(element);
    if(removed) {
    	fireElementRemoved(element.getObject());
    }
  }
    
  
  /**
   * Add the given Relation to this ReferenceSet
   *
   * @param element
   *        The element to be added.
   */
 /*@
   @ also protected behavior
   @
   @ pre element != null;
   @*/
  @Override
  protected boolean register(Association<? extends TO,? super FROM> element) {
    boolean added = _elements.add(element);
    if(added) {
    	fireElementAdded(element.getObject());
    	return true;
    }
    return false;
  }
  

 /*@
   @ also public behavior
   @
   @ post \result == (contains(registered)) &&
   @                 (\forall Relation r; r != registered;
   @                   oldConnections.contains(r) == contains(r)
   @                 );
   @*/
  public /*@ pure @*/ boolean registered(List<Association<? extends TO,? super FROM>> oldConnections, Association<? extends TO,? super FROM> registered) {
    return (oldConnections != null) &&
           (contains(registered)) &&
           new SafePredicate<Association<? extends TO,? super FROM>>() {
             public boolean eval(Association<? extends TO,? super FROM> o) {
               return MultiAssociation.this.contains(o);
             }
           }.forAll(_elements);
  }

 /*@
   @ also public behavior
   @
   @ post \result == (oldConnections.contains(unregistered)) &&
   @                 (\forall Object o; oldConnections.contains(o); o instanceof Relation) &&
   @                 (! contains(unregistered)) &&
   @                 (\forall Relation r; r != unregistered;
   @                   oldConnections.contains(r) == contains(r));
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
  private HashSet<Association<? extends TO,? super FROM>> _elements;

	@Override
	public void replace(Association<? extends TO, ? super FROM> element, Association<? extends TO, ? super FROM> newElement) {
		if(contains(element)) {
	  	checkLock();
	  	checkLock(element);
	  	checkLock(newElement);
	  	disableEvents();
		  remove(element);
		  add(newElement);
		  enableEvents();
		  fireElementReplaced(element.getObject(), newElement.getObject());
		}
	}

  /**
   * {@inheritDoc} 
   */
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
  
  @Override
	public <E extends Exception> void apply(Action<? super TO, E> action) throws E {
  	for(Association<? extends TO,? super FROM> element: _elements) {
  		action.perform(element.getObject());
  	}
  }

  private Set<TO> _cache;
  
	public void flushCache() {
		_cache = null;
	}
	

}