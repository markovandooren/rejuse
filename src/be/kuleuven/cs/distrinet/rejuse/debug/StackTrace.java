package be.kuleuven.cs.distrinet.rejuse.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A debugging class that stores the current stacktrace. The stacktrace can be used to track when
 * a certain event happened. Examples are object creation and object modification.
 * 
 * @author Marko van Dooren
 */
public class StackTrace {

	/**
	 * Create a new stack trace. An exception is thrown and caught, and its stack trace is stored.
	 */
	public StackTrace() {
		try {
			throw new Exception();
		} catch (Exception e) {
			_stackTrace = e.getStackTrace();
		}
	}
	
	/**
	 * Return the stacktrace.
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
	public List<StackTraceElement> trace() {
		return new ArrayList<StackTraceElement>(Arrays.asList(_stackTrace));
	}	

	private StackTraceElement[] _stackTrace;
}
