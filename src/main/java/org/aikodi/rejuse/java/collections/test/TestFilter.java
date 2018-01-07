package org.aikodi.rejuse.java.collections.test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.aikodi.rejuse.java.collections.Collections;
import org.aikodi.rejuse.java.collections.Filter;
import org.aikodi.rejuse.junit.CVSRevision;

import junit.framework.TestCase;
 
/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestFilter extends TestCase {
  public TestFilter(String name) {
    super(name);
  }
  
  private Vector<Integer> $intVector;
  public void setUp() {
    $intVector = new Vector();
    for(int i=0; i<37; i++) {
      $intVector.add(i);
    }
  }
  
  public void testFilter() {
    Collection clone = new ArrayList($intVector);
    new Filter() {
      public boolean criterion(Object element) {
        return true;
      }
    }.retain($intVector);
    assertTrue(Collections.identical($intVector,clone));
  

    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.removeNonMatchingElementIn($intVector);
    assertTrue(Collections.identical($intVector,clone));

    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()<30;
      }
    }.retain($intVector);
    assertTrue($intVector.size()==30);
    boolean bool = $intVector.stream().anyMatch(element -> element >= 30);
    assertTrue(! bool);
    
    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() < 20;
      }
    }.removeNonMatchingElementIn($intVector);
    
    assertTrue($intVector.size() == 10);
    bool = $intVector.stream().anyMatch(element -> element < 30);
    assertTrue(! bool);    
    
    
    // null as argument
    Collection nullCollection = null;    
    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.retain(nullCollection);

    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.removeNonMatchingElementIn(nullCollection);
    
    // empty collections
    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() >= 20;
      }
    }.retain(new HashSet());

    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() >= 20;
      }
    }.removeNonMatchingElementIn(new Vector());
    
    // collection containing only null references
    Vector stupidVector=new Vector();
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }
    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.retain(stupidVector);
    assertTrue(stupidVector.size() == 0);
    for(int i=0; i<100;i++) {
      stupidVector.add(null);
    }
    new Filter() {
      public boolean criterion(Object element) {
        return false;
      }
    }.removeNonMatchingElementIn(stupidVector);
    assertTrue(stupidVector.size() == 100);

  }
  
}
 

