package com.github.i49.pulp.core.provider;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.spi.EpubProvider;
import com.github.i49.pulp.core.PublicationImpl;
import com.github.i49.pulp.core.container.PublicationReaderFactoryImpl;
import com.github.i49.pulp.core.container.PublicationWriterFactoryImpl;

/**
 * An implementation of {@link EpubProvider} interface.
 */
public class EpubProviderImpl implements EpubProvider {
	
	public EpubProviderImpl() {
	}
	
	@Override
	public Publication createPublication() {
		return new PublicationImpl();
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return new PublicationReaderFactoryImpl();
	}
	
	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl();
	}
}
