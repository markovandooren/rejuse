package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Mapping;
import be.kuleuven.cs.distrinet.rejuse.java.collections.SafeTransitiveClosure;
import be.kuleuven.cs.distrinet.rejuse.java.collections.SkipList;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;
import be.kuleuven.cs.distrinet.rejuse.java.comparator.ComparableComparator;

/**
 * @author Marko van Dooren
 */
public class Node<V> {
  
  /**
   * Initialize a new node for the given object.
   * 
   * @param object
   *        The object that is put in the graph.
   */
 /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObject() == object;
   @ post getEdges().isEmpty();
   @*/
  public Node(V object) {
    _object = object;
    _starts = new HashSet<>();
    _ends = new HashSet<>();
  }
  
  /**
   * Return the object of this node.
   */
 /*@
   @ public behavior
   @
   @ post \result != null; 
   @*/
  public V getObject() {
    return _object;
  }
  
  /**
   * The object of this edge.
   */
  private V _object;
  
  /**
   * Add the given edge to this node.
   * 
   * @param edge
   *        The edge to be added.
   */
 /*@
   @ public behavior
   @
   @ pre edge != null;
   @ // The edge must already haven been connected to this node.
   @ pre edge.startsIn(this) || edge.endsIn(this)
   @
   @ post edge.startsIn(this) ==> getStartEdges().contains(edge)
   @ post edge.endsIn(this) ==> getEndEdges().contains(edge)
   @*/
  void addEdge(Edge<V> edge) {
    if(edge.startsIn(this)) {
      _starts.add(edge);
    }
    if(edge.endsIn(this)) {
      _ends.add(edge);
    }
  }
  
  /**
   * Remove the given edge from this node.
   * 
   * @param edge
   *        The edge to be removed.
   */
 /*@
   @ public behavior
   @
   @ post ! getEdges().contains(edge); 
   @*/
  void removeEdge(Edge<V> edge) {
    _starts.remove(edge);
    _ends.remove(edge);
  }
  
  /**
   * Return all edges starting in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result != null
   @ post ! \result.contains(null);
   @ post (\forall Edge e; e != null; \result.contains(e) == e.startsIn(this)); 
   @*/
  public Set<Edge<V>> getStartEdges() {
    return new HashSet<>(_starts);
  }
  
  /**
   * Return all edges ending in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result != null
   @ post ! \result.contains(null);
   @ post (\forall Edge e; e != null; \result.contains(e) == e.endsIn(this)); 
   @*/
  public Set<Edge<V>> getEndEdges() {
    return new HashSet<>(_starts);
  }
  
  /**
   * Return the number of edges starting in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getStartEdges().size();
   @*/
  public int getNbStartEdges() {
    return _starts.size();
  }
  
  /**
   * Return the number of edges ending in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getEndEdges().size();
   @*/
  public int getNbEndEdges() {
    return _ends.size();
  }

  /**
   * Return all edges connected to this node
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.containsAll(getStartEdges());
   @ post \result.containsAll(getEndEdges());
   @ post (\forall Object o; result.contains(o); 
   @        getStartEdges().contains(o) || getEndEdges().contains(o));
   @*/  
  public Set<Edge<V>> getEdges() {
    Set<Edge<V>> result = new HashSet<>(_starts);
    result.addAll(_ends);
    return result;
  }
  
  /**
   * Check whether or not this node is a leaf node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getStartEdges().isEmpty();
   @*/
  public boolean isLeaf() {
    return _starts.isEmpty();
  }
  
  /**
   * The edges starting in this node.
   */
  private Set<Edge<V>> _starts;
  
  /**
   * The edges ending in this node.
   */
  private Set<Edge<V>> _ends;
  
  /**
   * Check whether or not the given node is reachable when starting from this
   * node.
   *  
   * @param other
   *        The node to be reached.
   */
 /*@
   @ public behavior
   @
   @ pre node != null;
   @*/ 
  public boolean canReach(Node<V> other) {
    //TODO inefficient, but it works
    return new SafeTransitiveClosure() {
      public void addConnectedNodes(Object node, Set acc) {
        acc.addAll(((Node)node).getDirectlyConnectedNodes());
      }
    }.closure(this).contains(other);
  }
  

 /*@
   @ also public behavior
   @
   @ post \result.equals(getObject().toString());
   @*/
  public String toString() {
    return _object.toString();
  }
  
  /**
   * 
   * @return
   */
 /*@
   @ public behavior
   @
   @ TODO: specs
   @*/
  public Set<Node<V>> getDirectlyConnectedNodes() {
    Set<Node<V>> result = new HashSet<>();
    for(Edge<V> edge: _starts) {
    	result.add(edge.getEndFor(this));
    }
    return result;
  }
  
  /**
   * 
   * @return
   */
 /*@
   @ public behavior
   @
   @ TODO: specs
   @*/
  public Set<V> getDirectlyConnectedObjects() {
    Set<V> result = new HashSet<>();
    for(Edge<V> edge: _starts) {
    	result.add(edge.getEndFor(this).getObject());
    }
    return result;
  }
}
