package org.rejuse.java.throwable.test;

import java.util.List;
import java.util.Vector;

import org.rejuse.java.throwable.StackTrace;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestStackTrace extends JutilTest {
  
  public TestStackTrace(String name) {
    super(name, new CVSRevision("1.6"));
  }

  public void setUp() {
  }
  
  
  public void testStrackTrace() {
    try {
      throw new IllegalArgumentException();
    }
    
    catch(Exception exc) {
      List myList = StackTrace.asList(exc);
      Vector vector = new Vector(myList);
      // first method is main().
      assertTrue(((String)vector.elementAt(0)).indexOf("main") != -1);
      // the method throwing the exception is testStrackTrace().
      assertTrue(((String)vector.elementAt(vector.size() - 1)).indexOf("testStrackTrace") != -1);
    }
  }

}


