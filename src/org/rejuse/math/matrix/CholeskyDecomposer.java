package org.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the Cholesky factorization of a
 * symmetric matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface CholeskyDecomposer {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
	 * <p>Return a QR factorization of this matrix.</p>
	 *
	 * @param matrix
	 *        The matrix to decompose.
	 */
 /*@
   @ public behavior
	 @
	 @ pre matrix != null;
	 @ pre matrix.isSymmetric();
	 @ pre (* matrix is non-singular *);
	 @
	 @ post \result != null;
	 @ post (* \result.R().times(\result.R()).equals(matrix) *);
	 @*/
	public /*@ pure @*/ CholeskyDecomposition decompose(Matrix matrix);
}