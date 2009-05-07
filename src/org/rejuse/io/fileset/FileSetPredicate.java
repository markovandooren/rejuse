package org.rejuse.io.fileset;

import java.io.File;
import java.util.List;

import org.rejuse.predicate.Predicate;
/*@ model import org.jutil.java.collections.Collections; @*/

/**
 * <p>A special class of predicates that allow a FileSet to work efficiently.</p>
 *
 * <center>
 *   <img src="doc-files/FileSetPredicate.png"/>
 * </center>
 *
 * <p>A FileSetPredicate allows a FileSet to limit the set of directories to process. It accomplishes
 * this by allowing the FileSetPredicate to suggest a list of directories which will certainly
 * contain all files that will evaluate to <code>true</code> for the predicate. It also provides a
 * method for checking if a certain directory can contain files that evaluate to <code>true</code>.
 * </p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface FileSetPredicate extends Predicate {

  /**  
   * <p>Suggest a list of directories which can contain files that
   * will evaluate to <code>true</code> for this FilePredicate.</p>
   *
   * <p>All files that can make this FilePredicate <code>true</code> will
   * be in one of the returned directories.</p>
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ // The result only contains non-null directories.
   @ post (\forall Object o; Collections.containsExplicitly(\result, o);
   @        (o instanceof File) && ((File)o).isDirectory());
   @*/
  public /*@ pure @*/ List suggestDirectories();

  /**
   * Check whether or not the given directory <b>can</b> contain
   * files which make this predicate evaluate to <code>true</code>.
   *
   * @param directory
   *        The directory to be checked
   */
 /*@
   @ public behavior
   @
   @ pre directory != null;
   @
   @ post \result == true | \result == false;
   @*/
  public /*@ pure @*/ boolean enterDirectory(File directory);
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
