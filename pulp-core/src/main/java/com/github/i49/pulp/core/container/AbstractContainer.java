package com.github.i49.pulp.core.container;

import java.io.Closeable;
import java.nio.file.Path;

public abstract class AbstractContainer implements Closeable {
	
	public static final String MIMETYPE_LOCATION = "mimetype";
	public static final String CONTAINER_DOCUMENT_LOCATION = "META-INF/container.xml";

	private final Path path;

	/**
	 * Constructs this container.
	 * @param path the location of this container. can be {@code null}.
	 */
	protected AbstractContainer(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}
}
