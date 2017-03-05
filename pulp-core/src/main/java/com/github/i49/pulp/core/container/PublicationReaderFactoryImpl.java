package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.core.NullArgumentException;

/**
 * An implementation of {@link PublicationReaderFactory}.
 */
public class PublicationReaderFactoryImpl implements PublicationReaderFactory {

	private final Supplier<Publication> supplier;
	
	/**
	 * Constructs this factory.
	 * @param supplier the supplier of {@link Publication} instances.
	 */
	public PublicationReaderFactoryImpl(Supplier<Publication> supplier) {
		this.supplier = supplier;
	}
	
	@Override
	public PublicationReader createReader(Path path) {
		if (path == null) {
			throw new NullArgumentException("path");
		}
		ReadableContainer container = null;
		try {
			container = new ReadableZipContainer(path);
		} catch (IOException e) {
			throw new EpubException(Messages.CONTAINER_NOT_READABLE(path), e);
		}
		return new EpubPublicationReader(container, this.supplier);
	}
}
