package org.aikodi.rejuse.data.graph;

/**
 * An interface for edges in a graph.
 *
 * @param <E> The self type of the edges.
 * @param <N> The self type of the nodes.
 * @param <T> The type of the objects in the graph.
 */
public interface IEdge<E extends IEdge<E, N, T>, N extends INode<N, E, T>, T> {

    /**
     * Return the node from which the given node is reached after
     * traversing this edge.
     *
     * @param end The end node.
     */
    N startFor(N end);

    /**
     * Return the node that is reach when this edge is traversed starting
     * from the given node.
     *
     * @param end The start node.
     */
    N endFor(N end);

}
