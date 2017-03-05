package com.github.i49.pulp.api.spi;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;

/**
 * Service Provider Interface (SPI) to be implemented by API implementor.
 * This type is for internal use and the users of the API need not to care this interface.
 */
public interface EpubServiceProvider {
	
	/**
	 * Creates an empty publication.
	 * @return an empty publication.
	 */
	Publication createPublication();

	/**
	 * Creates an instance of factory that can be used to produce {@link PublicationReader}.
	 * @return an instance of publication reader factory.
	 */
	PublicationReaderFactory createReaderFactory();

	/**
	 * Creates an instance of factory that can be used to produce {@link PublicationWriter}.
	 * @return an instance of publication writer factory.
	 */
	PublicationWriterFactory createWriterFactory();
}
