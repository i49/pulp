package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link PublicationWriterFactory}.
 */
public class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	public PublicationWriterFactoryImpl() {
	}
	
	@Override
	public PublicationWriter createWriter(Path path) {
		if (path == null) {
			throw new IllegalArgumentException("path is null");
		}
		WriteableContainer container = null;
		try {
			container = new WriteableZipContainer(path);
		} catch (IOException e) {
			throw new EpubException(Messages.CONTAINER_NOT_WRITEABLE(path), e);
		}
		return new EpubPublicationWriter3(container);
	}

	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		if (stream == null) {
			throw new IllegalArgumentException("stream is null");
		}
		return new EpubPublicationWriter3(new WriteableZipContainer(stream));
	}
}
