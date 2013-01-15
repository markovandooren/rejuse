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
	
