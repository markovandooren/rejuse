package org.rejuse.graph;

/**
 * @author Marko van Dooren
 */
public class BidiEdgeFactory extends EdgeFactory {

  /**
   * Initialize a new bidirectional edge factory that uses a weightfunction that
   * always returns zero.
   */
 /*@
   @ public behavior
   @
   @ post getWeightFunction() == ZeroWeightFunction.prototype();
   @*/
  public BidiEdgeFactory() {
    super();
  }
 
  /**
   * Initialize a new bidirectional edge factory with the given weightfunction.
   * 
   * @param weightFunction
   *        The weight function that has to be used when creating edges.
   */
 /*@
   @ public behavior
   @
   @ post getWeightFunction() == weightFunction;
   @*/
  public BidiEdgeFactory(WeightFunction weightFunction) {
    super(weightFunction);
  }
 
  /*@
    @ also public behavior
    @
    @ post \result instanceof BidiEdge;
    @*/
  public Edge createEdge(Node first, Node second, double weight) {
    return new BidiEdge(first, second, weight);
  }
}
