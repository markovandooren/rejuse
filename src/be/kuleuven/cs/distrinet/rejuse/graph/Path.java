package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;

/**
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
    _edges = new ArrayList();
    _length = 0;
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
  protected Path(Node<V> start, List edges, double length) {
    _start= start;
    _end = start;
    _edges = new ArrayList(edges);
    _length = length;
  }
  
  
  
  /**
   * Return the start node for this path. 
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public Node getStart() {
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
  public List<Node<V>> getNodes() {
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
    return new ArrayList(_edges);
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
  	Weight weight = edge.get(Weight.class);
  	if(weight != null) {
  		_edges.add(edge);
  		_end = edge.endFor(_end);
  		_length += weight.weight();
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
  
  private List<Edge<V>> _edges;
  
  /**
   * Return the weight of this path.
   */
  public double getLength() {
    return _length;
  }
  
  private double _length;
  
  private Node<V> _start;
  
  private Node<V> _end;

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
    else if((getLength() < o.getLength())){
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
    return new Path<V>(_start, _edges, _length);
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
    }.applyTo(getNodes());
    result.append(" : "+getLength());
    return result.toString();
  }
}
