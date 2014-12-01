package be.kuleuven.cs.distrinet.rejuse.graph.adapter;

import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.ImmutableSet;

import be.kuleuven.cs.distrinet.rejuse.contract.Contracts;
import be.kuleuven.cs.distrinet.rejuse.graph.Edge;
import be.kuleuven.cs.distrinet.rejuse.graph.Graph;
import be.kuleuven.cs.distrinet.rejuse.graph.Node;
import be.kuleuven.cs.distrinet.rejuse.graph.UniEdge;
import be.kuleuven.cs.distrinet.rejuse.graph.UniEdgeFactory;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class JungAdapter<V> implements edu.uci.ics.jung.graph.Graph<V, Edge<V>> {

	public JungAdapter(Graph<V> graph) {
		Contracts.notNull(graph, "A null graph cannot be adapted to a JUNG graph.");
		_graph = graph;
	}
	
	private Graph<V> _graph;

	@Override
	public boolean addEdge(Edge<V> arg0, Collection<? extends V> arg1) {
		throw new Error();
	}

	@Override
	public boolean addEdge(Edge<V> arg0, Collection<? extends V> arg1, EdgeType arg2) {
		throw new Error();
	}

	@Override
	public boolean addVertex(V object) {
		if(_graph.contains(object)) {
			return false;
		} else {
		  _graph.addNode(object);
		  return true;
		}
	}

	@Override
	public boolean containsEdge(Edge<V> arg0) {
		return containsVertex(arg0.getFirst().object()) && containsVertex(arg0.getSecond().object());
	}

	@Override
	public boolean containsVertex(V arg0) {
		return _graph.contains(arg0);
	}

	@Override
	public int degree(V arg0) {
		return getIncidentEdges(arg0).size();
	}

	@Override
	public Edge<V> findEdge(V arg0, V arg1) {
		for(Edge<V> edge:_graph.node(arg0).edges()) {
			if(edge.getFirst() == arg1 || edge.getSecond() == arg1) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public Collection<Edge<V>> findEdgeSet(V arg0, V arg1) {
		ImmutableSet.Builder<Edge<V>> builder = ImmutableSet.builder();
		for(Edge<V> edge:_graph.node(arg0).edges()) {
			if(edge.getFirst() == arg1 || edge.getSecond() == arg1) {
				builder.add(edge);
			}
		}
		return builder.build();
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		if(_graph.edgeFactory() instanceof UniEdgeFactory) {
			return EdgeType.DIRECTED;
		} else {
			return EdgeType.UNDIRECTED;
		}
	}

	@Override
	public int getEdgeCount() {
		return _graph.nbEdges();
	}

	@Override
	public int getEdgeCount(EdgeType arg0) {
		if(arg0 == getDefaultEdgeType()) {
		return getEdgeCount();
		} else {
			return 0;
		}
	}

	@Override
	public EdgeType getEdgeType(Edge<V> arg0) {
		return getDefaultEdgeType();
	}

	@Override
	public Collection<Edge<V>> getEdges() {
		return _graph.edges();
	}

	@Override
	public Collection<Edge<V>> getEdges(EdgeType arg0) {
		if(arg0 == getDefaultEdgeType()) {
			return getEdges();
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public int getIncidentCount(Edge<V> arg0) {
		return 2;
	}

	@Override
	public Collection<Edge<V>> getIncidentEdges(V arg0) {
		return _graph.node(arg0).edges();
	}

	@Override
	public Collection<V> getIncidentVertices(Edge<V> arg0) {
		return ImmutableSet.of(arg0.getFirst().object(),arg0.getSecond().object());
	}

	@Override
	public int getNeighborCount(V arg0) {
		return getNeighbors(arg0).size();
	}

	@Override
	public Collection<V> getNeighbors(V arg0) {
		ImmutableSet.Builder<V> builder = ImmutableSet.builder();
		builder.addAll(_graph.node(arg0).directSuccessors());
		builder.addAll(_graph.node(arg0).directPredecessors());
		return builder.build();
	}

	@Override
	public int getVertexCount() {
		return _graph.nbNodes();
	}

	@Override
	public Collection<V> getVertices() {
		return _graph.objects();
	}

	@Override
	public boolean isIncident(V arg0, Edge<V> arg1) {
		return arg1.getFirst().object() == arg0 || arg1.getSecond().object() == arg0; 
	}

	@Override
	public boolean isNeighbor(V arg0, V arg1) {
		return isPredecessor(arg0, arg1) || isSuccessor(arg0, arg1);
	}

	@Override
	public boolean removeEdge(Edge<V> arg0) {
		arg0.terminate();
		return true;
	}

	@Override
	public boolean removeVertex(V arg0) {
		if(_graph.contains(arg0)) {
			_graph.removeNode(arg0);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addEdge(Edge<V> arg0, V arg1, V arg2) {
		Node<V> firstNode = arg0.getFirst();
		Node<V> secondNode = arg0.getSecond();
		if(firstNode.hasDirectSuccessor(secondNode)) {
			return false;
		} else {
		_graph.addEdge(firstNode.object(), secondNode.object());
		return true;
		}
	}

	@Override
	public boolean addEdge(Edge<V> arg0, V arg1, V arg2, EdgeType arg3) {
		return addEdge(arg0,arg1,arg2);
	}

	@Override
	public V getDest(Edge<V> arg0) {
		if(arg0 instanceof UniEdge) {
			return ((UniEdge<V>)arg0).end();
		} else {
			return null;
		}
	}

	@Override
	public Pair<V> getEndpoints(Edge<V> arg0) {
		return new Pair<>(arg0.getFirst().object(),arg0.getSecond().object());
	}

	@Override
	public Collection<Edge<V>> getInEdges(V arg0) {
		return (Collection)_graph.node(arg0).incomingEdges();
	}

	@Override
	public V getOpposite(V arg0, Edge<V> arg1) {
		return arg1.opposite(_graph.node(arg0)).object();
	}

	@Override
	public Collection<Edge<V>> getOutEdges(V arg0) {
		return (Collection)_graph.node(arg0).outgoingEdges();
	}

	@Override
	public int getPredecessorCount(V arg0) {
		return _graph.node(arg0).directPredecessors().size();
	}

	@Override
	public Collection<V> getPredecessors(V arg0) {
		return _graph.node(arg0).directPredecessors();
	}

	@Override
	public V getSource(Edge<V> arg0) {
		if(arg0 instanceof UniEdge) {
			return ((UniEdge<V>)arg0).start();
		} else {
			return null;
		}
	}

	@Override
	public int getSuccessorCount(V arg0) {
		return _graph.node(arg0).directSuccessors().size();
	}

	@Override
	public Collection<V> getSuccessors(V arg0) {
		return _graph.node(arg0).directSuccessors();
	}

	@Override
	public int inDegree(V arg0) {
		return _graph.node(arg0).nbIncomingEdges();
	}

	@Override
	public boolean isDest(V arg0, Edge<V> arg1) {
		return arg1.endsIn(_graph.node(arg0));
	}

	@Override
	public boolean isPredecessor(V arg0, V arg1) {
		return _graph.node(arg0).hasDirectPredecessor(_graph.node(arg1));
	}

	@Override
	public boolean isSource(V arg0, Edge<V> arg1) {
		return arg1.startsIn(_graph.node(arg0));
	}

	@Override
	public boolean isSuccessor(V arg0, V arg1) {
		return _graph.node(arg0).hasDirectSuccessor(_graph.node(arg1));
	}

	@Override
	public int outDegree(V arg0) {
		return _graph.node(arg0).nbOutgoingEdges();
	}
	

}
