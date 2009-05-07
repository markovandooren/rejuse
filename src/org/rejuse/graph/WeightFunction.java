package org.rejuse.graph;

/**
 * @author Marko van Dooren
 */
public interface WeightFunction {
  public double getWeight(Node first, Node second);
}
