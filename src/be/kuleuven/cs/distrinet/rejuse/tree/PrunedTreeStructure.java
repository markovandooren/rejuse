package be.kuleuven.cs.distrinet.rejuse.tree;

import java.util.Collections;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.function.Function;
import be.kuleuven.cs.distrinet.rejuse.predicate.UniversalPredicate;

import com.google.common.collect.ImmutableList;

/**
 * A pruned tree structure provides a pruned view on a tree structure. The
 * pruned tree structure contains only those elements satisfy its predicate.
 * In addition, by using a {@link TreePredicate}, it avoids traversing the
 * entire underlying tree structure by skipping subtrees of which the
 * predicate indicates that it can never match elements.
 *  
 * @author Marko van Dooren
 *
 * @param <T> The type of the nodes in the tree structure.
 */
public class PrunedTreeStructure<T> extends TreeStructure<T> {

	public PrunedTreeStructure(TreeStructure<T> underLyying, TreePredicate<T, Nothing> predicate) {
		_underLying = underLyying;
		_predicate = predicate;
	}
	
	private TreeStructure<T> _underLying;
	
	@Override
	public T node() {
	  return _underLying.node();
	}
	
	@Override
	public T parent() {
		return _underLying.parent();
	}

	@Override
	public List<? extends T> children() {
		if(canSucceedBeyond(node())) {
			return _underLying.children();
		} else {
			return Collections.EMPTY_LIST;
		}
	}
	
	public final boolean canSucceedBeyond(T node) {
		return _predicate.canSucceedBeyond(node);
	}
	
	private TreePredicate<T, Nothing> _predicate;

	@Override
	public TreeStructure<T> tree(T element) {
	  return new PrunedTreeStructure<>(_underLying.tree(element), _predicate);
	}
	
}
