package be.kuleuven.cs.distrinet.rejuse.io.fileset;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.True;

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
public class LocalDisk extends AbstractPredicate<File> implements FileSetPredicate {

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

