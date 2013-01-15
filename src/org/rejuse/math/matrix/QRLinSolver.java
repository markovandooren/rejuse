package org.rejuse.math.matrix;

/**
 * <p>A class of objects that solve linear systems of equations represented
 * as a matrix using its QR decomposition.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class QRLinSolver extends AbstractSolver implements LinSolver {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new QRLinSolver with the given QRDecomposer.
	 *
	 * @param decomposer
	 *        The QRDecomposer to be used by this QRLinSolver.
	 */	
 /*@
   @ public behavior
	 @
	 @ pre decomposer != null;
	 @
	 @ post getDecomposer() == decomposer;
	 @*/
	public /*@ pure @*/ QRLinSolver(QRDecomposer decomposer) {
		_decomposer = decomposer;
	}
	
	/**
	 * Return the QRDecomposer of this QRLinSolver.
	 */
	public /*@ pure @*/ QRDecomposer getDecomposer() {
		return _decomposer;
	}
	
	/**
	 * see superclass
	 */
	public /*@ pure @*/ Column solve(Matrix A, Column b) {
		QRDecomposition qr = getDecomposer().decompose(A);
		Column y = qr.QtransposeTimes(b);
		return backSubstitute(qr.R(), y);
  }
	
	/**
	 * The QRDecomposer used by this QRLinSolver.
	 */
 /*@
   @ private invariant _decomposer != null;
	 @*/
	private QRDecomposer _decomposer;
}
	
