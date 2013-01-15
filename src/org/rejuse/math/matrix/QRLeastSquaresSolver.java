package org.rejuse.math.matrix;

/**
 * <p>A class of objects that solve least squares problems using its QR decomposition.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class QRLeastSquaresSolver extends AbstractSolver implements LeastSquaresSolver {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new QRLeastSquaresSolver with the given QRDecomposer.
	 *
	 * @param decomposer
	 *        The QRDecomposer to be used by this QRLeastSquaresSolver.
	 */	
 /*@
   @ public behavior
	 @
	 @ pre decomposer != null;
	 @
	 @ post getDecomposer() == decomposer;
	 @*/
	public QRLeastSquaresSolver(QRDecomposer decomposer) {
		_decomposer = decomposer;
	}
	
	/**
	 * Return the QRDecomposer of this QRLeastSquaresSolver.
	 */
	public /*@ pure @*/ QRDecomposer getDecomposer() {
		return _decomposer;
	}
	
	/**
	 * see superclass
	 */
	public /*@ pure @*/ Column solve(Matrix A, Column b) {
 		QRDecomposition qr = getDecomposer().decompose(A);
 		Column y = qr.QreducedTransposeTimes(b);
 		return backSubstitute(qr.Rreduced(), y);
  }
	
	/**
	 * The QRDecomposer used by this QRLeastSquaresSolver.
	 */
 /*@
   @ private invariant _decomposer != null;
	 @*/
	private QRDecomposer _decomposer;
}
	
