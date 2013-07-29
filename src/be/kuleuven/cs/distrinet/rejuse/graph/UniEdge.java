package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko van Dooren
 */
public class UniEdge<V> extends AbstractEdge<V> {

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
  public UniEdge(Node<V> start, Node<V> end) {
    super(start,end);
  }
  
  /**
   * Return the start node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getFirst(); 
   @*/  
  public Node<V> getStart() {
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
  public Node<V> getEnd() {
    return getEnd();
  }

 /*@
   @ also public behavior
   @
   @ post \result == (getFirst() == node);
   @*/
  public boolean startsIn(Node<V> node) {
    return getFirst() == node;
  }

  /*@
    @ also public behavior
    @
    @ post \result == (getSecond() == node);
    @*/
  public boolean endsIn(Node<V> node) {
    return getSecond() == node;
  }

 /*@
   @ also public behavior
   @
   @ post \result.size() == 1;
   @ post \result.contains(getFirst());
   @*/
  public Set<Node<V>> getStartNodes() {
    Set<Node<V>> result = new HashSet<>();
    result.add(getFirst());
    return result;
  }

 /*@
   @ also public behavior
   @
   @ post \result == getSecond(); 
   @*/
  public Node<V> getEndFor(Node<V> start) {
    return getSecond();
  }

  /*@
    @ also public behavior
    @
    @ post \result == getFirst(); 
    @*/
  public Node<V> getStartFor(Node<V> end) {
    return getFirst();
  }

  /*@
    @ also public behavior
    @
    @ post \result.size() == 1;
    @ post \result.contains(getSecond());
    @*/
  public Set<Node<V>> getEndNodes() {
    Set<Node<V>> result = new HashSet<>();
    result.add(getSecond());
    return result;
  }

}
