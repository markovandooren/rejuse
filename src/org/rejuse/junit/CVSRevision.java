package org.rejuse.junit;

/**
 * A class of CVS revisions. A revision is created using the string
 * that CVS uses to save version information in a file.
 *
 * <center>
 *   <img src="doc-files/CVSRevision.png"/>
 * </center>
 *
  * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class CVSRevision extends AbstractRevision {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new CVSRevision with the given String.
	 *
	 * @param revision
	 *        A String representing the CVS revision. This is the string
	 *        the CVS itself uses. The format is "$Revision$", where x
	 *        is the version number, or just "1.3.234.1.5" or so . 
	 *        The format is more formally described in the preconditions.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre revision != null;
	 @ // The revision must either be a revision number, or a CVS revision string.
	 @ // In other words : "x.x.x.x" or "$Revision$".
	 @ pre new Pattern("\\d*(\\.(\\d)*)*").matcher(revision).matches() ||
	 @     new Pattern("$Revision$").matcher(revision).matches();
	 @
	 @ post (* number i == the i'th x *);
	 @*/
	public CVSRevision(String revision) {
		int index = 0;
		int nbDots = 0;
		while(index >= 0)	{
			index = revision.indexOf(".",index+1);
			if(index >= 0) {
				nbDots++;
			}
		}
		// number of .'s == number of x's - 1
		_numbers = new int[nbDots+1];
		index = revision.indexOf(" ") + 1;
		int prev = index;
		for(int i=1; i <= nbDots; i++)	{
			index = revision.indexOf(".",index+1);
			_numbers[i-1] = new Integer(revision.substring(prev,index)).intValue();
			prev=index+1;
		}
    index = revision.indexOf(" ", index);
		if(index < 0) {
			// In this case an ordinary number is supplied.
			index = revision.length();
		}
		_numbers[nbDots] = new Integer(revision.substring(prev,index)).intValue();
	}

	/**
	 * See superclass.
	 */
	public /*@ pure @*/ int getNumber(int baseOneIndex) {
		if(baseOneIndex > length()) {
			return 0;
		}
		return _numbers[baseOneIndex-1];
	}

	/**
	 * See superclass.
	 */
	public /*@ pure @*/ int length() {
		return _numbers.length;
	}

 /*@
	 @ private invariant _numbers != null;
	 @ private invariant _numbers.length >= 1;
   @ private invariant (\forall int i; i>=0 && i<_numbers.length;
	 @                     _numbers[i] >= 0);
	 @*/
	private int[] _numbers;
}

