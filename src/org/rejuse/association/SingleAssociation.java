package org.rejuse.association;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>A class of Relation components for implementing a binding in which the object of
 * the Reference has a relation with only 1 other object.</p>
 *
 * <center><img src="doc-files/Reference.png"/></center>
 *
 * <p>In UML this class is used for implementing multiplicity 1:</p>
 *
 * <center><img src="doc-files/reference_uml.png"/></center>
 *
 * <p>In Java, you get the following situation.</p>
 * <center><img src="doc-files/reference_java.png"/></center>
 *
 * <p>Note that the question mark is represented by a <code>Relation</code> object since
 * we don't know its multiplicity. The double binding between the <code>Reference</code> 
 * object and <code>A</code> is made by passing <code>this</code> to the constructor of 
 * Reference.</p>
 *
 * <p>This is actually a lightweight version of the APElement class.
 * of the <a href="http://www.beedra.org">Beedra</a> framework of Jan Dockx.</p>
 * 
 * <p>This class is typically using in the following way.
 * <pre><code>
 *public class B {
 *  public B() {
 *    _a = new Reference(this);
 *  }
 *
 *  public A getA() {
 *    return (A)_a.getOtherEnd();
 *  }
 *
 *  public void setA(A a) {
 *    _a.connectTo(a == null ? null : a.getBLink());
 *  }
 *
 *  Reference getALink() {
 *    return _a;
 *  }
 *
 *  private Reference _a;
 *}
 * </code></pre>
 *
 * <p>The other class must have a method <code>getBLink()</code>, which returns an 
 * <a href="Association.html"><code>Association</code></a> object that represents the other side
 * of the bi-directional binding. In case the two classes are not in the same package that means
 * that <code>getBLink()</code> must be <code>public</code> there, but that is also the case when
 * not working with these components. Note that if the binding should not be mutable from class 
 * <code>B</code> the <code>setA()</code> may be removed. Similarly, <code>getALink()</code> may 
 * be removed if the binding is not mutable from class <code>A</code>.</p>
 *
 * @author  Marko van Dooren
 * @release $Name$
 */
public class SingleAssociation<FROM,TO> extends Association<FROM,TO> {

 /*@ 
   @ public invariant getOtherRelation() != null ==>
   @                  getOtherRelation().contains(this);
   @*/
  
  /**
   * Initialize a new Reference for the given object.
   * The new Reference will be unconnected.
   *
   * @param object
   *        The object at this side of the binding.
   */
 /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObject() == object;
   @ post getOtherRelation() == null;
   @*/
  public SingleAssociation(FROM object) {
    super(object);
  }

  /**
   * Initialize a new Reference for the given object,
   * connected to the given Relation.
   *
   * @param object
   *        The object at this side of the binding.
   * @param other
   *        The Relation component at the other side of the binding.
   */
 /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObject() == object;
   @ post getOtherRelation() == other;
   @ post other != null ==> other.contains(this);
   @ post other.registered(new ArrayList(), this);
   @*/
  public SingleAssociation(FROM object, Association<? extends TO,? super FROM> other) {
    super(object);
    connectTo(other);
  }
    
  /**
   * Return the Object at the other end of this double binding.
   */
 /*@
   @ public behavior
   @
   @ post getOtherRelation() == null ==> \result == null;
   @ post getOtherRelation() != null ==> \result == getOtherRelation().getObject();
   @*/
  public /*@ pure @*/ TO getOtherEnd() {
    if (getOtherRelation() == null) {
        return null;
    }
    return getOtherRelation().getObject();
  }


 /*@
   @ also public behavior
   @
   @ post \result != null;
   @ post getOtherEnd() != null ==> (
   @                                 \result.size() == 1 &&
   @                                 \result.contains(getOtherEnd())
   @                                );
   @ post getOtherEnd() == null ==> \result.isEmpty();
   @*/
  public /*@ pure @*/ List<TO> getOtherEnds() {
    ArrayList<TO> result = new ArrayList<TO>();
    if (getOtherRelation() == null) {
        return result;
    }
    result.add(getOtherRelation().getObject());
    return result;
  }

