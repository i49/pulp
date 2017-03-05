package com.github.i49.pulp.api;

import java.nio.file.Path;

/**
 * A factory type for creating instances of {@link PublicationReader}.
 */
public interface PublicationReaderFactory {

	/**
	 * Creates an instance of {@link PublicationReader}.
	 * @param path the location where the file to be read is located.
	 * @return created instance of {@link PublicationReader}.
	 * @exception IllegalArgumentException if given {@code path} is {@code null}.
	 * @exception EpubException if an I/O error occurred.
	 */
	PublicationReader createReader(Path path);
}
