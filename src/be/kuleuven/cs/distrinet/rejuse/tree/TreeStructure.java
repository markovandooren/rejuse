package be.kuleuven.cs.distrinet.rejuse.tree;

import static be.kuleuven.cs.distrinet.rejuse.collection.CollectionOperations.exists;
import static be.kuleuven.cs.distrinet.rejuse.collection.CollectionOperations.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.action.Action;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.UniversalPredicate;

/**
 * A TreeStructure provides a tree view on a data structure. The data structure itself
 * can be anything, including a data structure with a different tree layout.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the node in the tree.
 */
public abstract class TreeStructure<T> {

  /**
   * @return the node at this place in the tree.
   */
  public abstract T node();

  /**
   * Return the parent of the given node.
   * 
   * @param node The node of which the parent is requested.
   */
  /*@
    @ public behavior
    @
    @ pre node != null;
    @*/
  public abstract T parent();

  /**
   * Return the direct children of the given element.
   * 
   * The result will never be null. All elements in the collection will have 
   * the given element as their parent according to this tree.
   */
  /*@
    @ public behavior
    @
    @ post \result != null;
    @ post result.stream().allMatch(e -> e.parent() == this);
    @*/
  public abstract List<? extends T> children();

  /**
   * Return the branches of this node.
   * 
   * @return A list containing the branches of this node. 
   *         The result is not null. The result does not contain null.
   */
  public List<TreeStructure<? extends T>> branches() {
    List<? extends T> children = children();
    List<TreeStructure<? extends T>> result = new ArrayList<>(children.size());
    for(T child: children) {
      result.add(tree(child));
    }
    return result;
  }

  /**
   * Return the tree structure of the given element.
   * 
   * @param element The element from which the retrieve the tree structure.
   * @return the tree structor of the given element.
   */
  public abstract TreeStructure<T> tree(T node);

  
  
  /**
   * Return the nearest ancestor of type T that satisfies the given predicate. Null if no such ancestor can be found.
   * 
   * @param <T>
   *        The type of the ancestor to be found
   * @param c
   *        The class object of type T (T.class)
   * @return
   */
 /*@
   @ public behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && predicate.eval(parent()) ==> \result == parent();
   @ post parent() != null && (! predicate.eval((T)parent())) 
   @          ==> \result == parent().nearestAncestor(predicate);
   @*/
  public <X extends T, E extends Exception> X nearestAncestor(UniversalPredicate<X,E> predicate) throws E {
    return nearest(predicate, parent());
  }

  /**
   * Return the nearest ancestor of type T that satisfies the given predicate. 
   * Null if no such ancestor can be found.
   * 
   * @param <T>
   *        The type of the ancestor to be found
   * @param c
   *        The class object of type T (T.class)
   * @return
   */
 /*@
   @ public behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && kind.isInstance(parent()) && predicate.eval((T)parent()) ==> \result == parent();
   @ post parent() != null && ((! kind.isInstance(parent())) || (kindc.isInstance(parent()) && ! predicate.eval((T)parent())) 
   @          ==> \result == parent().nearestAncestor(kind, predicate);
   @*/
  public <X extends T, E extends Exception> X nearestAncestor(Class<X> kind, Predicate<X, E> predicate) throws E {
    return nearestIncluding(kind, predicate, parent());
  }

  public <X extends T, E extends Exception> X nearestAncestorOrSelf(Class<X> kind, Predicate<X, E> predicate) throws E {
    return nearestIncluding(kind, predicate, node());
  }

	private <X extends T, E extends Exception> X nearestIncluding(Class<X> kind, Predicate<X, E> predicate, T el) throws E {
		while ((el != null) && ((! kind.isInstance(el)) || (! predicate.eval((X)el)))) {
      el = tree(el).parent();
    }
    return (X) el;
	}

  public <X extends T, E extends Exception> X nearestAncestorOrSelf(UniversalPredicate<X, E> predicate) throws E {
    return nearest(predicate, node());
  }

