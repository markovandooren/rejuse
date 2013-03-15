package be.kuleuven.cs.distrinet.rejuse.graph;

/**
 * @author Marko van Dooren
 */
public class ZeroWeightFunction implements WeightFunction {

  private ZeroWeightFunction() {
  }
  
 /*@
   @ also public behavior
   @
   @ post \result == 0;
   @*/
  public double getWeight(Node first, Node second) {
    return 0;
  }
  
  /**
   * Return the protoype of this zero weight function.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public static ZeroWeightFunction prototype() {
    return _prototype;
  }
  
  private static ZeroWeightFunction _prototype = new ZeroWeightFunction();
}
