package be.kuleuven.cs.distrinet.rejuse.java.collections.test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Collections;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Exists;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestExists extends JutilTest {

  private HashSet $hashSet;

  public TestExists(String name) {
    super(name, new CVSRevision("1.17"));
  }
  
  public void setUp() {
    $hashSet = new HashSet();
    for(int i=0; i<10; i++) {
      $hashSet.add(new Integer(i));
    }
    
  }
  
  public void testExists() {
    Collection clone = new Vector($hashSet);
    boolean bool = new Exists() {
                     public boolean criterion(Object element) {
                        return ((Integer)element).intValue() == 7;
                     }
                   }.in($hashSet);
    assertTrue(bool);
    assertTrue(Collections.identical($hashSet,clone));
    bool = new Exists() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() == 17;
             }
           }.in($hashSet);
    assertTrue(! bool);
    assertTrue(Collections.identical($hashSet,clone));
    Collection nullCollection = null;
    // shouldn't throw an exception
    bool = new Exists() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() == 17;
             }
    }.in(nullCollection);
    assertTrue(! bool);
    Vector stupidVector = new Vector();
    for(int i=0;i<37;i++) {
      stupidVector.add(null);
    }
    // just for fun a vector containing only null elements.
    bool = new Exists() {
             public boolean criterion(Object element) {
               return element == new Object();
             }
    }.in(stupidVector);
    // an empty collection
    bool = new Exists() {
             public boolean criterion(Object element) {
               return ((Integer)element).intValue() == 17;
             }
    }.in(new ArrayList());
    assertTrue(! bool);    
  }
}

