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
	
	/**
	 * Returns the content source for this resource.
	 * @return the content source for this resource, or {@code null} if no source is assigned.
	 */
	ContentSource getContentSource();
	
	/**
	 * Assigns the content source that will provide initial content for this resource.
	 * @param source the content source for this resource, can be {@code null}.
	 */
	void setContentSource(ContentSource source);
	
	/**
	 * Opens a new {@link InputStream} that provides the content of this resource. 
	 * @return a new input stream, which should be closed by the caller of this method.
	 * @exception IllegalStateException if content source is not assigned.
	 * @exception IOException if an I/O error has occurs.
	 */
	InputStream openContent() throws IOException;
	
	/**
	 * Returns the content of this resource.
	 * @return the content of this resource as a byte array.
	 * @exception IllegalStateException if content source is not assigned.
	 * @exception IOException if an I/O error has occurs.
	 */
	byte[] getContent() throws IOException;
}
