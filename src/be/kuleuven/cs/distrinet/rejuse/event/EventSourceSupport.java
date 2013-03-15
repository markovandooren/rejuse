package be.kuleuven.cs.distrinet.rejuse.event;

import java.util.Set;
import java.util.HashSet;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;

import java.util.EventObject;
import java.util.EventListener;
/*@ model import org.jmlspecs.models.JMLObjectSet; @*/


/**
 * <p>This class is a support class for event sources. A source of events
 *   can delegate registration, deregistration and notification of listeners
 *   for a particular kind of event to an object of this class.</p>
 * <p>The actual source of the events should have delegation methods for all
 *   the add and removal method of this class with the correct static type
 *   for the Listener.</p>
 * <p>For adding and removing listeners to the set, equals() is used.</p>
 * <p>{@link isValidEvent(EventListener)} is used as an abstract type
 *   invariant and as an abstract precondition in {@link add(EventListener)}.
 *   This makes it possible to transport information about the listeners
 *   available in subtypes, thru {@link add(EventListener)} and {@link
 *   fireEvent(EventObject, Notifier)} to the {@link Notifier} that will
 *   take care of the actual dispatch of an event to a listener.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 */
public class EventSourceSupport /* implements ListenerValidity */ {


	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /*@    
    @ // The set of listeners. Because it is a set, it does contain duplicates.
    @ public model JMLObjectSet listeners
    @   initially listeners != null && listeners.isEmpty();   

    @ // The set of listeners is never null.
    @ public invariant listeners != null;
    
    @ // The set of listeners contains no null references.
    @ public invariant ! listeners.has(null);
    
    @ // The listeners are all EventListeners.
    @ public invariant (\forall Object o; listeners.has(o);
    @                       o instanceof EventListener);
    
    @ // The listeners are all valid.
    @ public invariant (\forall EventListener l; listeners.has(l);
    @                       isValidListener(l));
    @*/
  
  /*@
    @ public behavior
    @   post \result <==> listeners.isEmpty();
    @*/
  final public /*@ pure @*/  boolean isEmpty() {
    return (_listeners == null) || _listeners.isEmpty();
  }

  /**
   * Add an event Listener to the listeners to be notified by this.
   * If the listener is already registered, the method has reached its
   * goal without doing anything.
   *
   * @param  listener
   *         The Listener which is to be added to the list of listeners.
   */
  /*@
    @ public behavior
    @   pre listener != null ==> isValidListener(listener);
    @   assignable listeners;
    @   // The given Listener is now in the set of listeners, if it is not null.
    @   post listener != null ==> listeners.equals(\old(listeners.insert(listener)));
    @   ensures_redundantly listener != null ==> listeners.has(listener);
    @*/
  final public void add(EventListener listener) {
    if (listener != null) {
      if (_listeners == null) {
        _listeners = new java.util.HashSet();
      }
      _listeners.add(listener);
    }
  }
    
  /**
   * Remove an event EventListener from the listeners to be notified by this.
   *
   * @param  listener
   *         The EventListener which is to be removed from the list of listeners.
   */
  /*@
    @ public behavior
    @   assignable listeners;
    @   // The given listener is not in the set of listeners.
    @   post listeners.equals(\old(listeners.remove(listener)));
    @   ensures_redundantly ! listeners.has(listener);
    @*/
  final public void remove(EventListener listener) {
    if (_listeners != null) {
      _listeners.remove(listener);
    }
  }

  /**
   * <formal-arg>jmlSet</formal-arg> and <formal-arg>javaSet</formal-arg>
   * contain the same elements.
   */
 /*@
   @ public behavior
   @   pre jmlSet != null;
   @   pre javaSet != null;
   @   post \result <==>
   @             (\forall Object o; javaSet.contains(o); jmlSet.has(o)) &&
   @              (\forall Object o; jmlSet.has(o); javaSet.contains(o));    
   @ static public model pure boolean isModelFor(JMLObjectSet jmlSet, Set javaSet);
   @*/
    
  /**
   * The instance variable that holds the registered listeners. We use
   * lazy initialization here.
   */
 /*@
   @ // If the set is not null, null is not in the set of listeners.
   @ private invariant _listeners != null ==> ! _listeners.contains(null);
   @
   @ // If the set is not null, it only contains objects of type
   @ // EventListener.
   @ private invariant _listeners != null ==>
   @                       (\forall Object o; _listeners.contains(o);
   @                           o instanceof EventListener);
   @
   @ // If the set is not null, it only contains valid listeners.
   @ private invariant _listeners != null ==>
   @                       (\forall EventListener l; _listeners.contains(l);
   @                           isValidListener(l));
   @*/
  private HashSet _listeners;

  //MvDMvDMvD : synchronize access to _listeners (also in clone())
  

  /**
   * <p>Calls {@link #notifyEventListener(EventListener, EventObject)} on all
   *   listeners with the given event.</p>
   * <p>The model inspector {#isValidListener(EventListener)} can be
   *   used to transport information about the registered listeners, thru
   *   this method, to the actual dispatch method in
   *   <formal-arg>notifier</formal-arg>.</p>
   *
   * @param  event
   *         The event to be sent to all registered listeners.
   * @param  notifier
   *         The notifier that will dispatch the event to the registered
   *         listeners.
   */
 /*@
   @ public behavior
   @   pre notifier != null;
   @   pre (\forall EventListener l; isValidListener(l);
   @          notifier.isValidListener(l));
   @   requires_redundantly (\forall EventListener l; listeners.has(l);
   @         notifier.isValidListener(l));
   @   pre notifier.isValidEvent(event);
   @   assignable \fields_of(\reach(listeners)), \fields_of(\reach(event)),
   @                \fields_of(\reach(notifier));
   @   post (\forall EventListener l; listeners.has(l);
   @           notifier.notifyEventListenerCalled(l, event));
   @*/
  final public void fireEvent(final EventObject event,
                              final Notifier notifier) {
    if (_listeners != null) {
      Set localListeners = (Set)_listeners.clone();
      new Visitor() {
            public void visit(Object element) {
              notifier.notifyEventListener((EventListener)element, event);
            }
          }.applyTo(localListeners);
    }
  }

  /**
   * A String representation of this. Lists all currently registered
   * listeners.  
   */
  public /*@ pure @*/ String toString() {
    String returnValue = _listeners.toString();
    return getClass().toString() + returnValue;
  }
}


