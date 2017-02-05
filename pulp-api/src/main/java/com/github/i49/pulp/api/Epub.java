package com.github.i49.pulp.api;

import java.io.OutputStream;
import java.util.function.Consumer;

import com.github.i49.pulp.api.spi.EpubProvider;

/**
 * Factory class for creating EPUB processing objects. 
 */
public final class Epub {

	/**
	 * Creates an instance of {@link Publication}.
	 * @param consumer accepts {@link PublicationBuilder} to build a publication.
	 * @return created publication.
	 */
	public static Publication createPublication(Consumer<PublicationBuilder> consumer) {
		return EpubProvider.provider().createPublication(consumer);
	}
	
	/**
	 * Creates a {@link PublicationWriter} instance that writes a publication to the given stream.
	 * @param stream the stream to which the writer will writes a publication.
	 * @return created publication writer.
	 */
	public static PublicationWriter createWriter(OutputStream stream) {
		return createWriterFactory().createWriter(stream);
	}
	
	/**
	 * Creates a {@link PublicationWriterFactory} instance that can create one or more {@link PublicationWriter}s.
	 * @return created publication writer factory.
	 */
	public static PublicationWriterFactory createWriterFactory() {
		return EpubProvider.provider().createWriterFactory();
	}
	
	private Epub() {
	}
}
