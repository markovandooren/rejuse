package org.rejuse.io.fileset;

import java.io.File;
import jregex.Replacer;
import org.rejuse.jregex.Pattern;

/**
 * <p>A class of filename patterns.</p>
 *
 * <center>
 *   <img src="doc-files/FileNamePattern.png"/>
 * </center>
 *
 * <p>A pattern consists of a sequence of primitive patterns separated by <code>File.separator</code>.
 * E.g. on unix platforms:</p>
 * <ul><li><code>primitive_pattern/primitive_pattern/primitive_pattern</code></li></ul>
 *
 * <p>A primitive pattern consists of characters that may appear in a filename or directory name 
 * (it may not contain <code>File.separator</code>, which isn't allowed on any platform anyway).
 * The * character has a special meaning. A single * matches any number of characters in a 
 * primitive_pattern. A double * matches any pattern. Some examples will make this clear.
 * <ul>
 *   <li><font color="blue"><code>document.tex</code></font> : matches a file named "document.tex" in the base directory.</li>
 *   <li><font color="blue"><code>do*tex</code></font> : matches all file of which the name starts 
 *   with "do" and ends with "tex".</li>
 *   <li><font color="blue"><code>Documents/*</code></font> : matches all files which directly reside in the "Documents" directory, which
 *   is a direct subdirectory of the base directory.</li>
 *   <li><font color="blue"><code>../Documents/*</code></font> : matches all files which directly
 *   reside in the "Documents" directory, which is a direct subdirectory of parent directory of 
 *   the base directory.</li>
 *   <li><font color="blue"><code>Documents/Presentation*&#x002f;*</code></font> : matches all files directly in a directory with a name
 *   starting with "Presentation", which resides directly in "Documents". The "Documents" directory again is
 *   a direct subdirectory of the base directory.
 *   <li><font color="blue"><code>**&#x002f;*</code></font> : matches all files in all directories 
 *   (recursively) in the base directory</li>
 *   <li><font color="blue"><code>Documents&#x002f;**&#x002f;*</code></font> : matches all files in all 
 *   subdirectories (recursively) in the "Documents" directory.</li>
 *   <li><font color="blue"><code>Documents&#x002f;**&#x002f;papers&#x002f;**&#x002f;*.pdf</code></font> : 
 *   matches all files with "pdf" as extension which resides in a directory which is a subdirectory of
 *   "Documents" and has a directory "papers" in its path relative to "Documents".</li>
 *   <li><font color="blue"><code>Documents&#x002f;**&#x002f;Presentation*&#x002f;**&#x002f;*.pdf</code></font>
 *   : matches the same as the previous pattern, but now with a directory in the path relative to "Documents"
 *   which begins with "Presentation".</li>
 * </ul>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class FileNamePattern {

	/**
	 * Initialize a new FileNamePattern with the given pattern.
	 *
	 * @param pattern
	 *        The pattern for the new PatternPredicate.
	 */
 /*@
	 @ public behavior
	 @
   @ pre pattern != null;
	 @
   @ post getPattern() == pattern;
	 @*/
	public FileNamePattern(String pattern) {
    _pattern = pattern;
    _regexPattern = patternToRegex(pattern);
	}
  
  /**
   * Return the pattern of this PatternPredicate.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ String getPattern() {
    return _pattern;
  }

  /**
   * Return the regex pattern of this PatternPredicate.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ // Waiting for switch to jdk 1.4 for formal specifications.
   @*/
  public /*@ pure @*/ String getRegexPattern() {
    return _regexPattern;
  }

  /**
   * <p>Return a String that represents a file separator in a regular expression.
   * This method is required because \ is used as escape character.</p>
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post File.separator.equals("\\") ==> \result.equals("\\\\");
   @ post ! File.separator.equals("\\") ==> \result.equals(File.separator);
   @*/
  public /*@ pure @*/ String getRegexSeparator() {
    if (File.separator.equals("\\")) {
      return _doubleSeparator;
    }
    else {
      return File.separator;
    }
  }

//  /**
//   * <p>Transform the given pattern to a regular expression.</p>
//   *
//   * @param pattern
//   *        The pattern to transform.
//   *
//   * <p>This method performs the following transformations on
//   * <a href="FileNamePattern.html#getPattern()"><code>getPattern()</code></a>.
//   * <ul>
//   *  <li>Transform ** into a pattern that matches any sequence of directories using 
//   *  <a href="FileNamePattern.html#getStarStarTransformer()"><code>getStarStarTransformer()</code></a></li>
//   *  <li>Transform * into a pattern that matches any sequence of characters exception <code>
//   *  File.separator</code> using 
//   *  <a href="FileNamePattern.html#getStarTransformer()"><code>getStarTransformer()</code></a></li>
//   *  <li>Transform 2 consecutive occurrences of <code>File.separator</code> by 1 occurrence using 
//   *  <a href="FileNamePattern.html#getSlashSlashTransformer()"><code>getSlashSlashTransformer()</code></a></li>
//   *  <li>Remove ./ occurrences using 
//   *  <a href="FileNamePattern.html#getDotSlashTransformer()"><code>getDotSlashTransformer()</code></a></li>
//   * </ul>
//   */
// /*@
//   @ public behavior
//   @
//   @ pre pattern != null;
//   @
//   @ post \result.equals(
//   @        getStarStarTransformer().replace(
//   @          getStarTransformer().replace(
//   @            getSlashSlashTransformer().replace(
//   @              getDotSlashTransformer().replace(pattern)
//   @            )
//   @          )
//   @        )
//   @      );
//   @*/
  private String patternToRegex(String pattern) {
    return _starStarTransformer.replace(
        _starTransformer.replace(
          _slashSlashRemover.replace(
              _dotSlashRemover.replace(pattern)
            )
          )
      );
  }
  
  private Replacer _dotSlashRemover = new Pattern("[^\\.]\\./").replacer("");

  private Replacer _slashSlashRemover = new Pattern(getRegexSeparator() + "{2,}").replacer(File.separator);

  private Replacer _starTransformer = new Pattern("(([^\\\\]|^)(\\\\\\\\)+|([^\\\\\\*]|^)|([^\\\\]|^)(\\\\\\\\)*\\\\\\*)\\*([^\\*]|$)").replacer("$1[^"+File.separator+"]*$7");
  //private Replacer _starTransformer = new Pattern("(([^\\\\]|^)(\\\\\\\\)+|([^\\\\\\*]|^)|([^\\\\]|^)(\\\\\\\\)*\\\\\\*)\\*([^\\*]|$)").replacer("$1["+File.separator+"]*$7");

  private Replacer _starStarTransformer = new Pattern("\\*\\*([^\\*]|$)").replacer("(.*$1)?");
  //private Replacer _starStarTransformer = new Pattern("\\*\\*([^\\*]|$)").replacer("(.*$1)*");

 /*@
   @ private invariant _pattern != null;
   @*/
  private String _pattern;

 /*@
   @ private invariant _regexPattern != null;
   @*/
  private String _regexPattern;

  private final static String _doubleSeparator = File.separator + File.separator;
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
