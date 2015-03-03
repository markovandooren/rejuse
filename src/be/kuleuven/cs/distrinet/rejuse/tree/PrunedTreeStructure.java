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
	
	TreeStructure<T> _underLying;
	
	@Override
	public T node() {
	  return _underLying.node();
	}
	
	@Override
	public T parent() {
		// Not a skipper
		return _underLying.parent();
//		return nearestAncestor(node, new UniversalPredicate<T, Nothing>(type()) {
//
//			@Override
//			public boolean uncheckedEval(T t) throws Nothing {
//				return PrunedTreeStructure.this.eval(t);
//			}
//		});
	}

	@Override
	public List<? extends T> children() {
		if(canSucceedBeyond(node())) {
			return _underLying.children();
		} else {
			return Collections.EMPTY_LIST;
		}
	}
	
//	/**
//	 * Navigate the 
//	 * @param nodes
//	 * @return
//	 * @throws E 
//	 * @throws Nothing 
//	 */
//	private List<? extends T> skip(List<? extends T> nodes, Function<T,TreeStructure<T>,Nothing> treeSelector) throws Nothing {
//		ImmutableList.Builder<T> builder = ImmutableList.builder(); 
//		for(T node: nodes) {
//			if(eval(node)) {
//				builder.add(node);
//			} else {
//				if(canSucceedBeyond(node)) {
//					builder.addAll(skip(treeSelector.apply(node).children(node),treeSelector));
//				}
//			}
//		}
//		return builder.build();
//	}
	
	public final boolean canSucceedBeyond(T node) {
		return _predicate.canSucceedBeyond(node);
	}
	
	private TreePredicate<T, Nothing> _predicate;
//	public abstract boolean eval(T node);

	@Override
	public TreeStructure<T> tree(T element) {
		return this;
	}
	
//	public abstract Class<T> type();

}
