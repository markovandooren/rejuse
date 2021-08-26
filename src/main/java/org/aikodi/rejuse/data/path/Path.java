package org.aikodi.rejuse.data.path;

import static org.aikodi.contract.Contract.requireNotNull;

public class Path<T> {

	private Path<T> _suffix;
	private T _element;
	private int _length;
	
	public Path(T element) {
		requireNotNull(element);
		
		_element = element;
		_length = 1;
	}
	
	public Path(T element, Path<T> suffix) {
		this(element);
		requireNotNull(suffix);
		
		_suffix = suffix;
		_length = suffix.length() + 1;
	}
	
	public T element() {
		return _element;
	}
	
	public Path<T> suffix() {
		return _suffix;
	}
	
	public int length() {
		return _length;
	}
	
	public Path<T> plus(T element) {
		Path<T> newSuffix;
		if (_suffix == null) {
			newSuffix = new Path<T>(element);
		} else {
			newSuffix = suffix().plus(element);
		}
		return new Path<T>(element(), newSuffix);
	}
}
