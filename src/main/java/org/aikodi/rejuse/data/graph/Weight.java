package org.aikodi.rejuse.data.graph;

/**
 * A class of weights.
 * 
 * @author Marko van Dooren
 */
public class Weight {

  /**
   * Create a new weight.
   * 
   * @param weight The value of the new weight.
   */
  public Weight(double weight) {
    _weight = weight;
  }

  /**
   * @return The value of the weight.
   */
  public double weight() {
    return _weight;
  }

  private double _weight;
}
