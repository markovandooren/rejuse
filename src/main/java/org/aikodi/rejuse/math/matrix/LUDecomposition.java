package org.aikodi.rejuse.math.matrix;
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

