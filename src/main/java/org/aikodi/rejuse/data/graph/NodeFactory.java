package org.aikodi.rejuse.data.graph;

/**
 * @author Marko van Dooren
 */
public interface NodeFactory<V> {
  
  /**
   * Create a new edge with the given first and second nodes. The
   * weight of the edge will be determined by the weight function of
   * this factory.
   * 
   * @param first
   *        The first node connected to the new edge.
   * @param second
   *        The second node connected to the new edge.
   */
 /*@
   @ public behavior
   @
   @ post \result == createEdge(first,
   @                            second,
   @                            getWeightFunction().getWeight(first, second));
   @*/
  public abstract Node<V> createNode(V object);
  
  

}
