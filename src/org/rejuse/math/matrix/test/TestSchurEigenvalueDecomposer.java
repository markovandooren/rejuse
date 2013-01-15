package org.rejuse.math.matrix.test;

import org.rejuse.math.matrix.Matrix;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.math.matrix.HouseholderHessenbergReducer;
import org.rejuse.math.matrix.ExplicitShiftQRSchurDecomposer;
import org.rejuse.math.matrix.*;

/** 
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestSchurEigenvalueDecomposer extends JutilTest{

  public TestSchurEigenvalueDecomposer(String name) {
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
		testEigenvalue(B);
		testEigenvalue(A);
	}

  public void tearDown() {
    //nothing actually
  }
	
	public void testEigenvalue(Matrix matrix) {
		SchurEigenvalueDecomposer decomposer = new SchurEigenvalueDecomposer(new ExplicitShiftQRSchurDecomposer(new HouseholderHessenbergReducer()), new QRLinSolver(new HouseholderQRDecomposer()));
		EigenvalueDecomposition decomposition = decomposer.decompose(matrix);
		Matrix lambda = decomposition.lambda();
	  //System.out.println(lambda);
		//System.out.println();
		Matrix eigenvectors  = decomposition.getEigenvectors();
		//System.out.println(eigenvectors);
		//System.out.println();
		//System.out.println(eigenvectors.times(lambda).times(eigenvectors.returnTranspose()));
		//System.out.println();
		//System.out.println(eigenvectors.times(eigenvectors.returnTranspose()));
	}
}
 

