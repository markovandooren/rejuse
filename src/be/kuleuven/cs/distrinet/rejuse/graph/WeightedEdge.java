//package be.kuleuven.cs.distrinet.rejuse.graph;
//
//import java.util.Set;
//
//public class WeightedEdge<V> implements Edge<V> {
//
//	public WeightedEdge(Edge<V> edge, double weight) {
//		_edge = edge;
//		_weight = weight;
//	}
//	
//	private double _weight;
//	
//	public double weight() {
//		return _weight;
//	}
//	
//	private Edge<V> _edge;
//
//	@Override
//	public void terminate() {
//		_edge.terminate();
//	}
//
//	@Override
//	public Set<Node<V>> getNodes() {
//		return _edge.getEndNodes();
//	}
//
//	@Override
//	public boolean startsIn(Node<V> node) {
//		return _edge.startsIn(node);
//	}
//
//	@Override
//	public boolean endsIn(Node<V> node) {
//		return _edge.endsIn(node);
//	}
//
//	@Override
//	public Set<Node<V>> getStartNodes() {
//		return _edge.getStartNodes();
//	}
//
//	@Override
//	public Node<V> getEndFor(Node<V> start) {
//		return _edge.getEndFor(start);
//	}
//
//	@Override
//	public Node<V> getStartFor(Node<V> end) {
//		return _edge.getStartFor(end);
//	}
//
//	@Override
//	public Set<Node<V>> getEndNodes() {
//		return _edge.getEndNodes();
//	}
//
//	@Override
//	public Node<V> getFirst() {
//		return _edge.getFirst();
//	}
//
//	@Override
//	public Node<V> getSecond() {
//		return _edge.getSecond();
//	}
//	
//	@Override
//	public Edge<V> cloneTo(Node<V> newSource, Node<V> newTarget) {
//		return new WeightedEdge<>(_edge.cloneTo(newSource, newTarget), weight());
//	}
//
//}
