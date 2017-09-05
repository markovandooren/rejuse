package org.aikodi.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the LU factorization of a matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface LUDecomposer {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
	 * <p>Return an LU factorization of this matrix.</p>
	 *
	 * @param matrix
	 *        The matrix to decompose.
	 */
 /*@
   @ public behavior
	 @
	 @ pre matrix != null;
	 @ pre matrix.isSquare();
	 @
	 @ post \result != null;
	 @ post (* \result.L().times(\result.U()).equals(matrix) *);
	 @ post \result.L().getNbRows() == matrix.getNbRows();
	 @*/
	public /*@ pure @*/ LUDecomposition decompose(Matrix matrix);
}
	
