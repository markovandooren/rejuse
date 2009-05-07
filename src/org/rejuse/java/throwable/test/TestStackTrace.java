package org.rejuse.java.throwable.test;

import java.util.List;
import java.util.Vector;

import org.rejuse.java.throwable.StackTrace;
import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestStackTrace extends JutilTest {
  
  public TestStackTrace(String name) {
    super(name, new CVSRevision("1.6"));
  }

  public void setUp() {
  }
  
  
  public void testStrackTrace() {
    try {
      throw new IllegalArgumentException();
    }
    
    catch(Exception exc) {
      List myList = StackTrace.asList(exc);
      Vector vector = new Vector(myList);
      // first method is main().
      assertTrue(((String)vector.elementAt(0)).indexOf("main") != -1);
      // the method throwing the exception is testStrackTrace().
      assertTrue(((String)vector.elementAt(vector.size() - 1)).indexOf("testStrackTrace") != -1);
    }
  }

}

/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
