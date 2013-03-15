package be.kuleuven.cs.distrinet.rejuse.java.collections;

import java.util.Set;

public abstract class SafeTransitiveClosure<T> extends TransitiveClosure<T> {

	@Override
	public abstract void addConnectedNodes(T node, Set<T> accumulator);
	
	public /*@ pure @*/ Set<T> closureFromAll(Set<T> startSet) {
    try {
    	return super.closureFromAll(startSet);
    } catch(RuntimeException exc) {
    	throw exc;
    } catch(Exception exc) {
    	throw new Error(exc);
    }
	}

	public /*@ pure @*/ Set<T> closure(T element) {
    try {
    	return super.closure(element);
    } catch(RuntimeException exc) {
    	throw exc;
    } catch(Exception exc) {
    	throw new Error(exc);
    }
	}
	
	public /*@ pure @*/ boolean isNaiveClosure(Set<T> startSet, Set<T> naiveClosureSet) {
    try {
    	return super.isNaiveClosure(startSet, naiveClosureSet);
    } catch(RuntimeException exc) {
    	throw exc;
    } catch(Exception exc) {
    	throw new Error(exc);
    }
	}
}
