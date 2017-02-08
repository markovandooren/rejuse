package org.aikodi.rejuse.io;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DirectoryScanner {

  /**
   * Return a set of files in the given path that have the given extension. The boolean
   * argument determines whether the search is recursive or not.
   * 
   * @param path			    The directory containing the requested files.
   * @param extension     Only files with this extension will be loaded
   * @param recursive	    Wether or not to the search is recursive
   */
  public Set<File> scan(File path, String extension, boolean recursive){
      Set<File> result = new HashSet<File>();
      File f = path;
      if(f.isDirectory()){
          File [] files = f.listFiles();

          for(int i=0; i < files.length; i++){
              if(recursive){
                  result.addAll(scan(files[i], extension, recursive));
              }else{
                  if(files[i].getName().endsWith(extension)){
                      result.add(files[i]);
                  }
              }
          }
      }else{
          if(f.getName().endsWith(extension)){
              result.add(f);
          }
      }
      return result;
  }

  /**
   * Return a set of files in the given path that have the given extension. The boolean
   * argument determines whether the search is recursive or not.
   * 
   * @param path			    The directory containing the requested files.
   * @param extension     Only files with this extension will be loaded
   * @param recursive	    Wether or not to the search is recursive
   */
  public Set<File> scan(String path, String extension, boolean recursive){
  	return scan(new File(path), extension, recursive);
  }

}
