package org.aikodi.rejuse.data.graph;

import static org.aikodi.contract.Contract.requireNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.aikodi.contract.Contract;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.action.UniversalConsumer;
import org.aikodi.rejuse.predicate.Predicate;

import com.google.common.base.Stopwatch;

/**
 * @author Marko van Dooren
 * 
 *         TODO WARNING : An undirected graph may not contain selfloops, i.e.,
 *         it may not contain an edge whose source is equal to its target.
 */
public class Graph<V> {

	/**
	 * Initialize an empty graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post edgeFactory() instanceof BidiEdgeFactory;
	 * 
	 * @ post nodeFactory() instanceof DefaultNodeFactory;
	 * 
	 * @ post getNbNodes() == 0;
	 * 
	 * @
	 */
	public Graph() {
		this(new DefaultNodeFactory<V>(), new BidiEdgeFactory<V>());
	}

	/**
	 * Initialize an empty graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post getEdgeFactory() instanceof BidiEdgeFactory;
	 * 
	 * @ post getNbNodes() == 0;
	 * 
	 * @
	 */
	public Graph(EdgeFactory<V> edgeFactory) {
		this(new DefaultNodeFactory<V>(), edgeFactory);
	}

	/**
	 * Initialize an empty graph that will use the given edge factory to create
	 * edges.
	 * 
	 * @param edgeFactory The edge factory to be used
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ pre edgeFactory != null;
	 * 
	 * @
	 * 
	 * @ post getEdgeFactory() instanceof BidiEdgeFactory;
	 * 
	 * @ post getNbNodes() == 0;
	 * 
	 * @
	 */
	public Graph(NodeFactory<V> nodeFactory, EdgeFactory<V> edgeFactory) {
		_nodeMap = new HashMap<>();
		_edgeFactory = edgeFactory;
		_nodeFactory = nodeFactory;
	}

	/**
	 * Return the edge factory of this graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post \result != null;
	 * 
	 * @
	 */
	public EdgeFactory<V> edgeFactory() {
		return _edgeFactory;
	}

	private EdgeFactory<V> _edgeFactory;

	/**
	 * Return the edge factory of this graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post \result != null;
	 * 
	 * @
	 */
	public NodeFactory<V> nodeFactory() {
		return _nodeFactory;
	}

	private NodeFactory<V> _nodeFactory;

	// TODO: do we need a node factory ?
	// A node should only be in 1 graph, so it might be best
	// to let graph act as a factory and not expose node.
	// if we only have 1 class of nodes, a separate factory isn't needed.

	/**
	 * Add a new node for the given object to this graph. If the object was already
	 * part of the graph, nothing changes.
	 * 
	 * @param object The object to be added to this graph
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ pre object != null;
	 * 
	 * @
	 * 
	 * @ post getObjects().contains(object)
	 * 
	 * @
	 */
	public Node<V> addNode(V object) {
		Node<V> node = _nodeMap.get(object);
		if (node == null) {
			Node<V> createNode = nodeFactory().createNode(object);
			_nodeMap.put(object, createNode);
			return createNode;
		}
		return node;
	}

	public void removeNode(V object) {
		if (contains(object)) {
			node(object).terminate();
			_nodeMap.remove(object);
		}
	}

	/**
	 * Create a new edge between the two given nodes.
	 * 
	 * @param first  The first node.
	 * @param second The second node.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ pre first != null;
	 * 
	 * @ pre second != null;
	 * 
	 * @ pre contains(first);
	 * 
	 * @ pre contains(second);
	 * 
	 * @
	 * 
	 * @ //TODO : postconditions.
	 * 
	 * @
	 */
	public Edge<V> addEdge(V first, V second) {
		return edgeFactory().createEdge(node(first), node(second));
	}

	/**
	 * Ensure that there is an edge from the first node to the second node. If there
	 * was already an edge, nothing changes. Otherwise an edge is added.
	 * 
	 * @param first
	 * @param second
	 */
	public Edge<V> ensureEdge(V first, V second) {
		Node<V> firstNode = node(first);
		Node<V> secondNode = node(second);
		Edge<V> edge = firstNode.someOutgoingEdge(secondNode);
		if (edge == null) {
			return addEdge(first, second);
		} else {
			return edge;
		}
	}

	/**
	 * Return the number of nodes in this graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post \result == getNodes().size();
	 * 
	 * @
	 */
	public int nbNodes() {
		return _nodeMap.size();
	}

	/**
	 * Return the node that represents the given object in this graph.
	 * 
	 * @param object The object of which the node is requested.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ pre contains(object);
	 * 
	 * @
	 * 
	 * @ post \result != null;
	 * 
	 * @
	 */
	public Node<V> node(Object object) {
		return _nodeMap.get(object);
	}

