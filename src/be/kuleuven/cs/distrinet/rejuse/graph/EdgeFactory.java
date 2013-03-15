package be.kuleuven.cs.distrinet.rejuse.graph;

/**
 * @author Marko van Dooren
 */
public abstract class EdgeFactory {
  
  /**
   * Initialize a new edge factory that uses a weightfunction that
   * always returns zero.
   */
 /*@
   @ public behavior
   @
   @ post getWeightFunction() == ZeroWeightFunction.prototype();
   @*/
  public EdgeFactory() {
    _weightFunction = ZeroWeightFunction.prototype();
  }
  
  /**
   * Initialize a new edge factory with the given weightfunction.
   * 
   * @param weightFunction
   *        The weight function that has to be used when creating edges.
   */
 /*@
   @ public behavior
   @
   @ post getWeightFunction() == weightFunction;
   @*/
  public EdgeFactory(WeightFunction weightFunction) {
    _weightFunction = weightFunction;
  }
  

  
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
  public Edge createEdge(Node first, Node second) {
    return createEdge(first, second, getWeightFunction().getWeight(first, second));
  }
  
  /**
   * Create a new edge with the given first and second nodes and weight.
   * 
   * @param first
   *        The first node connected to the new edge.
   * @param second
   *        The second node connected to the new edge.
   * @param weight
   *        The weight of the new edge.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.getFirst() == first;
   @ post \result.getSecond() == second;
   @ post \result.getWeight() == weight;
   @*/
  public abstract Edge createEdge(Node first, Node second, double weight);
  
  /**
   * Return the weight function of this edge factory.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public WeightFunction getWeightFunction() {
    return _weightFunction;
  }
  
  /**
   * The weight function of this edge factory.
   */
  private WeightFunction _weightFunction;
}
