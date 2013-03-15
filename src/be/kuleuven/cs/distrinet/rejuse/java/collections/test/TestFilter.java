package be.kuleuven.cs.distrinet.rejuse.java.collections.test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Collections;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Exists;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Filter;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
 
/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestFilter extends JutilTest {
  public TestFilter(String name) {
    super(name, new CVSRevision("1.13"));
  }
  
  private Vector $intVector;
  public void setUp() {
    $intVector = new Vector();
    for(int i=0; i<37; i++) {
      $intVector.add(new Integer(i));
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
    }.discard($intVector);
    assertTrue(Collections.identical($intVector,clone));

    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()<30;
      }
    }.retain($intVector);
    assertTrue($intVector.size()==30);
    boolean bool = new Exists() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()>=30;
      }
    }.in($intVector);
    assertTrue(! bool);
    
    new Filter() {
      public boolean criterion(Object element) {
        return ((Integer) element).intValue() < 20;
      }
    }.discard($intVector);
    
    assertTrue($intVector.size() == 10);
    bool = new Exists() {
      public boolean criterion(Object element) {
        return ((Integer)element).intValue()<20;
      }
    }.in($intVector);
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
    }.discard(nullCollection);
    
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
    }.discard(new Vector());
    
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
    }.discard(stupidVector);
    assertTrue(stupidVector.size() == 100);

  }
  
}
 

