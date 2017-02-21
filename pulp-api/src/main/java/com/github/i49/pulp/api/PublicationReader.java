package com.github.i49.pulp.api;

import java.io.Closeable;
import java.io.IOException;

public interface PublicationReader  extends Closeable {

	/**
	 * Closes this reader and frees any resources associated with this reader.
	 * @exception EpubException if an I/O error occurred.
	 */
	void close();
	
	/**
	 * Reads a publication.
	 * @return the publication read by this reader.
	 * @exception EpubException if the publication cannot be read due to some error such as {@link IOException}.
	 */
	Publication read();
}
