package be.kuleuven.cs.distrinet.rejuse.graph;

/**
 * @author Marko van Dooren
 */
public interface WeightFunction {
  public double getWeight(Node first, Node second);
}
