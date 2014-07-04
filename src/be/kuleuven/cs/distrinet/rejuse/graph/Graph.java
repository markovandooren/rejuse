package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.action.Action;
import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;

/**
 * @author Marko van Dooren
 * 
 * TODO WARNING : An undirected graph may not contain selfloops, i.e., it may not 
 *                contain an edge whose source is equal to its target.
 */
public class Graph<V> {
  
  /**
   * Initialize an empty graph.
   */
 /*@
   @ public behavior
   @
   @ post edgeFactory() instanceof BidiEdgeFactory;
   @ post nodeFactory() instanceof DefaultNodeFactory;
   @ post getNbNodes() == 0;
   @*/
  public Graph() {
    this(new DefaultNodeFactory<V>(),new BidiEdgeFactory<V>());
  }
  
  /**
   * Initialize an empty graph.
   */
 /*@
   @ public behavior
   @
   @ post getEdgeFactory() instanceof BidiEdgeFactory;
   @ post getNbNodes() == 0;
   @*/
  public Graph(EdgeFactory<V> edgeFactory) {
    this(new DefaultNodeFactory<V>(),edgeFactory);
  }
   
  /**
   * Initialize an empty graph that will use the given edge factory to create
   * edges.
   * 
   * @param edgeFactory
   *        The edge factory to be used
   */
 /*@
   @ public behavior
   @
   @ pre edgeFactory != null;
   @
   @ post getEdgeFactory() instanceof BidiEdgeFactory;
   @ post getNbNodes() == 0;
   @*/
  public Graph(NodeFactory<V> nodeFactory,EdgeFactory<V> edgeFactory) {
    _nodeMap = new HashMap<>();
    _edgeFactory = edgeFactory;
    _nodeFactory = nodeFactory;
  }
  
  /**
   * Return the edge factory of this graph.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public EdgeFactory<V> edgeFactory() {
    return _edgeFactory;
  }
  
  private EdgeFactory<V> _edgeFactory;

  /**
   * Return the edge factory of this graph.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public NodeFactory<V> nodeFactory() {
    return _nodeFactory;
  }
  
  private NodeFactory<V> _nodeFactory;

  
  //TODO: do we need a node factory ?
  // A node should only be in 1 graph, so it might be best
  // to let graph act as a factory and not expose node.
  // if we only have 1 class of nodes, a separate factory isn't needed.

  /**
   * Add a new node for the given object to this graph. If the
   * object was already part of the graph, nothing changes.
   * 
   * @param object
   *        The object to be added to this graph
   */
 /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObjects().contains(object) 
   @*/
	public Node<V> addNode(V object) {
    Node<V> node = _nodeMap.get(object);
		if(node == null) {
		  Node<V> createNode = nodeFactory().createNode(object);
			_nodeMap.put(object, createNode);
			return createNode;
    }
    return node;
	}
	
	public void removeNode(V object) {
		if(contains(object)) {
			node(object).terminate();
			_nodeMap.remove(object);
		}
	}
	
  /**
   * Create a new edge between the two given nodes.
   * 
   * @param first
   *        The first node.
   * @param second
   *        The second node.
   */
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @ pre contains(first);
   @ pre contains(second);
   @
   @ //TODO : postconditions.
   @*/
	public Edge<V> addEdge(V first, V second) {
		return edgeFactory().createEdge(node(first), node(second));
	}
  
	/**
	 * Ensure that there is an edge from the first node
	 * to the second node. If there was already an edge,
	 * nothing changes. Otherwise an edge is added.
	 * 
	 * @param first
	 * @param second
	 */
	public Edge<V> ensureEdge(V first, V second) {
		Node<V> firstNode = node(first);
		Node<V> secondNode = node(second);
		Edge<V> edge = firstNode.someOutgoingEdge(secondNode);
		if(edge == null) {
			return addEdge(first, second);
		} else {
			return edge;
		}
	}
	
  /**
   * Return the number of nodes in this graph.
   */
 /*@
   @ public behavior
   @
   @ post \result == getNodes().size();
   @*/
  public int nbNodes() {
    return _nodeMap.size();
  }
  
  /**
   * Return the node that represents the given object in this
   * graph.
   * 
   * @param object
   *        The object of which the node is requested.
   */
 /*@
   @ public behavior
   @
   @ pre contains(object);
   @
   @ post \result != null;
   @*/
  public Node<V> node(Object object) {
    return _nodeMap.get(object);
  }
  
  /**
   * Check whether or not the given object is in this graph.
   */
 /*@
   @ public behavior
   @
   @ post object == null ==> \result == false;
   @*/
  public boolean contains(V object) {
    return _nodeMap.containsKey(object);
  }
	
  /**
   * Return all nodes in this graph.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ // The result only contains non-null Node objects.
   @ post (\forall Object o; \result.contains(o)
   @         o instanceof Node);
   @*/
	public Set<Node<V>> nodes() {
    return new HashSet<>(_nodeMap.values());
	}
  
  /**
   * Return all nodes in this graph.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ TODO: specs
   @*/
  public Set<V> objects() {
    return new HashSet<>(_nodeMap.keySet());
  }
  
  /**
   * Check whether or not this graph is empty.
   */
 /*@
   @ public behavior
   @
   @ post \result == (getNbNodes() == 0);
   @*/
  public boolean isEmpty() {
    return _nodeMap.isEmpty();
  }
  
  /**
   * Return the leaf nodes of this graph.
   */
 /*@
   @ post \result != null;
   @ // No nodes that are not in this graph.
   @ post getNodes().containsAll(\result);
   @ post (\forall Object o; \result.contains(o); o instanceof Node);
   @ post (\forall Node node; getNodes().contains(node);
   @         \result.contains(node) == node.isLeaf());
   @*/
  public Set<Node<V>> getLeaves() {
    Set<Node<V>> result = nodes();
    new AbstractPredicate<Node<V>,Nothing>() {
      public boolean eval(Node<V> o) {
        return o.isLeaf();
      }
    }.filter(result);
    return result;
  }
  
