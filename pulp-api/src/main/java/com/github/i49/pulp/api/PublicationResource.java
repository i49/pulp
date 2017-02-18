package com.github.i49.pulp.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * A resource that contains content or instructions that contribute to
 * the logic and rendering of a {@link Publication}.
 */
public interface PublicationResource {

	/**
	 * Returns the location of this resource inside or outside the EPUB container.
	 * @return the location of this resource.
	 */
	URI getLocation();
	
	/**
	 * Returns the media type of this resource.
	 * @return the media type of this resource.
	 */
	CoreMediaType getMediaType();
	
	/**
	 * Opens a new {@link InputStream} providing the content of this resource. 
	 * @return a new input stream, which should be closed by the caller of this method.
	 * @throws IOException if I/O error occurs.
	 */
	InputStream openContent() throws IOException;
}
