package org.aikodi.rejuse.math.matrix;

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

