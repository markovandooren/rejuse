package org.aikodi.rejuse.tree;

import static org.aikodi.contract.Contract.requireNotNull;

import java.util.Collections;
import java.util.List;

import org.aikodi.rejuse.action.Nothing;

/**
 * A pruned tree structure provides a pruned view on a tree structure. The
 * pruned tree structure contains only those elements satisfy its predicate. In
 * addition, by using a {@link TreePredicate}, it avoids traversing the entire
 * underlying tree structure by skipping subtrees of which the predicate
 * indicates that it can never match elements.
 *
 * An element of the underlying tree cannot be part of a pruned tree if its
 * parent is not part of the pruned tree.
 *
 * @param <T>
 *          The type of the nodes in the tree structure.
 * @author Marko van Dooren
 */
public class PrunedTreeStructure<T, N extends Exception>
		implements TreeStructure<T, N> {

	/**
	 * The tree structure that determines the nodes that are potentially in this
	 * pruned tree structure.
	 *
	 * The underlying tree structure cannot be null.
	 */
	private TreeStructure<T, N> _underLying;

	/**
	 * The tree predicate use to determine if a subtree should be entered.
	 */
	private TreePredicate<T, Nothing> _predicate;

	/**
	 * @param underLying
	 *          The tree structure that forms the basis for this tree structure.
	 *          The underlying tree structure cannot be null.
	 * @param predicate
	 *          The predicate that determines which elements from the underlying
	 *          tree are part of the pruned tree structure. The predicate cannot
	 *          be null.
	 */
	public PrunedTreeStructure(TreeStructure<T, N> underLying,
			TreePredicate<T, Nothing> predicate) {
		requireNotNull(underLying);
		requireNotNull(predicate);

		_underLying = underLying;
		_predicate = predicate;
	}

	/**
	 * @return The node of the underlying tree structure.
	 */
	@Override
	public T node() {
		return _underLying.node();
	}

	/**
	 * The parent of the underlying tree structure.
	 * 
	 * @return
	 */
	@Override
	public T parent() {
		return _underLying.parent();
	}

	/**
	 * @return If the predicate can succeed for children of the node, then the
	 *         children of the node that satisfy the predicate are returned.
	 *         Otherwise, an empty list is returned.
	 * @throws N
	 */
	@Override
	public List<T> children() throws N {
		if (_predicate.canSucceedBeyond(node())) {
			return _predicate.filteredList(_underLying.children());
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * @return A pruned tree structure for the given element.
	 *         that uses the same predicate as this pruned tree structure.
	 */
	@Override
	public PrunedTreeStructure<T, N> tree(T element) {
		if (element == null) {
			throw new IllegalArgumentException("The element cannot be null.");
		}
		return new PrunedTreeStructure<>(_underLying.tree(element), _predicate);
	}

}
