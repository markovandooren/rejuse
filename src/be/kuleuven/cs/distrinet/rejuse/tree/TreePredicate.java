//package be.kuleuven.cs.distrinet.rejuse.tree;
//
//import be.kuleuven.cs.distrinet.rejuse.predicate.UniversalPredicate;
//
//public abstract class TreePredicate<D, E extends Exception> extends UniversalPredicate<D, E> {
//
//	public TreePredicate(Class<D> type) {
//		super(type);
//	}
//
//	/**
//	 * Check whether or not this predicate can match a descendant of the
//	 * given node. If false, traversal of the subtree can be avoided.
//	 * 
//	 * @param node
//	 * @return
//	 */
//	public abstract boolean canSucceedBeyond(D node);
//	
//}
