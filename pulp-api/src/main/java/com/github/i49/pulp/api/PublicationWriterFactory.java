package com.github.i49.pulp.api;

import java.io.OutputStream;
import java.nio.file.Path;

/**
 * A factory type for creating instances of {@link PublicationWriter}.
 */
public interface PublicationWriterFactory {

	/**
	 * Creates an instance of {@link PublicationWriter}.
	 * @param path the location where a publication will be stored by the writer.
	 * @return created instance of {@link PublicationWriter}.
	 * @exception NullPointerException if given {@code path} is {@code null}.
	 */
	PublicationWriter createWriter(Path path);
	
	/**
	 * Creates an instance of {@link PublicationWriter}.
	 * @param stream the stream to which a publication will be written.
	 * @return created instance of {@link PublicationWriter}.
	 * @exception NullPointerException if given {@code stream} is {@code null}.
	 */
	PublicationWriter createWriter(OutputStream stream);
}
