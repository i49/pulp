package com.github.i49.pulp.core.provider;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;

/**
 * Proxy of {@link EpubServiceProvider} implementation.
 */
public class EpubServiceProviderProxy implements EpubServiceProvider {

	// The singleton of {@link EpubServiceProviderImpl} shared by all threads.
	private static final EpubServiceProvider impl = new EpubServiceProviderImpl();
	
	public EpubServiceProviderProxy() {
	}

	@Override
	public Publication createPublication() {
		return impl.createPublication();
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return impl.createReaderFactory();
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return impl.createWriterFactory();
	}
}
