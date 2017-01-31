package com.github.i49.pulp.core;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResourceFactory;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.spi.EpubProvider;

/**
 * An implementation of {@link EpubProvider} interface.
 */
public class EpubProviderImpl extends EpubProvider {
	
	private final XmlService xmlService = new XmlService();
	
	public EpubProviderImpl() {
	}

	@Override
	public Publication createPublication() {
		return new PublicationImpl();
	}

	@Override
	public PublicationResourceFactory createResourceFactory() {
		return new PublicationResourceFactoryImpl(this.xmlService);
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl(this.xmlService);
	}
}
