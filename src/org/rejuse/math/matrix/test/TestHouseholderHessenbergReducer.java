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
 

