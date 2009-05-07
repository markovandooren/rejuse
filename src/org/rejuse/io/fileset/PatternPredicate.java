package org.rejuse.io.fileset;

import java.io.File;
import org.rejuse.jregex.Pattern;
import jregex.Replacer;
import java.util.List;
import java.util.ArrayList;
import org.rejuse.predicate.Predicate;
import org.rejuse.predicate.True;

/**
 * <p>A class of predicates that check whether a filename matches a certain pattern relative to a base
 * directory.</p>
 * 
 * <center>
 *   <img src="doc-files/PatternPredicate.png"/>
 * </center>
 *
 * <p>A PatternPredicate can optionally "contain" another predicate, so it can be used in filesets.</p>
 *
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class PatternPredicate extends BaseDirPredicate implements FileSetPredicate {
  
	/**
	 * Initialize a new PatternPredicate with the given base directory
   * and pattern.
	 *
	 * @param baseDir
	 *        The base directory for the new PatternPredicate.
	 * @param pattern
	 *        The pattern for the new PatternPredicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre baseDir != null;
	 @ pre baseDir.isDirectory();
   @ pre pattern != null;
	 @
	 @ post getBaseDir() == baseDir;
   @ post getPattern() == pattern;
   @ post getPredicate().equals(new True());
	 @*/
	public PatternPredicate(File baseDir, FileNamePattern pattern) {
    this(baseDir, pattern, new True());
	}

	/**
	 * Initialize a new PatternPredicate with the given base directory,
   * pattern and predicate .
	 *
	 * @param baseDir
	 *        The base directory for the new PatternPredicate.
	 * @param pattern
	 *        The pattern for the new PatternPredicate.
   * @param predicate
   *        The predicate for the new PatternPredicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre baseDir != null;
	 @ pre baseDir.isDirectory();
   @ pre pattern != null;
   @ pre predicate != null;
	 @
	 @ post getBaseDir() == baseDir;
   @ post getPattern() == pattern;
   @ post getPredicate().equals(new True());
	 @*/
	public PatternPredicate(File baseDir, FileNamePattern pattern, Predicate predicate) {
		super(baseDir);
    _pattern = pattern;
    _fullRegexPattern = stripDotDot(baseDir.getAbsolutePath() + File.separator + _pattern.getRegexPattern());
    _evalPattern = new Pattern(_fullRegexPattern);
    _support = new FileSetPredicateSupport(predicate);
	}

  /**
   * Return the pattern of this PatternPredicate.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ FileNamePattern getPattern() {
    return _pattern;
  }

  /**
   * Return the regex pattern of this PatternPredicate.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ // FIXME
   @*/
  public /*@ pure @*/ String getFullRegexPattern() {
    return _fullRegexPattern;
  }

 /*@
   @ also public behavior
   @
   @ post new Pattern(getFullRegexPattern()).matches(file.getAbsolutePath()) &&
   @      getPredicate().eval(file);
   @*/
  public /*@ pure @*/ boolean evalFile(File file) throws Exception {
    //System.out.println("Checking file : " + file.getAbsolutePath());
    //System.out.println("pattern : " + getFullRegexPattern());
    //System.out.println("Result : " + (_evalPattern.matches(file.getAbsolutePath()) && getPredicate().eval(file)));
    return _evalPattern.matches(file.getAbsolutePath()) && getPredicate().eval(file);
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
   @ also public behavior
   @
   @ post \fresh(\result);
   @ post \result.size() == 1;
   @ //TODO
   @*/
  public /*@ pure @*/ List suggestDirectories() {
    List result = new ArrayList();
    String dirName = getDeterministicPathName(); 
    File dir = new File(dirName);
    result.add(dir);
    return result;
  }

  /**
   * FIXME
   */
  public /*@ pure @*/ boolean enterDirectory(File directory) {
    String dirName = directory.getAbsolutePath()+File.separator;
    String pattern = new Pattern("[^"+getRegexSeparator()+"]*$").replacer("").replace(getBaseDir().getAbsolutePath() + File.separator + getPattern().getPattern());
    String regexPattern = patternToEnter(stripDotDot(pattern));
    //System.out.println("Directory : " + dirName);
    //System.out.println("Pattern for directory check : " + regexPattern);
    //System.out.println("Result : " + new Pattern(regexPattern).matches(dirName));
    return new Pattern(regexPattern).matches(dirName);
  }

 /*@
   @ private invariant _evalPattern != null;
   @*/
  private Pattern _evalPattern;

  private String patternToEnter(String pattern) {
     String temp = _starTransformer.replace(
                     _slashSlashRemover.replace(
                         _dotSlashRemover.replace(pattern)
                       )
                     );
    while(_starStarPattern.matcher(temp).find()) {
      temp = _starStarEnterTransformer.replace(temp);
    }
    return temp;
  }

 /*@
   @ private behavior
   @
   @ post \result != null;
   @*/
  private String getDeterministicPathName() {
    return stripDotDot(getBaseDir().getAbsolutePath() + File.separator + 
        getLongestDeterministicDirName(_pattern.getPattern())); 
  }

 /*@
   @ private behavior
   @
   @ pre name != null;
   @*/
  private String getLongestDeterministicDirName(String name) {
    return new Pattern(getRegexSeparator()+"[^"+getRegexSeparator()+"]*$").replacer("").replace(new Pattern("(|[^\\*"+getRegexSeparator()+"]*|([^\\*"+getRegexSeparator()+"]*"+getRegexSeparator()+")*[^\\*"+getRegexSeparator()+"]*)\\*.*").replacer("$1").replace(name));
  }

  private String stripDotDot(String name) {
    Pattern dotDotSlash = new Pattern("[^"+getRegexSeparator()+"]*"+getRegexSeparator()+"\\.\\."+getRegexSeparator());
    Replacer dotDotSlashReplacer = dotDotSlash.replacer(""); 
    // Keep removing ./ until there are no occurrences left
    while(dotDotSlash.matcher(name).find()) {
      name = dotDotSlashReplacer.replace(name);
    }
    return name;
  }

  /*@ pure @*/ String getRegexSeparator() {
    if (File.separator.equals("\\")) {
      return _doubleSeparator;
    }
    else {
      return File.separator;
    }
  }

 /*@
   @ private invariant _pattern != null;
   @*/
  private FileNamePattern _pattern;

  private final static String _doubleSeparator = File.separator + File.separator;

  private Pattern _starStarPattern = new Pattern("\\*\\*([^\\*]|$)(.*)");
  //private Replacer _starStarEnterTransformer = _starStarPattern.replacer("((.*$1)?$2)?");
  private Replacer _starStarEnterTransformer = _starStarPattern.replacer(".*");

  private FileSetPredicateSupport _support;

  private Replacer _dotSlashRemover = new Pattern("[^\\.]\\./").replacer("");

  private Replacer _slashSlashRemover = new Pattern(getRegexSeparator() + "{2,}").replacer(File.separator);

  private Replacer _starTransformer = new Pattern("(([^\\\\]|^)(\\\\\\\\)+|([^\\\\\\*]|^)|([^\\\\]|^)(\\\\\\\\)*\\\\\\*)\\*([^\\*]|$)").replacer("$1[^"+File.separator+"]*$7");

  private String _fullRegexPattern;


//  public static void main(String[] args) {
//    String in = args[0];
//    System.out.println("in : "+in);
//    System.out.println("out : "+_doubleStarTransformer.replace(_starTransformer.replace(in)));
    //System.out.println("pattern : "+pattern.pattern());
//    File home = new File("/home/marko");
//    File file1 = new File("/home/marko/Documents/edoc");
//    File file2 = new File("/home/marko/Documents/java/tests/inner/C.java");
//    PatternPredicate predicate = new PatternPredicate(home, "Documents/*");
//    System.out.println("full regex pattern : "+predicate.getRegexPattern());
//    System.out.println("full file1 name : "+file1.getAbsolutePath());
//    System.out.println("file1 matches : " + predicate.eval(file1));
//    System.out.println("full file2 name : "+file2.getAbsolutePath());
//    System.out.println("file2 matches : " + predicate.eval(file2));
//    predicate = new PatternPredicate(home, "Documents/**/*");
//    System.out.println("full regex pattern : "+predicate.getRegexPattern());
//    System.out.println("full file1 name : "+file1.getAbsolutePath());
//    System.out.println("file1 matches : " + predicate.eval(file1));
//    System.out.println("full file2 name : "+file2.getAbsolutePath());
//    System.out.println("file2 matches : " + predicate.eval(file2));
//    predicate = new PatternPredicate(home, "Documents/**/*.java");
//    System.out.println("full regex pattern : "+predicate.getRegexPattern());
//    System.out.println("full file1 name : "+file1.getAbsolutePath());
//    System.out.println("file1 matches : " + predicate.eval(file1));
//    System.out.println("full file2 name : "+file2.getAbsolutePath());
//    System.out.println("file2 matches : " + predicate.eval(file2));
//    predicate = new PatternPredicate(home, "Documents/**/tests/**/*.java");
//    System.out.println("full regex pattern : "+predicate.getRegexPattern());
//    System.out.println("full file1 name : "+file1.getAbsolutePath());
//    System.out.println("file1 matches : " + predicate.eval(file1));
//    System.out.println("full file2 name : "+file2.getAbsolutePath());
//    System.out.println("file2 matches : " + predicate.eval(file2));
//    predicate = new PatternPredicate(home, "Documents/**/inner/*.java");
//    System.out.println("full regex pattern : "+predicate.getRegexPattern());
//    System.out.println("full file1 name : "+file1.getAbsolutePath());
//    System.out.println("file1 matches : " + predicate.eval(file1));
//    System.out.println("full file2 name : "+file2.getAbsolutePath());
//    System.out.println("file2 matches : " + predicate.eval(file2));
//  }

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
