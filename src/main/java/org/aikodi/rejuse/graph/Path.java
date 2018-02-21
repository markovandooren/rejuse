package org.aikodi.rejuse.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>A path in a graph. A path consists of a sequence of
 * nodes that are connected by edges.</p>
 * 
 * <p>A path is defined by the start node and the edges of
 * which it consists. There may be multiple edges between two nodes,
 * and because they can have different weights, the edges must be stored
 * instead of the nodes </p> 
 * 
 * @author Marko van Dooren
 */
public class Path<V> implements Comparable<Path<V>>, IWalk<Node<V>, Edge<V>, V> {
  
  /**
   * Initialize a new path with the given start node.
   * 
   * @param start
   *        The start of the path.
   */
 /*@
   @ public behavior
   @
   @ post getStart() == start;
   @ post getNbEdges() == 0;
   @*/
  public Path(Node<V> start) {
    _start= start;
    _end = start;
    _weight = 0;
    _nodes.add(_start);
  }
  
  /**
   * Initialize a new path with the given start node.
   * 
   * @param start
   *        The start of the path.
   */
 /*@
   @ public behavior
   @
   @ pre start != null;
   @ pre edges != null;
   @
   @ TODO further specs
   @
   @ post getStart() == start;
   @ post getEdges().equals(edges);
   @*/
  protected Path(Node<V> start, List<Edge<V>> edges, double length) {
    _start= start;
    _end = start;
    _nodes.add(_start);
    edges.forEach(e -> addEdge(e));
    _weight = length;
  }
  
  /**
   * Check whether the given edge is a part of this path.
   * 
   * @param edge The edge to be checked.
   * @return True if the edge is part of this path. False otherwise.
   */
 /*@
   @ public behavior
   @
   @ pre edge != null;
   @
   @ post \result == getEdges().contains(edge);
   @*/
  public boolean traverses(Edge<V> edge) {
  	return _edgeSet.contains(edge);
  }
  
  public boolean visits(Node<V> node) {
  	return _nodes.contains(node);
  }
  
  /**
   * Return the start node for this path. 
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public Node<V> start() {
    return _start;
  }

  /**
   * {@inheritDoc}
   */
  public List<Node<V>> nodes() {
    final ArrayList<Node<V>> result = new ArrayList<>();
    result.add(_start);
    Node<V> current = _start;
    for(Edge<V> edge: _edges) {
      Node<V> next = edge.endFor(current);
			result.add(next);
			current = next;
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public Node<V> node(int index) {
	  Node<V> result = start();
	  for (int i = 1; i <= _edges.size(); i++) {
		  result = _edges.get(i - 1).endFor(result);
	  }
	  return result;
  }
  
  /**
   * {@inheritDoc}
   */
  public List<Edge<V>> edges() {
    return new ArrayList<>(_edges);
  }
  
  /**
   * Add the given edge to this path.
   * 
   * @param edge
   */
 /*@
   @ public behavior
   @
   @ pre edge != null
   @ pre edge.startsIn(\old(getEnd()));
   @*/
  public void addEdge(Edge<V> edge) {
    _edges.add(edge);
    _edgeSet.add(edge);
    _end = edge.endFor(_end);
    Weight weight = edge.get(Weight.class);
    if(weight != null) {
      _weight += weight.weight();
    }
    _nodes.add(_end);
  }

  /**
   * {@inheritDoc}
   */
  public Node<V> end() {
    return _end;
  }
  
  /**
   * Return the number of nodes in this path.
   */
 /*@
   @ public behavior
   @
   @ post \result == getNbEdges() + 1; 
   @*/
  public int getNbNodes() {
    return _edges.size() + 1;
  }
  
  /**
   * Return the number of edges in this path.
   */
 /*@
   @ public behavior
   @
   @ post \result == getEdges().size(); 
   @*/
  public int getNbEdges() {
    return _edges.size();
  }
  
  private Set<Node<V>> _nodes = new HashSet<>();
  
  /**
   * The edges of which this path consists.
   */
  private List<Edge<V>> _edges = new ArrayList<>();
  private Set<Edge<V>> _edgeSet = new HashSet<>();
  
  /**
   * @return the length of this path.
   */
  public int length() {
    return _edges.size();
  }
  
  public double weight() {
  	return _weight;
  }
  
  /**
   * A cache for the length of the path.
   */
  private double _weight;
  
  /**
   * The start of the path.
   */
  private Node<V> _start;
  
  /**
   * The end of the path.
   */
  private Node<V> _end;

  /**
   * Compare the length of the path with the length of the given path.
   * @{inheritDoc}
   */
  /*@
    @ also public behavior
    @
    @ post ! (o instanceof Path) ==> \result == -1;
    @ post (o instanceof Path) ==> \result == Math.sign(getLength() - ((Path)o).getLength());
    @*/
  public int compareTo(Path<V> o) {
    if(equals(o)) {
      return 0;
    }
    else if((weight() < o.weight())){
        return -1;
    }
    else if((length() < o.length())){
      return -1;
  }
    else {
      return 1;
    }
  }
  
 /*@
   @ also public behavior
   @
   @ TODO: specs   
   @*/
  public Path<V> clone() {
    return new Path<V>(_start, _edges, _weight);
  }
  
  /**
   * {@inheritDoc}
   */
  public String toString() {
    final StringBuilder result = new StringBuilder();
    boolean first = true;
		for (Object o : nodes()) {
			if (!first) {
				result.append(" -> ");
			} else {
				first = false;
			}
			result.append(o.toString());
		}
		result.append(" : " + length());
    return result.toString();
  }

  @Override
  public int indexOf(V object) {
	  Node<V> current = _start;
	  for (int i = 0; i < length(); i++) {
		if (current.object().equals(object)) {
			return i;
		}
		current = _edges.get(i).endFor(current);
	  }
	  return -1;
  }
}
