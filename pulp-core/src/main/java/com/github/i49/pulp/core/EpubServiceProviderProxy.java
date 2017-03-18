package com.github.i49.pulp.core;

import java.net.URI;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;

/**
 * Proxy of {@link EpubServiceProvider} implementation.
 */
public class EpubServiceProviderProxy implements EpubServiceProvider {

	// The singleton of {@link EpubServiceProviderImpl} shared by all threads.
	private static final EpubServiceProvider singleton = new EpubServiceProviderImpl();
	
	public EpubServiceProviderProxy() {
	}

	@Override
	public Publication createPublication() {
		return singleton.createPublication();
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return singleton.createReaderFactory();
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return singleton.createWriterFactory();
	}

	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		return singleton.createResourceBuilderFactory(baseURI);
	}
}
