package org.aikodi.rejuse.math.matrix;

/**
 * A class of eigenvalue decomposers using an explict shift algorithm.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class SchurEigenvalueDecomposer implements EigenvalueDecomposer {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new SchurEigenvalueDecomposer with the given
	 * HessenbergReducer
	 *
	 * @param decomposer
	 *        The SchurDecomposer to be used by this SchurEigenvalueDecomposer.
	 * @param linSolver
	 *        The LinSolver to be used by this SchurEigenvalueDecomposer.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre decomposer != null;
	 @ pre linSolver != null;
	 @
	 @ post getSchurDecomposer() == decomposer;
	 @ post getLinSolver() == linSolver;
	 @*/
	public /*@ pure @*/ SchurEigenvalueDecomposer(SchurDecomposer decomposer, LinSolver linSolver) {
		_decomposer = decomposer;
		_linSolver = linSolver;
  }

	/**
	 * Return the SchurDecomposer used by this SchurEigenvalueDecomposer
	 * to calculate eigenvalue decompositions.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ SchurDecomposer getSchurDecomposer() {
		return _decomposer;
  }
	
	/**
	 * Return the LinSolver used by this SchurEigenvalueDecomposer
	 * to calculate eigenvalue decompositions.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ LinSolver getLinSolver() {
		return _linSolver;
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ EigenvalueDecomposition decompose(Matrix matrix) {
		SchurDecomposition schurDecomposition = getSchurDecomposer().decompose(matrix);
		return new SchurEigenvalueDecomposition(schurDecomposition, getLinSolver());
  }
	
	/**
	 * The SchurDecomposer used by this SchurEigenvalueDecomposer
	 * to calculate eigenvalue decompositions.
	 */
 /*@
	 @ private invariant _decomposer != null;
	 @*/
	private SchurDecomposer _decomposer;	

	/**
	 * The LinSolver used by this SchurEigenvalueDecomposer
	 * to calculate eigenvalue decompositions.
	 */
 /*@
	 @ private invariant _linSolver != null;
	 @*/
	private LinSolver _linSolver;	
}

