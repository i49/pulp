package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.core.NullArgumentException;

/**
 * An implementation of {@link PublicationWriterFactory}.
 */
public class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	public PublicationWriterFactoryImpl() {
	}
	
	@Override
	public PublicationWriter createWriter(Path path) {
		if (path == null) {
			throw new NullArgumentException("path");
		}
		WriteableContainer container = null;
		try {
			container = new WriteableZipContainer(path);
		} catch (IOException e) {
			throw new EpubException(Messages.containerNotWriteable(path), e);
		}
		return new EpubPublicationWriter3(container);
	}

	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		if (stream == null) {
			throw new NullArgumentException("stream");
		}
		return new EpubPublicationWriter3(new WriteableZipContainer(stream));
	}
}
