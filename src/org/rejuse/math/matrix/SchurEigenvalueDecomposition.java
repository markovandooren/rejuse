package org.rejuse.math.matrix;

/**
 * A class of eigenvalue decomposers using an explict shift algorithm.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class SchurEigenvalueDecomposition implements EigenvalueDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new SchurEigenvalueDecomposition with the given decomposition and LinSolver.
	 *
	 * @param decomposition
	 *        The schur decomposition of the matrix.
	 * @param linSolver
	 *        The LinSolver to be used for calculating the eigenvectors.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre decomposition != null;
	 @ pre linSolver != null;
	 @
	 @ post getLinSolver() == linSolver;
	 @*/
	public /*@ pure @*/ SchurEigenvalueDecomposition(SchurDecomposition decomposition, LinSolver linSolver) {
		_linSolver = linSolver;
		_decomposition = decomposition;
		Matrix R = decomposition.R();
		int n = R.getNbColumns(); 
		_eigenvalues = new double[n];
		for(int i=1; i<=n;i++) {
			_eigenvalues[i-1] = R.elementAt(i,i);
    }
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix lambda() {
		return new Matrix(_eigenvalues);
	}
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ int getNbEigenvalues() {
		return _eigenvalues.length;
	}
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Matrix getEigenvectors() {
		ensureEigenvectors();
		return (Matrix)_eigenvectors.clone();
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column getEigenvalues() {
		return new Column(_eigenvalues);
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ Column getEigenvector(int index) {
		ensureEigenvectors();
		return _eigenvectors.getColumn(index);
  }
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ double getEigenvalue(int index) {
		return _eigenvalues[index-1];
	}
	
	/**
	 * Ensure that the eigenvectors are computed.
	 */
	private void ensureEigenvectors() {
		if(_eigenvectors == null) {
			computeEigenvectors();
		}
	}
	
	/**
	 * Return the LinSolver used to compute the eigenvectors.
	 */
	public /*@ pure @*/ LinSolver getLinSolver() {
		return _linSolver;
	}
	
  /**
	 * Compute the eigenvectors of the matrix.
	 * After this, the decomposition will be discarded.
	 */
	private void computeEigenvectors() {
		Matrix R = _decomposition.R();
		Matrix Q = _decomposition.Q();
		int n = R.getNbColumns();
		_eigenvectors = new Matrix(n,n);
		Column yk = new Column(n);
		Column xk = new Column(n);
	  
		for(int i=1; i<=n; i++) {
			// copy upper left part when i > 1
			if(i>1) {
				Matrix A = new Matrix(i-1,i-1);
				Column b = new Column(i-1);

				A.setSubMatrix(1,1,R.subMatrix(1,1,i-1,i-1));

			  // subtract the eigenvalue of the eigenvector we're computing from 
	   	  // the diagonal elements of A
				double eigenvalue = R.elementAt(i,i);
				for(int j=1;j<i;j++) {
					A.setElementAt(j,j,A.elementAt(j,j)-eigenvalue);
				}
			
				// compute the righthandside			
				b.setSubColumn(1,R.getColumn(i).subColumn(1,i-1));
		  	b.multiply(-1);	
				// solve the linear system
				// create the eigenvector of R
				yk.setSubColumn(1,getLinSolver().solve(A,b).subColumn(1,i-1));
			}
			yk.setElementAt(i,1);
			for(int j=i+1; j<=n;j++) {
		  	yk.setElementAt(j,0);
			}
			// compute the eigenvector of A
			// the getColumn(1) is an unnecessary clone
      xk = Q.times(yk).getColumn(1);
			xk.divide(xk.norm(2));
			_eigenvectors.setColumn(i,xk);

    }//for
		// set _decomposition to null, we don't need it anymore
		_decomposition = null;
  }
		/**
		 * An array containing the eigenvalues
		 */
	 /*@
		 @ private invariant _eigenvalues != null;
		 @ private invariant _eigenvalues.length > 0;
		 @*/
		private double[] _eigenvalues;

		/**
		 * The Schurdecomposition from the matrix.
		 * 
		 * It is kept in order to save the cost of calculating the
		 * eigenvectors when they aren't needed. They are calculated lazy.
		 */
	 /*@
		 @ // As long as the eigenvectors aren't computed yet,
		 @ // the decomposition must be available.
		 @ private invariant _eigenvectors == null ==> _decomposition != null;
		 @*/
		private SchurDecomposition _decomposition;

	  /**
		 * The LinSolver used to compute the eigenvectors.
		 */
	 /*@
		 @ private invariant _linSolver != null;
		 @*/
		private LinSolver _linSolver;
		/**
		 * A matrix containing the eigenvectors
		 */
		private Matrix _eigenvectors;
	}
/*
<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the JUTIL.ORG Software License for more details. For more information,
please see http://org-jutil.sourceforge.net/</copyright>/
*/
