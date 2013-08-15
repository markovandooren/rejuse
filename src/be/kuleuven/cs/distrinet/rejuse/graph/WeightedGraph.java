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
  	final Map done = new HashMap();
  	// The list of adjacent nodes that remain to be done.
  	SkipList<Path<V>> adjacent = new SkipList<>(new ComparableComparator());
  	//final TreeSet newAdjacent = new TreeSet(new ComparableComparator());
  	final HashMap<Node<V>,Path<V>> temp = new HashMap<>();
  	adjacent.add(new Path<V>(node(start)));

  	while((! done.containsKey(endNode)) && (! adjacent.isEmpty())) {
  		// search the node closest to the 'done' nodes
  		final Path<V> closest = adjacent.first();
  		final Node latest = closest.getEnd();
  		// Move its shortest path from 'adjacent' to 'done'      
  		adjacent.remove(closest);
  		done.put(closest.getEnd(), closest);

  		Set startingFromLatest = closest.getEnd().outgoingEdges();

  		// 1) add the paths adjacent to the path we just added.
  		new Visitor() {
  			public void visit(Object o) {
  				Edge e = (Edge)o;
  				if(! done.containsKey(e.nodeConnectedTo(latest))) {
  					Path path = (Path)closest.clone();
  					path.addEdge((Edge) e);
  					temp.put(path.getEnd(), path);
  				}
  			}
  		}.applyTo(startingFromLatest);

  		// 2) remove from both 'temp' and 'adjacent' the paths for which a
  		//    shorter path exists.

  		Iterator iter = adjacent.iterator();
  		while(iter.hasNext()) {
  			Path path = (Path)iter.next();
  			// If the destination can also be reached using a path in temp,
  			// remove the longest path.
  			Node destination = path.getEnd(); 
  			if(temp.containsKey(destination)) {
  				Path alternative = (Path)temp.get(destination);
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
  	Path result = (Path)done.get(endNode);
  	if(result == null) {
  		throw new NoSuchElementException();
  	}
  	else {
  		return result;
  	}
  }

}
