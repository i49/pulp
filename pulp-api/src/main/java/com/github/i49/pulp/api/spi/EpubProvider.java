package com.github.i49.pulp.api.spi;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationWriterFactory;

/**
 * Service Provider Interface (SPI) to be implemented by API implementor.
 * This type is for internal use and the users of the API need not to care this interface.
 */
public interface EpubProvider {
	
	/**
	 * Creates an empty publication.
	 * @return an empty publication.
	 */
	Publication createPublication();

	/**
	 * Creates an instance of factory that can produce publication writers.
	 * @return an instance of publication writer factory.
	 */
	PublicationWriterFactory createWriterFactory();
}