  /**
   * Set the other side of this binding.
   *
   * @param other
   *        The new Relation this Reference will be connected to.
   */
 /*@
   @ public behavior
   @
   @ post registered(\old(getOtherRelations()), other);
   @ post other != null ==> other.registered(\old(other.getOtherRelations()), this);
   @*/
  public void connectTo(Association<? extends TO,? super FROM> other) {
  	checkLock();
  	checkLock(getOtherRelation());
  	checkLock(other);
    if (other != _other) {
      register(other);
      if (other != null) {
          other.register(this);
      }
    }
  }

 /*@
   @ also public behavior
   @
   @ post \result == (registered != null ==> contains(registered)) &&
   @                 (
   @                   (
   @                     (oldConnections.size() == 0)
   @                   )
   @                   ||
   @                   (
   @                     (oldConnections.size() == 1) &&
   @                     (
   @                       (oldConnections.get(1) == registered)
   @                       ||
   @                       (
   @                         (! contains((Relation)oldConnections.get(1)))
   @                         //(\forall Relation r; r != this;
   @                         //   \old(((Relation)oldConnections.get(1)).contains(r)) == 
   @                         //   ((Relation)oldConnections.get(1)).contains(r)
   @                         //) FIXME
   @                       )
   @                     )
   @                   )
   @                 );
   @*/
  public /*@ pure @*/ boolean registered(List<Association<? extends TO,? super FROM>> oldConnections, Association<? extends TO,? super FROM> registered) {
    return (oldConnections != null) &&
           (contains(registered)) &&
           (
             (
               (oldConnections.size() == 0)
             )
             ||
             (
               (oldConnections.size() == 1) &&
               (
                 (oldConnections.get(1) == registered)
                 ||
                 (
                   (! contains(oldConnections.get(1)))
                 )
               )
             )
           );
  }

 /*@
   @ also public behavior
   @
   @ post \result == (getOtherRelations().isEmpty()) &&
   @                 (
   @                   (
   @                     (oldConnections.size() == 1) &&
   @                     (oldConnections.get(1) == unregistered)
   @                   )
   @                   ||
   @                   (oldConnections.size() == 0)
   @                 );
   @*/
  public /*@ pure @*/ boolean unregistered(List<Association<? extends TO,? super FROM>> oldConnections, Association<? extends TO,? super FROM> unregistered) {
    return (oldConnections != null) &&
           (getOtherEnds().isEmpty()) &&
           (
             (
               (oldConnections.size() == 1) &&
               (oldConnections.get(1) == unregistered)
             )
             ||
             (oldConnections.size() == 0)
           );
  }

 /*@
   @ also protected behavior
   @
   @ post \result == true;
   @*/
  protected /*@ pure @*/ boolean isValidElement(Association<? extends TO,? super FROM> relation) {
    return true;
  }

  /**
   * See superclass
   */
  @Override
  protected void unregister(Association<? extends TO,? super FROM> other) {
  	if(_other != null) {
  		TO old = _other.getObject();
      _other = null;
      fireElementRemoved(old);
  	}
  }

  @Override
  protected void register(Association<? extends TO,? super FROM> other) {
    if(_other != null) {
  		TO old = _other.getObject();
      _other.unregister(this);
      _other = other;
      TO newObject = (_other != null ? _other.getObject() : null);
			fireElementReplaced(old, newObject);
    }
    // _other == null
    else if(other != null){
      _other = other;
      fireElementAdded(_other.getObject());
    }
  }

  /**
   * return the Relation this Reference belongs to
   */
  public /*@ pure @*/ Association<? extends TO,? super FROM> getOtherRelation() {
    return _other;
  }
  
  /**
   * See superclass.
   */
  public /*@ pure @*/ List<Association<? extends TO,? super FROM>> getOtherAssociations() {
    ArrayList<Association<? extends TO,? super FROM>> result = new ArrayList<Association<? extends TO,? super FROM>>();
    if(_other != null) {
      result.add(_other);
    }
    return result;
  }
  
  /**
   * The Relation this Reference belongs to
   */
  private Association<? extends TO,? super FROM> _other;

	@Override
	public void replace(Association<? extends TO, ? super FROM> element, Association<? extends TO, ? super FROM> newElement) {
		if(contains(element)) {
			connectTo(newElement);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		connectTo(null);
	}
}