package com.github.i49.pulp.api;

import java.nio.file.Path;

/**
 * {@link EpubException} that will be thrown when parsing EPUB.
 */
public class EpubParsingException extends EpubException {

	private static final long serialVersionUID = 1L;

	private final String location;
	private final Path containerPath;
	
	/**
	 * Constructs this exception.
	 * @param message the detail message.
	 * @param location the location of the entry in the container that caused the problem.
	 * @param containerPath the path of the container.
	 */
	public EpubParsingException(String message, String location, Path containerPath) {
		super(message);
		this.location = location;
		this.containerPath = containerPath;
	}
	
	/**
	 * Constructs this exception.
	 * @param message the detail message.
	 * @param cause the real cause of this exception.
	 * @param location the location of the entry in the container that caused this problem.
	 * @param containerPath the path of the container.
	 */
	public EpubParsingException(String message, Throwable cause, String location, Path containerPath) {
		super(message, cause);
		this.location = location;
		this.containerPath = containerPath;
	}
	
	/**
	 * Return the location of the entry in the container that caused this problem.
	 * @return the location of the entry.
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Returns the path of the container being parsed.
	 * @return the path of the container.
	 */
	public Path getContainerPath() {
		return containerPath;
	}
}
