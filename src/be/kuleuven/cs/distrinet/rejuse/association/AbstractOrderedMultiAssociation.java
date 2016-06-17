package be.kuleuven.cs.distrinet.rejuse.association;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import be.kuleuven.cs.distrinet.rejuse.action.Action;
import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;

public abstract class AbstractOrderedMultiAssociation<FROM,TO> extends AbstractMultiAssociation<FROM,TO> {

	//@ public invariant contains(null) == false;
  //@ public invariant getObject() != null;

  /*@ 
   @ public invariant (\forall Relation e; contains(e);
   @                    e.contains(this));
   @*/


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
	public AbstractOrderedMultiAssociation(FROM object) {
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
	public AbstractOrderedMultiAssociation(FROM object, int initialCapacity) {
    super(object);
    _initialCapacity = initialCapacity;
  }

	
  private int _initialCapacity;

  protected int initialCapacity() {
  	return _initialCapacity;
  }
  
  @Override
  public /*@ pure @*/ List<TO> getOtherEnds() {
    if(isCaching()) {
      if(_cache == null) {
        if(isStored()) {
          Builder<TO> builder = ImmutableList.<TO>builder();
          internalAssociations().forEach(e ->builder.add(e.getObject()));
          _cache = builder.build();
        } else {
          _cache = Collections.EMPTY_LIST;
        }
      }
      return _cache;
    } else {
      return doGetOtherEnds();
    }
  }

  protected /*@ pure @*/ List<TO> doGetOtherEnds() {
    List<TO> result = new ArrayList<TO>(size());
    addOtherEndsTo(result);
    //	  increase();
    return result;
  }



  /**
   * {@inheritDoc}
   */  
  public void addInFront(Association<? extends TO,? super FROM> element) {
    checkLock();
    checkLock(element);
    if(element != null) {
      boolean added = element.register(this);
      // Skip a redundant contains check.
      if(added) {
        registerInFrontPrivate(element);
      }
    }
  }

  /**
   * @element the element to be added
   * @index the base-1 index
   */  
  public void addAtIndex(Association<? extends TO,? super FROM> element, int baseOneIndex) {
    if(! contains(element)) {
      checkLock();
      checkLock(element);
      if(element != null) {
        element.register(this);
        // Skip a redundant contains check.
        registerAtIndexPrivate(element, baseOneIndex);
      }
    }
  }

  public void addBefore(TO existing, Association<? extends TO,? super FROM> toBeAdded) {
    addAtIndex(toBeAdded, indexOf(existing));
  }

  public void addAfter(TO existing, Association<? extends TO,? super FROM> toBeAdded) {
    addAtIndex(toBeAdded, indexOf(existing)+1);
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
    if(isStored()) {
      int index = indexOfAssociation(oldAssociation);
      if(index != -1) {
        checkLock();
        checkLock(oldAssociation);
        checkLock(newAssociation);
        setAssociationAt(index, newAssociation);
        newAssociation.register(this);
        oldAssociation.unregister(this);
        fireElementReplaced(oldAssociation.getObject(), newAssociation.getObject());
      }
    }
  }

  public void addOtherEndsTo(Collection<? super TO> collection) {
    if(isStored()) {
      internalAssociations().forEach(e -> collection.add(e.getObject()));
    }
  }

  public TO lastElement() {
    int size = size();
    if(size > 0) {
      return associationAt(size-1).getObject();
    } else {
      return null;
    }
  }
  
  /**
   * @param index the index of the requested element.
   */
  public TO elementAt(int index) {
    return associationAt(index).getObject();
  }

  /**
   * Return the index of the given element. Indices start at 1. Return -1 if the element is not present in this association.
   */
  /*@
   @ public behavior
   @
   @ post elementAt(\result).equals(element);
   @*/
  public int indexOf(TO element) {
    int index = -1;
    if(isStored()) {
      int size = size();
			for(int i = 0; i < size; i++) {
        if(elementAt(i).equals(element)) {
          index = i;
          break;
        }
      }
    }
    return index;
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
    return new ArrayList<Association<? extends TO,? super FROM>>(internalAssociations());
  }

  @Override
  protected void unregister(Association<? extends TO,? super FROM> association) {
    //    if(contains(association)) {
    unregisterInternal(association);
    //    }
  }

  protected void unregisterInternal(Association<? extends TO, ? super FROM> association) {
    if(isStored()) {
      boolean removed = removeAssociation(association);
      if(removed) {
        fireElementRemoved(association.getObject());
      }
    }
  }


  @Override
  protected boolean register(Association<? extends TO,? super FROM> association) {
    if(! contains(association)) {
      registerPrivate(association);
      return true;
    }
    return false;
  }

  protected void registerPrivate(Association<? extends TO, ? super FROM> association) {
    elements().add(association);
    fireElementAdded(association.getObject());
  }

  private void registerInFrontPrivate(Association<? extends TO, ? super FROM> association) {
    elements().add(0,association);
    fireElementAdded(association.getObject());
  }

  private void registerAtIndexPrivate(Association<? extends TO, ? super FROM> association,int index) {
    elements().add(index-1,association);
    fireElementAdded(association.getObject());
  }

  private List<Association<? extends TO,? super FROM>> elements() {
    if(! isStored()) {
      initStorage();
    }
    return internalAssociations();
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
    boolean result = (oldConnections != null) &&
        (contains(registered)) &&
        (isStored());
    if(result) {
      for(Association<? extends TO,? super FROM> o: internalAssociations())  {
        if(! contains(o)) {
          result = false;
          break;
        }
      }
    }
    return result;
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
        new AbstractPredicate<Association<? extends TO,? super FROM>,Nothing>() {
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
    return isStored() ? internalAssociations().size() : 0;
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
    return isStored() ? internalAssociations().contains(element) : false;
  }

  @Override
  public <E extends Exception> void apply(Action<? super TO, E> action) throws E {
    if(isStored()) {
      for(Association<? extends TO,? super FROM> e: internalAssociations()) {
        action.perform(e.getObject());
      }
    }
  }

  private List<TO> _cache;

  public void flushCache() {
    _cache = null;
  }

  public abstract Association<? extends TO,? super FROM> associationAt(int index);
  
  protected abstract void setAssociationAt(int index, Association<? extends TO,? super FROM> association);
  
  public abstract int indexOfAssociation(Association<? extends TO,? super FROM> association);

  protected abstract boolean isStored();

  @Override
  protected abstract List<Association<? extends TO, ? super FROM>> internalAssociations();

  protected abstract void initStorage();

  protected abstract boolean removeAssociation(Association<? extends TO,? super FROM> association);

}
