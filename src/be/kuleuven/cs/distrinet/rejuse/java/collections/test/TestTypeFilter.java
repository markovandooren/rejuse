package be.kuleuven.cs.distrinet.rejuse.java.collections.test;
import java.util.Collection;
import java.util.HashSet;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Collections;
import be.kuleuven.cs.distrinet.rejuse.java.collections.TypeFilter;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestTypeFilter extends JutilTest {
  
  public TestTypeFilter(String name) {
    super(name, new CVSRevision("1.11"));
  }
  
  Collection $col1;
//   Collection $col2;
//   Collection $col3;
  
  public void setUp() {
    $col1 = new HashSet();
//     $col2 = new Vector();
//     $col3 = new ArrayList();
    $col1.add(new Object());
    $col1.add(new Integer(2));
    $col1.add(new Boolean(false));
    
  }
  
  
  public void testTypeFilter() {
    TypeFilter filter = new TypeFilter(Integer.class);
    filter.discard($col1);
    //System.out.println($col1);
    assertTrue($col1.size() == 2);
    assertTrue($col1.contains(new Boolean(false)));
    assertTrue(! $col1.contains(new Integer(2)));
    filter.discard($col1);
    filter.retain($col1);
    assertTrue($col1.size() == 0);
    // null as argument
    Collection nullCollection = null;
    filter.discard(nullCollection);
    filter.retain(nullCollection);
    setUp();
    filter = new TypeFilter(Object.class);
    Collection clone = new HashSet($col1);
    filter.retain($col1);
    assertTrue(Collections.identical($col1,clone));
    filter.discard($col1);
    assertTrue($col1.size() == 0);
  }

}

