package org.rejuse.java.regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import jregex.Replacer;

import org.rejuse.jregex.Pattern;


/**
 * <p>A class of object the replace regular expressions for others.</p>
 *
 * <center>
 *   <img src="doc-files/RegexReplacer.png"/>
 * </center>
 *
 * <p>For example, it can replace a regular expression in a file and write the output to another file.</p>
 *
 * <p><b>At this moment, we use <a href="http://jregex.sourceforge.net">JRegex</a> instead of jdk 1.4 regular expressions
 * because:</b></p>
 * <ul>
 * 	<li><b>The 1.4 jdk is not yet available on all major platforms.</b></li>
 * 	<li><b>The current 1.4 JVM is anything but stable on e.g. Linux (no experience with windows):
 * 	       it segfaults for example when making a SkipList with more than 15000 elements.</b></li>
 * </ul>
 * <p>This means that this class will change in the future, and the 
 * <a href="Pattern.html"><code>Pattern</code></a> class will probably disappear since it's 
 * only adapts the JRegex Pattern class to the interface of <code>java.util.regex.Pattern</code>
 * in order to simplify porting afterwards.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class RegexReplacer {
	
	/**
	 * Initialize a new RegexReplacer for the given pattern and replacement.
	 *
	 * @param pattern
	 *        a String containing the pattern to be replaced.
	 * @param replacement
	 *        a String containing the pattern to replace the matched pattern.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre pattern != null;
	 @ pre replacement != null;
	 @ pre (* pattern must be a valid regular expression *);
	 @
	 @ post getPattern().pattern().equals(pattern);
	 @ // The pattern is a multi line pattern.
	 @ post getPattern().flags() == Pattern.MULTILINE;
	 @ post getReplacement() == replacement;
	 @*/
	public RegexReplacer(String pattern, String replacement) {
		//_pattern = Pattern.compile(pattern, Pattern.MULTILINE);
		_pattern = new Pattern(pattern, Pattern.MULTILINE);
		_replacement = replacement;
  }
	
	/**
	 * Initialize a new RegexReplacer for the given pattern, replacement and flags.
	 *
	 * @param pattern
	 *        A String containing the pattern to be replaced.
	 * @param replacement
	 *        A String containing the pattern to replace the matched pattern.
	 * @param flags
	 *        The flags for the pattern.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre pattern != null;
	 @ pre replacement != null;
	 @ pre (* pattern must be a valid regular expression *);
	 @ pre (* flags must be a valid flag for a pattern. *);
	 @ pre (* The flags should be a bitwise or of the provide static flags, <b>THIS WILL CHANGE IN THE FUTURE</b> *);
	 @
	 @ post getPattern().pattern().equals(pattern);
	 @ post getPattern().flags() == flags;
	 @ post getReplacement() == replacement;
	 @*/
	public RegexReplacer(String pattern, String replacement, int flags) {
		//_pattern = Pattern.compile(pattern, flags);
		_pattern = new Pattern(pattern, flags);
		_replacement = replacement;
  }

//	/**
//	 * <p>Replace all occurrences of the pattern of this RegexReplacer by the replacement
//	 * of this RegexReplacer in the given input, and send the result to the given output.</p>
//	 *
//	 * <p>The output stream must be closed "manually" afterwards if necessary.</p>
//	 *
//	 * @param input
//	 *        The character sequence in which the pattern must be replaced.
//	 * @param output
//	 *        The OutputStream to which the result must be written.
//	 */	
// /*@
//	 @ public behavior
//	 @
//	 @ pre input != null;
//	 @ pre output != null;
//	 @
//	 @ post (* all occurrences of getPattern() will be replaced by getReplacement()
//	 @         in the input, and the result will be written the output. *);
//	 @*/
//	public void replace(CharSequence input, OutputStream output) {
//			String result = replace(input);
//			PrintWriter outputter = new PrintWriter(output);
//			outputter.print(result);
//  }
	
