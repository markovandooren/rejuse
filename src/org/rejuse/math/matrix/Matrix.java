package org.rejuse.math.matrix;

/**
 * A class of 2D matrices.
 *
 * This version doesn't try that hard to perform stable calculations.
 * If you need that, use the non-existing nummath package. If you don't know what it means,
 * use this version :)
 *
 * Matrix also doesn't report any overflow (standard Java behavior).
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Matrix extends NMatrix {
  
/**
 * MvDMvDMvD : some operations don't have 2 sensible names for use as a mutator and inspector.
 *
 * At this moment, I have 3 possible naming schemes for tranformations on 2D matrices.
 *
 *  1) name() : a mutator which applies the tranformation to this matrix.
 *     if you want a clone, just make it yourself.
 *
 *  2) name() : an inspector which returns a new matrix
 *     nameThis() : a mutator which applies the tranformation to this matrix.
 *
 *  3) returnName() : an inspector which returns a new matrix
 *     name() : a mutator which applies the tranformation to this matrix.
 *
 * Personally, I like 3) the best. It feels like talking to a matrix. If you say "matrix.transpose"
 * it will transpose itself.If you say "matrix.returnTranspose", it will return a transpose (a new matrix).
 */
  
 	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

/*@
   @ public invariant getNbDimensions() == 2;
   @*/

  /**
   * Initialize a new Matrix with the given number of rows and columns
   *
   * @param columns
   *        The number of columns for the new Matrix.
   * @param rows
   *        The number of rows for the new Matrix.
   */
 /*@
   @ public behavior
   @
   @ post getNbColumns() == columns;
   @ post getNbRows() == rows;
   @ post (\forall int i; i>0 && i < getNbRows();
   @        (\forall int j; j > 0 && j < getNbColumns();
   @          elementAt(i,j) == 0));
   @*/
  public /*@ pure @*/ Matrix(int rows, int columns) {
    _array = new double[columns][rows];
  }

  /**
	 * Initialize a new Matrix from the given 2D array of doubles.
	 *
	 * @param elements
	 *        A 2D array containing the elements of the new Matrix.
	 *        The first dimension is for the rows, the second for the columns.
	 */	
 /*@
	 @ public behavior
	 @
	 @ pre elements != null;
	 @ pre elements.length > 0;
	 @ pre (\exists int nbColumns; nbColumns > 0;
	 @        (\forall int i; i>=0 && i<elements.length;
	 @          elements[i] != null &&
	 @          elements[i].length == nbColumns));
	 @
	 @ post getNbRows() == elements.length;
	 @ post getNbColumns() == elements[0].length;
	 @ post (\forall int i; i>=0 && i<elements.length;
	 @        (\forall int j; j>=0 && j<elements[0].length;
	 @          elements[i][j] == elementAt(i+1,j+1)));
	 @*/
	public /*@ pure @*/ Matrix(double[][] elements) {
		_array = new double[elements[0].length][elements.length];
		int rows = elements.length;
		int columns = elements[0].length;
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				_array[j][i] = elements[i][j];
    	}
    }
  }
	
  /**
	 * Initialize a new diagonal Matrix with the given array of diagonal
	 * element.
	 *
	 * @param elements
	 *        An array containing the diagonal elements of the new Matrix.
	 */	
 /*@
	 @ public behavior
	 @
	 @ pre elements != null;
	 @ pre elements.length > 0;
	 @
	 @ post getNbRows() == elements.length;
	 @ post isSquare();
	 @ post isDiagonal();
	 @ post (\forall int i; i>=0 && i<elements.length;
	 @          elements[i] == elementAt(i+1,i+1));
	 @*/
	public /*@ pure @*/ Matrix(double[] elements) {
		_array = new double[elements.length][elements.length];
		int rows = elements.length;
		for(int i=rows; --i>=0;) {
			_array[i][i] = elements[i];
    }
  }
	
  /**
	 * Return a new unity matrix of the given size
	 *
	 * @param size
	 *        The number of rows/columns of the matrix
	 */
 /*@
   @ public behavior
	 @
	 @ pre size > 0;
	 @
	 @ post \fresh(\result);
	 @ post \result.getNbRows() == size;
	 @ post \result.getNbColumns() == size;
	 @ post (\forall int i; i>=1 && i<=size;
	 @        (\forall int j; j>=1 && j<=size;
	 @          ((i==j) ==> \result.elementAt(i,j) == 1) &&
	 @          ((i!=j) ==> \result.elementAt(i,j) == 0)));
	 @*/
	public static /*@ pure @*/ Matrix unity(int size) {
		Matrix result = new Matrix(size, size);
		for (int i=1; i<=size; i++) {
		  result.setElementAt(i,i,1);
		}
		return result;
	}

  /**
   * @see superclass
   */
  public /*@ pure @*/ int getNbDimensions() {
    return 2;
  }

  /**
   * @see superclass
   */
  public /*@ pure @*/ int[] getDimensions() {
    return new int[]{getNbRows(), getNbColumns()};
  }

  /**
   * Return the number of rows of this matrix.
   */
 /*@
   @ public behavior
   @
   @ post \result == getDimensions()[0];
   @*/
  public /*@ pure @*/ int getNbRows() {
    return _array[0].length;
  }

  /**
   * Return the number of columns of this matrix.
   */
 /*@
   @ public behavior
   @
   @ post \result == getDimensions()[1];
   @*/
  public /*@ pure @*/ int getNbColumns() {
    return _array.length;
  }

  /**
   * @see superclass
   */
  public /*@ pure @*/ double elementAt(int[] index) {
    return _array[index[1] - 1][index[0] - 1];
  }

  /**
   * Return the element at the given row and column.
   *
   * @param row
   *        The row of the element to be retrieved.
   * @param column
   *        The column of the element to be retrieved.
   */
 /*@
   @ public behavior
   @
   @ pre validIndex(row, column);
   @
   @ post \result == elementAt(new int[]{row, column});
   @*/
  public /*@ pure @*/ double elementAt(int row, int column) {
    return _array[column - 1][row - 1];
  }

  /**
   * Set the element at the given row and column to
   * the given value.
   *
   * @param row
   *        The row of the element to be retrieved.
   * @param column
   *        The column of the element to be retrieved.
   * @param value
   *        The new value for the element at the given row and column.
   */
 /*@
   @ public behavior
   @
   @ pre validIndex(row, column);
   @
   @ post elementAt(row, column) == value;
   @*/
  public void setElementAt(int row, int column, double value) {
    _array[column-1][row-1] = value;
  }
  /**
   * @see superclass
   */
  public void setElementAt(int[] index, double value) {
    _array[index[1]-1][index[0]-1] = value;
  }

  /**
   * Check whether or not the given row and column point to
   * a valid position for this Matrix.
   *
   * @param row
   *        The row of the position.
   * @param column
   *        The row of the position.
   */
 /*@
   @ public behavior
   @
   @ post \result == (row > 0) &&
   @                 (row <= getNbRows()) &&
   @                 (column > 0) &&
   @                 (column <= getNbColumns());
   @*/
  public /*@ pure @*/ boolean validIndex(int row, int column) {
    return validIndex(new int[]{row, column});
  }

  /**
   * Return the column with the given index.
   *
   * @param index
   *        The index of the requested column.
   */
 /*@
   @ public behavior
   @
   @ pre index > 0 && index <= getNbColumns();
   @
   @ post \result != null;
   @ post \result.size() == getNbRows();
   @ post (\forall int i; i>0 && i < getNbRows();
   @         \result.elementAt(i) == elementAt(i,index));
   @*/
  public /*@ pure @*/ Column getColumn(int index) {
    return new Column(_array[index-1]);
  }

	/**
	 * Set the i-th column of this matrix
	 *
	 * @param i
	 *        The index of the column to be set.
	 * @param column
	 *        The column which will be the new i-th column of this matrix
	 */
 /*@
   @ public behavior
	 @
	 @ pre i>=1 && i<=getNbColumns();
	 @ pre column != null;
	 @ pre column.size() == getNbRows();
	 @
	 @ post getColumn(i).equals(column);
	 @*/
	public void setColumn(int i, Column column) {
		for (int k=1; k<=getNbRows(); k++) {
			setElementAt(k, i, column.elementAt(k));
		}
	}

  /**
   * Return the row with the given index.
   *
   * @param index
   *        The index of the requested row.
   */
 /*@
   @ public behavior
   @
   @ pre index > 0 && index <= getNbRows();
   @
   @ post \result != null;
   @ post \result.size() == getNbColumns();
   @ post (\forall int i; i>0 && i < getNbColumns();
   @         \result.elementAt(i) == elementAt(index, i));
   @*/
  public /*@ pure @*/ Row getRow(int index) {
    double[] row = new double[getNbColumns()];
    for(int i=0; i<row.length; i++){
      row[i] = _array[i][index-1];
    }
    return new Row(row);
  }

	/**
	 * Set the i-th row of this matrix
	 *
	 * @param i
	 *        The index of the row to be set.
	 * @param column
	 *        The row which will be the new i-th row of this matrix
	 */
 /*@
   @ public behavior
	 @
	 @ pre i>=1 && i<=getNbRows();
	 @ pre row != null;
	 @ pre row.size() == getNbColumns();
	 @
	 @ post getRow(i).equals(row);
	 @*/
	public void setRow(int i, Row row) {
		for (int k=1; k<=getNbColumns(); k++) {
			setElementAt(i, k, row.elementAt(k));
		}
	}
	
	
  /**
   * Add the given matrix to this matrix.
   *
   * @param other
   *        The matrix to be added.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre sameDimensions(other);
   @
   @ post (\forall int i; i>=1 && i <= getNbRows();
   @        (\forall int j; j>=1 && j <= getNbColumns();
   @          elementAt(i,j) ==
   @            \old(elementAt(i,j)) + \old(other.elementAt(i,j))));
   @*/
  public void add(Matrix other) {
    int rows = getNbRows();
    int columns = getNbColumns();
    for (int i= 0; i<rows; i++) {
      for(int j = 0; j<columns; j++) {
        _array[j][i] = _array[j][i] + other.elementAt(i+1,j+1);
      }
    }    
  }
  
  /**
   * Return a new matrix that is the sum of this matrix
   * and the given matrix
   *
   * @param other
   *        The matrix to be added.
   */  
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre sameDimensions(other);
   @
   @ post \result != null;
   @ post \result.sameDimensions(this);
   @ post (\forall int i; i>=1 && i <= getNbRows();
   @        (\forall int j; j>=1 && j <= getNbColumns();
   @          \result.elementAt(i,j) ==
   @            elementAt(i,j) + other.elementAt(i,j)));
   @*/
  public /*@ pure @*/ Matrix plus(Matrix other) {
    Matrix newMatrix = (Matrix) clone();
    newMatrix.add(other);
    return newMatrix;
  }

  /**
   * Subtract the given matrix from this matrix.
   *
   * @param other
   *        The matrix to be added.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre sameDimensions(other);
   @
   @ post (\forall int i; i>=1 && i <= getNbRows();
   @        (\forall int j; j>=1 && j <= getNbColumns();
   @          elementAt(i,j) ==
   @            \old(elementAt(i,j)) - \old(other.elementAt(i,j))));
   @*/
  public void subtract(Matrix other) {
		int rows = getNbRows();
    int columns = getNbColumns();
    for (int i= 0; i<rows; i++) {
      for(int j = 0; j<columns; j++) {
        _array[j][i] = _array[j][i] - other.elementAt(i+1,j+1);
      }
    }    
  }
  
  /**
   * Return a new matrix that equals this matrix minus
   * the given matrix.
   *
   * @param other
   *        The matrix to be added.
   */  
 /*@
   @ public behavior 
   @
   @ pre other != null;
   @ pre sameDimensions(other);
   @
   @ post \result != null;
   @ post \result.sameDimensions(this);
   @ post (\forall int i; i>=1 && i <= getNbRows();
   @        (\forall int j; j>=1 && j <= getNbColumns();
   @          \result.elementAt(i,j) ==
   @            elementAt(i,j) - other.elementAt(i,j)));
   @*/
  public /*@ pure @*/ Matrix minus(Matrix other) {
    Matrix newMatrix = (Matrix) clone();
    newMatrix.subtract(other);
    return newMatrix;
  }
  
  /**
   * Right-multiply this matrix by another.
   * \result = this * other
   *
   * @param other
   *        The matrix to multiply this matrix by.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre other.getNbRows() == getNbColumns();
   @ pre other.getNbColumns() == getNbRows();
   @
   @ post \result != null;
   @ post (\forall int i; i>=1 && i<= getNbRows();
   @        (\forall int j; j>=1 && j<= other.getNbColumns();
   @           \result.elementAt(i,j) == 
   @           (\sum int k; k>=1 && k<= getNbColumns(); 
   @            elementAt(i,k) * other.elementAt(k,j))));
   @ post \result.getNbRows() == getNbRows();
   @ post \result.getNbColumns() == other.getNbColumns();
   @ post \fresh(\result);
   @
   @ // MvDMvDMvD We should put this everywhere we return a matrix
   @ // since matrices are mutable. We don't want to change an existing one.
   @*/
  public /*@ pure @*/ Matrix times(Matrix other) {
    Matrix newMatrix = new Matrix(getNbRows(),other.getNbColumns());
		int rows = getNbRows();
		int columns = getNbColumns();
		int otherColumns = other.getNbColumns();
    for (int i=1; i <=rows; i++) {
      for (int j=1; j <=otherColumns; j++) {
        double element=0;
				//double[] terms = new double[columns];
        for(int k=1; k <= columns; k++) {
					//terms[k-1] = elementAt(i,k) * other.elementAt(k,j);
          element+=elementAt(i,k) * other.elementAt(k,j);
        }
				// sorts in ascending order
				//java.util.Arrays.sort(terms);
				//for(int k=1; k <= columns; k++) {
				//	element += terms[k-1];
        //}
        newMatrix.setElementAt(i,j,element);
      }
    }
    return newMatrix;
  }
  
  /**
   * Return a matrix that equals this matrix times a given factor.
   *
   * @param factor
   *        The factor to multiply this matrix with.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.sameDimensions(this);
   @ post (\forall int i; i>=1 && i<= getNbRows();
   @        (\forall int j; j>=1 && j<= getNbColumns();
   @          \result.elementAt(i,j) == elementAt(i,j) * factor));
   @*/
  public /*@ pure @*/ Matrix times(double factor) {
    Matrix newMatrix = new Matrix(getNbRows(),getNbColumns());
    for (int i=1; i <=getNbRows(); i++) {
      for (int j=1; j <=getNbColumns(); j++) {
        newMatrix.setElementAt(i,j,elementAt(i,j)*factor);
      }
    }
    return newMatrix;
  }  
	
  /**
   * Multiply this matrix by a given factor.
   *
   * @param factor
   *        The factor to multiply this matrix with.
   */
 /*@
   @ public behavior
   @
   @ post (\forall int i; i>=1 && i<= getNbRows();
   @        (\forall int j; j>=1 && j<= getNbColumns();
   @          elementAt(i,j) == \old(elementAt(i,j)) * factor));
   @*/
  public void multiply(double factor) {
    for (int i=1; i <=getNbRows(); i++) {
      for (int j=1; j <=getNbColumns(); j++) {
        _array[j-1][i-1] = _array[j-1][i-1]*factor;
      }
    }
  }  

  /**
   * Divide this matrix by a given factor.
   *
   * @param factor
   *        The factor to divide this matrix by.
   */
 /*@
   @ public behavior
   @
   @ post (\forall int i; i>=1 && i<= getNbRows();
   @        (\forall int j; j>=1 && j<= getNbColumns();
   @          elementAt(i,j) == \old(elementAt(i,j)) / factor));
   @*/
  public void divide(double factor) {
    for (int i=1; i <=getNbRows(); i++) {
      for (int j=1; j <=getNbColumns(); j++) {
        _array[j-1][i-1] = _array[j-1][i-1]/factor;
      }
    }
  }  
	
	  
  /**
   * Transpose this matrix.
   */
 /*@
   @ public behavior
   @
   @ post getNbColumns() == \old(getNbRows());
   @ post getNbRows() == \old(getNbColumns());
   @ post (\forall int i; i>=1 && i<=getNbRows();
   @        (\forall int j; j>=1 && j<getNbColumns();
   @          elementAt(i,j) == \old(elementAt(j,i))));
   @*/
  public void transpose() {
    // inefficient : see returnTranspose()
    double[][] newArray = new double[getNbRows()][getNbColumns()];
    int rows=getNbRows();
    int columns=getNbColumns();
    for (int i= 0; i<rows; i++) {
      for(int j = 0; j<columns; j++) {
        newArray[i][j] = _array[j][i];
      }
    }
    _array=newArray;
  }

	/**
	 * <p>Perform a givens rotation on this matrix from the left side with the givens matrix defined by
	 * c, s, i and k as follows (m = getNbRows()).</p>
	 * <pre>
	 *     1       i       k       m
	 * 
	 * 1   1 . . . 0 . . . 0 . . . 0
	 *     . .     . .     . .     .
	 *     .   .   .   .   .   .   .
	 *     .     . .     . .     . .
	 * i   0 . . . c . . . s . . . 0
	 *     . .     . .     . .     .
	 *     .   .   .   .   .   .   .
	 *     .     . .     . .     . .
	 * k   0 . . .-s . . . c . . . 0
	 *     . .     . .     . .     .
	 *     .   .   .   .   .   .   .
	 *     .     . .     . .     . .
	 * m   0 . . . 0 . . . 0 . . . 1
	 * </pre>
	 * <p> This matrix (<code>A</code>) will be transformed into <code>G*A</code>.</p>
	 * @param c
	 *        The value of c
	 * @param c
	 *        The value of s
	 * @param i
	 *        The value of i
	 * @param k
	 *        The value of k
	 */
 /*@
	 @ public behavior
	 @
	 @ pre (1 <= i) && (i < k) && (k <= getNbRows());
	 @
	 @ post getRow(i).equals(\old(getRow(i).times(c).minus(getRow(k).times(s))));
	 @ post getRow(k).equals(\old(getRow(i).times(s).plus(getRow(k).times(c))));
	 @*/
	public void leftGivens(double c, double s, int i, int k) {
		//rows 1...i-1, i+1 ... k-1, k+1 ... n remain untouched.
		Row row_i = getRow(i);
		Row row_k = getRow(k);
		setRow(i,row_i.times(c).minus(row_k.times(s)).getRow(1));
		setRow(k,row_i.times(s).plus(row_k.times(c)).getRow(1));
  }
	
	/**
	 * <p>Perform a givens rotation on this matrix from the right side with the givens matrix defined by
	 * c, s, i and k as follows (n = getNbColumns()).</p>
	 * <pre>
	 *     1       i       k       n
	 * 
	 * 1   1 . . . 0 . . . 0 . . . 0
	 *     . .     . .     . .     .
	 *     .   .   .   .   .   .   .
	 *     .     . .     . .     . .
	 * i   0 . . . c . . . s . . . 0
	 *     . .     . .     . .     .
	 *     .   .   .   .   .   .   .
	 *     .     . .     . .     . .
	 * k   0 . . .-s . . . c . . . 0
	 *     . .     . .     . .     .
	 *     .   .   .   .   .   .   .
	 *     .     . .     . .     . .
	 * n   0 . . . 0 . . . 0 . . . 1
	 * </pre>
	 * <p> This matrix (<code>A</code>) will be transformed into <code>A*G</code>.</p>
	 * @param c
	 *        The value of c
	 * @param c
	 *        The value of s
	 * @param i
	 *        The value of i
	 * @param k
	 *        The value of k
	 */
 /*@
	 @ public behavior
	 @
	 @ pre (1 <= i) && (i < k) && (k <= getNbColumns());
	 @
	 @ post getColumn(i).equals(\old(getColumn(i).times(c).minus(getColumn(k).times(s))));
	 @ post getColumn(k).equals(\old(getColumn(i).times(s).plus(getColumn(k).times(c))));
	 @*/
	public void rightGivens(double c, double s, int i, int k) {
		//columns 1...i-1, i+1 ... k-1, k+1 ... n remain untouched.
		Column column_i = getColumn(i);
		Column column_k = getColumn(k);
		setColumn(i,column_i.times(c).minus(column_k.times(s)).getColumn(1));
		setColumn(k,column_i.times(s).plus(column_k.times(c)).getColumn(1));
  }
	  
  /**
   * Return the transpose of this matrix.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \fresh(\result);
   @ post \result.getNbRows() == getNbColumns();
   @ post \result.getNbColumns() == getNbRows();
   @ post (\forall int i; i>0 && i<= getNbRows();
   @        (\forall int j; j>0 && j <getNbColumns();
   @          elementAt(i,j) == \result.elementAt(j,i)));
   @*/
  public /*@ pure @*/ Matrix returnTranspose() {
    // MvDMvDMvD : very inefficient. It would be better to have a
    //             boolean flag indicating whether the internal
    //             indices are [column][row] or [row][column].
    //             This however makes other operations slightly less
    //             efficient since a test has to be done.
    Matrix newMatrix = new Matrix(getNbColumns(), getNbRows());
    for (int i=1; i<=getNbRows(); i++) {
      for(int j=1; j<=getNbColumns(); j++) {
        newMatrix.setElementAt(j,i,elementAt(i,j));
      }
    }
    return newMatrix;
  }

  /**
   * Check wether the given matrix has the same dimensions as this one.
   *
   * @param other
   *        The other matrix
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \result == (getNbColumns() == other.getNbColumns()) &&
   @                 (getNbRows() == other.getNbRows());
   @*/
  public /*@ pure @*/ boolean sameDimensions(Matrix other) {
    return ((getNbColumns() == other.getNbColumns()) && 
            (getNbRows() == other.getNbRows()));
  }

