package org.rejuse.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko van Dooren
 */
public abstract class Edge {
	
  
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @
   @ post getFirst() == first;
   @ post getSecond() == second;
   @ post getWeight() == weight;
   @*/
  public Edge(Node first, Node second, double weight) {
    _first = first;
    _second = second;
    _weight = weight;
    first.addEdge(this);
    second.addEdge(this);
  }
  
 /*@
   @ post ! \old(getFirst()).contains(this);
   @ post ! \old(getSecond()).contains(this);
   @ post getFirst() == null;
   @ post getSecond() == null;
   @*/
  public void terminate() {
    _first.removeEdge(this);
    _second.removeEdge(this);
    _first = null;
    _second = null;
  }
  
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
	public Set getNodes() {
		Set result = new HashSet();
		result.add(_first);
		result.add(_second);
		return result;
	}
  
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
  public abstract boolean startsIn(Node node);

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
  public abstract boolean endsIn(Node node);
  
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
  public abstract Set getStartNodes();
  
  /**
   * Return the node that is reached when traversing this edge starting from
   * the given node.
   * 
   * @param start
   *        The start node.
   */
 /*@
   @ public behavior
   @
   @ pre startsIn(start);
   @*/
  public abstract Node getEndFor(Node start);
	
  /**
   * Return the node that is used as a start node when the given node is the
   * end of travering this edge.
   * 
   * @param end
   *        The end node.
   */
 /*@
   @ public behavior
   @
   @ pre endsIn(end);
   @*/
  public abstract Node getStartFor(Node end);
  
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
  public abstract Set getEndNodes();
  
  /**
   * Return the weight of this edge.
   */
  public double getWeight() {
    return _weight;
  }
	
  private double _weight;
  
	/**
   * Return the first node of this edge.
	 */
	public Node getFirst() {
		return _first;
	}
	
  /**
   * Return the second node of this edge.
   */
  public Node getSecond() {
    return _second;
  }
  
	private Node _first;
	private Node _second;
}
