package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

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
		return edgeFactory().createEdge(getNode(first), getNode(second));
	}
  
	/**
	 * Ensure that there is an edge from the first node
	 * to the second node. If there was already an edge,
	 * nothing changes. Otherwise an edge is added.
	 * 
	 * @param first
	 * @param second
	 */
	public void ensureEdge(V first, V second) {
		Node<V> firstNode = getNode(first);
		Node<V> secondNode = getNode(second);
		if(! firstNode.isDirectlyConnectedTo(secondNode)) {
			addEdge(first, second);
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
  public int getNbNodes() {
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
  public Node<V> getNode(Object object) {
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
  public boolean contains(Object object) {
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
	public Set<Node<V>> getNodes() {
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
  public Set<V> getObjects() {
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
    Set<Node<V>> result = getNodes();
    new SafePredicate() {
      public boolean eval(Object o) {
        return ((Node)o).isLeaf();
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
			for(Edge<V> edge: originalNode.getStartEdges()) {
				if(! done.contains(edge)) {
					Node<V> originalTarget = edge.getEndFor(originalNode);
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
  	for(Node<V> node: other.getNodes()) {
  		V sourceObject = node.getObject();
			Node<V> source = getNode(sourceObject);
  		if(source == null) {
  			addNode(sourceObject);
  		}
  		for(Edge<V> edge: node.getStartEdges()) {
    		V targetObject = edge.getEndFor(node).getObject();
  			Node<V> target = getNode(targetObject);
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
}