	/**
	 * Check whether or not the given object is in this graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post object == null ==> \result == false;
	 * 
	 * @
	 */
	public boolean contains(V object) {
		return _nodeMap.containsKey(object);
	}

	/**
	 * Return all nodes in this graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post \result != null;
	 * 
	 * @ // The result only contains non-null Node objects.
	 * 
	 * @ post (\forall Object o; \result.contains(o)
	 * 
	 * @ o instanceof Node);
	 * 
	 * @
	 */
	public Set<Node<V>> nodes() {
		return new HashSet<>(_nodeMap.values());
	}

	/**
	 * Return all nodes in this graph.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post \result != null;
	 * 
	 * @ TODO: specs
	 * 
	 * @
	 */
	public Set<V> objects() {
		return new HashSet<>(_nodeMap.keySet());
	}

	/**
	 * Check whether or not this graph is empty.
	 */
	/*
	 * @
	 * 
	 * @ public behavior
	 * 
	 * @
	 * 
	 * @ post \result == (getNbNodes() == 0);
	 * 
	 * @
	 */
	public boolean isEmpty() {
		return _nodeMap.isEmpty();
	}

	/**
	 * Return the leaf nodes of this graph.
	 */
	/*
	 * @
	 * 
	 * @ post \result != null;
	 * 
	 * @ // No nodes that are not in this graph.
	 * 
	 * @ post getNodes().containsAll(\result);
	 * 
	 * @ post (\forall Object o; \result.contains(o); o instanceof Node);
	 * 
	 * @ post (\forall Node node; getNodes().contains(node);
	 * 
	 * @ \result.contains(node) == node.isLeaf());
	 * 
	 * @
	 */
	public Set<Node<V>> getLeaves() {
		Set<Node<V>> result = nodes();
		new Predicate<Node<V>, Nothing>() {
			public boolean eval(Node<V> o) {
				return o.isLeaf();
			}
		}.filter(result);
		return result;
	}

	public Graph<V> clone() {
		Graph<V> result = cloneSelf();
		Map<Node<V>, Node<V>> cloneMap = new HashMap<>();
		Set<Entry<V, Node<V>>> entrySet = _nodeMap.entrySet();
		for (Map.Entry<V, Node<V>> entry : entrySet) {
			cloneMap.put(entry.getValue(), entry.getValue().bareClone());
		}
		// Keep the set of edges because a bidirectional
		// edge is shared between nodes, and we don't want
		// to copy it twice.
		Set<Edge<V>> done = new HashSet<>();
		for (Map.Entry<V, Node<V>> entry : entrySet) {
			Node<V> originalNode = entry.getValue();
			Node<V> clonedNode = cloneMap.get(originalNode);
			for (Edge<V> edge : originalNode.successorEdges()) {
				if (!done.contains(edge)) {
					Node<V> originalTarget = edge.endFor(originalNode);
					Node<V> clonedTarget = cloneMap.get(originalTarget);
					edge.cloneTo(clonedNode, clonedTarget);
					done.add(edge);
				}
			}
		}
		return result;
	}

	/**
	 * Return a graph that contains the nodes and edges of both this graph and the
	 * given graph.
	 * 
	 * @param other The graph to be combined with this graph. The graph cannot be
	 *              null.
	 * @return The result is not null and contains all nodes and edges of both this
	 *         graph and the other graph.
	 */
	public Graph<V> plus(Graph<V> other) {
		requireNotNull(other);

		Graph<V> result = clone();
		for (Node<V> node : other.nodes()) {
			V sourceObject = node.object();
			Node<V> source = node(sourceObject);
			if (source == null) {
				addNode(sourceObject);
			}
			for (Edge<V> edge : node.successorEdges()) {
				V targetObject = edge.endFor(node).object();
				Node<V> target = node(targetObject);
				if (target == null) {
					addNode(targetObject);
				}
			}
		}
		return result;
	}

	protected Graph<V> cloneSelf() {
		return new Graph<>(nodeFactory(), edgeFactory());
	}

	private Map<V, Node<V>> _nodeMap;

	public <E extends Exception> void applyToObjects(UniversalConsumer<? super V, E> action) throws E {
		for (V v : _nodeMap.keySet()) {
			action.perform(v);
		}
	}

