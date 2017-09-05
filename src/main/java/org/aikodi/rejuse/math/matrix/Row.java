package org.aikodi.rejuse.math.matrix;

/**
 * A class of matrices containing only 1 row.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Row extends Matrix {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
   @ public invariant getNbRows() == 1;
   @*/

  /**
   * Create a new Row with the given size.
   *
   * @param size
   *        The size of the new row.
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
  public /*@ pure @*/ Row(int size) {
    super(1,size);
  }

  /**
   * Create a new Row with the given elements.
   *
   * @param elements
   *        An array containing the elements for this row.
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
  public /*@ pure @*/ Row(double[] elements) {
    super(1,elements.length);
    for(int i=1; i <= elements.length; i++) {
      setElementAt(1,i,elements[i-1]);
    }
  }

  /**
   * Return the element at the given index.
   *
   * @param index
   *        The index of the element to be retrieved.
   */
 /*@
   @ public behavior
   @
   @ pre validIndex(index);
   @
   @ post \result == elementAt(1, index);
   @*/
  public /*@ pure @*/ double elementAt(int index) {
    return elementAt(1, index);
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
		setElementAt(1,index,value);
	}

	/**
   * Return the size of this Row.
   */
 /*@
   @ public behavior
   @
   @ post \result == getNbColumns();
   @*/
  public /*@ pure @*/ int size() {
    return getNbColumns();
  }

	/**
	 * Return a sub-row of this row starting from <lower> and ending
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
	public /*@ pure @*/ Row subRow(int lower, int upper) {
		int size = upper - lower + 1;
		Row result = new Row(size);
		for(int i=lower; i<=upper; i++) {
			result.setElementAt(i - lower + 1, elementAt(i));
		}
		return result;
	} 
	
	/**
	 * Replace a sub-row of this Row, starting at the given position
	 * with the given row.
	 *
	 * @param lower
	 *        The index at which the row must be pasted.
	 * @param row
	 *        The row to paste into this row.
	 */
 /*@
   @ public behavior
	 @
	 @ pre validIndex(lower);
	 @ pre validIndex(lower + row.size() - 1);
	 @ pre row != null;
	 @
	 @ post subRow(lower, lower + row.size() - 1).equals(row);
	 @*/
	public void setSubRow(int lower, Row row) {
		int size = row.size();
		for (int i=1; i<= size; i++) {
			setElementAt(i + lower - 1, row.elementAt(i));
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

