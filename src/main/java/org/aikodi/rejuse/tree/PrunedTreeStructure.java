package org.aikodi.rejuse.tree;

import java.util.Collections;
import java.util.List;

import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.function.Function;
import org.aikodi.rejuse.predicate.UniversalPredicate;

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
public class PrunedTreeStructure<T,N extends Exception> extends TreeStructure<T, N> {

	public PrunedTreeStructure(TreeStructure<T, N> underLyying, TreePredicate<T, Nothing> predicate) {
		_underLying = underLyying;
		_predicate = predicate;
	}
	
	private TreeStructure<T, N> _underLying;
	
	@Override
	public T node() {
	  return _underLying.node();
	}
	
	@Override
	public T parent() {
		return _underLying.parent();
	}

	@Override
	public List<T> children() throws N {
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
	public TreeStructure<T, N> tree(T element) {
	  return new PrunedTreeStructure<>(_underLying.tree(element), _predicate);
	}
	
}
