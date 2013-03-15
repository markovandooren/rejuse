package be.kuleuven.cs.distrinet.rejuse.java.collections.test;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Collections;
import be.kuleuven.cs.distrinet.rejuse.java.collections.SafeAccumulator;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestAccumulator extends JutilTest {
  
  public TestAccumulator(String name) {
    super(name, new CVSRevision("1.14"));
  }
  
  private Set $set;
  private Set $cloneSet;
  
  public void setUp() {
    $set = new HashSet();
    $cloneSet = new HashSet();    
    for(int i=1; i <=100;i++) {
      Integer integer = new Integer(i);
      $set.add(integer);
      $cloneSet.add(integer);
    }
    // sum is n*(n+1)/2 = 5050
  }
  public void testAccumulateSet() {
    Set clone = new HashSet($set);
    Integer sum = (Integer) new SafeAccumulator() {
                              public Object initialAccumulator() {
                                return new Integer(0);
                              }

                              public Object accumulate(Object element, Object acc) {
                                return new Integer( ((Integer) element).intValue() +
                                                    ((Integer) acc).intValue());
                              }
                          }.accumulate($set);
    assertEquals(sum, new Integer(5050));
    assertEquals($set,$cloneSet);
    // Check whether $set has changed. 
   assertTrue(Collections.identical($set,$cloneSet));
   assertTrue(Collections.identical($set,clone));
  }
}

