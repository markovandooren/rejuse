package org.rejuse.math.matrix;
/**
 * This class represents a QR factorization of a matrix.
 *
 * The methods Qtimes and QtranposeTimes are provide because the matrix
 * Q itself isn't interesting most of the times, and isn't required 
 * explicitly to calculate Q * x and transpose(Q) * x.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface QRDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
	 @ public invariant Q().getNbColumns() == R().getNbRows();
	 @*/

	/**
	 * Return the Q matrix of this QR factorization.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.getNbRows() == R().getNbRows();
	 @ post \result.getNbColumns() == R().getNbRows();
	 @ // TODO: post \result.isOrthoNormal();
	 @*/
	public /*@ pure @*/ Matrix Q();
	
	/**
	 * Return the R matrix of this QR factorization.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isUpperTriangular();
	 @*/
	public /*@ pure @*/ Matrix R();

	/**
	 * Return the reduced Q matrix.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result.equals(Q().subMatrix(1,1,Q().getNbRows(), R().getNbColumns()));
	 @*/
	public /*@ pure @*/ Matrix Qreduced();

	/**
	 * Return the reduced R matrix.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result.equals(R().subMatrix(1,1,R().getNbColumns(), R().getNbColumns()));
	 @*/
	public /*@ pure @*/ Matrix Rreduced();
	
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
	 * Return Qreduced().times(column)
	 *
	 * @param column
	 *        The vector with which Qreduced must be multiplied
	 */
 /*@
   @ public behavior
	 @
	 @ pre column != null;
	 @ pre column.size() == Qreduced().getNbColumns();
	 @
	 @ post \result.equals(Qreduced().times(column));
	 @*/
	public /*@ pure @*/ Column QreducedTimes(Column column);

	
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

	/**
	 * Return Qreduced().returnTranspose().times(column)
	 *
	 * @param column
	 *        The vector with which Q must be multiplied
	 */
 /*@
   @ public behavior
	 @
	 @ pre column != null;
	 @ pre column.size() == Qreduced().getNbRows();
	 @
	 @ post \result.equals(Qreduced().returnTranspose().times(column));
	 @*/
	public /*@ pure @*/ Column QreducedTransposeTimes(Column column);
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
