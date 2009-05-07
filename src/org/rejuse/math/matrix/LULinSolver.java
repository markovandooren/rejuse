package org.rejuse.math.matrix;

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
/*<copyright>Copyright (C) 1997-2002. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://www.jutil.org/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://jutil.org/</copyright>*/
