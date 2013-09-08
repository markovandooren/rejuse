package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.java.collections.SkipList;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;
import be.kuleuven.cs.distrinet.rejuse.java.comparator.ComparableComparator;

public class WeightedGraph<V> extends Graph<V> {

	public WeightedGraph(NodeFactory<V> nodeFactory, WeightedEdgeFactory<V> edgeFactory) {
		super(nodeFactory, edgeFactory);
	}
	
  public Edge<V> addEdge(V first, V second, double weight) {
  	return ((WeightedEdgeFactory<V>)edgeFactory()).createEdge(node(first), node(second), weight);
  }
  
  /**
   * Return the shortest path to the given node.
   *  
   * @param other
   *        The destination.
   */
 /*@
   @ public behavior
   @
   @ pre node != null;
   @*/
  public Path<V> distance(V start, V end) {
  	Node<V> endNode = node(end);
  	//TODO first version only uses the weight of the edges, no
  	// weightfunction

  	// The list of nodes we have already calculated, together with their
  	// shortest path from this node.
  	final Map<Node<V>,Path<V>> done = new HashMap<>();
  	// The list of adjacent nodes that remain to be done.
  	SkipList<Path<V>> adjacent = new SkipList<>(new ComparableComparator());
  	//final TreeSet newAdjacent = new TreeSet(new ComparableComparator());
  	final HashMap<Node<V>,Path<V>> temp = new HashMap<>();
  	adjacent.add(new Path<V>(node(start)));

  	while((! done.containsKey(endNode)) && (! adjacent.isEmpty())) {
  		// search the node closest to the 'done' nodes
  		final Path<V> closest = adjacent.first();
  		final Node<V> latest = closest.getEnd();
  		// Move its shortest path from 'adjacent' to 'done'      
  		adjacent.remove(closest);
  		done.put(closest.getEnd(), closest);

  		Set<Edge<V>> startingFromLatest = closest.getEnd().outgoingEdges();

  		for(Edge<V> edge: startingFromLatest) {
				if(! done.containsKey(edge.endFor(latest))) {
					Path<V> path = closest.clone();
					path.addEdge(edge);
					temp.put(path.getEnd(), path);
				}
  		}
  		// 1) add the paths adjacent to the path we just added.

  		// 2) remove from both 'temp' and 'adjacent' the paths for which a
  		//    shorter path exists.

  		Iterator<Path<V>> iter = adjacent.iterator();
  		while(iter.hasNext()) {
  			Path<V> path = iter.next();
  			// If the destination can also be reached using a path in temp,
  			// remove the longest path.
  			Node<V> destination = path.getEnd(); 
  			if(temp.containsKey(destination)) {
  				Path<V> alternative = temp.get(destination);
  				if(alternative.compareTo(path) < 0) {
  					iter.remove();
  				}
  				else {
  					temp.remove(destination);
  				}
  			}
  		}

  		// 3) merge both sets
  		adjacent.addAll(temp.values());

  		// Clear temp
  		temp.clear();
  	}
  	Path<V> result = done.get(endNode);
  	if(result == null) {
  		throw new NoSuchElementException();
  	}
  	else {
  		return result;
  	}
  }

}
