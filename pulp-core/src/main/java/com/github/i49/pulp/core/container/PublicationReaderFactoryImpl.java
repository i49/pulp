package com.github.i49.pulp.core.container;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationReaderFactory;

/**
 * An implementation of {@link PublicationReaderFactory}.
 */
public class PublicationReaderFactoryImpl implements PublicationReaderFactory {

	private final Supplier<Publication> supplier;
	
	/**
	 * Constructs this factory.
	 * @param supplier the supplier of a {@link Publication} instance.
	 */
	public PublicationReaderFactoryImpl(Supplier<Publication> supplier) {
		this.supplier = supplier;
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
			throw new EpubException(Messages.containerNotFound(path), e);
		} catch (IOException e) {
			throw new EpubException(Messages.containerNotReadable(path), e);
		}
		return new EpubPublicationReader(container, this.supplier);
	}
}
