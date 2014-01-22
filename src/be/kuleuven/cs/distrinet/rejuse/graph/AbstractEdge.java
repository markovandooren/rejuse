package be.kuleuven.cs.distrinet.rejuse.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

/**
 * A class of edges.
 * 
 * The type parameters are an attempt at making it more convenient to use the graph
 * without casting too much. For this to work, E should be the self type.
 * 
 * @author Marko van Dooren
 */
public abstract class AbstractEdge<V> extends Edge<V> {
  
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @
   @ post getFirst() == first;
   @ post getSecond() == second;
   @*/
  public AbstractEdge(Node<V> first, Node<V> second) {
    _first = first;
    _second = second;
    first.addEdge(this);
    second.addEdge(this);
  }
  
 /*@
   @ post ! \old(getFirst()).contains(this);
   @ post ! \old(getSecond()).contains(this);
   @ post getFirst() == null;
   @ post getSecond() == null;
   @*/
  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#terminate()
	 */
  @Override
	public void terminate() {
    _first.removeEdge(this);
    _second.removeEdge(this);
    _first = null;
    _second = null;
  }
  
  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#getNodes()
	 */
 /*@
   @ public behavior
   @ 
   @ post \result != null
   @ post \result.contains(getFirst());
   @ post \result.contains(getSecond());
   @ post getFirst() == getSecond() ==> \result.size() == 1
   @ post getFirst() != getSecond() ==> \result.size() == 2
   @*/
	@Override
	public Set<Node<V>> getNodes() {
		if(_nodes == null) {
			Builder<Node<V>> builder = ImmutableSet.<Node<V>>builder();
			builder.add(_first);
			builder.add(_second);
			_nodes = builder.build();
		}
		return _nodes;
	}
	
	private ImmutableSet<Node<V>> _nodes;
  
  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#startsIn(be.kuleuven.cs.distrinet.rejuse.graph.Node)
	 */
 /*@
   @ public behavior
   @
   @ //false when the given node is null
   @ post node == null ==> \result == false;
   @*/  
  @Override
	public abstract boolean startsIn(Node<V> node);

  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#endsIn(be.kuleuven.cs.distrinet.rejuse.graph.Node)
	 */
 /*@
   @ public behavior
   @
   @ //false when the given node is null
   @ post node == null ==> \result == false;
   @*/  
  @Override
	public abstract boolean endsIn(Node<V> node);
  
  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#getStartNodes()
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ // The result is a subset of the set of all connected nodes
   @ post getNodes().containsAll(\result);
   @ post (\forall Node n; getNodes().contains(n);
   @        startsIn(n));
   @*/
  @Override
	public abstract Set<Node<V>> startNodes();
  
  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#getStartFor(be.kuleuven.cs.distrinet.rejuse.graph.Node)
	 */
 /*@
   @ public behavior
   @
   @ pre endsIn(end);
   @*/
  @Override
	public abstract Node<V> startFor(Node<V> end);
  
  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#getEndNodes()
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ // The result is a subset of the set of all connected nodes
   @ post getNodes().containsAll(\result);
   @ post (\forall Node n; getNodes().contains(n);
   @        endsIn(n));
   @*/
  @Override
	public abstract Set<Node<V>> endNodes();
  
	/* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#getFirst()
	 */
	@Override
	public Node<V> getFirst() {
		return _first;
	}
	
  /* (non-Javadoc)
	 * @see be.kuleuven.cs.distrinet.rejuse.graph.IEdge#getSecond()
	 */
  @Override
	public Node<V> getSecond() {
    return _second;
  }
  
	private Node<V> _first;
	private Node<V> _second;
	
	private Map<Class,Object> _metadata;
	
	public <T> T get(Class<T> key) {
		if(_metadata == null) {
			return null;
		}
		return (T) _metadata.get(key);
	}
	
	public <T> void put(Class<T> key, T value) {
		if(_metadata == null) {
			_metadata = new HashMap<>();
		}
		_metadata.put(key, value);
	}
}
