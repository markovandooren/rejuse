package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko van Dooren
 */
public class BidiEdge<V> extends AbstractEdge<V> {

  /**
   * Initialize a new bidirectional edge with the given nodes.
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
  public BidiEdge(Node<V> first, Node<V> second) {
    super(first,second);
  }
  
 /*@
   @ also public behavior
   @
   @ post \result == (getFirst() == node) ||
   @                 (getSecond() == node);
   @*/
  public boolean startsIn(Node<V> node) {
    return getFirst() == node || getSecond() == node;
  }

 /*@
   @ also public behavior
   @
   @ post \result == (getFirst() == node) ||
   @                 (getSecond() == node);
   @*/
  public boolean endsIn(Node<V> node) {
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
