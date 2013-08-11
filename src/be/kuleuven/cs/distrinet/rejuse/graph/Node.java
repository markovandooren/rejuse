package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.java.collections.SafeTransitiveClosure;

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
  public V object() {
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
  public Set<Edge<V>> outgoingEdges() {
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
  public Set<Edge<V>> incomingEdges() {
    return new HashSet<>(_starts);
  }
  
  /**
   * Return the number of edges starting in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result == outgoingEdges().size();
   @*/
  public int nbOutgoingEdges() {
    return _starts.size();
  }
  
  /**
   * Return the number of edges ending in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result == incomingEdges().size();
   @*/
  public int nbIncomingEdges() {
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
  public Set<Edge<V>> edges() {
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
        acc.addAll(((Node)node).directlyConnectedNodes());
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
  public Set<Node<V>> directlyConnectedNodes() {
    Set<Node<V>> result = new HashSet<>();
    for(Edge<V> edge: _starts) {
    	result.add(edge.nodeConnectedTo(this));
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
  public Set<V> directlyConnectedObjects() {
    Set<V> result = new HashSet<>();
    for(Edge<V> edge: _starts) {
    	result.add(edge.nodeConnectedTo(this).object());
    }
    return result;
  }
  
  /**
   * Check whether this node is directly connected to the
   * given node.
   * 
   * @param node The node of which must be determined if it is directly connected to this node.
   */
 /*@
   @ public behavior
   @
   @ pre node != null;
   @
   @ post \result == \exists(Edge<V> e; getStartEdges().contains(e); e.getEndFor(this) == node);
   @*/
  public boolean isDirectlyConnectedTo(Node<V> node) {
  	for(Edge<V> edge: _starts) {
  		if(edge.nodeConnectedTo(this) == node) {
  			return true;
  		}
  	}
  	return false;
  }
  
  /**
   * Return the edges that directly connect this node to the
   * given node. If no such edge exists, null is returned.
   * @param node
   * @return
   */
  public List<Edge<V>> directlyConnectingEdges(Node<V> node) {
  	List<Edge<V>> result = new ArrayList<>();
  	for(Edge<V> edge: _starts) {
  		if(edge.nodeConnectedTo(this) == node) {
  			result.add(edge);
  		}
  	}
  	return result;
  }
  
  public Node<V> bareClone() {
  	return new Node<V>(object());
  }
}
