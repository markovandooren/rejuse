package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

/**
 * @author Marko van Dooren
 * 
 * TODO WARNING : An undirected graph may not contain selfloops, i.e., it may not 
 *                contain an edge whose source is equal to its target.
 */
public class Graph {
  
  /**
   * Initialize an empty graph.
   */
 /*@
   @ public behavior
   @
   @ post getEdgeFactory() instanceof BidiEdgeFactory;
   @ post getNbNodes() == 0;
   @*/
  public Graph() {
    this(new BidiEdgeFactory());
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
  public Graph(EdgeFactory edgeFactory) {
    _nodeMap = new HashMap();
    _edgeFactory = edgeFactory;
  }
  
  /**
   * Return the edge factory of this graph.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public EdgeFactory getEdgeFactory() {
    return _edgeFactory;
  }
  
  private EdgeFactory _edgeFactory;

  //TODO: do we need a node factory ?
  // A node should only be in 1 graph, so it might be best
  // to let graph act as a factory and not expose node.
  // if we only have 1 class of nodes, a separate factory isn't needed.

  /**
   * Add a new node for the given object to this graph
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
	public void addNode(Object object) {
    if(_nodeMap.get(object) == null) {
		  _nodeMap.put(object, new Node(object));
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
	public Edge addEdge(Object first, Object second) {
		return _edgeFactory.createEdge(getNode(first), getNode(second));
	}
  
  /**
   * Create a new edge between the two given nodes with the given weight.
   * 
   * @param first
   *        The first node.
   * @param second
   *        The second node.
   * @param weight
   *        The weight of the new edge.
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
  public Edge addEdge(Object first, Object second, double weight) {
    return _edgeFactory.createEdge(getNode(first), getNode(second), weight);
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
  public Node getNode(Object object) {
    return (Node)_nodeMap.get(object);
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
	public Set getNodes() {
    return new HashSet(_nodeMap.values());
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
  public Set getObjects() {
    return new HashSet(_nodeMap.keySet());
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
  public Set getLeaves() {
    Set result = getNodes();
    new SafePredicate<Node>() {
      public boolean eval(Node o) {
        return ((Node)o).isLeaf();
      }
    }.filter(result);
    return result;
  }
  
  private Map _nodeMap;
}