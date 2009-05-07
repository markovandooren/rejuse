package org.rejuse.math.matrix.test;

import org.rejuse.math.matrix.Matrix;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.math.matrix.HouseholderHessenbergReducer;
import org.rejuse.math.matrix.HessenbergReduction;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestHouseholderHessenbergReducer extends JutilTest{

  public TestHouseholderHessenbergReducer(String name) {
    super(name, new CVSRevision("1.4"));
  }

	private Matrix A;  
	private Matrix B;  
   
  public void setUp() {
		A = new Matrix(new double [][] {{ 1,0.5,2,1}, 
																		{ 2,0,3,1},
																		{-1,2,4,1},
																		{ 7,2,4,3}
																	 });

  }
 
	public void test() {
		testHessenberg(A);
	}
 
  public void tearDown() {
    //nothing actually
  }
	
	public void testHessenberg(Matrix matrix) {
		HouseholderHessenbergReducer reducer = new HouseholderHessenbergReducer();
		HessenbergReduction reduction = reducer.reduce(matrix);
		Matrix result = reduction.H();
		//System.out.println("Matrix:");
		//System.out.println(matrix);
		//System.out.println("\nHessenberg reduction:");
		//System.out.println(result);
		//System.out.println("\nQ:");
		//System.out.println(reduction.Q());
		//System.out.println("\nQ * A * Q':");
		//System.out.println(reduction.Q().times(result).times(reduction.Q().returnTranspose()));
	}
}
 
/*
<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the JUTIL.ORG Software License for more details. For more information,
please see http://org-jutil.sourceforge.net/</copyright>/
*/
