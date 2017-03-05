package com.github.i49.pulp.core.provider;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;
import com.github.i49.pulp.core.MediaTypeRegistry;
import com.github.i49.pulp.core.PublicationImpl;
import com.github.i49.pulp.core.container.PublicationReaderFactoryImpl;
import com.github.i49.pulp.core.container.PublicationWriterFactoryImpl;

/**
 * An implementation of {@link EpubServiceProvider} interface.
 */
public class EpubServiceProviderImpl implements EpubServiceProvider {
	
	private final MediaTypeRegistry typeRegistry = new MediaTypeRegistry();
	
	@Override
	public Publication createPublication() {
		return new PublicationImpl(typeRegistry);
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return new PublicationReaderFactoryImpl(this::createPublication);
	}
	
	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl();
	}
}
