package org.rejuse.event;

import java.util.EventListener;
import java.util.EventObject;

/**
 * <p>Instances of this type add the posibility for users to <em>ask</em>
 * whether the instance is applicable to a given listener/event
 * combination.</p>
 *
 * <p>The {@link ChainNotifier} chains instances of this type. It uses
 * {@link isApplicable(EventListener, EventObject)} to decide whether
 * or not to use a particular applicability notifier to notify a
 * particular listener with a particular event.</p>
 *
 * <pre>
 * ...
 *
 * /oo
 *   o also public behavior
 *   o
 *   o pre listener != null;
 *   o
 *   o post \result == listener instanceof <var>EventName</var>Listener;
 *   oo/
 * public boolean isApplicable(EventListener listener, EventObject event) {
 *   return (listener instanceof <var>EventName</var>Listener);
 * }
 * </pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 *
 * @author  Jan Dockx
 */
public interface ApplicabilityNotifier extends Notifier {

  /*
   * TODO: what should be done with this ? This explains Design By Contract.
   *
   * <p>The actual preconditions of the method
   * {@link Notifier#notifyEventListener(EventListener, EventObject)} may be
   * stronger than this method. {@link isApplicable(EventListener,
   * EventObject)} should only test just enough to determine
   * whether or not the intance can deal with this listener/event
   * combination <em>in the context of the listener/event combinations that
   * actually will be offered to it</em>. E.g., if we know for sure that
   * the event argument will never be null, this method does not have to
   * return <literal>false</literal> if it is called with an actual null argument,
   * although the model method {@link Notifier#isValidEvent(EventObject)
   * does. More specifically, there does not have to be code to check this. The
   * specification is written this way in the interest of performance.</p>
   * <p>Typically, the method checks the type of the listener.
   * {@link TypeApplicabilityNotifier} is a subclass that offers support
   * for this.</p>
   */

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>If a listener or event is valid, then this must return true.
   *   The implication in the formal specification means that the
   *   model methods can be stronger than the implementation of this
   *   method. This method only needs to test things which cannot be
   *   proven.</p>
   */
 /*@
   @ public behavior
   @
   @ pre listener != null;
   @
   @ post isValidListener(listener) && isValidEvent(event) ==> (\result == true);
   @*/
  public boolean isApplicable(EventListener listener, EventObject event);
        
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
