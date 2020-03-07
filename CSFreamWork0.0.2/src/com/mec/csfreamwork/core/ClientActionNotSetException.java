package com.mec.csfreamwork.core;

public class ClientActionNotSetException extends Exception {
	private static final long serialVersionUID = -2459137707101041360L;

	public ClientActionNotSetException() {
	}

	public ClientActionNotSetException(String message) {
		super(message);
	}

	public ClientActionNotSetException(Throwable cause) {
		super(cause);
	}

	public ClientActionNotSetException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientActionNotSetException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
