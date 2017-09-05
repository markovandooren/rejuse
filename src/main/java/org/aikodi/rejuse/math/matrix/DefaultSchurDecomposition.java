package org.aikodi.rejuse.math.matrix;

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
public class DefaultSchurDecomposition implements SchurDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new DefaultSchurDecomposition with the given R and Q matrices.
	 *
	 * @param R
	 *        The R matrix of the Schur decomposition.
	 * @param Q
	 *        The Q matrix of the Schur decomposition.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre R != null;
	 @ pre R.isUpperTriangular();
	 @ pre Q != null;
	 @ // TODO: pre Q.isUnary();
	 @
	 @ post R().equals(R);
	 @ post Q().equals(Q);
	 @*/
	public /*@ pure @*/ DefaultSchurDecomposition(Matrix R, Matrix Q) {
		_R = R;
		_Q = Q;
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix R() {
		return _R;
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix Q() {
		return _Q;
	}

	/**
	 * Return the R matrix of this DefaultSchurDecomposition
	 */
 /*@
   @ private invariant _R != null;
	 @ private invariant _R.isUpperTriangular();
	 @*/
	private Matrix _R;

	/**
	 * Return the Q matrix of this DefaultSchurDecomposition
	 */
 /*@
   @ private invariant _Q != null;
	 @ // TODO: private invariant _Q.isUnary();
	 @*/
	private Matrix _Q;
}

