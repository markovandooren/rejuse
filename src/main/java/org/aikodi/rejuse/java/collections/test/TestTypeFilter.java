package org.aikodi.rejuse.java.collections.test;
import java.util.Collection;
import java.util.HashSet;

import org.aikodi.rejuse.java.collections.Collections;
import org.aikodi.rejuse.java.collections.TypeFilter;

import junit.framework.TestCase;

/** 
 * @author  Marko van Dooren
 */
public class TestTypeFilter extends TestCase {
  
  public TestTypeFilter(String name) {
    super(name);
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
    filter.removeNonMatchingElementIn($col1);
    //System.out.println($col1);
    assertTrue($col1.size() == 2);
    assertTrue($col1.contains(new Boolean(false)));
    assertTrue(! $col1.contains(new Integer(2)));
    filter.removeNonMatchingElementIn($col1);
    filter.retain($col1);
    assertTrue($col1.size() == 0);
    // null as argument
    Collection nullCollection = null;
    filter.removeNonMatchingElementIn(nullCollection);
    filter.retain(nullCollection);
    setUp();
    filter = new TypeFilter(Object.class);
    Collection clone = new HashSet($col1);
    filter.retain($col1);
    assertTrue(Collections.identical($col1,clone));
    filter.removeNonMatchingElementIn($col1);
    assertTrue($col1.size() == 0);
  }

}

