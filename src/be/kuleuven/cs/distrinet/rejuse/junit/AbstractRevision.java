package be.kuleuven.cs.distrinet.rejuse.junit;

/**
 * A class that implements the non-basic methods of Revision. Subclasses only need
 * implements <a href="Revision.html#getNumber(int)">getNumber(int)</a> and 
 * <a href="Revision.html#length()">length()</a>.
 *
 * <center>
 *   <img src="doc-files/AbstractRevision.png"/>
 * </center>
 *
  * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class AbstractRevision implements Revision {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * See superclass
	 */
	public /*@ pure @*/ int getMajor() {
		return getNumber(1);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ int getMinor() {
		return getNumber(2);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ int getMicro() {
		return getNumber(3);
	}

	/**
	 * See superclass.
	 */
	public /*@ pure @*/ boolean equals(Object other) {
		if (!(other instanceof Revision)) {
			return false;
		}
		Revision otherRevision=(Revision)other;
		int nb=Math.max(length(), otherRevision.length());
		for(int i=1; i<=nb; i++) {
			if(getNumber(i) != otherRevision.getNumber(i)) {
				return false;
			}
		}
		return true;
	}
	
	public int hashCode() {
		int result = 0;
		int length = length();
		for(int i=0; i< length; i++) {
			result += 2^(i);
		}
		return result;
	}
	
//	@Override
	public int compareTo(Revision o) {
		if(o == null) {
			return +1;
		}
		int tmp = getMajor() - o.getMajor();
		if(tmp != 0) {
			return tmp;
		}
		
		tmp = getMinor() - o.getMinor();
		if(tmp != 0) {
			return tmp;
		}

		tmp = getMicro() - o.getMicro();
		if(tmp != 0) {
			return tmp;
		}
		return 0;
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ String toString() {
		// Use a StringBuffer in order not to be too inefficient
		StringBuffer buffer = new StringBuffer();
		// Treat the first number separately, it is not preceeded
		// by a "."
		buffer.append(String.valueOf(getNumber(1)));
		// cache length() for efficiency.
		int length=length();
		// append all numbers other than the first one
		// by appending a ".", and then the string representation
		// of the number.
		for(int i=2; i<= length; i++) {
			buffer.append(".");
			buffer.append(String.valueOf(getNumber(i)));
		}
		return buffer.toString();
	}
}

