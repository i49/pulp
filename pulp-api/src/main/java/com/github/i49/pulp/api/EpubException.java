package com.github.i49.pulp.api;

/**
 * The exception thrown by the EPUB processing API.
 */
public class EpubException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EpubException(String message) {
		super(message);
	}
	
	public EpubException(Throwable cause) {
		super(cause);
	}

	public EpubException(String message, Throwable cause) {
		super(message, cause);
	}
}
