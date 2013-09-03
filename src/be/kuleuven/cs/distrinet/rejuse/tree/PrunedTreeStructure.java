package be.kuleuven.cs.distrinet.rejuse.tree;

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
public abstract class PrunedTreeStructure<T> extends TreeStructure<T> {

	public PrunedTreeStructure(TreeStructure<T> underLyying) {
		_underLying = underLyying;
	}
	
	TreeStructure<T> _underLying;
	
	@Override
	public T parent(T node) {
		return nearestAncestor(node, new UniversalPredicate<T, Nothing>(type()) {

			@Override
			public boolean uncheckedEval(T t) throws Nothing {
				return PrunedTreeStructure.this.eval(t);
			}
		});
	}

	@Override
	public List<? extends T> children(T element) {
		return skip(ImmutableList.of(element), new Function<T, TreeStructure<T>, Nothing>() {
			@Override
			public TreeStructure<T> apply(T argument) {
				return tree(argument);
			}
		});
	}
	
	/**
	 * Navigate the 
	 * @param nodes
	 * @return
	 * @throws E 
	 * @throws Nothing 
	 */
	private List<? extends T> skip(List<? extends T> nodes, Function<T,TreeStructure<T>,Nothing> treeSelector) throws Nothing {
		ImmutableList.Builder<T> builder = ImmutableList.builder(); 
		for(T node: nodes) {
			if(eval(node)) {
				builder.add(node);
			} else {
				if(canSucceedBeyond(node)) {
					builder.addAll(skip(treeSelector.apply(node).children(node),treeSelector));
				}
			}
		}
		return builder.build();
	}
	
	public abstract boolean canSucceedBeyond(T node);
	
//	private UniversalPredicate<T, Nothing> _predicate;
	public abstract boolean eval(T node);
	
	public abstract Class<T> type();

}
