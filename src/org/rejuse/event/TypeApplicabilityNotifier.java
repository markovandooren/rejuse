package org.rejuse.event;


import java.util.EventListener;
import java.util.EventObject;


/**
 * <p>A support class for optional notifiers that depend on the type of
 *   the listener and the event to be processed.</p>  
 * <p>Most often a {@link ChainNotifier} will be used in the case
 *   where the {@link EventSourceSupport} contains listeners of
 *   different types, or when different call-back methods of the
 *   listeners need to be called depending on the type of the event
 *   fired. This class implements the {@link
 *   ApplicabilityNotifier#isApplicable(EventListener, EventObject)} method
 *   to check whether the type of the listener and the event is
 *   compatible with a given listener type and event type.</p>
 * <p>Note that we do not demand this type to be <cite>pure</cite> (see
 *   JML), although all the methods we offer are.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @release $Name$
 */
abstract public class TypeApplicabilityNotifier
    implements ApplicabilityNotifier
    /*@ , ListenerTypeReliancy, EventTypeReliancy @*/ {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /*@
    @ public behavior
    @   pre listenerType != null;
    @   pre Class.forName("java.util.EventListener").isAssignableFrom(listenerType);
    @   pre eventType != null;
    @   pre Class.forName("java.util.EventObject").isAssignableFrom(eventType);
    @   assignable listenerType, eventType;
    @   post this.listenerType == listenerType;
    @   post this.eventType == eventType;
    @*/
  public /*@ pure @*/  TypeApplicabilityNotifier(Class listenerType, Class eventType) {
    $listenerType = listenerType;
    $eventType = eventType;
  }

  /*@
    @ public behavior
    @   pre listenerType != null;
    @   pre Class.forName("java.util.EventListener").isAssignableFrom(listenerType);
    @   assignable listenerType, eventType;
    @   post this.listenerType == listenerType;
    @   post this.eventType == Class.forName("java.util.EventObject");
    @*/
  public /*@ pure @*/ TypeApplicabilityNotifier(Class listenerType) {
    $listenerType = listenerType;
    $eventType = EventObject.class;
  }

  /*@
    @ also
    @   public behavior
    @     pre listener != null;
    @     post \result ==>
    @             (listenerType.isInstance(listener) &&
    @             ((event == null) || eventType.isInstance(event)));
    @        // remember that isInstance would be false when event == null
    @*/
  public /*@ pure @*/ boolean isApplicable(EventListener listener,
                                    EventObject event) {
    return getListenerType().isInstance(listener) &&
              ((event == null) || getEventType().isInstance(event));
  }
  
/* JDJDJD I hate to have to introduce this model field. Let's just say
          explicitly getListenerType() is a basic inspector, and use that. 
          Later we can see whether event that is necessary. Let's also
          use modifiable instead of assignable, to avoid discussions about
          not-being-able-to-assign-to-an-inspector. */
  
  /*@
    @ public behavior
    @   post \result == listenerType;
    @*/
  final public /*@ pure @*/ Class getListenerType() {
    return $listenerType;
  }
  
  /*@
    @ private depends listenerType <- $listenerType;
    @ private represents listenerType <- $listenerType;
    
    @ private invariant $listenerType != null;
    @ private invariant Class.forName("java.util.EventListener").isAssignableFrom($listenerType);
    @*/
  private Class $listenerType;
  
  /*@
    @ public behavior
    @   post \result == eventType;
    @*/
  final public /*@ pure @*/  Class getEventType() {
    return $eventType;
  }

  /*@
    @ private depends eventType <- $eventType;
    @ private represents eventType <- $eventType;

    @ private invariant $eventType != null;
    @ private invariant Class.forName("java.util.EventObject").isAssignableFrom($eventType);
    @*/
  private Class $eventType;
        
}
/*
<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the JUTIL.ORG Software License for more details. For more information,
please see http://org-jutil.sourceforge.net/</copyright>/
*/
