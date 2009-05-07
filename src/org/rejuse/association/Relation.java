package org.rejuse.association;

import java.util.List;

/**
 * <p>A class of objects that can be used to set up bi-directional relations between objects.</p>
 *
 * <center><img src="doc-files/Relation.png"/></center>
 *
 * <p>This class provides the general interface that is needed in order to create different types 
 * of bindings that can be created by taking two arbitrary multiplicities.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class Relation<FROM,TO> {
  
  /**
   * Initialize a new Relation for the given object.
   * The new Relation will be unconnected.
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
   @*/
  public Relation(FROM object) {
    _object = object;
  }

  /**
   * Check whether or not the given Relation is connected
   * to this one.
   *
   * @param relation
   *        The relation which is possibly on the other end
   *        of the binding.
   */
 /*@
   @ public behavior
   @
   @ post \result == getOtherRelations().contains(relation);
   @*/
  public /*@ pure @*/ boolean contains(Relation<? extends TO,? super FROM> relation) {
    return getOtherRelations().contains(relation);
  }

 /*@
   @ also public behavior
   @
   @ post \result == (other == this);
   @*/
  public /*@ pure @*/ boolean equals(Object other) {
    return other == this;
  }

  /**
   * Add the given Relation as a participant in this
   * binding.
   */
 /*@
   @ protected behavior
   @
   @ pre isValidElement(other);
   @
   @ post registered(\old(getOtherRelations()), other);
   @*/
  protected abstract void register(Relation<? extends TO,? super FROM> other);

  /**
   * Remove the given Relation as a participant in this
   * binding.
   */
 /*@
   @ protected behavior
   @
   @ pre contains(other);
   @
   @ post unregistered(\old(getOtherRelations()), other);
   @*/
  protected abstract void unregister(Relation<? extends TO,? super FROM> other);

  /**
   * Check whether or not the given Relation may be connected to
   * this Relation.
   */
 /*@
   @ protected behavior
   @
   @ post \result == true | \result == false;
   @*/
  protected /*@ pure @*/ abstract boolean isValidElement(Relation<? extends TO,? super FROM> relation);

  /**
   * Check whether or not the current state corresponds to connecting
   * to the given Relation when being connected to the Relations
   * in the given list.
   *
   * @param oldConnections
   *        The List of Relations this Relation was connected to before.
   * @param registered
   *        The Relation this Relation has connected to.
   */
 /*@
   @ public behavior
   @
   @ pre oldConnections != null;
   @ pre ! oldConnections.contains(null);
   @ pre (\forall Object o; oldConnections.contains(o); o instanceof Relation);
   @
   @ post ! contains(registered) ==> \result == false;
   @ post ! (\forall Relation r; r != registered;
   @          oldConnections.contains(r) == contains(r)) ==> \result == false;
   @*/
  public /*@ pure @*/ abstract boolean registered(List<Relation<? extends TO,? super FROM>> oldConnections, Relation<? extends TO,? super FROM> registered);

  /**
   * Check whether or not the current state corresponds to disconnecting
   * from the given Relation when being connected to the Relations
   * in the given list.
   *
   * @param oldConnections
   *        The List of Relations this Relation was connected to before.
   * @param registered
   *        The Relation this Relation has connected to.
   */
 /*@
   @ public behavior
   @
   @ pre oldConnections != null;
   @ pre ! oldConnections.contains(null);
   @ pre (\forall Object o; oldConnections.contains(o); o instanceof Relation);
   @
   @ post contains(unregistered) ==> \result == false;
   @ post ! oldConnections.contains(unregistered) ==> \result == false;
   @ post ! (\forall Relation r; r != unregistered;
   @          oldConnections.contains(r) == contains(r)) ==> \result == false;
   @*/
  public /*@ pure @*/ abstract boolean unregistered(List<Relation<? extends TO,? super FROM>> oldConnections, Relation<? extends TO,? super FROM> unregistered);

  /**
   * Return the objects on the other side of the binding.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.size() == getOtherRelations().size();
   @ post (\forall Object o; \result.contains(o);
   @        (\exists Relation r; getOtherRelations().contains(r);
   @           r.getObject() == o));
   @*/
  public /*@ pure @*/ abstract List<TO> getOtherEnds();

  /**
   * Return the Relations on the other side of the binding.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post (\forall Object o; \result.contains(o); o instanceof Relation);
   @ post ! \result.contains(null);
   @*/
  public /*@ pure @*/ abstract List<Relation<? extends TO,? super FROM>> getOtherRelations();

  /**
   * Return the object on the n side of the 1-n binding represented by
   * this OneSide
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ FROM getObject() {
    return _object; 
  }
  
  /**
   * The object on this side of the binding.
   */
  private FROM _object;

	public abstract void replace(Relation<? extends TO,? super FROM> element, Relation<? extends TO,? super FROM> newElement);
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
