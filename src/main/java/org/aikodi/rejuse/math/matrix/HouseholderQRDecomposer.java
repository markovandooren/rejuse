package org.aikodi.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the QR factorization of a matrix using
 * the Householder Algorithm.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class HouseholderQRDecomposer implements QRDecomposer {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * <p>The decomposition is done using the Householder algorithm.</p>
	 * <p>The algorithmic complexity is <b>O(2mn^2 - (2/3)n^3)</b>.</p>
	 */
	public /*@ pure @*/ QRDecomposition decompose(Matrix matrix) {
		Matrix R = (Matrix) matrix.clone();
		int columns = matrix.getNbColumns();
		int rows = matrix.getNbRows();
		Column[] vs = new Column[columns];
	  for (int k=1; k<=columns; k++) {
			Column x = R.getColumn(k).subColumn(k, rows);
			Column v = new Column(rows - k + 1);
			double x1 = x.elementAt(1);
			double signX1 = org.aikodi.rejuse.math.ExtMath.sign(x1);
			v.setElementAt(1,x.norm(2)*signX1);
			v.add(x);
			v.normalize();
			vs[k-1]=v;
			Matrix A = R.subMatrix(k,k,rows,columns);
			// subtracting from A saves us a clone()
			// mutating temp saves us another one ( <-> .times(2))
			Matrix temp = v.times(v.returnTranspose().times(A));
			temp.multiply(2);
			A.subtract(temp);
			R.setSubMatrix(k,k,A);
		}
		// explicitly make R an upper triangular matrix.
		for(int col=1; col<=columns; col++) {
			for(int row=col+1; row<=rows; row++) {
				R.setElementAt(row,col,0);
      }
    }
		return new HouseholderQRDecomposition(R,vs);
	}
}