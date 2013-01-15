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


