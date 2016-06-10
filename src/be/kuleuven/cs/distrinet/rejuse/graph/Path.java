package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;

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
public class Path<V> implements Comparable<Path<V>> {
  
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
    _edges = new ArrayList<>();
    _weight = 0;
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
    _edges = new ArrayList<>(edges);
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
  	return _edges.contains(edge);
  }
  
  public boolean visits(Node<V> node) {
  	boolean result = false;
  	Node<V> current = _start;
  	for(int i=0; ! result && i < _edges.size(); i++) {
  		result = current.equals(node);
  		current = _edges.get(i).endFor(current);
  	}
  	return result;
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
   * Return the list of nodes that define this path.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post ! \result.contains(null);
   @ post (\forall Object o; \result.contains(o);
   @        o instanceof Node);
   @ post \result.size() == getNbEdges() + 1;
   @ post \result.get(0) == getStart();
   @ post (\forall int i; i > 0 && i <= \result.size();
   @         \result.get(i) == getEdges().get(i-1).getEndFor(\result.get(i-1)))
   @*/
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
   * Return the list of edges that define this path.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post ! \result.contains(null);
   @ post (\forall Object o; \result.contains(o);
   @        o instanceof Edge);
   @ TODO consistency
   @*/
  public List<Edge<V>> getEdges() {
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
    _end = edge.endFor(_end);
    Weight weight = edge.get(Weight.class);
    if(weight != null) {
      _weight += weight.weight();
    }
  }
  
  /**
   * Return the last node of this path.
   */
 /*@
   @ public behavior
   @
   @*/
  public Node<V> getEnd() {
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
  
  /**
   * The edges of which this path consists.
   */
  private List<Edge<V>> _edges;
  
  /**
   * @return the length of this path.
   */
  public double length() {
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
   * See superclass
   */
  public String toString() {
    final StringBuffer result = new StringBuffer();
    new Visitor() {
      private boolean first = true;
      public void visit(Object o) {
        if(! first) {
          result.append(" -> ");
        }
        else {
          first = false;
        }
        result.append(o.toString());
      }
    }.applyTo(nodes());
    result.append(" : "+length());
    return result.toString();
  }
}
