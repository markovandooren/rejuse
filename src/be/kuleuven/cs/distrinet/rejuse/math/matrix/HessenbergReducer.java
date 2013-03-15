package be.kuleuven.cs.distrinet.rejuse.math.matrix;

/**
 * A class of matrix operators that compute the Hessenberg reduction
 * of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface HessenbergReducer {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Compute the Hessenberg reduction of the given matrix
	 *
	 * @param matrix
	 *        The matrix of which the Hessenberg reduction must be
	 *        computed.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre matrix != null;
	 @ pre matrix.isSquare();
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ HessenbergReduction reduce(Matrix matrix);
} 

