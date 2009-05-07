package org.rejuse.io.fileset;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.rejuse.java.collections.RobustVisitor;
import org.rejuse.java.collections.Visitor;
import org.rejuse.predicate.Or;
import org.rejuse.predicate.Predicate;
import org.rejuse.predicate.PrimitiveTotalPredicate;
/*@ model import org.jutil.predicate.TypePredicate; @*/
/*@ model import org.jutil.java.collections.Collections; @*/

/**
 * <p>A class of objects that represent a set of files.</p>
 *
 * <center>
 *   <img src="doc-files/FileSet.png"/>
 * </center>
 *
 * <p>A FileSet represents a <a href="FileSet.html#getFiles()">set of files</a> which satisfy certain
 * criteria. In order for a file to be included, it must satisfy at least one 
 * <a href="FileSet.html#includePredicate()">include criterium</a>, and may
 * not satify any <a href="FileSet.html#excludePredicate()">exclude criteria</a>.</p>
 *
 * <p>A criterium consists of a <a href="FileSetPredicate.html"><code>FileSetPredicate</code></a>,
 * which may have additional criteria embedded in itself. The <code>FileSetPredicate</code> is necessary in
 * order to be able to provide an efficient way for calculating all the files in a <code>FileSet</code>.</p>
 *
 * <p>This class was inspired by the fileset from <a href="http://jakarta.apache.org/ant/index.html">Ant</a>.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class FileSet {

	/**
	 * <p>Initialize a new FileSet with includes all files.</p>
	 * <p>Since the logical <code>or</code> of 0 predicates is <code>false</code>,
   * the new FileSet will initially be empty.</p>
	 */
 /*@
	 @ public behavior
	 @
	 @ // include all files
	 @ post includePredicate().nbSubPredicates() == 0;
	 @ // exclude no files
	 @ post excludePredicate().nbSubPredicates() == 0;
	 @*/
	public FileSet() {
		_includePredicates = new Or();
		_excludePredicates = new Or();
	}

	/**
	 * Return the files in this FileSet.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \fresh(\result);
	 @ post (\forall Object o; Collections.containsExplicitly(\result,o);
	 @        (o instanceof File) && contains((File)o));
   @ post (\forall File f; contains(f); Collections.containsExplicitly(\result,f));
	 @*/
	public /*@ pure @*/ Set getFiles() throws Exception {
    final List roots = new ArrayList();

    // Ask all file predicate to suggest directories
    // in which matching files might be found
    new Visitor() {
      public void visit(Object o) {
        roots.addAll(((FileSetPredicate)o).suggestDirectories());
      }
    }.applyTo(_includePredicates.getSubPredicates());

    List directories = roots;

    // DEBUG
//    System.out.println("##### SUGGESTED DIRECTORIES #####");
//    new Visitor() {
//      public void visit(Object o) {
//        System.out.println(((File)o).getAbsolutePath());
//      }
//    }.applyTo(directories);
//    System.out.println("#################################");

		final Set files = new TreeSet();
		new RobustVisitor() {
			public Object visit(Object o) throws Exception {
				files.addAll(getFiles((File)o));
        // No undo data required
        return null;
			}
      public void unvisit(Object element, Object undo) {
        // Do nothing here
      }
		}.applyTo(directories);

		return files;
	}

  /**
   * Retrieve all matching files in the given directory.
   *
   * @param directory
   *        The directory to search for files.
   */
 /*@
	 @ private behavior
	 @
	 @ pre directory != null;
	 @ pre directory.isDirectory();
   @
   @ post \fresh(\result);
   @ post (\forall Object o; Collections.containsExplicitly(\result, o);
   @        o instanceof File && contains((File)o));
   @ // FIXME other direction
   @ // FIXME the returned files will be in this directory, or a
   @ //       subdirectory.
	 @*/
	private /*@ pure @*/ List getFiles(final File directory) throws Exception {
		ArrayList list = new ArrayList();
    // Check whether or not some predicate wants to enter this directory or not.
    boolean process = new PrimitiveTotalPredicate() {
                        public boolean eval(Object o) {
                          return ((FileSetPredicate)o).enterDirectory(directory);
                        }
                      }.exists(_includePredicates.getSubPredicates());
    if(process) {
      //System.out.println("Entering "+directory.getAbsolutePath());
  		File[] files = directory.listFiles();
      // If the user has no read permission in a directory,
      // listFiles() will return null.
      if(files != null) {
        for(int i=0; i<files.length; i++) {
          File entity = files[i];
          // recurse if the entity is a directory
          // don't test, we only test on files. 
          // This is inefficient, maybe FileSetPredicate should
          // support alternative methods.
          if(entity.isDirectory()) {
            list.addAll(getFiles(entity));
          }
          // If the entity is a file, check whether or not
          // it should be included, and add it to the set
          // if that is the case
          else {
            if(contains(entity)) {
              list.add(entity);
            }
          }
        }
      }
      else {
        // assert process == false;
        // What do we do here ? FIXME
      }
    }
		return list;
	}

	/**
	 * <p>Include files for which the given fileset predicate evaluates
	 * to <code>true</code> in this FileSet.</p>
	 *
	 * @param predicate
	 *        The predicate that must be added to the include
	 *        criteria of this FileSet.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre predicate != null;
	 @
	 @ post includePredicate().nbSubPredicates() == \old(includePredicate().nbSubPredicates()) + 1;
	 @ post includePredicate().predicateAt(includePredicate().nbSubPredicates()) == predicate;
	 @*/
	public void include(FileSetPredicate predicate) {
		_includePredicates.add(predicate);
	}

	/**
	 * <p>Exclude files for which the given filset predicate evaluates
	 * to <code>true</code> from this FileSet.</p>
	 *
	 * @param predicate
	 *        The predicate that must be added to the exclude
	 *        criteria of this FileSet.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre predicate != null;
	 @
	 @ post excludePredicate().nbSubPredicates() == \old(excludePredicate().nbSubPredicates()) + 1;
	 @ post excludePredicate().predicateAt(excludePredicate().nbSubPredicates()) == predicate;
	 @*/
	public void exclude(FileSetPredicate predicate) {
		_excludePredicates.add(predicate);
	}

	/**
	 * <p>Remove the given predicate from the include criteria of
	 * this FileSet.</p>
	 *
	 * @param predicate
	 *        The predicate to remove from the include criteria
	 */
 /*@
	 @ public behavior
	 @
	 @ // All predicates different from the given predicate
	 @ // are moved x places to the left, where x is the
	 @ // number of occurrences of the given predicate
	 @ // that occur before that predicate.
	 @ post (\forall int i; i > 0 && i < \old(includePredicate().nbSubPredicates());
	 @   \old(includePredicate().predicateAt(i)) != predicate ==> (
	 @   		\old(includePredicate().predicateAt(i)) == includePredicate().predicateAt(
	 @      	i - (int)(\num_of int j; j > 0 && j < i; 
	 @               \old(includePredicate().predicateAt(j)) == predicate
	 @            )
	 @      )
	 @   )
	 @ );
	 @ // The new number of subpredicates equals the old number
	 @ // of subpredicates minus the number of occurrences of the
	 @ // given predicate.
	 @ post includePredicate().nbSubPredicates() == \old(includePredicate().nbSubPredicates()) -
	 @          (\num_of int i; i> 0 && i <= \old(includePredicate().nbSubPredicates());
	 @             \old(includePredicate().predicateAt(i)) == predicate);
	 @*/
	public void removeInclude(FileSetPredicate predicate) {
		_includePredicates.removeAll(predicate);
	}

	/**
	 * <p>Remove the given predicate from the exclude criteria of
	 * this FileSet.</p>
	 *
	 * @param predicate
	 *        The predicate to remove from the exclude criteria
	 */
 /*@
	 @ public behavior
	 @
	 @ // All predicates different from the given predicate
	 @ // are moved x places to the left, where x is the
	 @ // number of occurrences of the given predicate
	 @ // that occur before that predicate.
	 @ post (\forall int i; i > 0 && i < \old(excludePredicate().nbSubPredicates());
	 @   \old(excludePredicate().predicateAt(i)) != predicate ==> (
	 @   		\old(excludePredicate().predicateAt(i)) == excludePredicate().predicateAt(
	 @      	i - (int)(\num_of int j; j > 0 && j < i; 
	 @               \old(excludePredicate().predicateAt(j)) == predicate
	 @            )
	 @      )
	 @   )
	 @ );
	 @ // The new number of subpredicates equals the old number
	 @ // of subpredicates minus the number of occurrences of the
	 @ // given predicate.
	 @ post excludePredicate().nbSubPredicates() == \old(excludePredicate().nbSubPredicates()) -
	 @          (\num_of int i; i> 0 && i <= \old(excludePredicate().nbSubPredicates());
	 @             \old(excludePredicate().predicateAt(i)) == predicate);
	 @*/
	public void removeExclude(FileSetPredicate predicate) {
		_excludePredicates.removeAll(predicate);
	}

	/**
	 * Check whether or not the given file is in this FileSet.
	 *
	 * @param file
	 *        To file to be checked.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == includePredicate().eval(file) && !excludePredicate().eval(file);
	 @*/
	public /*@ pure @*/ boolean contains(File file) throws Exception {
		//System.out.println("Matching "+file.getAbsolutePath());
		return (_includePredicates.eval(file) && (! _excludePredicates.eval(file)));
	}

  /**
	 * <p>Return the predicate which should evaluate to <code>true</code> for
	 * a file in order to be included in this FileSet.</p>
	 * 
	 * <p>If you modify the predicates in the returned <code>Or</code>, the behavior
	 * of this FileSet will also change because we cannot perform a deep clone without
	 * forcing all predicates to implement <code>clone()</code>, which is too much of
	 * a burden. Modifying the result itself does not affect this FileSet.</p>
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \fresh(\result);
	 @ // The result is completely composed of file predicates, and thus
	 @ // can not throw exceptions.
	 @ post (\forall int i; i>0 && i <= \result.nbSubPredicates();
	 @         \result.predicateAt(i) instanceof FileSetPredicate);
	 @*/
	public /*@ pure @*/ Or includePredicate() {
		Predicate[] array = new Predicate[0];
		_includePredicates.getSubPredicates().toArray(array);
		return new Or(array);
	}

  /**
	 * <p>Return the predicate which should evaluate to <code>true</code> for
	 * a file in order to be excluded in this FileSet.</p>
	 * 
	 * <p>If you modify the predicates in the returned <code>Or</code>, the behavior
	 * of this FileSet will also change because we cannot perform a deep clone without
	 * forcing all predicates to implement <code>clone()</code>, which is too much of
	 * a burden. Modifying the result itself does not affect this FileSet.</p>
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \fresh(\result);
	 @ // The result is completely composed of file predicates, and thus
	 @ // can not throw exceptions.
	 @ post (\forall int i; i>0 && i <= \result.nbSubPredicates();
	 @         \result.predicateAt(i) instanceof FileSetPredicate);
	 @*/
	public /*@ pure @*/ Or excludePredicate() {
		Predicate[] array = new Predicate[0];
		_includePredicates.getSubPredicates().toArray(array);
		return new Or(array);
	}

  /**
   * A class of Visitors that operate on files, and can additionally
   * perform a visit on all elements in its FileSet.
   */
	public abstract class FileVisitor extends RobustVisitor {

   /*@
     @ also public behavior
     @
     @ post ! (Object instanceof File) ==> (\result == false);
     @
     @ public model boolean isValidElement(Object element);
     @*/

    /**
     * Apply visit(File) to the given object, which must be a File.
     */
    public final Object visit(Object element) throws Exception{
      return visit((File)element);
    }

    /**
     * Perform an action on the given File.
     */
   /*@
     @ public behavior
     @
     @ signals (Exception) !isValidElement(file);
     @*/
		public abstract Object visit(File file) throws Exception;

    /**
     * Visit all Files in this FileSet.
     */
    public void apply() throws Exception {
      applyTo(FileSet.this.getFiles());
    }
	}

	/**
	 * An Or predicate containing all the criteria used
	 * to determine whether or not a file should be included
	 * in this FileSet.
	 */
 /*@
	 @ private invariant _includePredicates != null;
	 @ private invariant new TypePredicate(FileSetPredicate.class).forall(_includePredicates.getSubPredicates());
	 @*/
	private Or _includePredicates;

	/**
	 * An Or predicate containing all the criteria used
	 * to determine whether or not a file should be excluded
	 * from this FileSet.
	 */
 /*@
	 @ private invariant _excludePredicates != null;
	 @ private invariant new TypePredicate(FileSetPredicate.class).forall(_excludePredicates.getSubPredicates());
	 @*/
	private Or _excludePredicates;
  
  public static void main(String[] args) throws Exception {
    FileSet fileSet = new FileSet();
    File parent = new File(args[0]);
    File parentExclude = new File(args[2]);
    PatternPredicate predicate = new PatternPredicate(parent, new FileNamePattern(args[1]));
    PatternPredicate excludePredicate = new PatternPredicate(parent, new FileNamePattern(args[3]));
    //System.out.println("pattern : "+predicate.getPattern().getPattern());
    //System.out.println("regex pattern : "+predicate.getPattern().getRegexPattern());
    //System.out.println("full regex pattern : "+predicate.getFullRegexPattern());
    fileSet.include(predicate);
    fileSet.exclude(excludePredicate);
    Set files = fileSet.getFiles();
    new Visitor() {
      public void visit(Object o) {
        System.out.println(o);
      }
    }.applyTo(files);
  }
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
