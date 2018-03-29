package org.aikodi.rejuse.data.graph;

/**
 * @author Marko van Dooren
 */
public class BidiEdgeFactory<V> implements EdgeFactory<V> {

  /*@
    @ also public behavior
    @
    @ post \result instanceof BidiEdge;
    @*/
  public Edge<V> createEdge(Node<V> first, Node<V> second) {
    return new BidiEdge<V>(first, second);
  }
}