	public <E extends Exception> void traverse(V start, UniversalConsumer<? super V, ? extends E> nodeAction,
			UniversalConsumer<? super Edge<V>, ? extends E> edgeAction) throws E {
		Node<V> initial = node(start);
		if (initial == null) {
			return;
		}
		LinkedList<Node<V>> todo = new LinkedList<>();
		todo.add(initial);
		Set<Node<V>> traversedNodes = new HashSet<>();
		Set<Edge<V>> traversedEdges = new HashSet<>();
		while (!todo.isEmpty()) {
			Node<V> current = todo.removeFirst();
			nodeAction.perform(current.object());
			traversedNodes.add(current);
			for (Edge<V> edge : current.successorEdges()) {
				if (!traversedEdges.contains(edge)) {
					Node<V> otherNode = edge.endFor(current);
					if (!traversedNodes.contains(otherNode)) {
						todo.addLast(otherNode);
					}
					edgeAction.perform(edge);
					traversedEdges.add(edge);
				}
			}
		}
	}

	public class IncrementalTopologicalReachabilityIterator {

		private void addReachableBy(Set<V> newSources, Set<V> accumulated) {
			Set<V> todo = new HashSet<V>(newSources);
			while (!todo.isEmpty()) {
				V current = todo.iterator().next();
				todo.remove(current);
				accumulated.add(current);
				Set<V> successors = _nodeMap.get(current).directSuccessors();
				for (V successor : successors) {
					if (!accumulated.contains(successor)) {
						todo.add(successor);
					}
				}
			}
		}

		private void addNewSources(Set<V> newSources) {
			for (V source : newSources) {
				numberOfUnvisitedReachablePredecessors.remove(source);
			}
			addReachableBy(newSources, reachable);
		}

		/**
		 * 
		 */
		private Set<V> reachable = new HashSet<V>();
		private Set<V> done = new HashSet<>();
		private Map<V, Long> numberOfUnvisitedReachablePredecessors = new HashMap<>();
		private Map<Long, Set<V>> countToNodes = new HashMap<>();

		private static final boolean RECOUNT = false;

		/**
		 * Node that cannot be returned anymore.
		 */

		private void decrementPredecessorCount(V successor) {
			if (!done.contains(successor)) {
				long newPredecessorCount;
				if (numberOfUnvisitedReachablePredecessors.containsKey(successor)) {
					newPredecessorCount = numberOfUnvisitedReachablePredecessors.get(successor) - 1;
					countToNodes.get(newPredecessorCount + 1).remove(successor);
				} else {
					Set<V> preds = _nodeMap.get(successor).directPredecessors();
					long reachablePredecessors = preds.stream()
							.filter(p -> (!done.contains(p) && reachable.contains(p))).count();
					newPredecessorCount = reachablePredecessors - 1;
				}
				if (!countToNodes.containsKey(newPredecessorCount)) {
					countToNodes.put(newPredecessorCount, new HashSet<>());
				}
				countToNodes.get(newPredecessorCount).add(successor);
				numberOfUnvisitedReachablePredecessors.put(successor, newPredecessorCount);
			}
		}

		private void decrementSuccessorCounts(V source) {
			Set<V> successorsOfSource = _nodeMap.get(source).directSuccessors();
			for (V successor : successorsOfSource) {
				decrementPredecessorCount(successor);
			}
		}

		public V next(Set<V> additionalSources) {
			// The origins do not have predecessors that still need to be followed.
			Set<V> newSources = additionalSources.stream().filter(s -> !done.contains(s)).collect(Collectors.toSet());

			if (!newSources.isEmpty()) {
				addNewSources(newSources);
				for (V source : newSources) {
					decrementSuccessorCounts(source);
				}
				done.addAll(newSources);
			}
			Set<V> candidates = countToNodes.get(0l);
			if (candidates == null || candidates.isEmpty()) {
				return null;
			} else {
				Iterator<V> iterator = candidates.iterator();
				V result = null;
				while (iterator.hasNext() && result == null) {
					result = iterator.next();
					iterator.remove();
					done.add(result);
					// If the candidate has been removed in the mean time, we do not return it.
					if (!_nodeMap.containsKey(result)) {
						result = null;
					}
				}
				return result;
			}
		}
	}

	public IncrementalTopologicalReachabilityIterator incrementalTopologicalReachabilityIterator() {
		return new IncrementalTopologicalReachabilityIterator();
	}

