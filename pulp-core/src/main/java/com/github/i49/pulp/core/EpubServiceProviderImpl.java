package com.github.i49.pulp.core;

import java.net.URI;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;
import com.github.i49.pulp.core.container.PublicationReaderFactoryImpl;
import com.github.i49.pulp.core.container.PublicationWriterFactoryImpl;
import com.github.i49.pulp.core.publication.MediaTypeRegistry;
import com.github.i49.pulp.core.publication.PublicationImpl;
import com.github.i49.pulp.core.publication.PublicationResourceBuilderFactoryImpl;

/**
 * An implementation of {@link EpubServiceProvider} interface.
 */
public class EpubServiceProviderImpl implements EpubServiceProvider {
	
	private final MediaTypeRegistry typeRegistry = new MediaTypeRegistry();
	
	@Override
	public Publication createPublication() {
		return new PublicationImpl();
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return new PublicationReaderFactoryImpl(this::createPublication);
	}
	
	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl();
	}

	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		if (baseURI == null) {
			throw new IllegalArgumentException("baseURI is null");
		}
		return new PublicationResourceBuilderFactoryImpl(baseURI, typeRegistry);
	}
}
