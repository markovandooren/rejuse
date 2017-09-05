package org.aikodi.rejuse.java.collections.test;
import java.util.NoSuchElementException;

import org.aikodi.rejuse.java.collections.ArrayCursor;
import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;
 
/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestArrayCursor extends JutilTest {
  
  public TestArrayCursor(String name) {
    super(name, new CVSRevision("1.7"));
  }
  
  public void setUp() {
  }
  
  public void testArrayCursor() {
    Integer[][][] array= new Integer[3][4][5];
    array[1][2][3] = new Integer(7);
    ArrayCursor iter = new ArrayCursor(array);
    if(! iter.atStart()){
      assertTrue(false);
    }
    try{
      iter.previous();
      assertTrue(false);
    }
    catch(NoSuchElementException exc){
    }

    iter.toStart();
    
    if(! iter.atStart()){
      assertTrue(false);
    }
    
    for(int i=1; i<60;i++){
      iter.next();
    }
    
    if(! iter.atEnd()){
      assertTrue(false);
    }
    
    try{
      iter.next();
      assertTrue(false);
    }
    catch(NoSuchElementException exc){
    }
    
    for(int i=1; i<60;i++){
      iter.previous();
    }
    if(! iter.atStart()){
      assertTrue(false);
    }
    
  }

}


