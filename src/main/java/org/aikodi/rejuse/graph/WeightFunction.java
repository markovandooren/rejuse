package org.aikodi.rejuse.graph;

/**
 * @author Marko van Dooren
 */
public interface WeightFunction<V> {
  public double getWeight(Node<V> first, Node<V> second);
}
