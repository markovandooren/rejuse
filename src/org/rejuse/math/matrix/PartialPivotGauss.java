package org.rejuse.math.matrix;

/**
 * <p>A class of objects that compute the LU factorization of a
 * square non-singular matrix using Gauss elimination with partial pivoting.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class PartialPivotGauss implements LUDecomposer {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * <p>See superclass</p>
	 * <p>The result is computed using Gauss elimination with partial
	 *    pivoting.</p>
	 */
	public /*@ pure @*/ LUDecomposition decompose(Matrix matrix) {
		Matrix U = (Matrix) matrix.clone();
		int m = matrix.getNbColumns();
		Matrix L = Matrix.unity(m);
		Matrix P = Matrix.unity(m);
		for(int k=1; k<=(m - 1); k++) {
			double max = Math.abs(U.elementAt(k,k));
			int row = k;
			for(int count = k+1; count<=m; count ++) {
				// determine the row with the biggest element
				double elem = Math.abs(U.elementAt(count,k));
				if (elem > max) {
					max = elem;
					row=count;
        }
			}
			
			// switch rows in L, P and U
			if (row != k) {
				// U
				Row temp_k = U.getRow(k);
				Row temp_row = U.getRow(row);
				U.setRow(k,temp_row);
				U.setRow(row,temp_k);
				// L
				Row temp_k_full = L.getRow(k);
				temp_k = temp_k_full.subRow(1,k-1);
				Row temp_row_full = L.getRow(row);
				temp_row = temp_row_full.subRow(1,k-1);
				temp_k_full.setSubRow(1,temp_row);
				temp_row_full.setSubRow(1,temp_k);
				L.setRow(k,temp_k_full);
				L.setRow(row,temp_row_full);
				// P
				// MvDMvDMvD : this is not efficient.
				temp_k = P.getRow(k);
				temp_row = P.getRow(row);
				P.setRow(k,temp_row);
				P.setRow(row,temp_k);
				
				double ukk = U.elementAt(k,k);
				//Row temp_row_k = U.getRow(k).subRow(k,m);
				Row temp_row_k = U.getRow(k);
				for(int j=k+1; j<=m; j++) {
					double ljk = U.elementAt(j,k)/ukk;
					L.setElementAt(j,k,ljk);
					//Row temp_row_j = U.getRow(j).subRow(k,m);
					Row temp_row_j = U.getRow(j);
					temp_row_j.subtract(temp_row_k.times(ljk));
					U.setRow(j,temp_row_j);
        }
      }
    }
		// set 0's in U and L
		for(int i=1; i<=m; i++) {
			for(int j=1; j<i; j++) {
				U.setElementAt(i,j,0);
      }
			for(int j=i+1; j<=m; j++) {
				L.setElementAt(i,j,0);
      }
    }
		return new DefaultLUDecomposition(L,U,P);
  }
} 
	