  public Graph<V> clone() {
  	Graph<V> result = cloneSelf();
  	Map<Node<V>,Node<V>> cloneMap = new HashMap<>();
  	Set<Entry<V, Node<V>>> entrySet = _nodeMap.entrySet();
		for(Map.Entry<V, Node<V>> entry: entrySet) {
  		cloneMap.put(entry.getValue(), entry.getValue().bareClone());
  	}
		// Keep the set of edges because a bidirectional
		// edge is shared between nodes, and we don't want
		// to copy it twice.
		Set<Edge<V>> done = new HashSet<>();
  	for(Map.Entry<V, Node<V>> entry: entrySet) {
  		Node<V> originalNode = entry.getValue();
			Node<V> clonedNode = cloneMap.get(originalNode);
			for(Edge<V> edge: originalNode.outgoingEdges()) {
				if(! done.contains(edge)) {
					Node<V> originalTarget = edge.endFor(originalNode);
					Node<V> clonedTarget = cloneMap.get(originalTarget);
					edge.cloneTo(clonedNode, clonedTarget);
					done.add(edge);
				}
  		}
  	}
  	return result;
  }
  
  public Graph<V> plus(Graph<V> other) {
  	Graph<V> result = clone();
  	for(Node<V> node: other.nodes()) {
  		V sourceObject = node.object();
			Node<V> source = node(sourceObject);
  		if(source == null) {
  			addNode(sourceObject);
  		}
  		for(Edge<V> edge: node.outgoingEdges()) {
    		V targetObject = edge.endFor(node).object();
  			Node<V> target = node(targetObject);
    		if(target == null) {
    			addNode(targetObject);
    		}
  		}
  	}
  	return result;
  }
  
  protected Graph<V> cloneSelf() {
  	return new Graph<>(nodeFactory(),edgeFactory());
  }
  
  private Map<V,Node<V>> _nodeMap;
  
  public <E extends Exception> void applyToObjects(Action<? super V, E> action) throws E {
  	for(V v: _nodeMap.keySet()) {
  		action.perform(v);
  	}
  }
  
  public <E extends Exception> void traverse(V start, Action<? super V, ? extends E> nodeAction, Action<? super Edge<V>,? extends E> edgeAction) throws E {
  	Node<V> initial = node(start);
  	if(initial == null) {
  		return;
  	}
  	LinkedList<Node<V>> todo = new LinkedList<>();
  	todo.add(initial);
  	Set<Node<V>> traversedNodes = new HashSet<>();
  	Set<Edge<V>> traversedEdges= new HashSet<>();
  	while(! todo.isEmpty()) {
  		Node<V> current = todo.removeFirst();
  		nodeAction.perform(current.object());
  		traversedNodes.add(current);
  		for(Edge<V> edge: current.outgoingEdges()) {
				if(! traversedEdges.contains(edge)) {
	  			Node<V> otherNode = edge.endFor(current);
					if(! traversedNodes.contains(otherNode)) {
	  				todo.addLast(otherNode);
	  			}
					edgeAction.perform(edge);
					traversedEdges.add(edge);
				}
  		}
  	}
  }
  
  public <E extends Exception> void traverseAll(Action<? super V, ? extends E> nodeAction, Action<? super Edge<V>,? extends E> edgeAction) throws E {
  	LinkedList<Node<V>> todo = new LinkedList<>();
  	todo.addAll(_nodeMap.values());

  	// We save some work by not tracking which
  	// node are already visited since they are all present
  	// to start with. Therefore the code is not shared.
  	Set<Edge<V>> traversedEdges= new HashSet<>();
  	while(! todo.isEmpty()) {
  		Node<V> current = todo.removeFirst();
  		nodeAction.perform(current.object());
  		for(Edge<V> edge: current.outgoingEdges()) {
				if(! traversedEdges.contains(edge)) {
					edgeAction.perform(edge);
					traversedEdges.add(edge);
				}
  		}
  	}

  }

  public Set<Edge<V>> edges() {
  	Set<Edge<V>> result = new HashSet<>();
  	for(Node<V> node: nodes()) {
  		result.addAll(node.outgoingEdges());
  	}
  	return result;
  }
  
  public int nbEdges() {
  	int result = 0;
  	for(Node<V> node: nodes()) {
  		result += node.nbOutgoingEdges();
  	}
  	return result;
  }
  
	public <E extends Exception> void filter(Predicate<? super Edge<V>,E> predicate) throws E {
		for(Edge<V> edge: edges()) {
			if(! predicate.eval(edge)) {
				edge.terminate();
			}
		}
	}
	
	public void remoteEdgesNotInvolvedInCycles() {
		Set<Node<V>> todo = nodes();
		boolean prune = true;
		while(prune) {
			Node<V> toPrune = null;
			Iterator<Node<V>> todoIterator = todo.iterator();
			while(todoIterator.hasNext()) {
				Node<V> node = todoIterator.next();
				todoIterator.remove();
				if(node.nbIncomingEdges() == 0 || node.nbOutgoingEdges() == 0) {
					toPrune = node;
					break;
				}
			}
			if(toPrune != null) {
				todo.addAll(toPrune.directSuccessorNodes());
				todo.addAll(toPrune.directPredecessorNodes());
				// Remove again if there is a self link.
				todo.remove(toPrune);
				removeNode(toPrune.object());
			} else {
				// Stop if nothing has been removed.
				prune = false;
			}
		}
	}

}