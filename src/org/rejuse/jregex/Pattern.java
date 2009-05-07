package org.rejuse.jregex;
import jregex.PatternSyntaxException;
import jregex.REFlags;

/**
 * <p>A class that adapts the <code>jregex.Pattern</code> interface to the 
 * <code>java.util.regex.Pattern</code> interface.</p>
 * 
 * <center>
 *   <img src="doc-files/Pattern.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Pattern extends jregex.Pattern {

 /*@
	 @ public invariant pattern() != null;
	 @*/

	// Copy some static variables for better syntax compatibility
	// with jdk 1.4
	public static final int DEFAULT = REFlags.DEFAULT;
	public static final int DOTALL = REFlags.DOTALL;
	public static final int MULTILINE = REFlags.MULTILINE;

	/**
   * Initialize a new Pattern with default flags and the
	 * given regular expression
	 *
   * @param regex  
	 * 				The regular expression string.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre regex != null;
	 @ 
	 @ post pattern().equals(regex);
	 @ post flags() == DEFAULT;
	 @
   @ signals (PatternSyntaxException)  (* The given String is not a valid regular expression *);
   @*/
  public Pattern(String regex) throws PatternSyntaxException{
    super(regex);
		_flags=DEFAULT;
  }

  /**
   * Initialize a new Pattern with the given regular expression and flags.
	 *
	 * @param regex
	 * 		    The regular expression string.
	 * @param flags
	 *        The flags for the new Pattern.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre regex != null;
	 @ pre (* The given flags must be valid. *);
	 @
	 @ post pattern().equals(regex);
	 @ post flags() == flags;
	 @
   @ signals (PatternSyntaxException)  (* The given String is not a valid regular expression *);
   @*/
  public Pattern(String regex, int flags) throws PatternSyntaxException{
    super(regex,flags);
		_flags=flags;
	}
  	
	/**
	 * Return the pattern used to build this Pattern.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result.equals(toString());
	 @*/
	public /*@ pure @*/ String pattern() {
		return toString();
	}

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == (other instanceof Pattern) &&
   @                 (pattern().equals(((Pattern)other).pattern())) &&
   @                 (flags() == ((Pattern)other).flags());
   @*/
  public boolean equals(Object other) {
    return (other instanceof Pattern) &&
           (pattern().equals(((Pattern)other).pattern())) &&
           (flags() == ((Pattern)other).flags());
  }

  /**
   * Return a clone of this Pattern.
   */
 /*@
   @ also public behavior
   @
   @ post \result.equals(this);
   @*/
  public Object clone() {
    return new Pattern(pattern(), _flags);
  }

	/**
	 * Return the flags of this Pattern.
	 */
	public /*@ pure @*/ int flags() {
		return _flags;
	}

  /**
   * The flags of this Pattern.
   */
  private int _flags;
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
