package org.aikodi.rejuse.data.graph;

/**
 * @author Marko van Dooren
 */
public class UniEdgeFactory<V> implements EdgeFactory<V> {
  
  /*@
    @ also public behavior
    @
    @ post \result instanceof UniEdge;
    @*/
  public Edge<V> createEdge(Node<V> first, Node<V> second) {
    return new UniEdge<V>(first, second);
  }


}