	/**
	 * PERFORMANCE: This does not do an incremental topological sort. It starts from
	 * scratch every time.
	 * 
	 * @param visited
	 * @param origins
	 * @return
	 */
	public V next(Set<V> visited, Set<V> origins) {
		List<V> todo = new ArrayList<V>(origins);
		Map<V, Integer> numberOfUnvisitedPredecessors = new HashMap<>();
		// The origins do not have predecessors that still need to be followed.
		for (V v : origins) {
			numberOfUnvisitedPredecessors.put(v, 0);
		}
		Set<V> done = new HashSet<>(origins);

		while (!todo.isEmpty()) {
			V current = todo.remove(todo.size() - 1);
			done.add(current);
			for (Node<V> node : _nodeMap.get(current).directSuccessorNodes()) {
				V object = node.object();
				int count;
				if (numberOfUnvisitedPredecessors.containsKey(object)) {
					count = numberOfUnvisitedPredecessors.get(object) - 1;
				} else {
					count = node.numberOfPredecessorEdges() - 1;
				}

				if (count == 0) {
					if (!visited.contains(object)) {
						return object;
					}
				}
				numberOfUnvisitedPredecessors.put(object, count);

				if (!done.contains(object)) {
					todo.add(object);
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
//		test(6);
//		main3();
		unlabeled(5);
	}

	public static void unlabeled(int n) {
		long start = System.nanoTime();
		// Each element is a list of ints that encode the predecessor bit vector.
		List<int[]> graphs = new ArrayList<>();
		graphs.add(new int[] {0});
		for (int i = 1; i < n; ++i) {
			// for each level i we compute graphs of size i + 1 vertices.
			// by the i-th vertex, which we will add can have i predecessors.
			List<int[]> graphsWithExtraNode = new ArrayList<>();
//			for (int p = 0; p <= i; p++) {
				// we compute all graphs in which an existing graph is expanded by node i
				for (int[] graph: graphs) {
					// We check if we can add node i with p predecessors to the graph
					int max = (int)Math.pow(2, i);
					for (int adjacencyList = 0; adjacencyList < max; adjacencyList++) {
						boolean add = true;
						// We check if there is a node that has a adjacency list that is a proper subset of
						// the adjacencyList of the new node.
						for (int nodeIndex = 0; add && nodeIndex < i; nodeIndex++) {
							int nodeAdjacencyList = graph[nodeIndex];
							boolean subset = (adjacencyList | nodeAdjacencyList) == adjacencyList;
							boolean different = adjacencyList != nodeAdjacencyList;
							boolean noSelfOrBackReference = adjacencyList < (1 << nodeIndex);
							if (subset && different && noSelfOrBackReference) {
								// we cannot add the adjancencyList
								add = false;
							}
						}
						if (add) {
							int[] expandedGraph = new int[i + 1];
							System.arraycopy(graph, 0, expandedGraph, 0, i);
							expandedGraph[i] = adjacencyList;
							graphsWithExtraNode.add(expandedGraph);
						}
					}
				} // end graph : graphs
				graphs = graphsWithExtraNode;
				graphsWithExtraNode = new ArrayList<>();
//			}
		}
		long end = System.nanoTime();
		System.out.println("Found " + graphs.size() + " graphs.");
		System.out.println((end - start) / 1_000_000 + "ms");
//		graphs.forEach(g -> {
//			for (int i = 0; i < g.length; i++) {
//				System.out.print(Integer.toBinaryString(g[i])+ ",");
//			}
//			System.out.println();
//		});
		
	}
	
	public static void main3() {
		Graph<Integer> graph = new Graph<>(new UniEdgeFactory<>());
		int N = 10000;
		Set<Integer> modified = new HashSet<>();
		for (int i = 0; i < N - 1; i++) {
			int target = N + i;

			graph.addNode(i);
			graph.addNode(target);
			graph.addEdge(i, target);

			modified.add(i);
		}

		long start = System.nanoTime();
		Stopwatch stopwatch = Stopwatch.createUnstarted();
		List<Integer> order = new ArrayList<>();
		Integer next = -1;
		Graph<Integer>.IncrementalTopologicalReachabilityIterator iterator = graph
				.incrementalTopologicalReachabilityIterator();
		stopwatch.start();
		for (int i = 0; i < N - 1; i++) {
			next = iterator.next(modified);
			modified.clear();
			if (next != null) {
				order.add(next);
				modified.add(next);
			}
		}
		stopwatch.stop();
		long end = System.nanoTime();
		order.forEach(e -> System.out.print(e + ","));
		System.out.println();
		System.out.println("Nodes: " + graph.nbNodes());
		System.out.println("Edge: " + graph.nbEdges());
		System.out.println((end - start) / 1_000_000 + "ms");
	}

	public static void test(int n) {
		boolean fixMinusOne = false;
		boolean dynamicOne = true;
		long graphCount = 0;
		long nSelectMax = (long) Math.pow(2, dynamicOne ? n + 1 : n);
		long edgesMax = (long) Math.pow(2, ((n-1) * (n - 2)) / 2);
		long startTime = System.nanoTime();
		for (long sourceNode = 0; sourceNode < nSelectMax; sourceNode++) {
//			System.out.print("Modified: ");
			Set<Integer> originalModified = new HashSet<>();
			if (fixMinusOne) {
				originalModified.add(-1);
			}
			for (int i = 0; i < (dynamicOne ? n + 1 : n) ; i++) {
				if ((sourceNode & (1L << i)) != 0) {
					originalModified.add(dynamicOne ? i - 1 : i);
				}
			}
//			System.out.println();

			for (int numberOfEdgesFromZero = 0; numberOfEdgesFromZero < n; numberOfEdgesFromZero++) {
				for (long edgeIterator = 0; edgeIterator < edgesMax; edgeIterator++) {
					Set<Integer> modified = new HashSet<>(originalModified);
					Graph<Integer> graph = new Graph<>(new UniEdgeFactory<>());
					for (int i = (fixMinusOne || dynamicOne) ? -1 : 0; i < n; i++) {
						graph.addNode(i);
					}
//				StringBuilder builder = new StringBuilder();
//				builder.append("Edges: (" + edgeIterator + ")");
					int offset = 0;
					for (int start = 0; start < n; start++) {
						if (fixMinusOne || dynamicOne) {
						  graph.addEdge(-1, start);
						}
						if (start == 0) {
							for (int i = 0; i < numberOfEdgesFromZero; i++) {
								graph.addEdge(0, i + 1);
							}
						} else {
							for (int end = start + 1; end < n; end++) {
								if ((edgeIterator & (1l << (end - start - 1 + offset))) != 0) {
									graph.addEdge(start, end);
//							builder.append(start + "->" + end + ",");
								}
							}
							offset += (n - 1 - start);
						}
					}

//				System.out.println(builder.toString());

					List<Integer> order = new ArrayList<>();
					Integer next = -2;
					Graph<Integer>.IncrementalTopologicalReachabilityIterator iterator = graph
							.incrementalTopologicalReachabilityIterator();

					while (next != null) {
						next = iterator.next(modified);
						modified.clear();
						if (next != null) {
							order.add(next);
							modified.add(next);
						}
					}
					int size = order.size();
					for (int i = 0; i < size - 1; i++) {
						if (order.get(i) >= order.get(i + 1)) {
							throw new Error();
						}
					}
					graphCount++;
//					order.forEach(v -> System.out.print(v+","));
//					System.out.println();

				}
			}
//			System.out.println("--------");
		}
		long endTime = System.nanoTime();
		System.out.println("Process " + graphCount + " graphs.");
		System.out.println((endTime - startTime) / 1_000_000 + "ms");
	}

	public static void main2() {
		Graph<Integer> graph = new Graph<>();
		int N = 4000;
		Set<Integer> modified = new HashSet<>();
		Set<Integer> visited = new HashSet<>();
		for (int i = 0; i < N - 1; i++) {
			int target = N + i;

			graph.addNode(i);
			graph.addNode(target);
			graph.addEdge(i, target);

			modified.add(i);
			visited.add(i);
		}

		List<Integer> order = new ArrayList<>();
		Integer next = -1;
		long start = System.nanoTime();
		for (int i = 0; i < N - 1; i++) {
			next = graph.next(visited, modified);
			order.add(next);
			if (next != null) {
				visited.add(next);
				modified.add(next);
			}
		}
		long end = System.nanoTime();
		order.forEach(e -> System.out.print(e + ","));
		System.out.println();
		System.out.println("Nodes: " + graph.nbNodes());
		System.out.println("Edge: " + graph.nbEdges());
		System.out.println((end - start) / 1_000_000 + "ms");
	}

	public <E extends Exception> void traverseAll(
			org.aikodi.rejuse.function.Consumer<? super V, ? extends E> nodeAction,
			org.aikodi.rejuse.function.Consumer<? super Edge<V>, ? extends E> edgeAction) throws E {
		LinkedList<Node<V>> todo = new LinkedList<>();
		todo.addAll(_nodeMap.values());

		// We save some work by not tracking which
		// node are already visited since they are all present
		// to start with. Therefore the code is not shared.
		Set<Edge<V>> traversedEdges = new HashSet<>();
		while (!todo.isEmpty()) {
			Node<V> current = todo.removeFirst();
			nodeAction.accept(current.object());
			for (Edge<V> edge : current.successorEdges()) {
				if (!traversedEdges.contains(edge)) {
					edgeAction.accept(edge);
					traversedEdges.add(edge);
				}
			}
		}

	}

	public Set<Edge<V>> edges() {
		Set<Edge<V>> result = new HashSet<>();
		for (Node<V> node : nodes()) {
			result.addAll(node.successorEdges());
		}
		return result;
	}

	public int nbEdges() {
		int result = 0;
		for (Node<V> node : nodes()) {
			result += node.numberOfSuccessorEdges();
		}
		return result;
	}

	public <E extends Exception> void filter(Predicate<? super Edge<V>, E> predicate) throws E {
		for (Edge<V> edge : edges()) {
			if (!predicate.eval(edge)) {
				edge.terminate();
			}
		}
	}

	public void removeEdgesNotInvolvedInCycles() {
		Set<Node<V>> todo = nodes();
		boolean prune = true;
		while (prune) {
			Node<V> toPrune = null;
			Iterator<Node<V>> todoIterator = todo.iterator();
			while (todoIterator.hasNext()) {
				Node<V> node = todoIterator.next();
				todoIterator.remove();
				if (node.numberOfPredecessorEdges() == 0 || node.numberOfSuccessorEdges() == 0) {
					toPrune = node;
					break;
				}
			}
			if (toPrune != null) {
				todo.addAll(toPrune.directSuccessorNodes());
				todo.addAll(toPrune.directPredecessorNodes());
				// Remove again if there is a self link.
				todo.remove(toPrune);
				removeNode(toPrune.object());
			} else {
				// Stop if nothing has been removed.
				prune = false;
			}
		}
	}

	public Map<V, List<V>> reachabilityMap() {
		Map<V, List<V>> result = new HashMap<>();
		List<Node<V>> nodes = new ArrayList<Node<V>>(_nodeMap.values());
		int size = nodes.size();
		Map<Node<V>, Integer> indexMap = new HashMap<>();
		for (int i = 0; i < size; i++) {
			indexMap.put(nodes.get(i), i);
		}

		boolean[][] adjacency = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			adjacency[i][i] = true;
		}
		for (int i = 0; i < size; i++) {
			Node<V> node = nodes.get(i);
			List<Node<V>> directSuccessorNodes = node.outgoings();
			for (Node<V> adj : directSuccessorNodes) {
				adjacency[i][indexMap.get(adj)] = true;
			}
		}
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					adjacency[i][j] = adjacency[i][j] || (adjacency[i][k] && adjacency[k][j]);
				}
			}
		}

