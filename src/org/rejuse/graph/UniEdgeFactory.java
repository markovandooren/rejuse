package org.rejuse.graph;

/**
 * @author Marko van Dooren
 */
public class UniEdgeFactory extends EdgeFactory {
  
  /**
   * Initialize a new unidirectional edge factory that uses a weightfunction that
   * always returns zero.
   */
 /*@
   @ public behavior
   @
   @ post getWeightFunction() == ZeroWeightFunction.prototype();
   @*/
  public UniEdgeFactory() {
    super();
  }
  
  /**
   * Initialize a new unidirectional edge factory with the given weightfunction.
   * 
   * @param weightFunction
   *        The weight function that has to be used when creating edges.
   */
 /*@
   @ public behavior
   @
   @ post getWeightFunction() == weightFunction;
   @*/
  public UniEdgeFactory(WeightFunction weightFunction) {
    super(weightFunction);
  }
  
  /*@
    @ also public behavior
    @
    @ post \result instanceof UniEdge;
    @*/
  public Edge createEdge(Node first, Node second, double weight) {
    return new UniEdge(first, second, weight);
  }


}
