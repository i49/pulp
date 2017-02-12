package com.github.i49.pulp.api;

/**
 * Service provider for EPUB processing objects.
 */
public interface EpubProvider {
	
	/**
	 * Creates an empty publication.
	 * @return an empty publication.
	 */
	Publication createPublication();

	/**
	 * Creates a new factory instance in order to create publication writers.
	 * @return an instance of publication writer factory.
	 */
	PublicationWriterFactory createWriterFactory();
}
