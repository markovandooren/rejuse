package org.rejuse.math.matrix;

/**
 * A class of schur decomposers using an explict shift
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class ExplicitShiftQRSchurDecomposer implements SchurDecomposer {
	
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new ExplicitShiftQRSchurDecomposer with the given
	 * HessenbergReducer
	 *
	 * @param reducer
	 *        The HessenbergReducer to be used by this ExplicitShiftQRSchurDecomposer.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre reducer != null;
	 @
	 @ post getHessenbergReducer() == reducer;
	 @ post getEpsilon() == 0.0000000000000001d;
	 @*/
	public ExplicitShiftQRSchurDecomposer(HessenbergReducer reducer) {
		_reducer = reducer;
		_epsilon = 0.0000000000000001d;
  }
	
	/**
	 * Initialize a new ExplicitShiftQRSchurDecomposer with the given
	 * HessenbergReducer
	 *
	 * @param reducer
	 *        The HessenbergReducer to be used by this ExplicitShiftQRSchurDecomposer.
	 * @param epsilon
	 *        The precision with which the results must be computed.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre reducer != null;
	 @ pre epsilon > 0;
	 @
	 @ post getHessenbergReducer() == reducer;
	 @ post getEpsilon() == epsilon;
	 @*/
	public /*@ pure @*/ ExplicitShiftQRSchurDecomposer(HessenbergReducer reducer, double epsilon) {
		_reducer = reducer;
		_epsilon = epsilon;
  }

	/**
	 * Return the HessenbergReducer used by this ExplicitShiftQRSchurDecomposer
	 * to calculate Schur decompositions.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ HessenbergReducer getHessenbergReducer() {
		return _reducer;
  }
	
	/**
	 * Return the precision with which the results must be computed.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result > 0;
	 @*/
	public /*@ pure @*/ double getEpsilon() {
		return _epsilon;
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ SchurDecomposition decompose(Matrix matrix) {
		// First we calculate the hessenberg reduction.
		
		double epsilon = _epsilon;
		HessenbergReduction reduction = getHessenbergReducer().reduce(matrix);
		Matrix Q = reduction.Q();
		Matrix A = reduction.H();
		int n = A.getNbRows();
		//double[] eigenvals = new double[n];
		
		for(int i = n; i>1; i--) {
			// iterate until Ai,i-1 is small enough
			while(Math.abs(A.elementAt(i,i-1)) > epsilon * (Math.abs(A.elementAt(i-1,i-1)) + Math.abs(A.elementAt(i,i)))) {
				// use the eigenvalue of the following matrix that is closest
				// to Ai,i as shift.
				//
				// Ai-1,i-1  Ai-1,i
				// Ai,i-1    Ai,i
				//
				// Therefore we must solve the quadratic equation
				// lambda^2 -(Ai-1,i-1 + Ai,i)*lambda + (Ai-1,i-1 * Ai,i - Ai,i-1 * Ai-1,i) = 0
				double a=1;
				double b=-(A.elementAt(i-1,i-1) + A.elementAt(i,i));
				double c=(A.elementAt(i-1,i-1) * A.elementAt(i,i)) - 
				(A.elementAt(i,i-1) * A.elementAt(i-1,i));
				double D = Math.pow(b,2.0) - 4*a*c;
				double sol1 = (-b + Math.sqrt(D))/(2*a);
				double sol2 = (-b - Math.sqrt(D))/(2*a);
				double shift=0;
				if (Math.abs(sol1 - A.elementAt(i,i)) < Math.abs(sol2 - A.elementAt(i,i))) {
					shift=sol1;
				}
				else {
					shift=sol2;
				}
				iterate(A, Q, shift);
      }
			A.setElementAt(i,i-1,0);
			// we've found diagonal element i
    }
		// The last 2 diagonal elements are found together.
		// At the end of the iteration, A[2,1] is small enough
		// meaning that A[1,1] also is a dioganal element.
		
		// MvDMvDMvD: Make A explicitly triangular ?
		return new DefaultSchurDecomposition(A,Q);
  }
	
	private void iterate(Matrix matrix, Matrix Q, double shift) {
		int m = matrix.getNbRows();
		double ck_prev = 0;
		double sk_prev = 0;
		double ck = 0;
		double sk = 0;
		matrix.setElementAt(1,1,matrix.elementAt(1,1)-shift);
		for(int k=1; k<=m; k++) {
			// left rotation
			if(k != m) {
				double akk = matrix.elementAt(k,k);
				double ak_plus_1_k = matrix.elementAt(k+1,k);
				double omega = Math.sqrt(Math.pow(akk,2.0) + Math.pow(ak_plus_1_k,2.0));
				ck = akk/omega;
				sk = -ak_plus_1_k/omega;
				
				// subtract shift in advance
				matrix.setElementAt(k+1,k+1,matrix.elementAt(k+1,k+1)-shift);
				
				// left givens rotation is not efficient since the elements on the
				// left are already 0.
				matrix.leftGivens(ck, sk, k, k+1);
				Q.rightGivens(ck, sk, k, k+1);
				
				// This should be the result of the left givens rotation,
				// but there will be rounding errors, so we overwrite
				// the values calculated by the givens rotation.
				// Of course the means even more useless calculations, but for
				// now it will do.
				matrix.setElementAt(k,k,omega);
				matrix.setElementAt(k+1,k,0);
				for(int i = 1; i<k; i++) {
					matrix.setElementAt(k,i,0);
					matrix.setElementAt(k+1,i,0);
        }
				
			}
			// right rotation
			if (k != 1) {
				matrix.rightGivens(ck_prev, sk_prev, k-1 , k);
				// add shift afterwards
				matrix.setElementAt(k-1,k-1,matrix.elementAt(k-1,k-1)+shift);
      }
			// store the givens coefficient after performing the right rotation
			ck_prev=ck;
			sk_prev=sk;
		}
		matrix.setElementAt(m,m,matrix.elementAt(m,m)+shift);
  }

	/**
	 * The HessenbergReducer used by this ExplicitShiftQREigenvalueDecomposer
	 * to calculate eigenvalue decompositions.
	 */
 /*@
	 @ private invariant _reducer != null;
	 @*/
	private HessenbergReducer _reducer;	
	
	/**
	 * The precision with which the results must be computed.
	 */
 /*@
	 @ private invariant _epsilon > 0;
	 @*/
	private double _epsilon;
}

