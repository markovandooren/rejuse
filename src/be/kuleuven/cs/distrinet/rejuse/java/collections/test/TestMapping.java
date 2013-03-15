package be.kuleuven.cs.distrinet.rejuse.java.collections.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Collections;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Mapping;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestMapping extends JutilTest {

  private Vector $integers;
  
  
  public TestMapping(String name) {
    super(name, new CVSRevision("1.13"));
  }
  
  public void setUp() {
    $integers = new Vector();
    for(int i=0;i < 37; i++) {
      $integers.add(new Integer(i));
    }
  }
  
  public void testApplyTo() {
    new Mapping() {
      public Object mapping(Object element) {
        return new Integer(((Integer) element).intValue() + 1);
      }
    }.applyTo($integers);
    new Visitor() {
      private int $number=1;
      public void visit(Object element) {
        TestMapping.this.assertTrue(((Integer)element).intValue() == $number);
        $number++;
      }
    }.applyTo($integers);
  }
  
  public void testApplyFromTo() {
    setUp();
    ArrayList list = new ArrayList($integers);
    ArrayList newIntegers = new ArrayList();
    Collection col = new Mapping() {
      public Object mapping(Object element) {
        return new Integer(((Integer) element).intValue() + 1);
      }
    }.applyFromTo($integers,newIntegers);
    assertTrue(Collections.identical($integers,list));
    assertTrue(col==newIntegers);
    new Visitor() {
      private int $number=1;
      public void visit(Object element) {
        TestMapping.this.assertTrue(((Integer)element).intValue() == $number);
        $number++;
      }
    }.applyTo(newIntegers);
    assertTrue($integers.size() == newIntegers.size());
    
  }
}

