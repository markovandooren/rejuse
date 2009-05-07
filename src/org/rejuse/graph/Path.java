package org.rejuse.graph;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.java.collections.Visitor;
import org.rejuse.math.ExtMath;

/**
 * @author Marko van Dooren
 */
public class Path implements Comparable {
  
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
  public Path(Node start) {
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
  protected Path(Node start, List edges, double length) {
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
  public List getNodes() {
    final ArrayList result = new ArrayList();
    result.add(_start);
    new Visitor() {
      public void visit(Object o) {
        result.add(((Edge)o).getEndFor((Node)result.get(result.size()-1)));
      }
    }.applyTo(_edges);
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
  public List getEdges() {
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
  public void addEdge(Edge edge) {
    _edges.add(edge);
    _end = edge.getEndFor(_end);
    _length += edge.getWeight();
  }
  
  /**
   * Return the last node of this path.
   */
 /*@
   @ public behavior
   @
   @*/
  public Node getEnd() {
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
  
  private ArrayList _edges;
  
  /**
   * Return the weight of this path.
   */
  public double getLength() {
    return _length;
  }
  
  private double _length;
  
  private Node _start;
  
  private Node _end;

  /*@
    @ also public behavior
    @
    @ post ! (o instanceof Path) ==> \result == -1;
    @ post (o instanceof Path) ==> \result == Math.sign(getLength() - ((Path)o).getLength());
    @*/
  public int compareTo(Object o) {
    if(equals(o)) {
      return 0;
    }
    else if((o instanceof Path) && (getLength() < ((Path)o).getLength())){
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
  public Object clone() {
    return new Path(_start, _edges, _length);
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
