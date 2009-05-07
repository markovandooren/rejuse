package org.rejuse.math.matrix;

/**
 * A class of schur decompositions of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface SchurDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
   @ public invariant Q().getNbColumns() == R().getNbColumns();
   @*/
	
	/**
	 * Return the R matrix of this SchurDecomposition.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isUpperTriangular();
	 @ post \result.isSquare();
	 @*/
	public /*@ pure @*/ Matrix R();

	/**
	 * Return the Q matrix of this SchurDecomposition.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ // TODO: post \result.isUnary();
	 @ post \result.isSquare();
	 @*/
	public /*@ pure @*/ Matrix Q();
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
