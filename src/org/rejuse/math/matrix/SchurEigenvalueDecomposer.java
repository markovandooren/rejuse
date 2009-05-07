package org.rejuse.math.matrix;

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
