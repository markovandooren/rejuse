package org.rejuse.math.matrix;
/**
 * This is a class of N-dimensional matrices
 *
 * The name Matrix has been reserved for 2D matrices since they are
 * use more frequently than a general N-dimensional matrix.
 *
 * Since java has no generic classes, a matrix contains double values.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class NMatrix {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
   @ public invariant getDimensions().length == getNbDimensions();
   @ public invariant (\forall int i; i>=0 && i<getNbDimensions();
   @                     getDimensions()[i] > 0);
   @*/

  /**
   * Return the number of dimensions of this matrix.
   */
 /*@
   @ public behavior
   @
   @ post \result > 0;
   @*/
  public abstract /*@ pure @*/ int getNbDimensions();

  /**
   * Return the dimensions of this matrix
   */
 /*@
   @ public behavior
   @
   @ post \result.length == getNbDimensions();
   @*/
  public abstract /*@ pure @*/ int[] getDimensions();

  /**
   * Return the element at the given index.
   *
   * @param index
   *        The index of the requested element.
   */
 /*@
   @ public behavior
   @
   @ pre validIndex(index);
   @*/
  public abstract /*@ pure @*/ double elementAt(int[] index);


  /**
   * Set the element at the given index to the given value.
   *
   * @param index
   *        The index of the element to be changed.
   * @param value
   *        The new value for the element at index <index>.
   */
 /*@
   @ public behavior
   @
   @ pre validIndex(index);
   @
   @ post elementAt(index) == value;
   @*/
  public abstract void setElementAt(int[] index, double value);

  /**
   * Check whether or not the given index is a valid index for this matrix.
   *
   * @param index
   *        The index to be verified.
   */
 /*@
   @ public behavior
   @
   @ post \result == ((index != null) &&
   @                  (index.length == getNbDimensions()) &&
   @                  (\forall int i; i>=0 && i<getNbDimensions();
   @                    (index[i] > 0) && (index[i] <= getDimensions()[i])));
   @*/
  public /*@ pure @*/ boolean validIndex(int[] index) {
    if ((index == null) || (index.length != getNbDimensions())){
      return false;
    }
    int[] dims = getDimensions();
    for(int i=0; i<index.length; i++) {
      if((index[i] <= 0) || index[i] > dims[i]) {
        return false;
      }
    }
    return true;
  }
}
/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
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
