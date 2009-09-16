package org.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.rejuse.association.MultiAssociation;

public class PropertyMutex<E> {

	public Set<Property<E>> members() {
	  return new HashSet<Property<E>>(_members.getOtherEnds());	
	}
	
	public Set<Property<E>> membersWithout(Property<E> property) {
		Set<Property<E>> result = new HashSet<Property<E>>(_members.getOtherEnds());
		result.remove(property);
		return result;
	}
	
	MultiAssociation<PropertyMutex<E>, Property<E>> memberLink() {
		return _members;
	}
	
	private MultiAssociation<PropertyMutex<E>, Property<E>> _members = new MultiAssociation<PropertyMutex<E>, Property<E>>(this);
}
