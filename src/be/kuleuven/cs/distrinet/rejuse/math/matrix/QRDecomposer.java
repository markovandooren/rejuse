package be.kuleuven.cs.distrinet.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the QR factorization of a matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface QRDecomposer {

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
	 @ pre matrix.getNbRows() >= matrix.getNbColumns();
	 @
	 @ post \result != null;
	 @ post (* \result.Q().times(\result.R()).equals(matrix) *);
	 @ post \result.R().getNbRows() == matrix.getNbRows();
	 @ post \result.R().getNbColumns() == matrix.getNbColumns();
	 @*/
	public /*@ pure @*/ QRDecomposition decompose(Matrix matrix);
}
	
