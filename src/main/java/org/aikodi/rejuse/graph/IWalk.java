package org.aikodi.rejuse.graph;

import java.util.List;

/**
 * A class walk walks in a graph.
 * 
 * @author Marko van Dooren
 *
 * @param <N>
 *            The self type of the nodes.
 * @param <E>
 *            The self type of the edges.
 * @param <T>
 *            The type of the elements in the graph.
 */
public interface IWalk<N extends INode<N, E, T>, E extends IEdge<E, N, T>, T> {

	/**
	 * Return the last node of this path.
	 */
	default N start() {
		return node(0);
	}

	/**
	 * Return the last node of this path.
	 */
	default N end() {
		return node(length());
	}

	/**
	 * Return the length of this walk.
	 * 
	 * @return The number of edges in this path.
	 */
	default int length() {
		return edges().size();
	}

	/**
	 * Return the list of edges that define this path.
	 * 
	 * @return The result is not null. The result does not contain null.
	 */
	List<E> edges();

	/**
	 * Return the list of nodes that define this path.
	 * 
	 * @return The result is not null. The first node is the start. The node at each
	 *         index i with i > 1 is the node at the end of edge i - 1 when starting
	 *         from node i - 1.
	 */
	List<N> nodes();

	/**
	 * Return the node at the given index.
	 * 
	 * @param index
	 *            The index of the requested node. The index cannot be negative. The
	 *            index cannot be larger than the length of this walk.
	 * @return
	 */
	default N node(int index) {
		if (index < 0 || index > length()) {
			throw new IllegalArgumentException("Index " + index + " is not valid for a walk with length " + length());
		}
		return nodes().get(index);
	}

	/**
	 * Return the index of the given object in this walk.
	 * 
	 * @param node
	 *            The object of which the index is requested. The object cannot be
	 *            null.
	 * @return
	 */
	default int indexOf(T object) {
		N current = start();
		List<E> edges = edges();
		for (int i = 0; i < length(); i++) {
			if (current.object().equals(object)) {
				return i;
			}
			current = edges.get(i).endFor(current);
		}
		return -1;
	}

	/**
	 * Return whether or not this walk is a cycle.
	 * 
	 * @return True if the start equals the end. False otherwise.
	 */
	default boolean isCycle() {
		return start().equals(end());
	}
}
