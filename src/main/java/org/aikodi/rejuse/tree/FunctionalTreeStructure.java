package org.aikodi.rejuse.tree;

import java.util.List;

import org.aikodi.rejuse.action.UniversalConsumer;
import org.aikodi.rejuse.predicate.TypePredicate;
import org.aikodi.rejuse.predicate.UniversalPredicate;

/**
 * A TreeStructure provides a tree view on a data structure. The data structure itself
 * can be anything, including a data structure with a different tree layout.
 * 
 * By using this class, you sacrifice design for performance. In a purely object-oriented
 * style, each node in the tree structure would have a reference to the object in the underlying data structure.
 * Let us call such a tree structure a StatefulTreeStructure.
 * This would have two advantages. First, we don't have to provide the data structure object
 * as a parameter with each method. This makes it easier to use. Second, since not all
 * tree structure objects in the tree may be of the same class, care must be taken that
 * the data structure objects that is passed as an argument actually corresponds to
 * that TreeStructure object. With a StatefulTreeStructure, nothing can go wrong since we do
 * not pass the data structure object as an argument in the first place. 
 * 
 * But creating a StatefulTreeStructure object for each object in your data structure is
 * more expensive than having a single (or a few) TreeStructure objects. When the
 * data structure objects themselves keep a reference to the tree structure, 
 * this overhead may be tolerable. This could be the case when that particular tree structure
 * is inherently present in the data structure. An example is the inheritance tree in a graph of
 * persons. In other cases, however, we would either have to synchronize the StatefulTreeStructure
 * objects with the underlying data structure, or create new StatefulTreeStructure objects on each use. 
 * For large data structures, this may become problematic.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the node in the tree.
 */
public abstract class FunctionalTreeStructure<T> {
	
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
	public abstract T parent(T node);
	
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
	public abstract List<? extends T> children(T element);
	
	/**
	 * Return the tree structure of the given element.
	 * 
	 * @param element The element from which the retrieve the tree structure.
	 * @return the tree structor of the given element.
	 */
	public abstract FunctionalTreeStructure<T> tree(T element);
	
	public <X extends T, E extends Exception> X nearestAncestor(T element, UniversalPredicate<X,E> predicate) throws E {
		T el = parent(element);
		while ((el != null) && (! predicate.eval((T)el))) {
			el = tree(el).parent(el);
		}
		return (X) el;
	}

	public <X extends T, E extends Exception> X nearestAncestorOrSelf(T element, UniversalPredicate<X, E> predicate) throws E {
		T el = element;
		while ((el != null) && (! predicate.eval((T)el))) {
			el = tree(el).parent(el);
		}
		return (X) el;
	}

	public <X extends T> X nearestAncestorOrSelf(T element, Class<X> c) {
		T el = element;
		while ((el != null) && (! c.isInstance(el))){
			el = tree(el).parent(el);
		}
		return (X) el;
	}
	
	public boolean hasAncestorOrSelf(T element, T ancestor) {
		T el = element;
		while ((el != null) && (el != ancestor)){
			el = tree(el).parent(el);
		}
		return el == ancestor;
	}

	public <X extends T> List<X> descendants(T element, Class<X> c) {
		List<X> result = children(element, c);
		for (T e : children(element)) {
			result.addAll(tree(e).descendants(e,c));
		}
		return result;
	}
	
	public <X extends T> List<X> children(T element, Class<X> c) {
		return new TypePredicate<X>(c).downCastedList(children(element));
	}

	public <X extends T, E extends Exception>  void apply(T element, UniversalConsumer<X,E> action) throws E {
		if(action.type().isInstance(element)) {
			action.perform((T)element);
		}
		for (T e : children(element)) {
			tree(e).apply(e,action);
		}
	}


}