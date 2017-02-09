package com.github.i49.pulp.core.provider;

import com.github.i49.pulp.api.EpubProvider;
import com.github.i49.pulp.api.PublicationBuilder;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.core.PublicationBuilderImpl;
import com.github.i49.pulp.core.PublicationWriterFactoryImpl;
import com.github.i49.pulp.core.XmlService;

/**
 * An implementation of {@link EpubProvider} interface.
 */
public class EpubProviderImpl implements EpubProvider {
	
	private final XmlService xmlService = new XmlService();
	
	public EpubProviderImpl() {
	}

	@Override
	public PublicationBuilder createPublicationBuilder() {
		return new PublicationBuilderImpl(this.xmlService);
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl(this.xmlService);
	}
}
