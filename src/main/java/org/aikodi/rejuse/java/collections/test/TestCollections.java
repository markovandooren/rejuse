package org.aikodi.rejuse.java.collections.test;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.aikodi.rejuse.java.collections.Collections;

import junit.framework.TestCase;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestCollections extends TestCase {
  
  public TestCollections(String name) {
    super(name);
  }
  
  private Vector $integers;
  private Vector $otherIntegers;
  private Map $map;
  private Map $otherMap;
      
  public void setUp() {
    $integers = new Vector();
    $otherIntegers = new Vector();
    for(int i=0;i<37;i++) {
      $integers.add(new Integer(i));
      $otherIntegers.add(new Integer(i));
    }
    $map = new HashMap();
    $otherMap = new HashMap();
    for(int i=0;i<37;i++) {
      $map.put(new Integer(i), new Integer(i+1));
      $otherMap.put(new Integer(i),new Integer(i+1));
    }
  }
  
  public void testIdenticalCollection() {
    Vector vek = new Vector($integers);
    Collection nullCollection = null;
    assertTrue(! Collections.identical($integers,$otherIntegers));
    assertTrue(! Collections.identical($integers,null));
    assertTrue(! Collections.identical(null,$otherIntegers));
    assertTrue(Collections.identical(nullCollection,nullCollection));
    assertTrue(Collections.identical($integers,$integers));
    assertTrue(Collections.identical($integers,vek));
  }
  
} 

