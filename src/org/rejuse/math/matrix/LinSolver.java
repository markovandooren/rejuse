package org.rejuse.math.matrix;
/**
 * <p>A class of objects that solve linear systems of equations represented
 * as a matrix.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface LinSolver {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Solve the system of linear equations as defined by
	 * A * x = b
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
	 @ pre A.isSquare();
	 @ pre (* A is nonsingular *);
	 @
	 @ // Aside from rounding errors, A * x = b
	 @ post (* A.times(\result).equals(b) *);
	 @*/
	public /*@ pure @*/ Column solve(Matrix A, Column b);
}
/*<copyright>Copyright (C) 1997-2002. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/	
