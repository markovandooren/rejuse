package org.aikodi.rejuse.data.graph;

import org.aikodi.contract.Contract;

import java.util.*;
import java.util.stream.Collectors;

public interface INode<N extends INode<N, E, T>, E extends IEdge<E, N, T>, T> {

    T object();

    /**
     * Return all edges starting in this node.
     */
   /*@
     @ public behavior
     @
     @ post \result != null
     @ post ! \result.contains(null);
     @ post (\forall Edge e; e != null; \result.contains(e) == e.startsIn(this));
     @*/
    Set<E> successorEdges();

    /**
     * Return the number of successor edges.
     *
     * @return The size of the collection of successor edges.
     */
    int numberOfSuccessorEdges();

    /**
     * Return the direct sucessor nodes.
     *
     * @return A set containing the ends of the successor edges for this node,
     * when starting from this node.
     */
    default Set<N> directSuccessorNodes() {
        return successorEdges().stream().map(edge -> edge.endFor((N) this)).collect(Collectors.toSet());
    }

    /**
     * Return the direct successors.
     *
     * @return A set containing the objects of the direct successor nodes.
     */
    default Set<T> directSuccessors() {
        return directSuccessorNodes().stream().map(node -> node.object()).collect(Collectors.toSet());
    }

    /**
     * Check whether or not this node is a leaf node.
     */
   /*@
     @ public behavior
     @
     @ post \result == getStartEdges().isEmpty();
     @*/
    default boolean isLeaf() {
        return successorEdges().isEmpty();
    }

    /**
     * Return all edges ending in this node.
     */
    Set<E> predecessorEdges();

    /**
     * Return the number of predecessor edges.
     *
     * @return The size of the collection of predecessor edges.
     */
    int numberOfPredecessorEdges();

    /**
     * Return the direct predecessor nodes.
     *
     * @return A set containing the starts of the predecessor edges for this node,
     * when ending in this node.
     */
    default Set<N> directPredecessorNodes() {
        return predecessorEdges().stream().map(edge -> edge.startFor((N) this)).collect(Collectors.toSet());
    }

    /**
     * Return the direct predecessor.
     *
     * @return A set containing the objects of the direct predecessor nodes.
     */
    default Set<T> directPredecessors() {
        return directPredecessorNodes().stream().map(node -> node.object()).collect(Collectors.toSet());
    }

    /**
     * Return all edges connected to this node
     */
   /*@
     @ public behavior
     @
     @ post \result != null;
     @ post \result.containsAll(getStartEdges());
     @ post \result.containsAll(getEndEdges());
     @ post (\forall Object o; result.contains(o);
     @        getStartEdges().contains(o) || getEndEdges().contains(o));
     @*/
    Set<E> edges();

    /**
     * Check if this node can reach the given target.
     *
     * @param target The target.
     *               The target cannot be null.
     *
     * @return True if and only if the given target is the object of
     *         a node that can be reached from this node.
     */
    default boolean canReach(T target) {
        Contract.requireNotNull(target);

        Set<N> done = new HashSet<>();
        List<N> todo = new ArrayList<>();
        todo.add((N)this);
        while (!todo.isEmpty()) {
            N current = todo.get(todo.size() - 1);
            if (!done.contains(current)) {
                done.add(current);
                if (current.object().equals(target)) {
                    return true;
                }
                todo.remove(todo.size() - 1);
                todo.addAll(current.directSuccessorNodes());
            } else {
                todo.remove(todo.size() - 1);
            }
        }
        return false;
    }

    /**
     * Check if this node can reach the given target node.
     *
     * @param targetNode The target node.
     *                   The target node cannot be null.
     *
     * @return True if and only if the given target node
     *         is a direct or indirect successor of this node.
     */
    default boolean canReachNode(N targetNode) {
        Contract.requireNotNull(targetNode);

        Set<N> done = new HashSet<>();
        List<N> todo = new ArrayList<>();
        todo.add((N)this);
        while (!todo.isEmpty()) {
            N current = todo.get(todo.size() - 1);
            if (!done.contains(current)) {
                done.add(current);
                if (current.equals(targetNode)) {
                    return true;
                }
                todo.remove(todo.size() - 1);
                todo.addAll(current.directSuccessorNodes());
            } else {
                todo.remove(todo.size() - 1);
            }
        }
        return false;
    }


}
