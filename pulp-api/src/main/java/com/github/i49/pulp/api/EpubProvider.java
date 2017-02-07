package com.github.i49.pulp.api;

import java.util.function.Consumer;

/**
 * Service provider for EPUB processing objects.
 */
public interface EpubProvider {

	/**
	 * Creates a publication.
	 * @return a publication.
	 */
	Publication createPublication(Consumer<PublicationBuilder> c);
	
	PublicationWriterFactory createWriterFactory();
}
