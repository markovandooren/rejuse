package be.kuleuven.cs.distrinet.rejuse.tree;

import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.action.Action;
import be.kuleuven.cs.distrinet.rejuse.predicate.TypePredicate;
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
	 * Return the tree structure of the given element.
	 * 
	 * @param element The element from which the retrieve the tree structure.
	 * @return the tree structor of the given element.
	 */
	public abstract TreeStructure<T> tree(T node);
	
	public <X extends T, E extends Exception> X nearestAncestor(UniversalPredicate<X,E> predicate) throws E {
		T el = parent();
		while ((el != null) && (! predicate.eval((T)el))) {
			el = tree(el).parent();
		}
		return (X) el;
	}

	public <X extends T, E extends Exception> X nearestAncestorOrSelf(T element, UniversalPredicate<X, E> predicate) throws E {
		T el = element;
		while ((el != null) && (! predicate.eval((T)el))) {
			el = tree(el).parent();
		}
		return (X) el;
	}

	public <X extends T> X nearestAncestorOrSelf(T element, Class<X> c) {
		T el = element;
		while ((el != null) && (! c.isInstance(el))){
			el = tree(el).parent();
		}
		return (X) el;
	}
	
	public boolean hasAncestorOrSelf(T element, T ancestor) {
		T el = element;
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
		return new TypePredicate<X>(c).downCastedList(children());
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