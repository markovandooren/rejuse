package org.aikodi.rejuse.math.matrix;

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

