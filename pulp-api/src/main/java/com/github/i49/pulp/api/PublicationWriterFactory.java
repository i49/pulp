package com.github.i49.pulp.api;

import java.io.OutputStream;

/**
 * A factory type for creating instances of {@link PublicationWriter}.
 */
public interface PublicationWriterFactory {

	/**
	 * Creates an instance of {@link PublicationWriter}.
	 * @param stream the stream to which a publication will be written.
	 * @return the instance of {@link PublicationWriter}.
	 */
	PublicationWriter createWriter(OutputStream stream);
}
