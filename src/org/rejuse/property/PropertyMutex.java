package org.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.rejuse.association.MultiAssociation;

public class PropertyMutex<E> {

	public Set<Prop<E>> members() {
	  return new HashSet<Prop<E>>(_members.getOtherEnds());	
	}
	
	public Set<Prop<E>> membersWithout(Prop<E> property) {
		Set<Prop<E>> result = new HashSet<Prop<E>>(_members.getOtherEnds());
		result.remove(property);
		return result;
	}
	
	MultiAssociation<PropertyMutex<E>, Prop<E>> memberLink() {
		return _members;
	}
	
	private MultiAssociation<PropertyMutex<E>, Prop<E>> _members = new MultiAssociation<PropertyMutex<E>, Prop<E>>(this);
}
