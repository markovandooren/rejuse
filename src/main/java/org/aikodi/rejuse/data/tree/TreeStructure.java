package org.aikodi.rejuse.data.tree;

import static java.util.stream.Collectors.toList;
import static org.aikodi.rejuse.collection.CollectionOperations.exists;
import static org.aikodi.rejuse.collection.CollectionOperations.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.aikodi.rejuse.action.UniversalConsumer;
import org.aikodi.rejuse.function.Consumer;
import org.aikodi.rejuse.predicate.Predicate;
import org.aikodi.rejuse.predicate.UniversalPredicate;

/**
 * A TreeStructure provides a tree view on a data structure. The data structure itself
 * can be anything, including a data structure with a different tree layout.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the node in the tree.
 * @param <N> The type of exception that can be thrown when navigating the tree.
 */
public interface TreeStructure<T, N extends Exception> {

  /**
   * @return the node at this place in the tree.
   */
  T node();

  /**
   * Return the parent of this node.
   */
  /*@
    @ default behavior
    @
    @ pre node != null;
    @*/
  T parent();

  /**
   * Return the direct children of the given element.
   * 
   * The result will never be null. All elements in the collection will have 
   * the given element as their parent according to this tree.
   */
  /*@
    @ default behavior
    @
    @ post \result != null;
    @ post result.stream().allMatch(e -> e.parent() == this);
    @*/
  List<T> children() throws N;

  /**
   * Return the branches of this node.
   * 
   * @return A list containing the branches of this node. 
   *         The result is not null. The result does not contain null.
   */
  default List<TreeStructure<? extends T,N>> branches() throws N {
    List<? extends T> children = children();
    List<TreeStructure<? extends T,N>> result = new ArrayList<>(children.size());
    for(T child: children) {
      result.add(tree(child));
    }
    return result;
  }

  /**
   * Return the tree structure of the given element.
   * 
   * @param node The element from which the retrieve the tree structure.
   * @return the tree structor of the given element.
   */
  TreeStructure<T,N> tree(T node);
  
