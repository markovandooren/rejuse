package org.aikodi.rejuse.graph;

public class DefaultNodeFactory<V> implements NodeFactory<V> {

	@Override
	public Node<V> createNode(V object) {
		return new Node<>(object);
	}

}
