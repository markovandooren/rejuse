package org.rejuse.math.matrix;

/**
 * <p>A HessenbergReduction represents a Hessenberg reduction of a matrix.</p>
 * <p>The Hessenberg reduction of a matrix A contains a Hessenberg matrix H and
 * a unitary matrix Q such that H=(Q*)*A*Q. (Q*) is the adjoint of Q.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface HessenbergReduction {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Return the Hessenberg matrix of this HessenbergReduction.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isSquare();
	 @ // TODO: post \result.isHessenberg();
	 @*/
	public /*@ pure @*/ Matrix H();
	
	/**
	 * Return the unitary Q matrix of this HessenbergReduction.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isSquare();
	 @ post (* \result.isUnitary() *);
	 @*/
	public /*@ pure @*/ Matrix Q();
	
	/**
	 * Return Q().times(column)
	 *
	 * @param column
	 *        The vector with which Q() must be multiplied
	 */
 /*@
   @ public behavior
	 @
	 @ pre column != null;
	 @ pre column.size() == Q().getNbColumns();
	 @
	 @ post \result.equals(Q().times(column));
	 @*/
	public /*@ pure @*/ Column Qtimes(Column column);

	/**
	 * Return Q().returnTranspose().times(column)
	 *
	 * @param column
	 *        The vector with which Q must be multiplied
	 */
 /*@
   @ public behavior
	 @
	 @ pre column != null;
	 @ pre column.size() == Q().getNbRows();
	 @
	 @ post \result.equals(Q().returnTranspose().times(column));
	 @*/
	public /*@ pure @*/ Column QtransposeTimes(Column column);
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
