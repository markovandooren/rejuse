package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko van Dooren
 */
public class BidiEdge extends Edge {

  /**
   * Initialize a new bidirectional edge with the given nodes
   * and the given weight.
   * 
   * @param first
   *        The first node.
   * @param end
   *        The second node.
   * @param weight
   *        The weight of this edge.
   */
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @
   @ post getFirst() == first;
   @ post getSecond() == second;
   @ post getWeight() == weight;
   @*/
  public BidiEdge(Node first, Node second, double weight) {
    super(first,second,weight);
  }
  
  /**
   * Initialize a new unidirectional edge with the given start and end nodes.
   * The weight of the new edge is 0.
   * 
   * @param first
   *        The first node.
   * @param end
   *        The second node.
   */
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @
   @ post getFirst() == first;
   @ post getSecond() == second;
   @*/
  public BidiEdge(Node first, Node second) {
    this(first,second,0);
  }

 /*@
   @ also public behavior
   @
   @ post \result == (getFirst() == node) ||
   @                 (getSecond() == node);
   @*/
  public boolean startsIn(Node node) {
    return getFirst() == node || getSecond() == node;
  }

 /*@
   @ also public behavior
   @
   @ post \result == (getFirst() == node) ||
   @                 (getSecond() == node);
   @*/
  public boolean endsIn(Node node) {
    return getFirst() == node || getSecond() == node;
  }

 /*@
   @ also public behavior
   @
   @ post \result.size() <= 2;
   @ post \result.contains(getFirst());
   @ post \result.contains(getSecond());
   @ post getFirst == getSecond() ==> \result.size() == 1;
   @*/
  public Set getStartNodes() {
    Set result = new HashSet();
    result.add(getFirst());
    result.add(getSecond());
    return result;
  }

 /*@
   @ also public behavior
   @
   @ post start == getFirst() ==> \result == getSecond(); 
   @ post start == getSecond() ==> \result == getFirst();
   @*/
  public Node getEndFor(Node start) {
    if(start == getFirst()) {
      return getSecond();
    }
    else {
      return getFirst();
    }
  }

  /*@
    @ also public behavior
    @
    @ post start == getFirst() ==> \result == getSecond(); 
    @ post start == getSecond() ==> \result == getFirst();
    @*/
  public Node getStartFor(Node end) {
    if(end == getFirst()) {
      return getSecond();
    }
    else {
      return getFirst();
    }
  }

  /*@
    @ also public behavior
    @
    @ post \result.size() <= 2;
    @ post \result.contains(getFirst());
    @ post \result.contains(getSecond());
    @ post getFirst == getSecond() ==> \result.size() == 1;
    @*/
  public Set getEndNodes() {
    Set result = new HashSet();
    result.add(getFirst());
    result.add(getSecond());
    return result;
  }

}
