package org.aikodi.rejuse.graph;

import java.util.Set;

public abstract class Edge<V> {

	/*@
	 @ post ! \old(getFirst()).contains(this);
	 @ post ! \old(getSecond()).contains(this);
	 @ post getFirst() == null;
	 @ post getSecond() == null;
	 @*/
	public abstract void terminate();

	/**
	 * Return the nodes connected to this edge.
	 */
	/*@
	  @ public behavior
	  @ 
	  @ post \result != null
	  @ post \result.contains(getFirst());
	  @ post \result.contains(getSecond());
	  @ post getFirst() == getSecond() ==> \result.size() == 1
	  @ post getFirst() != getSecond() ==> \result.size() == 2
	  @*/
	public abstract Set<Node<V>> getNodes();

	/**
	 * Check whether or not this edge starts in the
	 * given node.
	 * 
	 * @param node
	 *        The node to be checked
	 */
	/*@
	  @ public behavior
	  @
	  @ //false when the given node is null
	  @ post node == null ==> \result == false;
	  @*/
	public abstract boolean startsIn(Node<V> node);

	/**
	 * Check whether or not this edge ends in the
	 * given node.
	 * 
	 * @param node
	 *        The node to be checked
	 */
	/*@
	  @ public behavior
	  @
	  @ //false when the given node is null
	  @ post node == null ==> \result == false;
	  @*/
	public abstract boolean endsIn(Node<V> node);

	/**
	 * Return the nodes that can be used as a start for
	 * traversing this edge.
	 */
	/*@
	  @ public behavior
	  @
	  @ post \result != null;
	  @ // The result is a subset of the set of all connected nodes
	  @ post getNodes().containsAll(\result);
	  @ post (\forall Node n; getNodes().contains(n);
	  @        startsIn(n));
	  @*/
	public abstract Set<Node<V>> startNodes();

	/**
	 * Return the node from which the given node is reached after
	 * traversing this edge.
	 * 
	 * @param end
	 *        The end node.
	 */
	/*@
	  @ public behavior
	  @
	  @ pre endsIn(end);
	  @*/
	public abstract Node<V> startFor(Node<V> end);

	/**
	 * Return the node that is reach when this edge is traversed starting
	 * from the given node.
	 * 
	 * @param end
	 *        The start node.
	 */
	/*@
	  @ public behavior
	  @
	  @ pre endsIn(end);
	  @*/
	public abstract Node<V> endFor(Node<V> end);
	
	public Node<V> opposite(Node<V> node) {
		if(node == getFirst()) {
			return getSecond();
		} else {
			return getFirst();
		}
	}
	
	/**
	 * Return the nodes that can be used as an end point when
	 * traversing this edge.
	 */
	/*@
	  @ public behavior
	  @
	  @ post \result != null;
	  @ // The result is a subset of the set of all connected nodes
	  @ post getNodes().containsAll(\result);
	  @ post (\forall Node n; getNodes().contains(n);
	  @        endsIn(n));
	  @*/
	public abstract Set<Node<V>> endNodes();

	/**
	 * Return the first node of this edge.
	 */
	public abstract Node<V> getFirst();

	/**
	 * Return the second node of this edge.
	 */
	public abstract Node<V> getSecond();
	
	protected abstract Edge<V> cloneTo(Node<V> newSource, Node<V> newTarget);
	
	public abstract <T> T get(Class<T> key);
	
	public abstract <T> void put(Class<T> key, T value);

}