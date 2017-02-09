package com.github.i49.pulp.api;

/**
 * Service provider for EPUB processing objects.
 */
public interface EpubProvider {

	/**
	 * Creates a builder of a publication.
	 * @return a builder of a publication.
	 */
	PublicationBuilder createPublicationBuilder();
	
	PublicationWriterFactory createWriterFactory();
}
