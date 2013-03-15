package be.kuleuven.cs.distrinet.rejuse.math.matrix;

/**
 * A class of schur decompositions of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface SchurDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
   @ public invariant Q().getNbColumns() == R().getNbColumns();
   @*/
	
	/**
	 * Return the R matrix of this SchurDecomposition.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isUpperTriangular();
	 @ post \result.isSquare();
	 @*/
	public /*@ pure @*/ Matrix R();

	/**
	 * Return the Q matrix of this SchurDecomposition.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ // TODO: post \result.isUnary();
	 @ post \result.isSquare();
	 @*/
	public /*@ pure @*/ Matrix Q();
}

