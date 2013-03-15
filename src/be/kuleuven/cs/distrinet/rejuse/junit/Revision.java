package be.kuleuven.cs.distrinet.rejuse.junit;

/**
 * <p>A class of Revisions of something.</p>
 *
 * <p>An example of a revision is software revison. A revision consists of a sequence of numbers.
 * The first three numbers are called "major", "minor" and "micro". Further numbers have no name.</p>
 *
 * <center>
 *   <img src="doc-files/Revision.png"/>
 * </center>
 *
  * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface Revision extends Comparable<Revision> {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Return the major number of this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == getNumber(1);
	 @*/
	public /*@ pure @*/ int getMajor();

	/**
	 * Return the minor number of this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == getNumber(2);
	 @*/
	public /*@ pure @*/ int getMinor();

	/**
	 * Return the micro number of this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == getNumber(3);
	 @*/
	public /*@ pure @*/ int getMicro();

	/**
	 * Return the index'th number of this Revision.
	 *
	 * @param index
	 * 				The index of the requested number.
	 */
 /*@
	 @ pre index >= 1;
	 @
	 @ post \result >=0;
	 @ post (index > length()) ==> (\result == 0);
	 @*/
	public /*@ pure @*/ int getNumber(int index);

	/**
	 * Return the number of numbers in this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result >= 1;
	 @*/
	public /*@ pure @*/ int length();

	/**
	 * Check whether or not this revision is equal to another
	 *
	 * @param other
	 *        The other revision.
	 */
 /*@
	 @ also public behavior
	 @
	 @ post (! (other instanceof Revision)) ==> (\result == false);
	 @ post (other instanceof Revision) ==> \result == 
	 @                                      (\forall int i; i>=1;  
	 @                                        getNumber(i) == ((Revision)other).getNumber(i));
	 @*/
	public /*@ pure @*/ boolean equals(Object other);
	
	/**
	 * Compare this revision to the other revision.
	 */
 /*@
   @ public behavior
   @
   @ post o == null ==> \result > 0;
   @ post (\exists i; i > 0 & length() >= i; 
   @        (\forall j; j > 0 & j < i; getNumber(j) == o.getNumber(j)) && 
   @        ((o.length() < i) || (getNumber(i) > o.getNumber(i)))
   @      ) 
   @ ==> \result > 0;
   @ (\exists i; i > 0 & o.length() >= i; 
   @    (\forall j; j > 0 & j < i; getNumber(j) == o.getNumber(j)) && 
   @    ((length() < i ) || (getNumber(i) < o.getNumber(i)))
   @ ) 
   @ ==> \result < 0;
   @*/
	public /*@ pure @*/ int compareTo(Revision o);
}

