package org.aikodi.rejuse.debug;

public class StackTraceWithSingleFrame {

	/**
	 * Create a new stack trace that stores only the frame of the stacktrace at the given index.
	 * @param index
	 */
 /*@
   @ public behavior
   @
   @ pre index >= 1;
   @
   @ post index() == oneBasedIndex;
   @
   @ exception (IllegalArgumentException) the current stack trace contains less than oneBasedIndex elements.
   @*/
	public StackTraceWithSingleFrame(int oneBasedIndex) {
		try {
			_index = oneBasedIndex;
			throw new Exception();
		} catch (Exception e) {
			try {
			  _frame = e.getStackTrace()[oneBasedIndex-1];
			} catch(ArrayIndexOutOfBoundsException exc) {
				throw new IllegalArgumentException("The current stacktrace contains less than "+oneBasedIndex+" elements.");
			}
		}
	}

	private StackTraceElement _frame;
	
	/**
	 * Return the stacktrace element that was at index index() when this object was created.
	 * @return
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
	public StackTraceElement frame() {
		return _frame;
	}
	
	private int _index;

	/**
	 * Return the index of the stacktrace element that will be stored.
	 */
 /*@
   @ public behavior
   @
   @ post \result >= 1;
   @*/
	public int index() {
		return _index;
	}
	
}
