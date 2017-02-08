package org.aikodi.rejuse.association;

public class LockException extends RuntimeException {

	/**
	 * No idea why this is needed, but Eclipse keeps nagging about it.
	 */
	private static final long serialVersionUID = 1L;

	public LockException() {
		super();
	}

	public LockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LockException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockException(String message) {
		super(message);
	}

	public LockException(Throwable cause) {
		super(cause);
	}

}