  /**
   * Return the descendants of the given type that are themselves no descendants of an element of the given type. In other words,
   * do a deep search for elements of the given type, but if you have found one, don't search its descendants.
   *
   * @param <T> The type of the elements for which the predicate can match.
   * @param c The type of the descendants that should be returned.
   */
  /*@
     @ public behavior
     @
     @ post \result != null;
     @*/
  public <X extends T> List<X> nearestDescendants(Class<X> c) {
		List<? extends T> tmp = children();
		List<X> result = new ArrayList<>();
		Iterator<? extends T> iter = tmp.iterator();
		while(iter.hasNext()) {
			T e = iter.next();
			if(c.isInstance(e)) {
				result.add((X)e);
				iter.remove();
			}
		}
		for (T e : tmp) {
			result.addAll(tree(e).nearestDescendants(c));
		}
		return result;
  }
  
  
  /**
   * Return the list of first descendants that satisfy the given predicate. First means that if
   * an element satisfies the predicate, the element itself is in the result but its descendants are ignored.
   * 
   * @param <T> The type of the elements for which the predicate can match.
   * @param predicate The predicate that must be satisfied.
   */
 /*@
   @ public behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @*/
  public <E extends Exception> List<T> nearestDescendants(Predicate<? super T,E> predicate) throws E {
		List<TreeStructure<? extends T>> tmp = branches();
		List<T> result = new ArrayList<>();
		Iterator<TreeStructure<? extends T>> iter = tmp.iterator();
		while(iter.hasNext()) {
			T e = iter.next().node();
			if(predicate.eval(e)) {
				result.add((T)e);
				iter.remove();
			}
		}
		for (TreeStructure<? extends T> e : tmp) {
			result.addAll(e.nearestDescendants(predicate));
		}
		return result;
  }

  /**
   * Return the list of first descendants that satisfy the given predicate. First means that if
   * an element satisfies the predicate, the element itself is in the result but its descendants are ignored.
   * 
   * @param <T> The type of the elements for which the predicate can match.
   * @param predicate The predicate that must be satisfied.
   */
 /*@
   @ public behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @*/
  public <X extends T, E extends Exception> List<X> nearestDescendants(UniversalPredicate<T,E> predicate) throws E {
		List<? extends T> tmp = children();
		List<X> result = new ArrayList<>();
		Iterator<? extends T> iter = tmp.iterator();
		while(iter.hasNext()) {
			T e = iter.next();
			if(predicate.eval(e)) {
				result.add((X)e);
				iter.remove();
			}
		}
		for (T e : tmp) {
			result.addAll(tree(e).nearestDescendants(predicate));
		}
		return result;
  }
  
  private <X extends T, E extends Exception> X nearest(UniversalPredicate<X, E> predicate, T el) throws E {
    while ((el != null) && (! predicate.eval(el))) {
      el = tree(el).parent();
    }
    return (X) el;
  }

  /**
   * Return the nearest element of type T. Null if no such ancestor can be found.
   * @param <T>
   *        The type of the ancestor to be found
   * @param c
   *        The class object of type T (T.class)
   * @return
   */
 /*@
   @ public behavior
   @
   @ post c.isInstance(this) ==> \result == this;
   @ post ! c.isInstance(this) && parent() != null ==> \result == parent().nearestAncestor(c);
   @ post ! c.isInstance(this) && parent() == null ==> \result == null;
   @*/
  public <X extends T> X nearestAncestorOrSelf(Class<X> c) {
    T el = node();
    while ((el != null) && (! c.isInstance(el))){
      el = tree(el).parent();
    }
    return (X) el;
  }

  /**
   * Return the farthest ancestor of the given type that satisfies the given predicate.
   */
 /*@
   @ public behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && c.isInstance(this) && parent().farthestAncestor(c) == null ==> \result == this;
   @ post parent() != null && (parent().farthestAncestor(c) != null) ==> \result == parent().farthestAncestor(c);
   @*/
  public <X extends T, E extends Exception> X farthestAncestor(UniversalPredicate<X,E> p) throws E {
    return farthest(p, parent());
  }

  /**
   * Return the farthest ancestor of the given type.
   */
 /*@
   @ public behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && c.isInstance(this) && parent().farthestAncestor(c) == null ==> \result == this;
   @ post parent() != null && (parent().farthestAncestor(c) != null) ==> \result == parent().farthestAncestor(c);
   @*/
  public <X extends T> X farthestAncestorOrSelf(Class<X> c) {
		X result = farthestAncestor(c);
		if((result == null) && (c.isInstance(this))) {
			result = (X) this;
		}
		return result;

  }

  /**
   * Return the farthest ancestor.
   */
  /*@
     @ public behavior
     @
     @ post parent() == null ==> \result == this;
     @ post parent() != null ==> \result == parent().furthestAncestor();
     @*/
  public T farthestAncestor() {
		T parent = parent();
		if(parent == null) {
			return node();
		} else {
			return tree(parent).farthestAncestor();
		}
  }

