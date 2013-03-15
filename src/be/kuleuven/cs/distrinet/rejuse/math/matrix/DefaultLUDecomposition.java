package be.kuleuven.cs.distrinet.rejuse.math.matrix;
/**
 * This class represents a default LU factorization of a square 
 * non-singular matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class DefaultLUDecomposition implements LUDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new DefaultLUDecomposition with the given 
	 * L and U matrices
	 *
	 * @param L
	 *        The L matrix of the LU decomposition
	 * @param U
	 *        The U matrix of the LU decomposition
   */
 /*@
	 @ public behavior
	 @
	 @ pre L != null;
	 @ pre L.isSquare();
	 @ pre L.isLowerTriangular();
	 @ pre (\forall int i; i>=1 && i<=L.getNbColumns();
	 @        L.elementAt(i,i) == 1);
	 @ pre U != null;
	 @ pre U.sameDimensions(L);
	 @ pre U.isUpperTriangular();
	 @ pre P != null;
	 @ pre P.isPermutationMatrix();
	 @ pre P.sameDimensions(L);
	 @
	 @ post L().equals(L);
	 @ post U().equals(U);
	 @ post P().equals(P);
	 @*/
	public DefaultLUDecomposition(Matrix L, Matrix U, Matrix P) {
		_L=(Matrix)L.clone();
		_U=(Matrix)U.clone();
		_P=(Matrix)P.clone();
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix L() {
		return (Matrix)_L.clone();
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix U() {
		return (Matrix)_U.clone();
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix P() {
		return (Matrix)_P.clone();
  }

	/**
	 * The L matrix of this LU decomposition.
	 */
 /*@
	 @ private invariant _L != null;
	 @ private invariant _L.isSquare();
	 @ private invariant _L.isLowerTriangular();
	 @ private invariant (\forall int i; i>=1 && i<=_L.getNbColumns();
	 @                     _L.elementAt(i,i) == 1);
	 @*/
	private Matrix _L;

	/**
	 * The L matrix of this LU decomposition.
	 */
 /*@
	 @ private invariant _U != null;
	 @ private invariant _U.isSquare();
	 @ private invariant _U.isUpperTriangular();
	 @ private invariant _U.sameDimensions(_L);
	 @*/
	private Matrix _U;

	/**
	 * The P matrix of this LU decomposition.
	 */
 /*@
	 @ private invariant _P != null;
	 @ private invariant _P.isPermutationMatrix();
	 @ private invariant _P.sameDimensions(_L);
	 @*/
	private Matrix _P;
}

