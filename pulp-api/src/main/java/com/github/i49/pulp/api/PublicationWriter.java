package com.github.i49.pulp.api;

import java.io.Closeable;
import java.io.IOException;

/**
 * A writer to write a publication.
 */
public interface PublicationWriter extends Closeable {
	
	/**
	 * Closes this writer and frees any resources associated with this writer.
	 * @throws EpubException if an I/O error occurred.
	 */
	void close();
	
	/**
	 * Writes a publication.
	 * @param publication the publication to be written.
	 * @throws EpubException if the publication cannot be written due to some error such as {@link IOException}.
	 */
	void write(Publication publication);
}