//   /**
// 	 * Return a reduced QR factorization of this matrix using 
// 	 * Gram-Schmidt orthogonalization.
// 	 */
//  /*@
//    @ public behavior
// 	 @
// 	 @ pre getNbRows() >= getNbColumns()
// 	 @*/
// 	public void GramQR() {
// 		Matrix Q = (Matrix) clone();
// 		int columns = getNbColumns();
// 		int rows = getNbRows();
// 		Matrix R = new Matrix(columns, columns);
// 		for (int i=1; i<= columns; i++) {
// 			double norm = Q.getColumn(i).norm(2);
// 			// rii = ||vi||
// 			R.setElementAt(i,i, norm);
// 			// qi = vi/rii
// 			for (int k=1; k<=rows; k++) {
// 				Q.setElementAt(k,i,Q.elementAt(k,i)/norm);
// 			}
// 			for (int j=i+1; j<=columns; j++) {
// 				// qi = vi
// 				// MvDMvDMvD
// 				// very inefficient. getColumn() clones and returnTranspose() clones again.
// 				double r = (Q.getColumn(i).returnTranspose().times(Q.getColumn(j))).elementAt(1,1);
// 				R.setElementAt(i,j,r);
// 				// vj = vj - rij*qi
// 				for (int k=1; k<= rows; k++) {
// 					Q.setElementAt(k,j,(Q.elementAt(k,j) - r*Q.elementAt(k,i)));
// 				}
// 			}
// 		}
// 		System.out.println(Q);
// 		System.out.println(R);
// 	}

	/**
	 * Return the submatrix starting from (x1,y1) to (x2,y2).
	 *
	 * @param x1
	 *        The row from which to start.
	 * @param y1
	 *        The column from which to start.
	 * @param x2
	 *        The end row.
	 * @param y2
	 *        The end column.
	 */
 /*@
   @ public behavior
	 @
	 @ pre validIndex(x1, y1);
	 @ pre validIndex(x2, y2);
	 @ pre x2 >= x1;
	 @ pre y2 >= y1;
	 @
	 @ post \fresh(\result);
	 @ post \result.getNbRows() == x2 - x1 + 1;
	 @ post \result.getNbColumns() == y2 - y1 + 1;
	 @ post (\forall int i; i>=x1 && i <=x2;
	 @        (\forall int j; j>=y1 && j <=y2;
	 @          \result.elementAt(i - x1 + 1, j - y1 + 1) == elementAt(i, j)));
	 @*/
	public /*@ pure @*/ Matrix subMatrix(int x1, int y1, int x2, int y2) {
		Matrix result = new Matrix(x2 - x1 + 1, y2 - y1 + 1);
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				result.setElementAt(i - x1 + 1, j - y1 + 1, elementAt(i, j));
			}
		}
		return result;
	}

  /**
	 * Replace a submatrix of this matrix with the given matrix 
	 * at the given coordinates.
	 *
	 * @param row
	 *        The row of the top-left element of the submatrix that will be replaced.
	 * @param column
	 *        The column of the top-left element of the submatrix that will be replaced.
	 * @param matrix
	 *        The matrix to put into this matrix
	 */
 /*@
   @ public behavior
	 @
	 @ pre matrix != null;
	 @ pre validIndex(row, column);
	 @ pre validIndex(row + matrix.getNbRows() - 1, column + matrix.getNbColumns() - 1);
	 @
	 @ post subMatrix(row, column, row + matrix.getNbRows() - 1, column + matrix.getNbColumns() - 1).equals(matrix);
	 @*/
	public void setSubMatrix(int row, int column, Matrix matrix) {
		int rows = matrix.getNbRows();
		int columns = matrix.getNbColumns();
		for (int i = 1; i <= rows; i++) {
			for (int j = 1; j <= columns; j++) {
				setElementAt(i + row - 1, j + column - 1, matrix.elementAt(i, j));
			}
		}
	}


	/**
	 * Check whether or not this matrix is upper triangular
	 */
 /*@
   @ public behavior
	 @
	 @ post \result == (\forall int i; i>=1 && i<=getNbRows();
	 @	                 (\forall int j; j>=1 && j<=getNbColumns();
	 @                     (i < j) ==> elementAt(i,j) == 0));
	 @*/
	public /*@ pure @*/ boolean isUpperTriangular() {
		int rows=getNbRows();
		int columns=getNbColumns();
		for (int i=1; i<= rows; i++) {
			for (int j=1; j<= columns && j < i; j++) {
				if (elementAt(i,j) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check whether or not this matrix is lower triangular
	 */
 /*@
   @ public behavior
	 @
	 @ post \result == (\forall int i; i>=1 && i<=getNbRows();
	 @	                 (\forall int j; j>=1 && j<=getNbColumns();
	 @                     (i > j) ==> elementAt(i,j) == 0));
	 @*/
	public /*@ pure @*/ boolean isLowerTriangular() {
		int rows=getNbRows();
		int columns=getNbColumns();
		for (int i=1; i<= rows; i++) {
			for (int j=i+1; j<= columns; j++) {
				if (elementAt(i,j) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check whether or not this matrix is a diagonal matrix.
	 */
 /*@
   @ public behavior
	 @
	 @ post \result == (\forall int i; i>=1 && i<=getNbRows();
	 @	                 (\forall int j; j>=1 && j<=getNbColumns();
	 @                     (j != i) ==> elementAt(i,j) == 0));
	 @*/
	public /*@ pure @*/ boolean isDiagonal() {
		int rows=getNbRows();
		int columns=getNbColumns();
		for (int i=1; i<= rows; i++) {
			for (int j=1; j<= columns; j++) {
				if ((i != j) && (elementAt(i,j) != 0)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * see superclass
	 */
	public /*@ pure @*/ String toString() {
	  StringBuffer result = new StringBuffer();
		int columns=getNbColumns();
		int rows=getNbRows();
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns - 1; j++) {
			  result.append(elementAt(i,j));
        result.append(", ");
			}
			result.append(elementAt(i,columns));
      result.append("\n");
		}
		return result.toString();
	}

  /**
   * Return a clone
   */
 /*@
   @ also public behavior
   @
   @ post equals(\result);
	 @ post \result instanceof Matrix;
   @*/
  public /*@ pure @*/ Object clone() {
    // MvDMvDMvD
    // should be made more efficient using a private constructor
    // which takes double[][] as an argument.
    Matrix newMatrix = new Matrix(getNbRows(), getNbColumns());
    for (int i=1; i<=getNbRows(); i++) {
      for(int j=1; j<=getNbColumns(); j++) {
        newMatrix.setElementAt(i,j,elementAt(i,j));
      }
    }
    return newMatrix;
  }  
  
  /**
   * Check whether the given object is equal to this matrix.
   */
 /*@
   @ also public behavior
   @
   @ post !(other instanceof Matrix) ==> (\result == false);
   @ post !(sameDimensions((Matrix)other)) ==> (\result == false);
   @ post (other instanceof Matrix) && (sameDimensions((Matrix)other)) ==>
   @       (\result == (\forall int i; i>=1 && i<=getNbRows();
   @                     (\forall int j; j>=1 && j<=getNbColumns();
   @                       ((Matrix)other).elementAt(i,j) ==
   @                       elementAt(i,j))));
   @*/
  public /*@ pure @*/ boolean equals(Object other) {
    if (!(other instanceof Matrix)) {
      return false;
    }
    if (!(sameDimensions((Matrix)other))) {
      return false;
    }
    for (int i=1; i<=getNbRows(); i++) {
      for(int j=1; j<=getNbColumns(); j++) {
        if(_array[j-1][i-1] != ((Matrix)other).elementAt(i,j)) {
          return false;
        }
      }
    }
    return true;
  }

	/**
	 * Check whether or not this matrix is square.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == (getNbColumns() == getNbRows());
	 @*/
	public /*@ pure @*/ boolean isSquare() {
		return getNbColumns() == getNbRows();
  }
	
	/**
	 * Check whether or not this matrix is symmetric.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == (isSquare() &&
	 @                  (\forall int i; i>=1 && i<=getNbRows();
	 @                    (\forall int j; j>=1 && j<=getNbColumns();
	 @                      elementAt(i,j) == elementAt(j,i))));
	 @*/
	public /*@ pure @*/ boolean isSymmetric() {
		if(! isSquare()) {
			return false;
    }
		boolean result = true;
		int rows = getNbRows();
		int columns = getNbColumns();
		// the last row need not be checked, so i<rows
		for(int i=1; result && i<rows; i++) {
			for(int j=i+1; result && j<=columns; j++) {
				result = (elementAt(i,j) == elementAt(j,i));
      }
    }
		return result;
  }

	/**
	 * Check whether or not this matrix is a permutation matrix.
	 */
 /*@
	 @ post \result == (
   @                  isSquare() &&
	 @                  // 1 non-zero element in each row
	 @                  (\forall int i; i>=1 && i<=getNbRows();
	 @                    (\num_of int j; j>=1 && j<=getNbColumns(); elementAt(i,j) != 0) == 1) &&
	 @                  // 1 non-zero element in each column
	 @                  (\forall int i; i>=1 && i<=getNbColumns();
	 @                    (\num_of int j; j>=1 && j<=getNbRows(); elementAt(j,i) != 0) == 1) &&
	 @                  // each element is 0 or 1
	 @                  (\forall int i; i>=1 && i<=getNbRows();
	 @                    (\forall int j; j>=1 && j<=getNbColumns();
	 @                      elementAt(i,j) == 0 || elementAt(i,j) == 1))
   @                 );
	 @*/
	public /*@ pure @*/ boolean isPermutationMatrix() {
		if(! isSquare()) {
			return false;
    }
		int n = getNbRows();
		boolean[] column_found = new boolean[n];
		for(int i=1; i<=n; i++) {
			boolean found = false;
			for(int j=1; j <= n; j++) {
				if((elementAt(i,j) != 0) && (elementAt(i,j) != 1)) {
					return false;
        }
				if(elementAt(i,j) == 1) {
					if(found == true) {
						// we had already found a 1 on this row.
						return false;
          }
					if(column_found[j-1]) {
						// we had already found in column j
						return false;
          }
					column_found[j-1] = true;
					found=true;
        }
    	}
    }
		return true;
  }
		  
  /**
   * The values are put in the array according to the following scheme
   * [column][row]
   * This makes it easier to extract a column from the matrix, which is used
   * more frequently than a column.
   */
 /*@
   @ private invariant _array != null;
   @ private invariant _array.length > 0;
   @ // All columns have the same length.
   @ private invariant (\forall int i; i>0 && i<_array.length;
   @                     _array[i].length == _array[i-1].length);
   @ private invariant _array[0].length > 0;
   @*/
  private double[][] _array;
}
/*<copyright>Copyright (C) 1997-2002. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://www.jutil.org/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://jutil.org/</copyright>*/
