package org.aikodi.rejuse.association;
//package be.kuleuven.cs.distrinet.rejuse.association;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import java.lang.ref.SoftReference;
//
//public class LoadableBidiList<FROM,TO> extends AbstractOrderedMultiAssociation<FROM, TO> {
//
//	public static abstract class Loader<T> {
//
//		private SoftReference<T> _reference;
//
//		public T get() {
//			T result;
//			SoftReference<T> reference = _reference;
//			if(reference != null) {
//				result = reference.get();
//				if(result == null) {
//					reference = null;
//					_reference = null;
//					_listeners.forEach(l -> l.unloaded(this));
//				} else {
//					return result;
//				}
//			} 
//			result = load();
//			// Just to satisfy the compiler. From this point on
//			// result isn't modified anymore.
//			final T loaded = result;
//			if(result == null) {
//				throw new IllegalStateException("Loaded a null reference.");
//			}
//			reference = new SoftReference<T>(result);
//			_reference = reference;
//			_listeners.forEach(l -> l.loaded(this, loaded));
//			return result;
//		}
//
//		protected abstract T load();
//
//		private List<LoadListener<T>> _listeners;
//
//		public void addListener(LoadListener<T> listener) {
//			if(listener == null) {
//				throw new IllegalArgumentException();
//			}
//			if(_listeners == null) {
//				_listeners = new ArrayList<>();
//			}
//			_listeners.add(listener);
//		}
//
//		public void removeListener(LoadListener<T> listener) {
//			if(listener == null) {
//				throw new IllegalArgumentException();
//			}
//			if(_listeners != null) {
//				_listeners.remove(listener);
//				if(_listeners.isEmpty()) {
//					_listeners = null;
//				}
//			}
//		}
//	}
//
//	public interface LoadListener<T> {
//		void loaded(Loader<T> loader, T object);
//
//		void unloaded(Loader<T> loader);
//	}
//
//	public LoadableBidiList(FROM object) {
//		super(object);
//	}
//
//	public LoadableBidiList(FROM object, int initialCapacity) {
//		super(object, initialCapacity);
//	}
//
//	private List<Loader<Association<? extends TO, ? super FROM>>> _elements;
//	
//	@Override
//	public Association<? extends TO, ? super FROM> associationAt(int index) {
//		checkIndex(index);
//		return _elements.get(index).get();
//	}
//	
//	public void add(Loader<Association<? extends TO, ? super FROM>> loader) {
//		if(loader == null) {
//			throw new IllegalArgumentException();
//		}
//	}
//
//	protected void checkIndex(int index) {
//		if(index < 0 || index >= size()) {
//			throw new IllegalArgumentException("Index "+index+" is not valid. The assocation has "+size() +" elements.");
//		}
//	}
//
//	@Override
//	protected void setAssociationAt(int index, Association<? extends TO, ? super FROM> association) {
//		todo
//	}
//
//	@Override
//	public int indexOfAssociation(Association<? extends TO, ? super FROM> association) {
//	}
//
//	@Override
//	protected boolean isStored() {
//	}
//
//	@Override
//	protected List<Association<? extends TO, ? super FROM>> internalAssociations() {
//	}
//
//	@Override
//	protected void initStorage() {
//		todo
//	}
//
//	@Override
//	protected boolean removeAssociation(Association<? extends TO, ? super FROM> association) {
//	}
//
//	@Override
//	public void clear() {
//		todo
//	}
//
//}
