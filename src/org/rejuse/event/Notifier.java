package org.rejuse.event;

import java.util.EventListener;
import java.util.EventObject;

/**
 * <p>Notifier instances are used to call the correct call-back method
 * defined in a listener type when {@link EventSourceSupport} fires
 * events. The method {@link EventSourceSupport.fireEvent(EventObject,
 * Notifier)} presents the event to the notifier for each listener
 * that is registered in the {@link EventSourceSupport} instance.</p>
 *
 * <p>Most often, this interface is implemented in an inner class of the
 * actual event source that used the {@link EventSourceSupport} instance.
 * A typical implementation looks like this:</p>
 * <pre>
 * ...
 * static final private Notifier _<var>eventName</var>Notifier =
 *     new Notifier() {
 *
 *           /oo
 *            o also public behavior
 *            o
 *            o post \result == (event != null) && 
 *            o                 (event instanceof <var>EventName</var>Event);
 *            o public model boolean isValidEvent(EventObject event);
 *            o/
 *
 *           /oo
 *            o also public behavior
 *            o
 *            o     pre listener != null;
 *            o     post \result == (listener instanceof <var>EventName</var>Listener);
 *            o public model boolean isValidListener(EventListener listener);
 *            o/
 *
 *           /oo
 *            o also public behavior
 *            o
 *            o pre listener != null;
 *            o pre isValidListener(listener);
 *            o pre isValidEvent(event);
 *            o
 *            o post (* ((<var>EventName</var>Listener)listener).<var>eventName</var>((<var>EventName</var>Event)event)
 *            o             is called *);
 *            o/
 *           public void notifyEventListener(EventListener listener, EventObject event) {
 *             ((<var>EventName</var>Listener)listener).<var>eventName</var>((<var>EventName</var>Event)event);
 *           }
 *         };
 * ...
 * </pre>
 * <p>{@link EventTypeReliancy} and {@link ListenerTypeReliance} offer
 *   support for the case where the validity of events or listeners is
 *   only limited to type.</p>
 * <p>In the above example, the notifier instance has no state, and can
 *   thus be stored in a final class variable, for use in all event
 *   firings. An event firing typically looks like this:</p>
 * <pre>
 * public void fooMutator(<samp>arguments</samp>) { 
 *   <samp>store old state</samp>
 *   <samp>do mutation</samp>
 *   <var>EventName</var>Event event = <samp>create new event using old and new state</samp>;
 *   $<var>eventName</var>EventSourceSupport.fireEvent(event, _<var>eventName</var>Notifier);
 * }
 * </pre>
 * <p>In case more elaborate firing mechanisms are needed, different
 *   notifiers can be created in the actual event source class, potentially
 *   as instance variables with state, potentially as inner classes in the
 *   mutator method, so that they have access to the full context of the
 *   mutator. If the $<var>eventName</var>EventSourceSupport is used to
 *   store listeners of different types, a chain of responsibility of
 *   notifiers can be used (see {@link ChainNotifier}).</p>
 * <p>{@link ApplicabilityNotifier} adds the possibility to check the
 *   applicability of a notifier in particular listener/event combinations
 *   at runtime. {@link TypeApplicabilityNotifier} adds specification that
 *   makes it more easy to specify that listeners and events of a
 *   particular type are valid.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Jan Dockx
 */
public interface Notifier /* extends ListenerValidity */ {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * This model inspector can be used to transport information about
   * the event argument of notifyEventListener from the actual event
   * source providing the event to the class implementing this interface.
   */ 
 /*@
   @ public pure model boolean isValidEvent(EventObject event);
   @*/
    
  /**
   * <p>Subclasses need to overwrite this method to call the call-back
   *   method defined in {@link listenerType} with
   *   <formal-arg>event</formal-arg>.</p>
   * <p>The postcondition is abstract. This is used in {@link
   *   EventSourceSupport} to specify that this method will indeed be
   *   called by {@link EventSourceSupport#fireEvent(EventObject,
   *   Notifier)}.</p>
   */
 /*@
   @ public behavior
   @   pre listener != null;
   @   pre isValidListener(listener);
   @   pre isValidEvent(event);
   @   assignable \fields_of(\reach(listener)), \fields_of(\reach(event)),
   @                \fields_of(\reach(this));
   @
   @   post notifyEventListenerCalled(listener, event);
   @*/
  public void notifyEventListener(EventListener listener, EventObject event);
  
  /**
   * <p>This model inspector is an abstract postcondition for {@link
   *   notifyEventListener(EventListener, EventObject)}. It can be used
   *   by clients to specify that {@link notifyEventListener(EventListener,
   *   EventObject)} is indeed called.</p>
   * <p>This is used in {@link EventSourceSupport} to specify that
   *   {@link notifyEventListener(EventListener, EventObject)} will be
   *   called by {@link EventSourceSupport#fireEvent(EventObject, Notifier)}.
   *   Because that class can only talk about this type, and not about a
   *   specific subtype where the abstract postcondition is elaborated,
   *   the abstract postcondition can only be made true by actually calling
   *   {@link notifyEventListener(EventListener, EventObject)}.</p>
   * <p>This techique depends on the Java limitation of not allowing covariant
   *   argument type strengthening. Otherwise, the
   *   {@link EventSourceSupport#fireEvent(EventObject, Notifier)} could be
   *   strengthened in a subclass of {@link EventSourceSupport} to only accept
   *   notifiers of a subtype of this interface, where a new  method could
   *   be added that uses this model method as abstract postcondition also.
   *   That class could implement {@link
   *   EventSourceSupport#fireEvent(EventObject, Notifier)} correctly then
   *   by calling that new method on all listeners, instead of {@link
   *   notifyEventListener(EventListener, EventObject)}.</p>
This cannot be replaced with a model field, because if it was: in this type,
there is no method that says it is assignable, except notifyEventListener,
which sets it to true. So, we can conclude that, once we used this notifier
to notify a listener, it never becomes false again. So, the postcondition of
the fireEvent method is trivially true for all future events.
Second note: this probably isn't true: we need the event/listener combination,
and we only can get that with a method anyway. In any case, this would prohibit
sending the same event twice.
   */
  /*@
    @ public behavior
    @   pre listener != null;
    @   pre isValidListener(listener);
    @   pre isValidEvent(event);
    @ public pure model boolean notifyEventListenerCalled(
    @                             EventListener listener, EventObject event);
    @*/
    
}
/*
 * <copyright>Copyright (C) 1997-2002. This software is copyrighted by 
 * the people and entities mentioned after the "@author" tags above, on 
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
