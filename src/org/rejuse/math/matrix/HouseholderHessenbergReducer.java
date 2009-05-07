package org.rejuse.math.matrix;

/**
 * <p>A class of matrix operators that compute the Hessenberg reduction
 * of a matrix using Householder reflections.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class HouseholderHessenbergReducer implements HessenbergReducer {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * See superclass
	 */
	public /*@ pure @*/ HessenbergReduction reduce(Matrix matrix) {
		Matrix H = (Matrix) matrix.clone();
		int m = matrix.getNbColumns();
		Column[] vs = new Column[m-2];
		int limit = m-2;
	  for (int k=1; k<=limit; k++) {
			Column x = H.getColumn(k).subColumn(k+1,m);
			Column v = new Column(m - k);
			double x1 = x.elementAt(1);
			double signX1 = org.rejuse.math.ExtMath.sign(x1);
			v.setElementAt(1,x.norm(2)*signX1);
			v.add(x);
			v.normalize();
			vs[k-1]=v;
			Matrix A = H.subMatrix(k+1,k,m,m);
			// subtracting from A saves us a clone()
			// mutating temp saves us another one ( <-> .times(2))
			Matrix temp = v.times(v.returnTranspose().times(A));
			temp.multiply(2);
			A.subtract(temp);
			H.setSubMatrix(k+1,k,A);
			
			A = H.subMatrix(1,k+1,m,m);
			temp = (A.times(v)).times(v.returnTranspose());
			temp.multiply(2);
			A.subtract(temp);
			H.setSubMatrix(1,k+1,A);
		}
		// explicitly make H a Hessenberg matrix.
		for(int col=1; col<=limit; col++) {
			for(int row=col+2; row<=m; row++) {
				H.setElementAt(row,col,0);
      }
    }
		return new HouseholderHessenbergReduction(H,vs);
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
