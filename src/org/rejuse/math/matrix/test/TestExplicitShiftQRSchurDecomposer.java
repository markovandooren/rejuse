package org.rejuse.math.matrix.test;

import org.rejuse.junit.CVSRevision;
import org.rejuse.junit.JutilTest;
import org.rejuse.math.matrix.ExplicitShiftQRSchurDecomposer;
import org.rejuse.math.matrix.HouseholderHessenbergReducer;
import org.rejuse.math.matrix.Matrix;
import org.rejuse.math.matrix.SchurDecomposition;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestExplicitShiftQRSchurDecomposer extends JutilTest{

  public TestExplicitShiftQRSchurDecomposer(String name) {
    super(name, new CVSRevision("1.4"));
  }

	private Matrix A;  
	private Matrix B;  
   
  public void setUp() {
		A = new Matrix(new double [][] {{ 1,0.5,2}, 
																		{ 2,0,3},
																		{-1,2,4}
																	 });

		
		// B has eigenvalues 1,3,17
		// The eigenvectors are : [2 3 4] [5 1 12] [6 9 -2]
		B = new Matrix(new double [][] {{ 20.1868131868132, -3.64835164835165, -6.85714285714286}, 
																		{ 25.7802197802198, -2.47252747252747, -10.2857142857143},
																		{ -0.0879120879120882, -2.98901098901099, 3.28571428571429}
																	 });
  }
  
	public void test() {
		testSchur(B);
		testSchur(A);
	}
  public void tearDown() {
    //nothing actually
  }
	
	public void testSchur(Matrix matrix) {
		ExplicitShiftQRSchurDecomposer decomposer = new ExplicitShiftQRSchurDecomposer(new HouseholderHessenbergReducer());
		SchurDecomposition decomposition = decomposer.decompose(matrix);
		Matrix R = decomposition.R();
		Matrix Q = decomposition.Q();
		//System.out.println(R);
		//System.out.println();
		//System.out.println(Q);
		//System.out.println();
		//System.out.println(Q.times(R).times(Q.returnTranspose()));
		//System.out.println();
		//System.out.println(Q.times(Q.returnTranspose()));
		
		
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
