package be.kuleuven.cs.distrinet.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.association.MultiAssociation;

public class PropertyMutex<F extends Property<?,F>> {

	public Set<F> members() {
	  return new HashSet<F>(_members.getOtherEnds());	
	}
	
	public Set<F> membersWithout(F property) {
		Set<F> result = new HashSet<F>(_members.getOtherEnds());
		result.remove(property);
		return result;
	}
	
	MultiAssociation<PropertyMutex<F>, F> memberLink() {
		return _members;
	}
	
	private MultiAssociation<PropertyMutex<F>, F> _members = new MultiAssociation<PropertyMutex<F>, F>(this);
}
