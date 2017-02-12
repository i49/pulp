package com.github.i49.pulp.api;

import java.io.IOException;
import java.io.InputStream;

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
	
	
	InputStream openStream() throws IOException;
}
