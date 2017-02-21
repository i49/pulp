package com.github.i49.pulp.core.container;

import java.io.InputStream;

import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationReaderFactory;

/**
 * An implementation of {@link PublicationReaderFactory}.
 */
public class PublicationReaderFactoryImpl implements PublicationReaderFactory {

	public PublicationReaderFactoryImpl() {
	}
	
	@Override
	public PublicationReader createReader(InputStream stream) {
		return new EpubPublicationReader(stream);
	}
}
