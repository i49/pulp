package com.github.i49.pulp.api;

/**
 * The exception thrown by Java API for EPUB processing.
 */
public class EpubException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs this exception with the specified detail message. 
	 * @param message the detail message that can be retrieved by calling {@link #getMessage()}. cannot be {@code null}.
	 */
	public EpubException(String message) {
		super(message);
	}
	
	/**
	 * Constructs this exception with the specified detail message and cause. 
	 * @param message the detail message that can be retrieved by calling {@link #getMessage()}. cannot be {@code null}.
	 * @param cause the real cause of this exception that can be retrieved by calling {@link #getCause()}. 
	 */
	public EpubException(String message, Throwable cause) {
		super(message, cause);
	}

	public EpubException(Throwable cause) {
		super(cause);
	}
}