//	/**
//	 * <p>Replace all occurrences of the pattern of this RegexReplacer by the replacement
//	 * of this RegexReplacer in the given input.</p>
//	 *
//	 * @param input
//	 *        The character sequence in which the pattern must be replaced.
//	 */	
// /*@
//	 @ public behavior
//	 @
//	 @ pre input != null;
//	 @
//	 @ post (* all occurrences of getPattern() will be replaced by getReplacement()
//	 @         in the input, and the result will be returned. *);
//	 @*/
//	public String replace(CharSequence input) {
//			Matcher m = getPattern().matcher(input);
//			return m.replaceAll(getReplacement());
//  }
	
	/**
	 * <p>Replace all occurrences of the pattern of this RegexReplacer in the input file
	 * by the replacement of this RegexReplacer, and write the result to the output file.</p>
	 *
	 * <p>Both File objects may refer to the same real file.</p>
	 * 
	 * @param input
	 *        The file in which the pattern must be replaced.
	 * @param output
	 *        The file to which the result must be written.
	 */	
 /*@
	 @ public behavior
	 @
	 @ pre input != null;
	 @ pre output != null;
	 @
	 @ post (* all occurrences of getPattern() will be replaced by getReplacement()
	 @         in the input file, and the result will be written the output file. *);
	 @
	 @ signals (FileNotFoundException) (* one of the files is not found *);
	 @ signals (IOException) (* something went wrong during IO *);
	 @*/
	public void replace(File input, File output) throws FileNotFoundException, IOException {
//	 * @param charSet
//	 *        The characterset of the input file.
//	 @ pre charSet != null;
//	 @ signals (MalformedInputException) (* There was an error when mapping bytes from the input file to characters.*);
//	 @ signals (UnmappableCharacterException) (* There was an error when mapping bytes from the input file to characters.*);
//	 @ signals (CharacterCodingException) (* There was an error when mapping bytes from the input file to characters.*);
// JDK 1.4 code
//			FileInputStream fis = new FileInputStream(input);
//			FileChannel fc = fis.getChannel();
//			// Get a CharBuffer from the source file
//			ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int)fc.size());
//
//			CharsetDecoder cd = charSet.newDecoder();
//			CharBuffer cb = cd.decode(bb);
//		
//			Matcher m = getPattern().matcher(cb);
//			String result = m.replaceAll(getReplacement());
//			fis.close();
//			// Can only be opened now in case both File object refer to the same
//			// real file. Should be tested more efficiently since in this case
//			// the matching doesn't have to happen.
//			FileOutputStream fos = new FileOutputStream(output);
//			PrintWriter outputter = new PrintWriter(fos);
//			outputter.print(result);
//			outputter.close();

// JRegex code
			FileReader reader = new FileReader(input);
//			StringBuffer buf = new StringBuffer;
//			int arraySize = 10000;
//			char[] chars = new char[arraySize];
//
//			int size = reader.read(chars,0,arraysize);
//			// Keep reading characters in the chars array, and append them
//			// to the StringBuffer
//			while (size != -1) {
//				buf.append(chars);
//				size = reader.read(chars,0,arraysize);
//			}
//
			// Make a replacer
			Replacer replacer = getPattern().replacer(getReplacement());
		
			String result = replacer.replace(reader,-1);

			// Close the file reader.
			reader.close();

			// Can only be opened now in case both File object refer to the same
			// real file. Should be tested more efficiently since in this case
			// the matching doesn't have to happen.
			FileOutputStream fos = new FileOutputStream(output);
			PrintWriter outputter = new PrintWriter(fos);
			outputter.print(result);
			outputter.close();
  }
	
	
	/**
	 * Return the pattern of this RegexReplacer.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Pattern getPattern() {
		return _pattern;
  }
	

	/**
	 * Return the replacement string of this RegexReplacer.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ String getReplacement() {
		return _replacement;
  }

	/**
	 * The regular expression to be replaced.
	 */	
 /*@
	 @ private invariant _pattern != null;
	 @*/
	private Pattern _pattern;
	
	/**
	 * The replacement for the regular expression.
	 */
 /*@
	 @ private invariant _replacement != null;
	 @*/
	private String _replacement;
	
	public static void main(String[] args) throws Exception {
		File input = new File(args[0]);
		File output = new File(args[1]);
    String substitute = null;
    if(args.length >= 4) {
      substitute = args[3];
    }
    else {
      substitute = "";
    }
		RegexReplacer replacer = new RegexReplacer(args[2], substitute);
		replacer.replace(input,output);
	}
}
/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
