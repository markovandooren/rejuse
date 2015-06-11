package be.kuleuven.cs.distrinet.rejuse.tree;

import static be.kuleuven.cs.distrinet.rejuse.collection.CollectionOperations.filter;

import java.util.ArrayList;
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

  public <X extends T, E extends Exception> X nearestAncestor(UniversalPredicate<X,E> predicate) throws E {
    return nearest(predicate, parent());
  }

  public <X extends T, E extends Exception> X nearestAncestorOrSelf(Class<X> kind, Predicate<X, E> predicate) throws E {
    T el = node();
    while ((el != null) && ((! kind.isInstance(el)) || (! predicate.eval((X)el)))) {
      el = tree(el).parent();
    }
    return (X) el;
  }

  public <X extends T, E extends Exception> X nearestAncestorOrSelf(UniversalPredicate<X, E> predicate) throws E {
    return nearest(predicate, node());
  }

  private <X extends T, E extends Exception> X nearest(UniversalPredicate<X, E> predicate, T el) throws E {
    while ((el != null) && (! predicate.eval(el))) {
      el = tree(el).parent();
    }
    return (X) el;
  }

  public <X extends T> X nearestAncestorOrSelf(Class<X> c) {
    T el = node();
    while ((el != null) && (! c.isInstance(el))){
      el = tree(el).parent();
    }
    return (X) el;
  }

  public <X extends T, E extends Exception> T farthestAncestor(UniversalPredicate<T,E> p) throws E {
    return farthest(p, parent());
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
    List result = children();
    filter(result, child -> c.isInstance(child));
    return result;
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