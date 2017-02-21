package com.github.i49.pulp.api;

import java.io.InputStream;

/**
 * A factory type for creating instances of {@link PublicationReader}.
 */
public interface PublicationReaderFactory {

	/**
	 * Creates an instance of {@link PublicationReader}.
	 * @param stream the stream from which a publication will be read.
	 * @return the instance of {@link PublicationReader}.
	 */
	PublicationReader createReader(InputStream stream);
}
