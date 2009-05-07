package org.rejuse.math.matrix.test;

import org.rejuse.math.matrix.Matrix;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.math.matrix.QRDecomposition;
import org.rejuse.math.matrix.HouseholderQRDecomposer;

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
