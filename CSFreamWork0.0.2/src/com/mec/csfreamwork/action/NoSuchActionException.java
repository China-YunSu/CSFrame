package com.mec.csfreamwork.action;

public class NoSuchActionException extends Exception {

	private static final long serialVersionUID = -4144840297819555302L;
	public NoSuchActionException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoSuchActionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchActionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NoSuchActionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoSuchActionException(Throwable cause) {
		super(cause);
	}

}
