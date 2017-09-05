package org.aikodi.rejuse.math.matrix.test;

import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;
import org.aikodi.rejuse.math.matrix.ExplicitShiftQRSchurDecomposer;
import org.aikodi.rejuse.math.matrix.HouseholderHessenbergReducer;
import org.aikodi.rejuse.math.matrix.Matrix;
import org.aikodi.rejuse.math.matrix.SchurDecomposition;

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
 

