package be.kuleuven.cs.distrinet.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the Schur factorization of a matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface SchurDecomposer {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
	 * <p>Return a Schur factorization of this matrix.</p>
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
	 @ post (* \result.Q().times(\result.R()).equals(matrix) *);
	 @ post \result.Q().getNbRows() == matrix.getNbRows();
	 @*/
	public /*@ pure @*/ SchurDecomposition decompose(Matrix matrix);
}
	
