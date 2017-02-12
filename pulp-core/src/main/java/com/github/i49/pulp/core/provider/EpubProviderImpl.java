package com.github.i49.pulp.core.provider;

import com.github.i49.pulp.api.EpubProvider;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.core.PublicationImpl;
import com.github.i49.pulp.core.PublicationWriterFactoryImpl;
import com.github.i49.pulp.core.PublicationResourceManager;
import com.github.i49.pulp.core.XmlService;

/**
 * An implementation of {@link EpubProvider} interface.
 */
public class EpubProviderImpl implements EpubProvider {
	
	private final XmlService xmlService = new XmlService();
	
	public EpubProviderImpl() {
	}
	
	@Override
	public Publication createPublication() {
		PublicationResourceManager resourceManager = new PublicationResourceManager(this.xmlService); 
		return new PublicationImpl(resourceManager);
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl(this.xmlService);
	}
}