  /**
   * Return the nearest ancestor of type T that satisfies the given predicate. Null if no such ancestor can be found.
   * 
   * @param <X>
   *        The type of the ancestor to be found
   * @param <E>
   *        The type of the exception that can be thrown by the predicate.
   * @return
   */
 /*@
   @ default behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && predicate.eval(parent()) ==> \result == parent();
   @ post parent() != null && (! predicate.eval((T)parent())) 
   @          ==> \result == parent().nearestAncestor(predicate);
   @*/
  default <X extends T, E extends Exception> X nearestAncestor(UniversalPredicate<X,E> predicate) throws E {
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
   @ default behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && kind.isInstance(parent()) && predicate.eval((T)parent()) ==> \result == parent();
   @ post parent() != null && ((! kind.isInstance(parent())) || (kindc.isInstance(parent()) && ! predicate.eval((T)parent())) 
   @          ==> \result == parent().nearestAncestor(kind, predicate);
   @*/
  default <X extends T, E extends Exception> X nearestAncestor(Class<X> kind, Predicate<X, E> predicate) throws E {
    return nearestIncluding(kind, predicate, parent());
  }

  default <X extends T, E extends Exception> X nearestAncestorOrSelf(Class<X> kind, Predicate<X, E> predicate) throws E {
    return nearestIncluding(kind, predicate, node());
  }

	default <X extends T, E extends Exception> X nearestIncluding(Class<X> kind, Predicate<X, E> predicate, T el) throws E {
		while ((el != null) && ((! kind.isInstance(el)) || (! predicate.eval((X)el)))) {
      el = tree(el).parent();
    }
    return (X) el;
	}

  default <X extends T, E extends Exception> X nearestAncestorOrSelf(UniversalPredicate<X, E> predicate) throws E {
    return nearest(predicate, node());
  }

  /**
   * Return the descendants of the given type that are themselves no descendants of an element of the given type. In other words,
   * do a deep search for elements of the given type, but if you have found one, don't search its descendants.
   *
   * @param <T> The type of the elements for which the predicate can match.
   * @param c The type of the descendants that should be returned.
   * @throws N 
   */
  /*@
     @ default behavior
     @
     @ post \result != null;
     @*/
  default <X extends T> List<X> nearestDescendants(Class<X> c) throws N {
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
   * @throws N 
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @*/
  default <E extends Exception> List<T> nearestDescendants(Predicate<? super T,E> predicate) throws E, N {
		List<TreeStructure<? extends T,N>> tmp = branches();
		List<T> result = new ArrayList<>();
		Iterator<TreeStructure<? extends T,N>> iter = tmp.iterator();
		while(iter.hasNext()) {
			T e = iter.next().node();
			if(predicate.eval(e)) {
				result.add((T)e);
				iter.remove();
			}
		}
		for (TreeStructure<? extends T,N> e : tmp) {
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
   * @throws N 
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @*/
  default <X extends T, E extends Exception> List<X> nearestDescendants(UniversalPredicate<T,E> predicate) throws E, N {
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
  
  default <X extends T, E extends Exception> X nearest(UniversalPredicate<X, E> predicate, T el) throws E {
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
   @ default behavior
   @
   @ post c.isInstance(this) ==> \result == this;
   @ post ! c.isInstance(this) && parent() != null ==> \result == parent().nearestAncestor(c);
   @ post ! c.isInstance(this) && parent() == null ==> \result == null;
   @*/
  default <X extends T> X nearestAncestorOrSelf(Class<X> c) {
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
   @ default behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && c.isInstance(this) && parent().farthestAncestor(c) == null ==> \result == this;
   @ post parent() != null && (parent().farthestAncestor(c) != null) ==> \result == parent().farthestAncestor(c);
   @*/
  default <X extends T, E extends Exception> X farthestAncestor(UniversalPredicate<X,E> p) throws E {
    return farthest(p, parent());
  }

  /**
   * Return the farthest ancestor of the given type.
   */
 /*@
   @ default behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && c.isInstance(this) && parent().farthestAncestor(c) == null ==> \result == this;
   @ post parent() != null && (parent().farthestAncestor(c) != null) ==> \result == parent().farthestAncestor(c);
   @*/
  default <X extends T> X farthestAncestorOrSelf(Class<X> c) {
		X result = farthestAncestor(c);
		if((result == null) && (c.isInstance(this))) {
			result = (X) this;
		}
		return result;

  }
  
  /**
   * Return a list of all ancestors. The direct parent is in front of the list, the
   * furthest ancestor is last.
   */
  /*@
     @ default behavior
     @
     @ post \result != null;
     @ post parent() == null ==> \result.isEmpty();
     @ post parent() != null ==> \result.get(0) == parent();
     @ post parent() != null ==> \result.subList(1,\result.size()).equals(parent().ancestors());
     @*/
  default List<T> ancestors() {
		if (parent()!=null) {
			List<T> result = tree(parent()).ancestors();
			result.add(0, parent());
			return result;
		} else {
			return new ArrayList<>();
		}
  }

  /**
   * Return a list of all ancestors of the given type. A closer ancestors will have a lower index than a 
   * farther ancestor.
   * 
   * @param c The kind of the ancestors that should be returned.
   */
 /*@
   @ default behavior
   @
   @ post \result != null;
   @ post parent() == null ==> \result.isEmpty();
   @ post parent() != null && c.isInstance(parent()) ==> \result.get(0) == parent()
   @                       && \result.subList(1,\result.size()).equals(parent().ancestors(c));
   @ post parent() != null && ! c.isInstance(parent()) ==> \result.equals(parent().ancestors(c));
   @*/
  default <X extends T> List<X> ancestors(Class<X> c) {
 		List<X> result = new ArrayList<>();
 		X el = nearestAncestor(c);
 		while (el != null){
 			result.add(el);
 			el = tree(el).nearestAncestor(c);
 		}
 		return result;
  }
  
  /**
   * Return a list of all ancestors of the given type. A closer ancestors will have a lower index than a 
   * farther ancestor.
   * 
   * @param predicate A predicate that determines which ancestors should be returned.
   */
 /*@
   @ default behavior
   @
   @ post \result != null;
   @ post parent() == null ==> \result.isEmpty();
   @ post parent() != null && predicate.eval(parent()) ==> \result.get(0) == parent()
   @                       && \result.subList(1,\result.size()).equals(parent().ancestors(c));
   @ post parent() != null && ! predicate.eval(parent()) ==> 
   @                       \result.equals(parent().ancestors(c));
   @*/
  default <X extends T, E extends Exception> List<X> ancestors(UniversalPredicate<X, E> predicate) throws E {
		return predicate.downCastedList(ancestors());
  }

  /**
   * Return the nearest ancestor of type T. Null if no such ancestor can be found.
   * @param <T>
   *        The type of the ancestor to be found
   * @param c
   *        The class object of type T (T.class)
   * @return
   */
 /*@
   @ default behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && c.isInstance(parent()) ==> \result == parent();
   @ post parent() != null && (! c.isInstance(parent())) ==> \result == parent().nearestAncestor(c);
   @*/
  default <X extends T> X nearestAncestor(Class<X> c) {
		T el = parent();
		while ((el != null) && (! c.isInstance(el))){
			el = tree(el).parent();
		}
		return (X) el;
  }



  /**
   * Return the farthest ancestor.
   */
  /*@
     @ default behavior
     @
     @ post parent() == null ==> \result == this;
     @ post parent() != null ==> \result == parent().furthestAncestor();
     @*/
  default T farthestAncestor() {
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
   @ default behavior
   @
   @ post parent() == null ==> \result == null;
   @ post parent() != null && c.isInstance(parent()) && parent().farthestAncestor(c) == null ==> \result == parent();
   @ post parent() != null && (parent().farthestAncestor(c) != null) ==> \result == parent().farthestAncestor(c);
   @*/
  default <X extends T> X farthestAncestor(Class<X> c) {
		TreeStructure<T,N> el = tree(parent());
		X anc = null;
		while(el != null) {
			while ((el != null) && (! c.isInstance(el.node()))){
				T parent = el.parent();
				if(parent != null) {
				  el = tree(parent);
				} else {
					el = null;
				}
			}
			if(el != null) {
				anc = (X) el.node();
				el = tree(el.parent());
			}
		}
		return anc;
	}
  
  
  default <E extends Exception, X extends T> X farthestAncestorOrSelf(UniversalPredicate<X,E> p) throws E {
    return farthest(p, node());
  }

  default <X extends T, E extends Exception> T farthestAncestorOrSelf(Class<X> kind, Predicate<X,E> p) throws E {
    return farthestAncestorOrSelf(UniversalPredicate.of(kind,p));
  }

  default <E extends Exception, X extends T> X farthest(UniversalPredicate<X, E> predicate, T el) throws E {
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
  
  /**
   * Check whether the given element is an ancestor of this element.
   * 
   * @param ancestor The potential ancestor.
   */
 /*@
   @ default behavior
   @
   @ pre c != null;
   @
   @ post \result == ancestors().contains(ancestor);
   @*/
  default boolean hasAncestor(T ancestor) {
    T el = parent();
    return el == null ? false : tree(el).hasAncestorOrSelf(ancestor);
  }

  /**
   * Check whether the given element is this element in the tree
   * or an ancestor of this element.
   * 
   * @param ancestor The potential ancestor.
   */
 /*@
   @ default behavior
   @
   @ pre c != null;
   @
   @ post \result == equals(ancestor) || ancestors().contains(ancestor);
   @*/
  default boolean hasAncestorOrSelf(T ancestor) {
    T el = node();
    while ((el != null) && (! el.equals(ancestor))){
      el = tree(el).parent();
    }
    return el == null || el.equals(ancestor);
  }

	/**
	 * Return all children of this element that are of the given type.
	 * 
	 * @param type The kind of the children that should be returned.
	 */
 /*@
	 @ default behavior
	 @
	 @ post \result != null;
	 @ post (\forall Element e; ; \result.contains(e) <==> children().contains(e) && c.isInstance(e));
	 @*/
  default <X> List<X> children(Class<X> type) throws N {
      return children().stream().filter(type::isInstance).map(type::cast).collect(toList());
  }

  /**
   * Return all children of this element that satisfy the given predicate.
   * 
   * @param type The type of the children to which the predicate must be applied.
   * @param predicate A predicate that determines which children should be returned.
   * @throws N 
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> children().contains(e) && predicate.eval(e) == true);
   @*/
  default <X extends T, E extends Exception> List<X> children(Class<X> type, Predicate<X,E> predicate) throws E, N {
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
   * @throws N 
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> children().contains(e) && predicate.eval(e) == true);
   @*/
  default <E extends Exception> List<T> children(Predicate<? super T,E> predicate) throws E, N {
		List<? extends T> tmp = children();
		filter(tmp,predicate);
		return (List<T>)tmp;
  }

  /**
   * Return all children of this element that are of the given type, and satisfy the given predicate.
   * 
   * @param predicate A predicate that determines which ancestors should be returned.
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> children().contains(e) && predicate.eval(e));
   @*/
  default <X extends T, E extends Exception> List<X> children(UniversalPredicate<X,E> predicate) throws E, N {
		return predicate.downCastedList(children());
  }

  /**
   * Recursively return all children of this element.
   * (The children, and the children of the children,...).
   */
 /*@
   @ default behavior
   @
   @ post \result != null;
   @ post (\forall T e; \result.contains(e) <=> children().contains(e) ||
   @          (\exists T c; children().contains(c); tree(c).descendants().contains(e)));
   @*/ 
  default List<T> descendants() throws N {
    List<T> result = children();
    for (T e : children()) {
      result.addAll(tree(e).descendants());
    }
    return result;
  }

  
  /**
   * Return all descendants of a given type.
   * 
   * @param type The type of the requested descendants.
   * 
   * @return All descendants of the requested type.
   * @throws N 
   */
 /*@
   @ default behavior
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> descendants().contains(e) && type.isInstance(e));
   @*/
  default <X> List<X> descendants(Class<X> type) throws N {
    List<X> result = children(type);
    for (T e : children()) {
      result.addAll(tree(e).descendants(type));
    }
    return result;
  }

  /**
   * Recursively return all descendants of this element that satisfy the given predicate.
   * 
   * @param predicate A predicate that determines which descendants are returned.
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> descendants().contains(e) && predicate.eval(e));
   @*/
	default <X extends T, E extends Exception> List<X> descendants(UniversalPredicate<X,E> predicate) throws E, N {
		List<X> result = children(predicate);
		predicate.filter(result);
		for (T e : children()) {
			result.addAll(tree(e).descendants(predicate));
		}
		return result;
	}
	
  /**
   * Recursively return all descendants of this element that satisfy the given predicate.
   * 
   * @param predicate A predicate that determines which descendants are returned.
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> descendants().contains(e) && predicate.eval(e));
   @*/
  default <E extends Exception> List<T> descendants(Predicate<? super T,E> predicate) throws E, N {
		// Do not compute all descendants, and apply predicate afterwards.
		// That is way too expensive.
		List<T> tmp = children();
		predicate.filter(tmp);
		List<T> result = (List<T>)tmp;
		for (T child : children()) {
			result.addAll(tree(child).descendants(predicate));
		}
		return result;
	}

  /**
   * Recursively return all descendants of this element that are of the given type, and satisfy the given predicate.
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result != null;
   @ post (\forall Element e; ; \result.contains(e) <==> descendants().contains(e) && c.isInstance(e) && predicate.eval(e));
   @*/
  default <X extends T, E extends Exception> List<X> descendants(Class<X> c, Predicate<X,E> predicate) throws E, N {
		List<X> result = children(c);
		predicate.filter(result);
		for (T e : children()) {
			result.addAll(tree(e).descendants(c, predicate));
		}
		return result;
  }

  /**
   * Check whether this element has a descendant of the given type.
   * 
   * @param c The class object representing the type the descendants.
   */
 /*@
   @ default behavior
   @
   @ pre type != null;
   @
   @ post \result == ! descendants(type).isEmpty();
   @*/
	default <X extends T> boolean hasDescendant(Class<X> type) throws N {
		List<T> children = children();
		if(children.stream().anyMatch(child -> type.isInstance(child))) {
			return true;
		}
		for(T child: children) {
			if(tree(child).hasDescendant(type)) {
				return true;
			}
		}
		return false;
	}

  
  /**
   * Check whether this element has a descendant that satisfies the given predicate.
   * 
   * @param predicate The predicate of which must be determined whether any descendants satisfy it.
   * @throws Exception 
   */
 /*@
   @ default behavior
   @
   @ pre predicate != null;
   @
   @ post \result == (\exists T t; descendants().contains(t); predicate.eval(t));
   @*/
  default <X extends T, E extends Exception> boolean hasDescendant(UniversalPredicate<X,E> predicate) throws Exception {
    return (! children(predicate).isEmpty()) || exists(children(), c -> tree(c).hasDescendant(predicate));
  }

  /**
   * Check whether this element has a descendant that satisfies the given predicate.
   * 
   * @param type The class object representing the type the descendants.
   * @param predicate The predicate of which we want to know whether it is satisfied by a descendant.
   * @throws N 
   */
 /*@
   @ default behavior
   @
   @ pre c != null;
   @
   @ post \result == ! descendants(type, predicate).isEmpty();
   @*/
  default <X extends T, E extends Exception> boolean hasDescendant(Class<X> type, Predicate<X,E> predicate) throws E, N {
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
  
  /**
   * Recursively apply the given action to this element and all of its 
   * descendants, but only if their type conforms to T.
   * 
   * @param action The action to apply.
   */
  default <X, E extends Exception> void apply(UniversalConsumer<X,E> action) throws E, N {
    T node = node();
    if(action.type().isInstance(node)) {
      action.perform((T)node);
    }
    for (T e : children()) {
      TreeStructure<? extends T,N> tree = tree(e);
      tree.apply(action);
    }
  }

  /**
   * Recursively pass this element and all of its descendants to
   * the given consumer if their type conforms to T.
   * 
   * @param kind A class object representing the type of elements to be 
   *             passed to the consumer.
   * @param consumer The consumer to which the elements must be provided.
   */
  default <X extends T, E extends Exception> void apply(Class<X> kind, Consumer<X,E> consumer) throws E, N {
	  if(kind.isInstance(node())) {
	     consumer.accept((X)node());
	  }
    for (T e : children()) {
       tree(e).apply(kind, consumer);
    }
  }

	/**
	 * Recursively pass this element and all of its descendants to the given
	 * consumer if their type conforms to T.
	 * 
	 * @param kind
	 *            A class object representing the type of elements to be passed to
	 *            the consumer.
	 * @param consumer
	 *            The consumer to which the elements must be provided.
	 */
	default <E extends Exception> void apply(Consumer<? super T, E> consumer) throws E, N {
		consumer.accept(node());
		for (T e : children()) {
			tree(e).apply(consumer);
		}
	}

}