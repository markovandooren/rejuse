package org.rejuse.math.matrix;
/**
 * <p>This class represents an LU factorization of a matrix.</p>
 * <p>P*A = L*U</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface LUDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
	 @ private invariant L().sameDimensions(U());
	 @ private invariant P().sameDimensions(U());
	 @*/
	
	/**
	 * Return the L matrix of this LU factorization.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isSquare();
	 @ post \result.isLowerTriangular();
	 @ post (\forall int i; i>=1 && i<=\result.getNbColumns();
	 @         \result.elementAt(i,i) == 1);
	 @*/
	public /*@ pure @*/ Matrix L();
	
	/**
	 * Return the U matrix of this LU factorization.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isSquare();
	 @ post \result.isUpperTriangular();
	 @*/
	public /*@ pure @*/ Matrix U();
	
	/**
	 * Return the P matrix of this LU factorization.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isPermutationMatrix();
	 @*/
	public /*@ pure @*/ Matrix P();
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
