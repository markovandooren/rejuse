package org.aikodi.rejuse.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
// TODO: JDK 1.4
//import java.net.URI;

/**
 * <p>A class that acts as an extension of <code>java.io.File</code>.</p>
 *
 * <center>
 *   <img src="doc-files/File.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */

public class ExtendedFile extends java.io.File {
	
	/**
	 * Initialize a new ExtendedFile using the given parent and child.
	 *
	 * See <a href="http://java.sun.com/j2se/1.4/docs/api/java/io/File.html#File(java.io.File, java.lang.String)">java.io.File.File(File, String)</a>.
	 */
	public ExtendedFile(java.io.File parent, String child) {
		super( child);
	}

	/**
	 * Initialize a new ExtendedFile 
	 *
	 * See <a href="http://java.sun.com/j2se/1.4/docs/api/java/io/File.html#File(java.io.File, java.lang.String)">java.io.File.File(File, String)</a>.
	 */
	public ExtendedFile(String pathname) {
		super(pathname);
	}

	/**
	 * Initialize a new ExtendedFile 
	 *
	 * See <a href="http://java.sun.com/j2se/1.4/docs/api/java/io/File.html#File(java.io.File, java.lang.String)">java.io.File.File(File, String)</a>.
	 */
	public ExtendedFile(String parent, String child) {
		super( child);
	}

// TODO: JDK 1.4
//	/**
//	 * Initialize a new ExtendedFile 
//	 *
//	 * See <a href="http://java.sun.com/j2se/1.4/docs/api/java/io/File.html#File(java.io.File, java.lang.String)">java.io.File.File(File, String)</a>.
//	 */
//	public ExtendedFile(URI uri) {
//		super(uri);
//	}

	/**
	 * Copies the given source file to the given destination file.
	 * If an exception occurs, the possibly incomplete destination file is
	 * not deleted.
	 * 
	 * @param source      
	 *        The source file
	 * @param destination 
	 *        The destination file
	 */
 /*@
	 @ public behavior
	 @
	 @ pre source != null;
	 @ pre destination != null;
	 @ // both files must be different
	 @ pre ! source.getCanonicalPath().equals(destination.getCanonicalPath());
	 @
	 @ post (* The source file is copied to the destination file *);
	 @
	 @ signals (FileNotFoundException) (* The source file does not exist, is a directory
	 @                                    or not readable *);
	 @ signals (FileNotFoundException) (* The destination file does not exist and can not
	 @                                    be create, is a directory or exists, but is not 
	 @																		writable *);
	 @ signals (SecurityException) (* The source file may not be read from *);
	 @ signals (SecurityException) (* The destination file may not be writen to *);
	 @ signals (IOException) (* An IO errors occurs *);
	 @*/
	public static void copy(File source, File destination) throws FileNotFoundException, SecurityException, IOException {
	  byte[] $data = new byte[100000];
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destination));
		
		int size = -1;
		
		while ((size = in.read($data)) != -1) {
			out.write($data, 0, size);
		}
		
		// Close both streams
		in.close();
		out.close();
	}


	/**
	 * Copies this file to the given destination file.
	 * If an exception occurs, the possibly incomplete destination file is
	 * not deleted.
	 * 
	 * @param destination 
	 *        The destination file
	 */
 /*@
	 @ public behavior
	 @
	 @ pre destination != null;
	 @ // both files must be different
	 @ pre ! getCanonicalPath().equals(destination.getCanonicalPath());
	 @
	 @ post (* This file is copied to the destination file *);
	 @
	 @ signals (FileNotFoundException) (* This file does not exist, is a directory
	 @                                    or not readable *);
	 @ signals (FileNotFoundException) (* The destination file does not exist and can not
	 @                                    be create, is a directory or exists, but is not 
	 @																		writable *);
	 @ signals (SecurityException) (* This file may not be read from *);
	 @ signals (SecurityException) (* The destination file may not be writen to *);
	 @ signals (IOException) (* An IO errors occurs *);
	 @*/
	public void copyTo(File destination) throws FileNotFoundException, SecurityException, IOException {
		copy(this,destination);
	}

}

