package org.rejuse.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.rejuse.java.collections.Mapping;
import org.rejuse.java.collections.SafeTransitiveClosure;
import org.rejuse.java.collections.SkipList;
import org.rejuse.java.collections.Visitor;
import org.rejuse.java.comparator.ComparableComparator;

/**
 * @author Marko van Dooren
 */
public class Node {
  
  /**
   * Initialize a new node for the given object.
   * 
   * @param object
   *        The object that is put in the graph.
   */
 /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObject() == object;
   @ post getEdges().isEmpty();
   @*/
  Node(Object object) {
    _object = object;
    _starts = new HashSet();
    _ends = new HashSet();
  }
  
  /**
   * Return the object of this node.
   */
 /*@
   @ public behavior
   @
   @ post \result != null; 
   @*/
  public Object getObject() {
    return _object;
  }
  
  /**
   * The object of this edge.
   */
  private Object _object;
  
  /**
   * Add the given edge to this node.
   * 
   * @param edge
   *        The edge to be added.
   */
 /*@
   @ public behavior
   @
   @ pre edge != null;
   @ // The edge must already haven been connected to this node.
   @ pre edge.startsIn(this) || edge.endsIn(this)
   @
   @ post edge.startsIn(this) ==> getStartEdges().contains(edge)
   @ post edge.endsIn(this) ==> getEndEdges().contains(edge)
   @*/
  void addEdge(Edge edge) {
    if(edge.startsIn(this)) {
      _starts.add(edge);
    }
    if(edge.endsIn(this)) {
      _ends.add(this);
    }
  }
  
  /**
   * Remove the given edge from this node.
   * 
   * @param edge
   *        The edge to be removed.
   */
 /*@
   @ public behavior
   @
   @ post ! getEdges().contains(edge); 
   @*/
  void removeEdge(Edge edge) {
    _starts.remove(edge);
    _ends.remove(edge);
  }
  
  /**
   * Return all edges starting in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result != null
   @ post ! \result.contains(null);
   @ post (\forall Object o; \result.contains(o); o instanceof Edge);
   @ post (\forall Edge e; e != null; \result.contains(e) == e.startsIn(this)); 
   @*/
  public Set getStartEdges() {
    Set result = new HashSet(_starts);
    return result;
  }
  
  /**
   * Return all edges ending in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result != null
   @ post ! \result.contains(null);
   @ post (\forall Object o; \result.contains(o); o instanceof Edge);
   @ post (\forall Edge e; e != null; \result.contains(e) == e.endsIn(this)); 
   @*/
  public Set getEndEdges() {
    Set result = new HashSet(_starts);
    return result;
  }
  
  /**
   * Return the number of edges starting in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getStartEdges().size();
   @*/
  public int getNbStartEdges() {
    return _starts.size();
  }
  
  /**
   * Return the number of edges ending in this node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getEndEdges().size();
   @*/
  public int getNbEndEdges() {
    return _ends.size();
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
  public Set getEdges() {
    Set result = new HashSet(_starts);
    result.addAll(_ends);
    return result;
  }
  
  /**
   * Check whether or not this node is a leaf node.
   */
 /*@
   @ public behavior
   @
   @ post \result == getStartEdges().isEmpty();
   @*/
  public boolean isLeaf() {
    return _starts.isEmpty();
  }
  
  /**
   * The edges starting in this node.
   */
  private Set _starts;
  
  /**
   * The edges ending in this node.
   */
  private Set _ends;
  
  /**
   * Check whether or not the given node is reachable when starting from this
   * node.
   *  
   * @param other
   *        The node to be reached.
   */
 /*@
   @ public behavior
   @
   @ pre node != null;
   @*/ 
  public boolean canReach(Node other) {
    //TODO inefficient, but it works
    return new SafeTransitiveClosure() {
      public Set getConnectedNodes(Object node) {
        return ((Node)node).getDirectlyConnectedNodes();
      }
    }.closure(this).contains(other);
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
  public Path dijkstra(Node node) {
    //TODO first version only uses the weight of the edges, no
    // weightfunction
    
    // The list of nodes we have already calculated, together with their
    // shortest path from this node.
    final Map done = new HashMap();
    // The list of adjacent nodes that remain to be done.
    SkipList adjacent = new SkipList(new ComparableComparator());
    //final TreeSet newAdjacent = new TreeSet(new ComparableComparator());
    final HashMap temp = new HashMap();
    adjacent.add(new Path(this));
    
    while((! done.containsKey(node)) && (! adjacent.isEmpty())) {
      // search the node closest to the 'done' nodes
      final Path closest = (Path)adjacent.getFirst();
      final Node latest = closest.getEnd();
      // Move its shortest path from 'adjacent' to 'done'      
      adjacent.remove(closest);
      done.put(closest.getEnd(), closest);
      
      Set startingFromLatest = closest.getEnd().getStartEdges();
      
      // 1) add the paths adjacent to the path we just added.
      new Visitor() {
        public void visit(Object o) {
          Edge e = (Edge)o;
          if(! done.containsKey(e.getEndFor(latest))) {
            Path path = (Path)closest.clone();
            path.addEdge(e);
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
    Path result = (Path)done.get(node);
    if(result == null) {
      throw new NoSuchElementException();
    }
    else {
      return result;
    }
  }
  
 /*@
   @ also public behavior
   @
   @ post \result.equals(getObject().toString());
   @*/
  public String toString() {
    return _object.toString();
  }
  
  /**
   * 
   * @return
   */
 /*@
   @ public behavior
   @
   @ TODO: specs
   @*/
  public Set getDirectlyConnectedNodes() {
    Set result = new HashSet(_starts);
    new Mapping() {
      public Object mapping(Object element) {
        return ((Edge)element).getEndFor(Node.this);
      }
    }.applyTo(result);
    return result;
  }
  
  /**
   * 
   * @return
   */
 /*@
   @ public behavior
   @
   @ TODO: specs
   @*/
  public Set getDirectlyConnectedObjects() {
    Set result = new HashSet(_starts);
    new Mapping() {
      public Object mapping(Object element) {
        return ((Edge)element).getEndFor(Node.this).getObject();
      }
    }.applyTo(result);
    return result;
  }
}
