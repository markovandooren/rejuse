package be.kuleuven.cs.distrinet.rejuse.math.matrix;
/**
 * This class represents a Cholesky factorization of a symmetric matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface CholeskyDecomposition {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Return the uppertriangular matrix of this CholeskyDecomposition.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isUpperTriangular();
	 @ post \result.isSquare();
	 @*/
	public /*@ pure @*/ Matrix R();
}