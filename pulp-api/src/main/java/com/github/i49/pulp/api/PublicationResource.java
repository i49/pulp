package com.github.i49.pulp.api;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A resource that contains content or instructions that contribute to
 * the logic and rendering of a publication.
 */
public interface PublicationResource {

	/**
	 * Returns the name of this publication.
	 * @return the name of this publication.
	 */
	String getName();
	
	/**
	 * Returns the media type of this publication.
	 * @return the media type of this publication.
	 */
	CoreMediaType getMediaType();
	
	/**
	 * Returns whether this resource is primary or not.
	 * @return {@code true} if this resource is primary, {@code false} otherwise.
	 */
	boolean isPrimary();
	
	void setPrimary(boolean primary);
	
	/**
	 * Persists this resource.
	 * @param stream the stream to which this resource will be written.
	 * @throws IOException thrown if I/O error occurred.
	 */
	void persist(OutputStream stream) throws IOException;
}
