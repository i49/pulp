package com.github.i49.pulp.cli;

/**
 * The exception thrown when the command failed.
 */
public class CommandException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CommandException(String message) {
		super(message);
	}
	
	public CommandException(Throwable cause) {
		super(cause);
	}
}
