package be.kuleuven.cs.distrinet.rejuse.association;


public abstract class AbstractMultiAssociation<FROM,TO> extends Association<FROM,TO> {

	public AbstractMultiAssociation(FROM object) {
		super(object);
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
  public abstract void remove(Association<? extends TO,? super FROM> other);

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
  public abstract void add(Association<? extends TO,? super FROM> element);

}
