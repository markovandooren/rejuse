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

