package org.rejuse.math.matrix;
/**
 * This class represents a Cholesky factorization of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class DefaultCholeskyDecomposition implements CholeskyDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new DefaultCholeskyDecomposition with the given R matrix.
	 *
	 * @param R
	 *        The R matrix of the Cholesky factorization
	 */
 /*@
   @ public behavior
	 @
	 @ pre R != null;
	 @ pre R.isUpperTriangular();
	 @ pre R.isSquare();
	 @
	 @ post R().equals(R);
	 @*/
  public /*@ pure @*/ DefaultCholeskyDecomposition(Matrix R) {
		_R=(Matrix)R.clone();
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix R() {
		return _R;
  }
	
	private Matrix _R;
}
/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
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