		for (int nodeNumber = 0; nodeNumber < size; nodeNumber++) {
			Node<V> node = nodes.get(nodeNumber);
			List<V> adjacent = new ArrayList<>();
			for (int connectedIndex = 0; connectedIndex < size; connectedIndex++) {
				if (adjacency[nodeNumber][connectedIndex]) {
					adjacent.add(nodes.get(connectedIndex).object());
				}
			}
			result.put(node.object(), adjacent);
		}
		return result;
	}

	/**
	 * Return all simple cycles in this graph. If there are multiple edges between
	 * nodes, separate paths will be generated for each edges if they are in a loop.
	 * Use {@link Graph#simpleCycles()} to restrict the algorithm purely to nodes.
	 * 
	 * The implementation uses the algorithm of Szwarcfiter and Lauer, which
	 * computes all simply cycles in O(N+E*(C+1)) where N is the number of nodes, E
	 * is the number of edges, and C is the number of simple cycles.
	 * 
	 * The cycles are computed by constructing a graph G' in which the edges of this
	 * graph are also represented as nodes. Because each edge is now a separate node
	 * that is connected with single edges to its nodes, this automatically
	 * separates the cycles. When a path is found, it is constructed using the edges
	 * whose nodes are in G'.
	 * 
	 * @return A non-null list with all simple cycles in this graph.
	 */
	public List<Path<V>> simpleCycles() {
		Map<Node<V>, GCycleNode<V>> map = new HashMap<>();
		Set<Node<V>> nodes = nodes();
		nodes.forEach(n -> {
			GCycleNode<V> cycleNode = new GCycleNode<>(n);
			map.put(n, cycleNode);
		});
		nodes.forEach(c -> {
			List<EdgeNode<V>> edgeNodes = new ArrayList<>();
			c.successorEdges().forEach(e -> {
				EdgeNode<V> edgeNode = new EdgeNode<>(e);
				edgeNodes.add(edgeNode);
				List<GCycleNode<V>> next = new ArrayList<>();
				next.add(map.get(e.endFor(c)));
				edgeNode.setA(next);
			});
			map.get(c).setA(edgeNodes);
		});
		List<Path<V>> result = new ArrayList<>();
		IndexedStack<GCycleNode<V>> stack = new IndexedStack<>();
		IndexedStack<EdgeNode<V>> edges = new IndexedStack<>();
		nodes.forEach(n -> map.get(n).cycle(0, stack, result, edges));
		return result;
	}

	private static class EdgeNode<V> extends GCycleNode<V> {

		public EdgeNode(Edge<V> node) {
			super(node);
		}

		public Edge<V> edge() {
			return (Edge<V>) object();
		}

		@Override
		protected void pop(IndexedStack<EdgeNode<V>> edgeNodes) {
			edgeNodes.pop();
		}

		@Override
		protected void push(IndexedStack<EdgeNode<V>> edgeNodes) {
			edgeNodes.push(this);
		}

		protected Path<V> createPath(IndexedStack<GCycleNode<V>> stack, GCycleNode<V> w,
				IndexedStack<EdgeNode<V>> edgeNodes) {
			Path<V> path = new Path((Node<V>) w.object());
			int start = edgeNodes.index((EdgeNode<V>) stack.at(stack.index(w) + 1));
			for (int i = start; i < edgeNodes.size(); i++) {
				path.addEdge(edgeNodes.at(i).edge());
			}
			return path;
		}
	}

	private static class GCycleNode<V> {
		private Object _node;
		private boolean _mark;
		private Set<GCycleNode<V>> _B = new HashSet<>();
		private Set<GCycleNode<V>> _A = new HashSet<>();
		private long _position;
		private boolean _reach;

		public GCycleNode(Object node) {
			_node = node;
		}

		protected void setA(Collection<? extends GCycleNode<V>> set) {
			_A = new HashSet<>(set);
		}

		@Override
		public String toString() {
			return object().toString();
		}

		public void unmark() {
			_mark = false;
			_B.forEach(y -> {
				y.addA(this);
				if (y.marked()) {
					y.unmark();
				}
			});
			_B.clear();
		}

		private void mark() {
			_mark = true;
		}

		public boolean cycle(int q, IndexedStack<GCycleNode<V>> stack, List<Path<V>> paths,
				IndexedStack<EdgeNode<V>> edgeNodes) {
			// v == this;
			boolean f = false;
			mark();
			stack.push(this);
			push(edgeNodes);
			int t = stack.size();
			_position = t;
			if (!reach()) {
				q = t;
			}

			Iterator<GCycleNode<V>> iterator = _A.iterator();
			while (iterator.hasNext()) {
				GCycleNode<V> w = iterator.next();
				if (!w.marked()) {
					boolean result = w.cycle(q, stack, paths, edgeNodes);
					if (result) {
						f = true;
					} else {
						w.addB(this);
						iterator.remove();
					}
				} else {
					if (w.position() <= q) {
						// output cycle
						Path<V> path = createPath(stack, w, edgeNodes);
						paths.add(path);
						f = true;
					} else {
						w.addB(this);
						iterator.remove();
					}
				}
			}
			;
			stack.pop();
			pop(edgeNodes);
			if (f) {
				unmark();
			}
			setReach();
			_position = Long.MAX_VALUE;
			return f;
		}

		protected void push(IndexedStack<EdgeNode<V>> edgeNodes) {
		}

		protected void pop(IndexedStack<EdgeNode<V>> edgeNodes) {
		}

		protected Path<V> createPath(IndexedStack<GCycleNode<V>> stack, GCycleNode<V> w,
				IndexedStack<EdgeNode<V>> edgeNodes) {
			throw new Error();
		}

		public Object object() {
			return _node;
		}

		private long position() {
			return _position;
		}

		private boolean reach() {
			return _reach;
		}

		private void setReach() {
			_reach = true;
		}

		public boolean marked() {
			return _mark;
		}

		private void addA(GCycleNode<V> node) {
			_A.add(node);
		}

		private void addB(GCycleNode<V> node) {
			_B.add(node);
		}

	}

	/**
	 * Return all simple cycles in this graph <b>when only considering adjacency of
	 * nodes</b>. If there are multiple edges between nodes, no separate paths will
	 * be returned. A path consists purely of a sequence of nodes. Use
	 * {@link Graph#simpleCycles()} to also involve the edges.
	 * 
	 * The implementation uses the algorithm of Szwarcfiter and Lauer, which
	 * computes all simply cycles in O(N+E*(C+1)) where N is the number of nodes, E
	 * is the number of edges, and C is the number of simple cycles.
	 * 
	 * @return A non-null list with all simple cycles in this graph.
	 */
	public List<List<V>> simpleCyclesByNodes() {
		List<CycleNode<V>> nodes = new ArrayList<>();
		Map<Node<V>, CycleNode<V>> map = new HashMap<>();
		nodes().forEach(n -> {
			CycleNode<V> cycleNode = new CycleNode<>(n);
			nodes.add(cycleNode);
			map.put(n, cycleNode);
		});
		nodes.forEach(c -> c.init(map));
		List<List<V>> result = new ArrayList<>();
		IndexedStack<CycleNode<V>> stack = new IndexedStack<>();
		nodes.forEach(n -> n.cycle(0, stack, result));
		return result;
	}

	private static class CycleNode<V> {
		private Node<V> _node;
		private boolean _mark;
		private Set<CycleNode<V>> _B = new HashSet<>();
		private Set<CycleNode<V>> _A = new HashSet<>();
		private long _position;
		private boolean _reach;

		public CycleNode(Node<V> node) {
			_node = node;
		}

		private void init(Map<Node<V>, CycleNode<V>> map) {
			node().directSuccessorNodes().forEach(s -> {
				_A.add(map.get(s));
			});
		}

		public void unmark() {
			_mark = false;
			_B.forEach(y -> {
				y.addA(this);
				if (y.marked()) {
					y.unmark();
				}
			});
			_B.clear();
		}

		private void mark() {
			_mark = true;
		}

		public boolean cycle(int q, IndexedStack<CycleNode<V>> stack, List<List<V>> paths) {
			boolean f = false;
			mark();
			stack.push(this);
			int t = stack.size();
			_position = t;
			if (!reach()) {
				q = t;
			}
			Iterator<CycleNode<V>> iterator = _A.iterator();
			while (iterator.hasNext()) {
				CycleNode<V> w = iterator.next();
				if (!w.marked()) {
					boolean result = w.cycle(q, stack, paths);
					if (result) {
						f = true;
					} else {
						w.addB(this);
						iterator.remove();
					}
				} else {
					if (w.position() <= q) {
						// output cycle
						List<V> path = createPath(stack, w);
						paths.add(path);
						f = true;
					} else {
						w.addB(this);
						iterator.remove();
					}
				}
			}
			;
			stack.pop();
			if (f) {
				unmark();
			}
			setReach();
			_position = Long.MAX_VALUE;
			return f;
		}

		private List<V> createPath(IndexedStack<CycleNode<V>> stack, CycleNode<V> w) {
			List<V> path = new ArrayList<>();
			path.add(node().object());
			int end = stack.index(this);
			int start = stack.index(w);
			for (int i = start; i <= end; i++) {
				path.add(stack.at(i).node().object());
			}
			return path;
		}

		private Node<V> node() {
			return _node;
		}

		private long position() {
			return _position;
		}

		private boolean reach() {
			return _reach;
		}

		private void setReach() {
			_reach = true;
		}

		public boolean marked() {
			return _mark;
		}

		private void addA(CycleNode<V> node) {
			_A.add(node);
		}

		private void addB(CycleNode<V> node) {
			_B.add(node);
		}

	}

	public List<Path<V>> inefficientCycles() {
		List<Path<V>> result = new ArrayList<>();
		Set<Node<V>> done = new HashSet<>();
		Set<Node<V>> all = nodes();
		for (Node<V> node : all) {
			accumulate(result, new Path<V>(node), done, new HashSet<>(), new HashSet<>());
			done.add(node);
		}
		return result;
	}

	private void accumulate(List<Path<V>> paths, Path<V> current, Set<Node<V>> done, Set<Node<V>> cannotIntroduceCycle,
			Set<Node<V>> introduceCycle) {
		Node<V> last = current.end();
		for (Edge<V> edge : last.successorEdges()) {
			Node<V> destination = edge.endFor(last);
			if (!(done.contains(destination) || cannotIntroduceCycle.contains(destination))) {
				Path<V> newPath = current.clone();
				newPath.addEdge(edge);
				if (current.start().equals(destination)) {
					paths.add(newPath);
					introduceCycle.addAll(newPath.nodes());
				} else if (!current.visits(destination)) {

					accumulate(paths, newPath, done, cannotIntroduceCycle, introduceCycle);
				} else if (current.visits(destination)) {

				}
			}
		}
	}

	private static class IndexedStack<T> {
		private List<T> _stack = new ArrayList<>();
		private Map<T, Integer> _indices = new HashMap<>();

		public int index(T t) {
			return _indices.get(t);
		}

		public void push(T t) {
			_indices.put(t, _stack.size());
			_stack.add(t);
		}

		public T peek() {
			return _stack.get(_stack.size() - 1);
		}

		public void pop() {
			_indices.remove(peek());
			_stack.remove(_stack.size() - 1);
		}

		public T at(int index) {
			return _stack.get(index);
		}

		public int size() {
			return _indices.size();
		}

	}

}