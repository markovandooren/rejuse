package org.aikodi.rejuse.data.tree;

import java.util.ArrayList;
import java.util.List;

import org.aikodi.rejuse.exception.Handler;
import org.aikodi.rejuse.exception.Handler.Executor;

public class GuardedTreeStructure<T, I extends Exception, O extends Exception> implements TreeStructure<T,O> {

	private TreeStructure<T, I> _original;
	Handler<? super I,? extends O> _handler;
	
	public GuardedTreeStructure(TreeStructure<T, I> original, Handler<? super I,? extends O> handler) {
		_original = original;
		_handler = handler;
	}
	
	@Override
	public T node() {
		return _original.node();
	}

	@Override
	public T parent() {
		return _original.parent();
	}

	@Override
	public List<T> children() throws O {
		List<T> result = new ArrayList<>();
		Executor<I> executor = () -> result.addAll(_original.children());
		_handler.execute(executor);
		return result;
	}

	@Override
	public TreeStructure<T, O> tree(T node) {
		return new GuardedTreeStructure<>(_original.tree(node), _handler);
	}

}
