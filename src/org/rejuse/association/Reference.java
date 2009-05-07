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
 * <p>The other class must have a method <code>getBLink()</code>, which returns a 
 * <a href="Relation.html"><code>Relation</code></a> object that represents the other side
 * of the bi-directional binding. In case the two classes are not in the same package that means
 * that <code>getBLink()</code> must be <code>public</code> there, but that is also the case when
 * not working with these components. Note that if the binding should not be mutable from class 
 * <code>B</code> the <code>setA()</code> may be removed. Similarly, <code>getALink()</code> may 
 * be removed if the binding is not mutable from class <code>A</code>.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Reference<FROM,TO> extends Relation<FROM,TO> {

 	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

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
  public Reference(FROM object) {
    super(object);
  }

  private boolean _locked = false;
  
  public void lock() {
  	_locked=true;
  }
  
  public boolean isLocked() {
  	return _locked;
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
  public Reference(FROM object, Relation<? extends TO,? super FROM> other) {
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
  public void connectTo(Relation<? extends TO,? super FROM> other) {
  	if(isLocked()) {
  		throw new IllegalArgumentException("Trying to modify locked reference.");
  	}
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
  public /*@ pure @*/ boolean registered(List<Relation<? extends TO,? super FROM>> oldConnections, Relation<? extends TO,? super FROM> registered) {
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
  public /*@ pure @*/ boolean unregistered(List<Relation<? extends TO,? super FROM>> oldConnections, Relation<? extends TO,? super FROM> unregistered) {
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
  protected /*@ pure @*/ boolean isValidElement(Relation<? extends TO,? super FROM> relation) {
    return true;
  }

  /**
   * See superclass
   */
  protected void unregister(Relation<? extends TO,? super FROM> other) {
  	if(isLocked()) {
  		throw new IllegalArgumentException("Trying to modify locked reference. This object: "+getObject().getClass().getName()+" Other object: "+_other.getObject().getClass().getName());
  	}
    _other = null;
  }

  /**
   * See superclass
   */
  protected void register(Relation<? extends TO,? super FROM> other) {
  	if(isLocked()) {
  		throw new IllegalArgumentException("Trying to modify locked reference.");
  	}
    if(_other != null) {
      _other.unregister(this);
    }
    _other = other;
  }

  /**
   * return the Relation this Reference belongs to
   */
  public /*@ pure @*/ Relation<? extends TO,? super FROM> getOtherRelation() {
    return _other;
  }
  
  /**
   * See superclass.
   */
  public /*@ pure @*/ List<Relation<? extends TO,? super FROM>> getOtherRelations() {
    ArrayList<Relation<? extends TO,? super FROM>> result = new ArrayList<Relation<? extends TO,? super FROM>>();
    if(_other != null) {
      result.add(_other);
    }
    return result;
  }
  
  /**
   * The Relation this Reference belongs to
   */
  private Relation<? extends TO,? super FROM> _other;

	@Override
	public void replace(Relation<? extends TO, ? super FROM> element, Relation<? extends TO, ? super FROM> newElement) {
		if(contains(element)) {
			connectTo(newElement);
		}
	}
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
