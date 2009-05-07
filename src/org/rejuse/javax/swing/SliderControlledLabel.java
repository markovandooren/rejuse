package org.rejuse.javax.swing;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * <p>A SliderControlledLabel is a JLabel that keeps its text equal to the
 * value of a JSlider.</p>
 *
 * <center>
 *   <img src="doc-files/SliderControlledLabel.png"/>
 * </center>
 *
 * <p><b>Do not register this SliderControlledLabel more than once with
 * getSlider(). Termination will not work because before jdk 1.4, JSlider
 * does not have a getChangeListeners() method.</b></p>
 *
 * @see     javax.swing.JLabel
 * @see     javax.swing.JSlider
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public class SliderControlledLabel extends JLabel implements ChangeListener {
	
 /*@
	 @ public invariant (* If getSlider() is not sending events,
	 @                     getText().equals(
	 @                           Integer.toString(getSlider().getValue())
	 @                              )
	 @                   *);
	 @ // The slider may not be null
	 @ public invariant getSlider() != null;
	 @ // This SliderControlledLabel is registered as a ChangeListener with the slider.
   @ // Only for jdk 1.4 :
	 @ // public invariant (\exists int i; i >= 0 && i < getSlider().getChangeListeners().length;
	 @ //                    getSlider().getChangeListeners()[i] == this);
	 @*/

	/**
	 * Initialize a new SliderControlledLabel
	 * that gets its value of the given slider.
	 *
	 * @param slider 
	 *        The slider this SliderControlledLabel has to show 
	 *        the value of.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre slider != null;
	 @
	 @ post getSlider() == slider;
	 @ post getText().equals(Integer.toString(slider.getValue()));
	 @*/
	public SliderControlledLabel(final JSlider slider){
		super();
	
		// initial update
		setText(Integer.toString(slider.getValue()));
		
		// register as listener with the JSlider.
		slider.addChangeListener(this);
		  	
	}

	/**
	 * Update this SliderControlledLabel.
	 *
	 * @param event
	 *        The event sent by the JSlider.
	 */
 /*@
	 @ also public behavior
	 @
	 @ post getText().equals(Integer.toString(getSlider().getValue()));
	 @*/
	public void stateChanged(ChangeEvent evt){
		setText(Integer.toString(_slider.getValue()));
	}

	/**
	 * Return the slider that controls the value of this SliderControlledLabel.
	 */
	public /*@ pure @*/ JSlider getSlider() {
		return _slider;
	}

	/**
	 * Terminate this SliderControlledLabel.
	 */
 /*@
	 @ public behavior
	 @
	 @ post getSlider() == null;
	 @ // <b>Can not work correct for pre-1.4 jdk's.</b>
   @ // jdk 1.4 only:
	 @ //post (\forall int i; i >= 0 && i< \old(getSlider()).getChangeListeners().length;
	 @ //       \old(getSlider()).getChangeListeners()[i] != this);
	 @*/
	public void terminate() {
		// For jdk 1.4, uncomment this code.
		//ChangeListener[] listeners = _slider.getChangeListeners();
		// remove all occurrences
		// for(int i=0; i < listeners.length; i++) {
		//	if(listeners[i]==this) {
				_slider.removeChangeListener(this);
		//	}
		//}
		_slider = null;
	}

 /*@
	 @ private invariant _slider != null;
	 @*/
	private JSlider _slider;
}
/*
 * <copyright>Copyright (C) 1997-2001. This software is copyrighted by 
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
