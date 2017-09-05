package org.aikodi.rejuse.math.matrix.test;

import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;
import org.aikodi.rejuse.math.matrix.HouseholderQRDecomposer;
import org.aikodi.rejuse.math.matrix.Matrix;
import org.aikodi.rejuse.math.matrix.QRDecomposition;

/**
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TestMatrix extends JutilTest{

  public TestMatrix(String name) {
    super(name, new CVSRevision("1.19"));
  }

	private Matrix A;  
	private Matrix B;  
   
  public void setUp() {
		A = new Matrix(4,3);
		A.setElementAt(1,1,3);
		A.setElementAt(1,2,3);
		A.setElementAt(1,3,2);
		A.setElementAt(2,1,4);
		A.setElementAt(2,2,4);
		A.setElementAt(2,3,1);
		A.setElementAt(3,1,0);
		A.setElementAt(3,2,6);
		A.setElementAt(3,3,2);
		A.setElementAt(4,1,0);
		A.setElementAt(4,2,8);
		A.setElementAt(4,3,1);

		B = Matrix.unity(4);
  }

	public void test() {
		testQR(A);
		testQR(B);
	} 
 
  public void tearDown() {
    //nothing actually
  }
	
	public void testQR(Matrix matrix) {
		QRDecomposition qr = new HouseholderQRDecomposer().decompose(matrix);
		Matrix product= qr.Q().times(qr.R());
	}
}
 

