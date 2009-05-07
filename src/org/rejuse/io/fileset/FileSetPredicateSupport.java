package org.rejuse.io.fileset;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.predicate.Predicate;

/**
 * <p>A class that can be used when implementing a FileSetPredicate.</p>
 *
 * <center>
 *   <img src="doc-files/FileSetPredicateSupport.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
class FileSetPredicateSupport {


  //TODO : remove this thing, I can't see what it's use is
  
  /**
   * Initialize a new FileSetPredicateSupport with the given predicate
   *
   * @param predicate
   *        The predicate encapsulated by the new FileSetPredicateSupport
   */
 /*@
   @ public behavior
   @
   @ pre predicate != null;
   @
   @ post getPredicate() == predicate;
   @*/
  public FileSetPredicateSupport(Predicate predicate) {
    _predicate = predicate;
  }

  /**
   * Return the predicate encapsulated by this FileSetPredicateSupport.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ Predicate getPredicate() {
    return _predicate;
  }

  /*@
    @ private invariant _predicate != null;
    @*/
  private Predicate _predicate;

 /*@
   @ public behavior
   @
   @ post \result.contains(getPredicate());
   @ post \result.size() == 1;
   @*/
  public /*@ pure @*/ List getSubPredicates() {
    List result = new ArrayList();
    result.add(_predicate);
    return result;
  }

 /*@
   @ public behavior
   @
   @ post \result == 1;
   @*/
  public /*@ pure @*/ int nbSubPredicates() {
    return 1;
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
