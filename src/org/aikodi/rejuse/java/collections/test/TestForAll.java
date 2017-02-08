package org.aikodi.rejuse.java.collections.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.aikodi.rejuse.java.collections.Collections;
import org.aikodi.rejuse.java.collections.ForAll;
import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestForAll extends JutilTest {

  private HashSet $hashSet;
  
  public TestForAll(String name) {
    super(name,new CVSRevision("1.19"));
  }
  
  public void setUp() {
    $hashSet = new HashSet();
    for(int i=0; i<10; i++) {
      $hashSet.add(new Integer(i));
    }
    
  }
  
  public void testForAll() {
    Collection clone = new Vector($hashSet);
    boolean bool = new ForAll() {
                     public boolean criterion(Object element) {
                        return ((Integer)element).intValue() == 7;
                     }
                   }.in($hashSet);
    assertTrue(! bool);
    assertTrue(Collections.identical($hashSet,clone));
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() <= 10;
             }
           }.in($hashSet);
    assertTrue(bool);
    assertTrue(Collections.identical($hashSet,clone));
    Collection nullCollection = null;
    // shouldn't throw an exception
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() == 17;
             }
    }.in(nullCollection);
    Vector stupidVector = new Vector();
    for(int i=0;i<37;i++) {
      stupidVector.add(null);
    }
    // just for fun a vector containing only null elements.
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return element == new Object();
             }
    }.in(stupidVector);
    assertTrue(! bool);
    // an empty collection
    bool = new ForAll() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() == 17;
             }
    }.in(new ArrayList());
    assertTrue(bool);
  }
}

