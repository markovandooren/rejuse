package be.kuleuven.cs.distrinet.rejuse.io.fileset;

import java.io.File;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;

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
public interface FileSetPredicate extends Predicate<File> {

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

