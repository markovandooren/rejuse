package be.kuleuven.cs.distrinet.rejuse.io;

import java.io.File;
import java.net.URI;

public class FileUtils {

	/**
	 * Return a file whose path is the absolute version of the
	 * given path. When the given path is relative, it is resolved
	 * relative to the given root. When the given path is already absolute,
	 * it is used directly.
	 * 
	 * @param path The path for which an absolute file must be returned.
	 * @param rootForRelativePaths The root for relative paths.
	 * @return
	 */
 /*@
   @ public behavior
   @
   @ pre path != null;
   @ pre rootForRelativePaths != null;
   @
   @ post \result != null;
   @ post new File(path).isAbsolute() ==> \result.getAbsolutePath().equals(path);
   @ post ! new File(path).isAbsolute() ==> 
   @          \result.getAbsolutePath().equals(rootForRelativePaths.getAbsolutePath()+File.separator+path);
   @*/
	public static File absoluteFile(String path, File rootForRelativePaths) {
		if(! rootForRelativePaths.isDirectory()) {
			throw new IllegalArgumentException("The given root is not a directory.");
		}
		File file = new File(path);
		if(!file.isAbsolute()) {
			file = new File(rootForRelativePaths.getAbsolutePath()+File.separator+path);
		}
		return file;
	}

	/**
	 * Return the absolute version of the given path. 
	 * When the given path is relative, it is resolved
	 * relative to the given root. When the given path is already absolute,
	 * it is returned directly.
	 * 
	 * @param path The path for which an absolute file must be returned.
	 * @param rootForRelativePaths The root for relative paths.
	 * @return
	 */
 /*@
   @ public behavior
   @
   @ pre path != null;
   @ pre rootForRelativePaths != null;
   @
   @ post \result != null;
   @ post new File(path).isAbsolute() ==> \result.equals(path);
   @ post ! new File(path).isAbsolute() ==> 
   @          \result.equals(rootForRelativePaths.getAbsolutePath()+File.separator+path);
   @*/
	public static String absolutePath(String path, File rootForRelativePaths) {
		return absoluteFile(path, rootForRelativePaths).getAbsolutePath();
	}
	
	/**
	 * Return the relative path of the given path with respect to
	 * the given base path.
	 * 
	 * @param basePath The base path of the requested relative path
	 * @param path The path of which the relative path is requested
	 */
	public static String relativePath(String basePath, String path) {
		return relativeURI(basePath, path).getPath();
	}

	/**
	 * Return the relative URI of the given path with respect to
	 * the given base path.
	 * 
	 * @param basePath The base path of the requested relative path
	 * @param path The path of which the relative URI is requested
	 */
	public static URI relativeURI(String basePath, String path) {
		return new File(basePath).toURI().relativize(new File(path).toURI());
	}

}
