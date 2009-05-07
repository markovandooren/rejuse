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
