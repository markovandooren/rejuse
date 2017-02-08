package org.aikodi.rejuse.math.matrix;
/**
 * This class represents a HouseHolder QR factorization of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class HouseholderQRDecomposition implements QRDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new HouseholderQRDecomposition combination with the given R matrix and
	 * v vectors.
	 *
	 * @param R
	 *        The R matrix of the QR factorization
	 * @param vs
	 *        An array of v vectors produce by the HouseHolder factorization
	 *        The first vector of the algorithm is at position -, the last
	 *        vector is at position vs.length - 1
	 */
 /*@
   @ public behavior
	 @
	 @ pre R != null;
	 @ pre R.isUpperTriangular();
	 @ pre vs != null;
	 @ pre vs.length == R.getNbRows();
	 @ pre (\forall int i; i>=0 && i<vs.length;
	 @       (vs[i] != null) &&
	 @       (vs[i].size() == R.getNbRows() - i));
	 @
	 @ post R().equals(R);
	 @ post (\forall int i; i>=1 && i<= vs.length;
	 @         getV(i).equals(vs[i-1]));
	 @*/
  public /*@ pure @*/ HouseholderQRDecomposition(Matrix R, Column[] vs) {
		_R=(Matrix)R.clone();
		_vectors = new Column[vs.length];
		for (int i=0; i<_vectors.length; i++) {
		  _vectors[i] = (Column) vs[i].clone();
		}
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix R() {
		return (Matrix)_R.clone();
	}

	/**
	 * <p>Return the i-th v vector as computed by the Householder
	 * algorithm that computed this QR decomposition.</p>
	 *
	 * @param i
	 *        <p>The index of the requested v vector.
	 *           Indices start from 1.</p>
	 */
	public /*@ pure @*/ Column getV(int i) {
		return (Column) _vectors[i-1];
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix Q() {
		return Q(_R.getNbRows());
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix Qreduced() {
		return Q(_R.getNbColumns());
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix Rreduced() {
		return _R.subMatrix(1,1,_R.getNbColumns(), _R.getNbColumns());
	}

	/**
	 * Return a matrix with the first k columns of Q.
	 *
	 * @param k
	 *        The number of columns
	 */
 /*@
   @ private behavior
	 @
	 @ pre k >= 1 && k <= R().getNbRows();
	 @
   @ post \result != null;
	 @ post \result.getNbRows() == R().getNbRows();
	 @ post \result.getNbColumns() == k;
	 @ post (* \result.getColumn(i) == i-th column of Q *);
	 @*/
	private /*@ pure @*/ Matrix Q(int k) {
		Matrix result = new Matrix(k, k);
		for (int i=1; i <= k; i++) {
			result.setColumn(i, Qcolumn(i));
		}
		return result;
	}

	/**
	 * Return a the i-th column of Q.
	 *
	 * @param i
	 *        The index of the requested column.
	 */
 /*@
   @ private behavior
	 @
	 @ pre i >= 1 && i <= R().getNbRows();
	 @
   @ post \result != null;
	 @ post \result.getNbRows() == R().getNbRows();
	 @ post (* \result == i-th column of Q *);
	 @*/
	private /*@ pure @*/ Column Qcolumn(int i) {
		Column unity = new Column(_R.getNbRows());
		unity.setElementAt(i,1);
		return Qtimes(unity);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column Qtimes(Column column) {
		Column x = (Column)column.clone();
		int m = x.size();
		int n = _R.getNbColumns();
		for (int k=n; k>= 1; k--) {
			Column vk = _vectors[k-1];
			Column xkm = x.subColumn(k,m);
			Matrix temp = vk.times(vk.returnTranspose().times(xkm));
			temp.multiply(2);
			xkm.subtract(temp);
			x.setSubColumn(k, xkm);
		}
		return x;
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column QreducedTimes(Column column) {
		int m = _R.getNbRows();
		Column b = new Column(m);
		b.setSubColumn(1,column);
		// b = [column | 0]
		return Qtimes(b);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column QtransposeTimes(Column column) {
		Column b = (Column)column.clone();
		int m = column.size();
		int n = _R.getNbColumns();
		for (int k=1; k<= n; k++) {
			Column vk = _vectors[k-1];
			Column bkm = b.subColumn(k,m);
			Matrix temp = vk.times(vk.returnTranspose().times(bkm));
			temp.multiply(2); 
			bkm.subtract(temp);
			b.setSubColumn(k, bkm);
		}
		return b;
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column QreducedTransposeTimes(Column column) {
		return QtransposeTimes(column).subColumn(1,_R.getNbColumns());
	}

	/**
	 * The R matrix of this QR factorization.
	 */
 /*@
	 @ private invariant _R != null;
   @ private invariant _R.isUpperTriangular();
	 @*/
	private Matrix _R;
 
	/**
	 * The v vectors produced by the HouseHolder triangularization.
	 */
 /*@
	 @ private invariant _vectors != null;
   @ private invariant (\forall int i; i>=0 && i<_vectors.length;
	 @                     _vectors[i] != null &&
	 @                     _vectors[i].size() == _R.getNbRows() - i);
	 @*/
	private Column[] _vectors;
}

