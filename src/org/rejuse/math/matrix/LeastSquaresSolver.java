 
package org.rejuse.math.matrix;
/**
 * <p>A class of objects that solve least square problems represented
 * as a matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface LeastSquaresSolver {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * <p>Solve the system of linear equations as defined by
	 * min(||b - A * x ||). The norm used is the 2-norm.</p>
	 *
	 * @param A
	 *        The matrix containing the coefficients of the equation.
	 *        Each row represents an equation. The i-th element of a row
	 *        represents the coefficient of the i-th variable in the equation
	 *        represented by that row.
	 * @param b
	 *        The column representing the right-hand sides of the equation.
	 *        The right-handig side of the i-th row is the i-th element of the
	 *        Column
	 */
 /*@
	 @ public behavior
	 @
	 @ pre A != null;
	 @ pre b != null;
	 @ pre b.size() == A.getNbRows();
	 @
	 @ post (* (\forall Column col; col.size() == b.size();
	 @           b.minus(A.times(\result)).norm(2) <=
	 @           b.minus(A.times(col)).norm(2)) *);
	 @*/
	//MvDMvDMvD : are there more preconditions ?
	public /*@ pure @*/ Column solve(Matrix A, Column b);
}

