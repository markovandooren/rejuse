package org.rejuse.io.fileset;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import org.rejuse.predicate.Predicate;
import org.rejuse.predicate.True;

/**
 * <p>A file predicate that includes all files on local disks.<p>
 *
 * <p>Think twice before using this, traversing the entire filesystem
 * isn't worlds most efficient operation.</p>
 *
 * <center>
 *   <img src="doc-files/LocalDisk.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class LocalDisk extends FilePredicate implements FileSetPredicate {

  /**
   * Initialize a new LocalDisk.
   */
 /*@
   @ public behavior
   @
   @ post getPredicate().equals(new True());
   @*/
  public LocalDisk() {
    this(new True());
  }

  /**
   * Initialize a new LocalDisk.
   */
 /*@
   @ public behavior
   @
   @ post getPredicate() == predicate;
   @*/
  public LocalDisk(Predicate predicate) {
    //TODO what was the meaning of this constructor ?
    _support = new FileSetPredicateSupport(predicate);
  }

 /*@
   @ also public behavior
   @
   @ // All files are on a local disk
   @ post \result == (file != null);
   @*/
  public /*@ pure @*/ boolean eval(File file) {
    return (file != null);
  }

 /*@
   @ also public behavior
   @
   @ post \fresh(\result);
   @ post (\forall Object o; \result.contains(o);
   @        o instanceof File);
   @ post \result.size() == Arrays.asList(File.listRoots()).size();
   @ post \result.containsAll(Arrays.asList(File.listRoots()));
   @*/
  public /*@ pure @*/ List suggestDirectories() {
    return new ArrayList(Arrays.asList(File.listRoots()));
  }

 /*@
   @ also public behavior
   @
   @ post \result == true;
   @*/
  public /*@ pure @*/ boolean enterDirectory(File directory) {
    return true;
  }

 /*@
   @ also public behavior
   @
   @ post \result == 1;
   @*/
  public /*@ pure @*/ int nbSubPredicates() {
    return _support.nbSubPredicates();
  }

 /*@
   @ also public behavior
   @
   @ post \result.contains(getPredicate());
   @ post \result.size() == 1;
   @*/
  public /*@ pure @*/ List getSubPredicates() {
    return _support.getSubPredicates();
  }

 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ Predicate getPredicate() {
    return _support.getPredicate();
  }

  /*@
    @ private invariant _support != null;
    @*/
  private FileSetPredicateSupport _support;
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
