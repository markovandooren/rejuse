package be.kuleuven.cs.distrinet.rejuse.java.collections.test;

import be.kuleuven.cs.distrinet.rejuse.java.collections.ZeroDimensionException;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestZeroDimensionException extends JutilTest {
  
  public TestZeroDimensionException(String name) {
    super(name, new CVSRevision("1.6"));
  }
  
  public void setUp() {
  }
  
  
  public void testZeroDimensionException() {
    Object[] array = new Object[4];
    String msg = "hoi";
    try {
      throw new ZeroDimensionException(array);
    }
    catch(ZeroDimensionException exc) {
      assertTrue(exc.getArray() == array);
    }
    try {
      throw new ZeroDimensionException(array,msg);
    }
    catch(ZeroDimensionException exc) {
      assertTrue(exc.getArray() == array);
      assertTrue(exc.getMessage() == msg);
    }
    
  }

}

