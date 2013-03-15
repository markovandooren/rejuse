package be.kuleuven.cs.distrinet.rejuse.math.matrix;
/**
 * This class represents a Cholesky factorization of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class DefaultCholeskyDecomposition implements CholeskyDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new DefaultCholeskyDecomposition with the given R matrix.
	 *
	 * @param R
	 *        The R matrix of the Cholesky factorization
	 */
 /*@
   @ public behavior
	 @
	 @ pre R != null;
	 @ pre R.isUpperTriangular();
	 @ pre R.isSquare();
	 @
	 @ post R().equals(R);
	 @*/
  public /*@ pure @*/ DefaultCholeskyDecomposition(Matrix R) {
		_R=(Matrix)R.clone();
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix R() {
		return _R;
  }
	
	private Matrix _R;
}

