package org.rejuse.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko van Dooren
 */
public class UniEdge extends Edge {

  /**
   * Initialize a new unidirectional edge with the given start and end nodes
   * and the given weight.
   * 
   * @param start
   *        The node where this edge starts.
   * @param end
   *        The node where this edge ends.
   * @param weight
   *        The weight of this edge.
   */
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @
   @ post getStart() == start;
   @ post getEnd() == end;
   @ post getWeight() == weight;
   @*/
  public UniEdge(Node start, Node end, double weight) {
    super(start,end,weight);
  }
  
  /**
   * Initialize a new unidirectional edge with the given start and end nodes.
   * The weight of the new edge is 0.
   * 
   * @param start
   *        The node where this edge starts.
   * @param end
   *        The node where this edge ends.
   */
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @
   @ post getStart() == start;
   @ post getEnd() == end;
   @*/
  public UniEdge(Node start, Node end) {
    this(start,end,0);
  }

  /**
   * Return the start node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getFirst(); 
   @*/  
  public Node getStart() {
    return getFirst();
  }

  /**
   * Return the end node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getEnd(); 
   @*/  
  public Node getEnd() {
    return getEnd();
  }

 /*@
   @ also public behavior
   @
   @ post \result == (getFirst() == node);
   @*/
  public boolean startsIn(Node node) {
    return getFirst() == node;
  }

  /*@
    @ also public behavior
    @
    @ post \result == (getSecond() == node);
    @*/
  public boolean endsIn(Node node) {
    return getSecond() == node;
  }

 /*@
   @ also public behavior
   @
   @ post \result.size() == 1;
   @ post \result.contains(getFirst());
   @*/
  public Set getStartNodes() {
    Set result = new HashSet();
    result.add(getFirst());
    return result;
  }

 /*@
   @ also public behavior
   @
   @ post \result == getSecond(); 
   @*/
  public Node getEndFor(Node start) {
    return getSecond();
  }

  /*@
    @ also public behavior
    @
    @ post \result == getFirst(); 
    @*/
  public Node getStartFor(Node end) {
    return getFirst();
  }

  /*@
    @ also public behavior
    @
    @ post \result.size() == 1;
    @ post \result.contains(getSecond());
    @*/
  public Set getEndNodes() {
    Set result = new HashSet();
    result.add(getSecond());
    return result;
  }

}
