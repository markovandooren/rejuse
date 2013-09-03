package be.kuleuven.cs.distrinet.rejuse.tree;

import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.UniversalPredicate;


public abstract class TreePredicate<D, E extends Exception> extends UniversalPredicate<D,E>{

	public TreePredicate(Class<D> type) {
		super(type);
	}

	/**
	 * Check whether or not this predicate can match a descendant of the
	 * given node. If false, traversal of the subtree can be avoided.
	 * 
	 * @param node
	 * @return
	 */
	public abstract boolean canSucceedBeyond(D node) throws E;

	public TreePredicate<D, E> or(final TreePredicate<D, ? extends E> other) {
		return new TreePredicate<D, E>(type()) {

			@Override
			public boolean canSucceedBeyond(D node) throws E {
				return TreePredicate.this.canSucceedBeyond(node) || other.canSucceedBeyond(node);
			}
			
			@Override
			public boolean uncheckedEval(D t) throws E {
				return TreePredicate.this.eval(t) || other.eval(t);
			}
			
			@Override
			public String toString() {
				return "("+TreePredicate.this.toString() + "|" + other.toString()+")";
			}
		};
	}

	public TreePredicate<D, E> and(final TreePredicate<D, ? extends E> other) {
		return new TreePredicate<D, E>(type()) {

			@Override
			public boolean canSucceedBeyond(D node) throws E {
				return TreePredicate.this.canSucceedBeyond(node) && other.canSucceedBeyond(node);
			}
			
			@Override
			public boolean uncheckedEval(D t) throws E {
				return TreePredicate.this.eval(t) && other.eval(t);
			}
			
			@Override
			public String toString() {
				return "("+TreePredicate.this.toString() + "&" + other.toString()+")";
			}

		};
	}
	
	public static TreePredicate<Object,Nothing> False() {
		return new TreePredicate<Object, Nothing>(Object.class) {
			@Override 
			public boolean canSucceedBeyond(Object node) {
				return false;
			}
			@Override
			public boolean uncheckedEval(Object t) {
				return false;
			}
			
			@Override
			public TreePredicate<Object, Nothing> or(
					TreePredicate<Object, ? extends Nothing> other) {
				return (TreePredicate<Object, Nothing>) other;
			}
			
			@Override
			public Predicate<Object, Nothing> and(
					Predicate<? super Object, ? extends Nothing> other) {
				return this;
			}
		};
		

	}
}
