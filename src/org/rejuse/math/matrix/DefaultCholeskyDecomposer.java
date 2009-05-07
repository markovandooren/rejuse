package org.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the Cholesky factorization of a
 * symmetric matrix using the algorithm in the book of Trefethen and Bau.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class DefaultCholeskyDecomposer implements CholeskyDecomposer {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * <p>See superclass</p>
	 * <p>The result is computed using the algorithm as defined in
	 * the book of Trefethen and Bau.</p>
	 */
	public /*@ pure @*/ CholeskyDecomposition decompose(Matrix matrix) {
		Matrix R = (Matrix) matrix.clone();
		int m = matrix.getNbRows();
		for(int k=1; k<=m; k++) {
			for(int j=k+1; j<=m; j++) {
				double factor = R.elementAt(k,j) / R.elementAt(k,k);
				for(int p=j; p<=m; p++) {
					double element = R.elementAt(j,p) - (R.elementAt(k,p) * factor);
					R.setElementAt(j, p, element);
        }
				for(int p=1; p<j; p++) {
					R.setElementAt(j,p,0);
        }
      }
    }
		return new DefaultCholeskyDecomposition(R);
  }
} 
/*<copyright>Copyright (C) 1997-2002. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://www.jutil.org/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://jutil.org/</copyright>*/	
