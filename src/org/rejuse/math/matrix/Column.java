package org.rejuse.math.matrix;

/**
 * A class of matrices containing only 1 column.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Column extends Matrix {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
   @ public invariant getNbColumns() == 1;
   @*/

  /**
   * Create a new Column with the given size.
   *
   * @param size
   *        The size of the new column.
   */
 /*@
   @ public behavior
   @
   @ pre size > 0;
   @
   @ post size() == size;
   @ post (\forall int i; i > 0 && i <= size();
   @        elementAt(i) == 0);
   @*/
  public /*@ pure @*/ Column(int size) {
    super(size,1);
  }

  /**
   * Create a new Column with the given elements.
   *
   * @param elements
   *        An array containing the elements for this Column
   */
 /*@
   @ public behavior
   @
   @ pre elements != null;
   @ pre elements.length > 0;
   @
   @ post size() == elements.length;
   @ post (\forall int i; i>0 && i <= size();
   @        elementAt(i) == elements[i-1]);
   @*/
  public /*@ pure @*/ Column(double[] elements) {
    super(elements.length, 1);
    for(int i=1; i <= elements.length; i++) {
      setElementAt(i,1,elements[i-1]);
    }
  }

  /**
   * Return the element at the given index
   *
   * @param index
   *        The index of the element to be retrieved.
   */
 /*@
   @ public behavior
   @
   @ pre validIndex(index);
   @
   @ post \result == elementAt(index, 1);
   @*/
  public /*@ pure @*/ double elementAt(int index) {
    return elementAt(index, 1);
  }

  /**
   * Set the element at the given index
   *
   * @param index
   *        The index of the element to be set
   * @param value
   *        The new value for the index
   */
 /*@
   @ public behavior
   @
   @ pre validIndex(index);
   @
   @ post elementAt(index) == value;
   @*/
	public void setElementAt(int index, double value) {
		setElementAt(index,1,value);
	}
	
  /**
   * Return the size of this Column.
   */
 /*@
   @ public behavior
   @
   @ post \result == getNbRows();
   @*/
  public /*@ pure @*/ int size() {
    return getNbRows();
  }

	/**
	 * Return a sub-column of this column starting from <lower> and ending
	 * at <upper>
	 *
	 * @param lower
	 *        The lower index
	 * @param upper
	 *        The upper index
   */
 /*@
   @ public behavior
	 @
	 @ pre validIndex(lower);
	 @ pre validIndex(upper);
	 @ pre lower <= upper;
	 @
	 @ post \result != null;
	 @ post \result.size() == upper - lower + 1;
	 @ post (\forall int i; i >= lower && i <= upper;
	 @       \result.elementAt(i - lower + 1) == elementAt(i));
	 @*/
	public /*@ pure @*/ Column subColumn(int lower, int upper) {
		int size = upper - lower + 1;
		Column result = new Column(size);
		for(int i=lower; i<=upper; i++) {
			result.setElementAt(i - lower + 1, elementAt(i));
		}
		return result;
	} 
	
	/**
	 * Replace a subcolumn of this Column, starting at the given position
	 * with the given column.
	 *
	 * @param lower
	 *        The index at which the column must be pasted.
	 * @param column
	 *        The column to paste into this column.
	 */
 /*@
   @ public behavior
	 @
	 @ pre validIndex(lower);
	 @ pre validIndex(lower + column.size() - 1);
	 @ pre column != null;
	 @
	 @ post subColumn(lower, lower + column.size() - 1).equals(column);
	 @*/
	public void setSubColumn(int lower, Column column) {
		int size = column.size();
		for (int i=1; i<= size; i++) {
			setElementAt(i + lower - 1, column.elementAt(i));
		}
	}
	
  /**
   * Return the p-norm of this vector.
   *
   * @param p
   *        The p in p-norm.
   */
 /*@
   @ public behavior
   @
   @ pre p > 0;
   @
   @ post \result == Math.pow((\sum int i; i>=1 && i <= size();
   @                            Math.pow(elementAt(i),p)),
   @                           1/p);
   @*/
  public /*@ pure @*/ double norm(int p) {
    double sum=0;
    for (int i=1; i <= size(); i++) {
      sum+=Math.pow(elementAt(i),(double)p);
    }
    return Math.pow(sum, ((double)((double)1)/((double)p)));
  }

	/**
	 * See superclass
	 */
 /*@
   @ also public behavior
	 @
	 @ post \result instanceof Column;
	 @*/
	public /*@ pure @*/ Object clone() {
		return getColumn(1);
	}

	/**
	 * Normalize this vector
	 */
 /*@
   @ public behavior
	 @
	 @ pre norm(2) > 0;
	 @
	 @ post (\forall int i; i>=1 && i <= size();
	 @         elementAt(i) == \old(elementAt(i)/norm(2)));
	 @*/
	public void normalize() {
		double norm = norm(2);
		int size = size();
		for (int i=1; i<= size; i++) {
			setElementAt(i,elementAt(i)/norm);
		}
	}
  
  /**
   * Check whether or not the given index is a valid
   * index for this Column.
   *
   * @param index
   *        The index to be verified
   */
 /*@
   @ public behavior
   @
   @ post \result == (index > 0) && (index <= size());
   @*/
  public /*@ pure @*/ boolean validIndex(int index) {
    return ((index > 0) && (index <= size()));
  }
}

