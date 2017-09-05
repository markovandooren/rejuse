package org.aikodi.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.aikodi.rejuse.association.MultiAssociation;

public class PropertyMutex<F extends Property<?,F>> {

	public Set<F> members() {
	  return new HashSet<F>(_members.getOtherEnds());	
	}
	
	public Set<F> membersWithout(F property) {
		Set<F> result = _members.getOtherEnds();
		result.remove(property);
		return result;
	}
	
	MultiAssociation<PropertyMutex<F>, F> memberLink() {
		return _members;
	}
	
	protected void membersModified() {
		// Must notify the members in the method. No point in caching here
		// because only membersWithout is invoked. The sibling can be cached in the
		// properties. This method notifies all members that a new sibling has arrived.
	}
	
	private MultiAssociation<PropertyMutex<F>, F> _members = new MultiAssociation<PropertyMutex<F>, F>(this) {
		@Override
		protected void fireElementAdded(F addedElement) {
			super.fireElementAdded(addedElement);
			membersModified();
		}
		protected void fireElementRemoved(F addedElement) {
			super.fireElementRemoved(addedElement);
			membersModified();
		};
		protected void fireElementReplaced(F oldElement, F newElement) {
			super.fireElementReplaced(oldElement, newElement);
			membersModified();
		};
	};
}
