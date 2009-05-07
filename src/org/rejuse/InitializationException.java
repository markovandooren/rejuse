package org.rejuse;

/**
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class InitializationException extends RuntimeException{
  
  /**
   * Initialize a new InitializationException with the given
   * message
   *
   * @param message
   *        The message
   */
 /*@
   @ public behavior
   @
   @ post getMessage() == message;
   @*/
  public InitializationException(String message) {
    super(message);
  }
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
