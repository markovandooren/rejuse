package org.rejuse.java.collections.test;
import java.util.HashSet;
import java.util.Set;

import org.rejuse.java.collections.SafeTransitiveClosure;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/* 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestTransitiveClosure extends JutilTest {
  
  public TestTransitiveClosure(String name) {
    super(name, new CVSRevision("1.15"));
  }
  
  Reference $A;
  Reference $B;
  Reference $C;
  Reference $D;
  Reference $E;
  Reference $F;
  Reference $G;
  Reference $H;
  Reference $I;
  Reference $J;
  SafeTransitiveClosure $closure;
  
  public void setUp() {
    $A = new Reference();
    $B = new Reference();
    $C = new Reference();
    $D = new Reference();
    $E = new Reference();
    $F = new Reference();
    $G = new Reference();
    $H = new Reference();
    $I = new Reference();
    $J = new Reference();
    
    $B.add($I);
    $B.add($J);
    $B.add($C);
    
    $C.add($B);
    $C.add($F);
    
    $D.add($J);
    
    $E.add($G);
    
    $F.add($E);
    $F.add($H);
    
    $G.add($A);
    $G.add($F);
    
    $H.add($A);
    
    $I.add($H);
    $I.add($J);
    
    $closure = new SafeTransitiveClosure() {
                                  public void addConnectedNodes(Object node, Set acc) {
                                    acc.addAll(((Reference) node).getReferences());
                                  }
                                };

  }
  
  
  public void testClosureFromAll() {
    Set isolatedSet = new HashSet();
    isolatedSet.add($A);
    isolatedSet.add($J);
    assertEquals(isolatedSet, $closure.closureFromAll(isolatedSet));
    Set all = new HashSet();
    all.add($A);
    all.add($B);
    all.add($C);
    all.add($D);
    all.add($E);
    all.add($F);
    all.add($G);
    all.add($H);
    all.add($I);
    all.add($J);
    Set toAll = new HashSet();
    toAll.add($B);
    toAll.add($D);
    assertEquals(all, $closure.closureFromAll(toAll));
    Set eSet = new HashSet();
    eSet.add($E);
    Set fromESet = new HashSet();
    fromESet.add($G);
    fromESet.add($F);
    fromESet.add($A);
    fromESet.add($E);
    fromESet.add($H);
    assertEquals(fromESet, $closure.closureFromAll(eSet));
  }
  
  public void testClosure() {
    Set fromESet = new HashSet();
    fromESet.add($G);
    fromESet.add($F);
    fromESet.add($A);
    fromESet.add($E);
    fromESet.add($H);
    assertEquals(fromESet, $closure.closure($E));
    Set fromJSet = new HashSet();
    fromJSet.add($J);
    assertEquals(fromJSet, $closure.closure($J));
    Set fromBSet = new HashSet();
    fromBSet.add($A);
    fromBSet.add($B);
    fromBSet.add($C);
    fromBSet.add($E);
    fromBSet.add($F);
    fromBSet.add($G);
    fromBSet.add($H);
    fromBSet.add($I);
    fromBSet.add($J);
    assertEquals(fromBSet, $closure.closure($B));
  }

  class Reference {
    private HashSet $references = new HashSet();
    
    public void add(Reference reference) {
      $references.add(reference);
    }
    
    public Set getReferences() {
      return new HashSet($references);
    }
    
  }
}
/*
 * <copyright>Copyright (C) 1997-2001. This software is copyrighted by 
 * the people and entities mentioned after the "@author" tags above, on 
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