  /**
   * Return the farthest ancestor of the given type.
   */
 /*@
   @ public behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && c.isInstance(parent()) && parent().farthestAncestor(c) == null ==> \result == parent();
   @ post parent() != null && (parent().farthestAncestor(c) != null) ==> \result == parent().farthestAncestor(c);
   @*/
  public <X extends T> X farthestAncestor(Class<X> c) {
		TreeStructure<T> el = tree(parent());
		X anc = null;
		while(el != null) {
			while ((el != null) && (! c.isInstance(el.node()))){
				el = tree(el.parent());
			}
			if(el != null) {
				anc = (X) el.node();
				el = tree(el.parent());
			}
		}
		return anc;
	}
  
  
  public <E extends Exception, X extends T> X farthestAncestorOrSelf(UniversalPredicate<X,E> p) throws E {
    return farthest(p, node());
  }

  public <X extends T, E extends Exception> T farthestAncestorOrSelf(Class<X> kind, Predicate<X,E> p) throws E {
    return farthestAncestorOrSelf(UniversalPredicate.of(kind,p));
  }

  private <E extends Exception, X extends T> X farthest(UniversalPredicate<X, E> predicate, T el) throws E {
    // Find the first one, including self
    el = tree(el).nearestAncestorOrSelf(predicate);
    X anc = (X)el;
    // Find the rest. We must now skip self because it already matched.
    while(el != null) {
      el = tree(el).nearestAncestor(predicate);
      if(el != null) {
        anc = (X) el;
      }
    }
    return anc;
  }
  
  public boolean hasAncestorOrSelf(T ancestor) {
    T el = node();
    while ((el != null) && (el != ancestor)){
      el = tree(el).parent();
    }
    return el == ancestor;
  }

  public <X> List<X> descendants(Class<X> c) {
    List<X> result = children(c);
    for (T e : children()) {
      result.addAll(tree(e).descendants(c));
    }
    return result;
  }

  public <X> List<X> children(Class<X> c) {
    List<? extends T> result = children();
    filter(result, child -> c.isInstance(child));
    return (List)result;
  }

  /**
   * Return all children of this element that satisfy the given predicate.
   * 
   * @param type The type of the children to which the predicate must be applied.
   * @param predicate A predicate that determines which children should be returned.
   */
  /*@
     @ public behavior
     @
     @ pre predicate != null;
     @
     @ post \result != null;
     @ post (\forall Element e; ; \result.contains(e) <==> children().contains(e) && predicate.eval(e) == true);
     @*/
  public <X extends T, E extends Exception> List<X> children(Class<X> type, Predicate<X,E> predicate) throws E {
    List<? extends T> children = children();
    List<X> result = new ArrayList<>(children.size());
    for(T child: children) {
      if(type.isInstance(child) && predicate.eval((X) child)) {
        result.add((X) child);
      }
    }
    return result;
  }

  /**
   * Return all children of this element that satisfy the given predicate.
   * 
   * @param predicate A predicate that determines which children should be returned.
   */
 /*@
   @ public behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> children().contains(e) && predicate.eval(e) == true);
   @*/
  public <E extends Exception> List<T> children(Predicate<? super T,E> predicate) throws E {
		List<? extends T> tmp = children();
		filter(tmp,predicate);
		return (List<T>)tmp;
  }

  /**
   * Check whether this element has a descendant that satisfies the given predicate.
   * 
   * @param predicate The predicate of which must be determined whether any descendants satisfy it.
   */
 /*@
   @ public behavior
   @
   @ pre predicate != null;
   @
   @ post \result == (\exists T t; descendants().contains(t); predicate.eval(t));
   @*/
  public <X extends T, E extends Exception> boolean hasDescendant(UniversalPredicate<X,E> predicate) throws E {
    return (! children(predicate).isEmpty()) || exists(children(), c -> tree(c).hasDescendant(predicate));
  }

  /**
   * Check whether this element has a descendant that satisfies the given predicate.
   * 
   * @param type The class object representing the type the descendants.
   * @param predicate The predicate of which we want to know whether it is satisfied by a descendant.
   */
 /*@
   @ public behavior
   @
   @ pre c != null;
   @
   @ post \result == ! descendants(type, predicate).isEmpty();
   @*/
  public <X extends T, E extends Exception> boolean hasDescendant(Class<X> type, Predicate<X,E> predicate) throws E {
    List<X> result = children(type, predicate);
    if (!result.isEmpty()) {
      return true;
    }
    for (T e : children()) {
      if (tree(e).hasDescendant(type, predicate)) {
        return true;
      }
    }
    return false;
  }
  
  public <X, E extends Exception>  void apply(Action<X,E> action) throws E {
    T node = node();
    if(action.type().isInstance(node)) {
      action.perform((T)node);
    }
    for (T e : children()) {
      TreeStructure<? extends T> tree = tree(e);
      tree.apply(action);
    }
  }


}