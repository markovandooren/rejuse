package org.aikodi.rejuse.graph;

public class WeightedEdgeFactory<V> implements EdgeFactory<V> {

	public WeightedEdgeFactory(EdgeFactory<V> edgeFactory, WeightFunction<V> weightFunction) {
		if(edgeFactory == null) {
			throw new IllegalArgumentException("The edge factory is null.");
		}
		if(weightFunction == null) {
			throw new IllegalArgumentException("The weight function is null.");
		}
		_edgeFactory = edgeFactory;
		_weightFunction = weightFunction();
	}

	public WeightedEdgeFactory(EdgeFactory<V> edgeFactory) {
		if(edgeFactory == null) {
			throw new IllegalArgumentException("The edge factory is null.");
		}
		_edgeFactory = edgeFactory;
		_weightFunction = new WeightFunction<V>() {

			@Override
			public double getWeight(Node<V> first, Node<V> second) {
				return 0;
			}
		};
	}

	public Edge<V> createEdge(Node<V> first, Node<V> second, double weight) {
		Edge<V> result = edgeFactory().createEdge(first, second);
		result.put(Weight.class, new Weight(weight));
		return result;
	}

	@Override
	public Edge<V> createEdge(Node<V> first, Node<V> second) {
		return createEdge(first, second, weightFunction().getWeight(first, second));
	}

	public WeightFunction<V> weightFunction() {
		return _weightFunction;
	}

	private WeightFunction<V> _weightFunction;

  /**
   * Return the edge factory of this graph.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public EdgeFactory<V> edgeFactory() {
    return _edgeFactory;
  }
  
  private EdgeFactory<V> _edgeFactory;

}
