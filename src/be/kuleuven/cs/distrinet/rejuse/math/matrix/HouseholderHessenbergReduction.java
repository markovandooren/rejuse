package be.kuleuven.cs.distrinet.rejuse.math.matrix;
/**
 * This class represents a HouseHolder Hessenberg factorization of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class HouseholderHessenbergReduction implements HessenbergReduction {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new HouseholderHessenbergReduction combination with the given
	 * H matrix and v vectors.
	 *
	 * @param H
	 *        The H matrix of the Hessenberg reduction.
	 * @param vs
	 *        An array of v vectors produce by the HouseHolder factorization
	 *        The first vector of the algorithm is at position -, the last
	 *        vector is at position vs.length - 1
	 */
 /*@
   @ public behavior
	 @
	 @ pre H != null;
	 @ pre H.isSquare();
	 @ // TODO: pre H.isHessenberg();
	 @ pre vs != null;
	 @ pre vs.length == H.getNbColumns() - 2;
	 @ pre (\forall int i; i>=0 && i<vs.length;
	 @       (vs[i] != null) &&
	 @       (vs[i].size() == H.getNbRows() - i - 1));
	 @
	 @ post H().equals(H);
	 @ post (\forall int i; i>=1 && i<= vs.length;
	 @         getV(i).equals(vs[i-1]));
	 @*/
  public /*@ pure @*/ HouseholderHessenbergReduction(Matrix H, Column[] vs) {
		_H=(Matrix)H.clone();
		_vectors = new Column[vs.length];
		for (int i=0; i<_vectors.length; i++) {
		  _vectors[i] = (Column) vs[i].clone();
		}
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix H() {
		return (Matrix)_H.clone();
	}

	/**
	 * <p>Return the i-th v vector as computed by the Householder
	 * algorithm that computed this Hessenberg decomposition.</p>
	 *
	 * @param i
	 *        <p>The index of the requested v vector.
	 *           Indices start from 1.</p>
	 */
 /*@
	 @ public behavior
	 @
	 @ pre i>=1;
	 @ pre i<=H().getNbColumns() - 2;
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Column getV(int i) {
		return (Column) _vectors[i-1];
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix Q() {
		int m=_H.getNbRows();
		Matrix result = new Matrix(m,m);
		for (int i=1; i <= m; i++) {
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
	 @ pre i >= 1 && i <= H().getNbRows();
	 @
   @ post \result != null;
	 @ post \result.getNbRows() == H().getNbRows();
	 @ post (* \result == i-th column of Q *);
	 @*/
	private /*@ pure @*/ Column Qcolumn(int i) {
		Column unity = new Column(_H.getNbRows());
		unity.setElementAt(i,1);
		return Qtimes(unity);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column Qtimes(Column column) {
		Column x = (Column)column.clone();
		int m = x.size();
		int n = _H.getNbColumns();
		int amount = n-2;
		for (int k=amount; k>= 1; k--) {
			Column vk = _vectors[k-1];
			//vk.size() = m - k
			Column xkm = x.subColumn(k+1,m);
			// xkm.size() == m - k
			Matrix temp = vk.times(vk.returnTranspose().times(xkm));
			temp.multiply(2);
			xkm.subtract(temp);
			x.setSubColumn(k+1, xkm);
		}
		return x;
	}


	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column QtransposeTimes(Column column) {
		Column b = (Column)column.clone();
		int m = column.size();
		int n = _H.getNbColumns();
		int amount = n - 2;
		for (int k=1; k<= amount; k++) {
			Column vk = _vectors[k-1];
			Column bkm = b.subColumn(k+1,m);
			Matrix temp = vk.times(vk.returnTranspose().times(bkm));
			temp.multiply(2); 
			bkm.subtract(temp);
			b.setSubColumn(k+1, bkm);
		}
		return b;
	}

	/**
	 * The R matrix of this QR factorization.
	 */
 /*@
	 @ private invariant _H != null;
	 @ private invariant _H.isSquare();
   @ // TODO: private invariant _H.isHessenberg();
	 @*/
	private Matrix _H;
 
	/**
	 * The v vectors produced by the HouseHolder triangularization.
	 */
 /*@
	 @ private invariant _vectors != null;
   @ private invariant (\forall int i; i>=0 && i<_vectors.length;
	 @                     _vectors[i] != null &&
	 @                     _vectors[i].size() == _H.getNbColumns() - i - 1);
	 @*/
	private Column[] _vectors;
}

