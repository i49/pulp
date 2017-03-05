package com.github.i49.pulp.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Content source that can provide initial content for {@link PublicationResource}.
 */
@FunctionalInterface
public interface ContentSource {

	/**
	 * Opens this content source for a {@link PublicationResource}.
	 * @param location the location of the resource using the content.
	 * @return {@link InputStream} that must be closed by the caller of this method.
	 * @throws IOException if an I/O error occurs.
	 * @see PublicationResource#getLocation()
	 */
	InputStream openSource(URI location) throws IOException;
}
