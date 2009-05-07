package org.rejuse.java.collections;


import java.util.Set;
import java.util.HashSet;
import java.util.ConcurrentModificationException;

/**
 * <p>An operator that calculates the transitive closure of a graph of objects.</p>
 * 
 * <center>
 *   <img src="doc-files/ForAll.png"/>
 * </center>
 *
 *<p>Objects of this class calculate the transitive closure of a graph,
 * defined by <code>public Set getConnectedNodes(Object node)</code>,
 * starting from a set of nodes or a single start node.</p>
 *
 * @author  Jan Dockx
 * @author  Marko van Dooren
 */
public abstract class TransitiveClosure<T> {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>This method needs to be implemented by subclasses, so that it returns
   * the nodes connected to <node> under the definition of the graph
   * for which we want the transitive closure.</p>
   *
   * @param  node
   *         The node to get the connected nodes for.
   */
 /*@
   @ // The given node may not be null
   @ pre node != null;
   @
   @ // Never returns null.
   @ post \result != null;
   @ // Never contains null.
   @ post (\forall Object o; \result.contains(o); o != null);
   @*/
  public abstract /*@ pure @*/ Set<T> getConnectedNodes(T node);
  
  /**
   * Transitive closure is a recursive definition. The recursive post condition below
   * returns true for all sets that contain the actual transitive closure, e.g., also
   * for the set of all objects. We want the smallest set (Least Fixed Point) of the
   * family of naive closure sets as result of closureFromAll. This extra requirement
   * is added in the post condition of that method.
   *
   * @see #closureFromAll
   */
  /*@
    @ pre startSet != null;
    @ pre naiveClosureSet != null;
    @
    @ post \result == (\forall Object o; startSet.contains(o);
    @                    (naiveClosureSet.contains(o) && 
    @                       (\exists Set ccn; isNaiveClosure(getConnectedNodes(o), ccn);
    @                             naiveClosureSet.containsAll(ccn))));
    @*/
  final public /*@ pure @*/ boolean isNaiveClosure(Set<T> startSet, Set<T> naiveClosureSet) {
    return naiveClosureSet.containsAll(closureFromAll(startSet));
  } 

  /**
   * <p>The transitive closure, starting from <startSet>, defined by
   * <code>public Set getConnectedNodes(Object node)</code>.</p>
   *
   * @param  startSet
   *         The set of nodes to start the transitive closure from.
   *         This set is not changed.
   */
 /*@
   @ // The given set may not be null
   @ pre startSet != null;
   @ // The given set may not contain null references.
   @ pre (\forall Object o ; startSet.contains(o) ; o != null);
   @
   @ // The result is effective.
   @ post \result != null;
   @ post \fresh(\result);
   @ // The transitive closure is the set of all nodes that can be reached from
   @ // a start set of nodes via connections defined by
   @ // <code>public Set getConnectedNodes(Object node)</code>.
   @ post isNaiveClosure(startSet, \result);
   @ // Least Fixed Point
   @ post (\forall Set cn;
   @          (cn != null) && isNaiveClosure(startSet, cn);
   @          (\result.size() <= cn.size()));
   @ post_redundantly (\forall Object o; \result.contains(o); o != null);
   @
	 @ signals (ConcurrentModificationException)
	 @         (* The collection was modified while calculating the transitive closure. *);
   @*/
  final public /*@ pure @*/ Set<T> closureFromAll(Set<T> startSet) throws ConcurrentModificationException {
    Set<T> result = new HashSet<T>();
    Set<T> newNodes = startSet;
    while (! newNodes.isEmpty()) {
      result.addAll(newNodes); // the new nodes need to be in the result set
      /* for each of the new nodes, collect their connected nodes, put them
          together, and find out which are new */
      newNodes = new Accumulator<T,Set<T>>() {
                            public Set<T> initialAccumulator() {
                              return new HashSet<T>();
                            }
                            public Set<T> accumulate(T element, Set<T> acc) {
                              acc.addAll(getConnectedNodes(element));
                              return acc;
                            }
                          }.accumulate(newNodes);
      newNodes.removeAll(result); // we already processed those
    }    
    return result;
  }
  
  /**
   * <p>The transitive closure, starting from <startNode>, defined by
   * <code>public Set getConnectedNodes(Object node)</code>.</p>
   *
   * @param  startNode
   *         The node to start the transitive closure from.
   */
 /*@
   @ // The given node may not be null.
   @ pre startNode != null ;
	 @
   @ post \result.equals(closureFromAll(new Singleton(startNode)));
   @
	 @ signals (ConcurrentModificationException) 
	 @         (* The collection was modified while calculating the transitive closure. *);
	 @*/
  final public /*@ pure @*/ Set<T> closure(T startNode) throws ConcurrentModificationException {
    Set<T> singleton = new HashSet<T>();
    singleton.add(startNode);
    return closureFromAll(singleton);
  }
}