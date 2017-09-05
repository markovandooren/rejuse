package org.aikodi.rejuse.math.matrix;

/**
 * <p>A class of objects that solve linear systems of equations represented
 * as a matrix using the LU decomposition of that matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class LULinSolver extends AbstractSolver implements LinSolver {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new LULinSolver with the given LUDecomposer.
	 *
	 * @param decomposer
	 *        The LUDecomposer to be used by this LULinSolver.
	 */	
 /*@
   @ public behavior
	 @
	 @ pre decomposer != null;
	 @
	 @ post getDecomposer() == decomposer;
	 @*/
	public /*@ pure @*/ LULinSolver(LUDecomposer decomposer) {
		_decomposer = decomposer;
	}
	
	/**
	 * Return the LUDecomposer of this LULinSolver.
	 */
	public /*@ pure @*/ LUDecomposer getDecomposer() {
		return _decomposer;
	}
	
	/**
	 * see superclass
	 */
	public /*@ pure @*/ Column solve(Matrix A, Column b) {
		// P*A = L*U
		// A*x = b
		// P*A*x = P*b
		// L*U*x = P*b
		// L*(U*x) = P*b
		// L*(y) = P*b
		// U*x = y
		LUDecomposition lu = getDecomposer().decompose(A);
		Column Pb = lu.P().times(b).getColumn(1);
		Column y = forwardSubstitute(lu.L(),Pb);
		return backSubstitute(lu.U(), y);
  }
	
	/**
	 * The QRDecomposer used by this QRLinSolver.
	 */
 /*@
   @ private invariant _decomposer != null;
	 @*/
	private LUDecomposer _decomposer;
}

