package org.rejuse.math.matrix;

/**
 * A class of eigenvalue decompositions of a matrix.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface EigenvalueDecomposition {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Return the eigenvalue matrix of this EigenvalueDecomposition.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isDiagonal();
   @ post \result.getNbRows() == getNbEigenvalues();
   @ post \result.isSquare();
	 @ post (\forall int i; i>=1 && i <= getNbEigenvalues();
	 @        \result.elementAt(i,i) == getEigenvalue(i));
	 @*/
	public /*@ pure @*/ Matrix lambda();

	/**
	 * Return the eigenvectors.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
  public /*@ pure @*/ Matrix getEigenvectors();

  /**
   * Return the eigenvalues.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post (\forall int i; i>=1 && i<=\result.size();
	 @        \result.elementAt(i) != 0);
	 @*/
  public /*@ pure @*/ Column getEigenvalues();

	/**
	 * Return the index'th eigenvector.
	 *
	 * @param index
	 *        The index of the requested eigenvector.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre index >= 1;
	 @ pre index <= getNbEigenvalues();
	 @
	 @ post \result.equals(getEigenvectors().getColumn(index));
	 @*/
  public /*@ pure @*/ Column getEigenvector(int index);

	/**
	 * Return the index'th eigenvalue.
	 *
	 * @param index
	 *        The index of the requested eigenvalue.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre index >= 1;
	 @ pre index <= getNbEigenvalues();
	 @
	 @ post \result == getEigenvalues().elementAt(index);
	 @*/
  public /*@ pure @*/ double getEigenvalue(int index);

  /**
	 * Return the number of eigenvalues.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == getEigenvalues().size();
	 @*/
	public /*@ pure @*/ int getNbEigenvalues();
}
/*<copyright>Copyright (C) 1997-2002. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
