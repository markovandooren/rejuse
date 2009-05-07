package org.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the eigenvalue factorization of a matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface EigenvalueDecomposer {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
	 * <p>Return an eigenvalue factorization of this matrix.</p>
	 *
	 * @param matrix
	 *        The matrix to decompose.
	 */
 /*@
   @ public behavior
	 @
	 @ pre matrix != null;
	 @ pre matrix.getNbRows() == matrix.getNbColumns();
	 @ pre (* matrix is not singular *);
	 @
	 @ post \result != null;
	 @ post (* \result.getEigenvectors().times(\result.lambda()).equals(matrix) *);
	 @ post (* (\forall int i; i>=1 && i<= matrix.getNbRows();
	 @            \result.getEigenvectors().times(\result.getEigenvector(i)).equals(\result.getEigenvector(i).times(\result.getEigenvalue(i))) *);
	 @ post \result.getEigenvectors().getNbRows() == matrix.getNbRows();
	 @ post \result.getEigenvectors().getNbColumns() == matrix.getNbColumns();
	 @*/
	public /*@ pure @*/ EigenvalueDecomposition decompose(Matrix matrix);
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
