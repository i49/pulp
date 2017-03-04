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
			throw new NullPointerException(Messages.NULL_PARAMETER("path"));
		}
		try {
			return new EpubPublicationWriter3(new WriteableZipContainer(path));
		} catch (IOException e) {
			// TODO 
			throw new EpubException(e.getMessage(), e);
		}
	}

	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		if (stream == null) {
			throw new NullPointerException(Messages.NULL_PARAMETER("stream"));
		}
		return new EpubPublicationWriter3(new WriteableZipContainer(stream));
	}
}
