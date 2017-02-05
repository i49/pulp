package com.github.i49.pulp.core;

import java.util.function.Consumer;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationBuilder;
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
	public Publication createPublication(Consumer<PublicationBuilder> c) {
		PublicationBuilderImpl builder = new PublicationBuilderImpl(this.xmlService);
		c.accept(builder);
		return builder.build();
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl(this.xmlService);
	}
}
