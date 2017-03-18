package com.github.i49.pulp.core.container;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link PublicationReaderFactory}.
 */
public class PublicationReaderFactoryImpl implements PublicationReaderFactory {

	private final EpubServiceProvider provider;
	
	/**
	 * Constructs this factory.
	 * @param provider the service provider.
	 */
	public PublicationReaderFactoryImpl(EpubServiceProvider provider) {
		this.provider = provider;
	}
	
	@Override
	public PublicationReader createReader(Path path) {
		if (path == null) {
			throw new IllegalArgumentException("path is null");
		}
		ReadableContainer container = null;
		try {
			container = new ReadableZipContainer(path);
		} catch (FileNotFoundException e) {
			throw new EpubException(Messages.CONTAINER_NOT_FOUND(path), e);
		} catch (IOException e) {
			throw new EpubException(Messages.CONTAINER_NOT_READABLE(path), e);
		}
		return new EpubPublicationReader(container, this.provider);
	}
}
