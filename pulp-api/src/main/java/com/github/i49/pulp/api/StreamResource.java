package com.github.i49.pulp.api;

import java.io.IOException;
import java.io.InputStream;

/**
 * A publication resource that can be read from an {@link InputStream}.
 */
public interface StreamResource extends PublicationResource {

	/**
	 * Opens a new input stream from which this resource can be read.
	 * @return a new input stream.
	 * @throws IOException if an I/O error occurs.
	 */
	InputStream openStream() throws IOException;
}
