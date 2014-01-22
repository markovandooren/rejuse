package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

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
    _outgoing = new HashSet<>();
    _incoming = new HashSet<>();
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
      _outgoing.add(edge);
    }
    if(edge.endsIn(this)) {
      _incoming.add(edge);
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
    _outgoing.remove(edge);
    _incoming.remove(edge);
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
    return new HashSet<>(_outgoing);
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
    return new HashSet<>(_outgoing);
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
    return _outgoing.size();
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
    return _incoming.size();
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
  	ImmutableSet.Builder<Edge<V>> builder = ImmutableSet.builder();
    builder.addAll(_outgoing);
    builder.addAll(_incoming);
    return builder.build();
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
    return _outgoing.isEmpty();
  }
  
  /**
   * The edges starting in this node.
   */
  private Set<Edge<V>> _outgoing;
  
  /**
   * The edges ending in this node.
   */
  private Set<Edge<V>> _incoming;
  
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
        acc.addAll(((Node)node).directSuccessorNodes());
      }
    }.closureFromAll(directSuccessorNodes()).contains(other);
  }
  

 /*@
   @ also public behavior
   @
   @ post \result.equals(getObject().toString());
   @*/
  public String toString() {
    return _object.toString();
  }
  
  public void terminate() {
  	Set<Edge<V>> incoming = ImmutableSet.copyOf(_incoming);
  	for(Edge<V> edge: incoming) {
  		edge.terminate();
  	}
  	Set<Edge<V>> outgoing = ImmutableSet.copyOf(_outgoing);
  	for(Edge<V> edge: outgoing) {
  		edge.terminate();
  	}
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
  public Set<Node<V>> directSuccessorNodes() {
    Set<Node<V>> result = new HashSet<>();
    for(Edge<V> edge: _outgoing) {
    	result.add(edge.endFor(this));
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
  public Set<Node<V>> directPredecessorNodes() {
    Set<Node<V>> result = new HashSet<>();
    for(Edge<V> edge: _incoming) {
    	result.add(edge.startFor(this));
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
  public Set<V> directSuccessors() {
    Set<V> result = new HashSet<>();
    for(Edge<V> edge: _outgoing) {
    	result.add(edge.endFor(this).object());
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
  public Set<V> directPredecessors() {
    Set<V> result = new HashSet<>();
    for(Edge<V> edge: _incoming) {
    	result.add(edge.startFor(this).object());
    }
    return result;
  }
  
  /**
   * Check whether this node has the given node as a
   * direct successor.
   * 
   * @param node The node of which must be determined if it 
   *             is a direct successor of this node.
   */
 /*@
   @ public behavior
   @
   @ pre node != null;
   @
   @ post \result == \exists(Edge<V> e; incomingEdges().contains(e); e.getEndFor(this) == node);
   @*/
  public boolean hasDirectSuccessor(Node<V> node) {
  	for(Edge<V> edge: _outgoing) {
  		if(edge.endFor(this) == node) {
  			return true;
  		}
  	}
  	return false;
  }
  
  /**
   * Check whether this node has the given node as a
   * direct predecessor.
   * 
   * @param node The node of which must be determined if it 
   *             is a direct predecessor of this node.
   */
 /*@
   @ public behavior
   @
   @ pre node != null;
   @
   @ post \result == \exists(Edge<V> e; outgoingEdges().contains(e); e.getEndFor(this) == node);
   @*/
  public boolean hasDirectPredecessor(Node<V> node) {
  	for(Edge<V> edge: _outgoing) {
  		if(edge.startFor(this) == node) {
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
  public List<Edge<V>> directlySuccessorEdges(Node<V> node) {
  	List<Edge<V>> result = new ArrayList<>();
  	for(Edge<V> edge: _outgoing) {
  		if(edge.endFor(this) == node) {
  			result.add(edge);
  		}
  	}
  	return result;
  }
  
  public Node<V> bareClone() {
  	return new Node<V>(object());
  }
}
