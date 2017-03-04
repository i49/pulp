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
	 * Returns the location of this resource, which is either inside or outside the EPUB container.
	 * If the location points inside the container, it is relative to the root directory
	 * of the container.
	 * @return the location of this resource.
	 */
	URI getLocation();
	
	/**
	 * Returns the media type of this resource.
	 * @return the media type of this resource.
	 */
	MediaType getMediaType();
	
	ContentSource getContentSource();
	
	void setContentSource(ContentSource source);
	
	/**
	 * Opens a new {@link InputStream} that provides the content of this resource. 
	 * @return a new input stream, which should be closed by the caller of this method.
	 * @throws IOException if I/O error occurs.
	 */
	InputStream openContent() throws IOException;
}
