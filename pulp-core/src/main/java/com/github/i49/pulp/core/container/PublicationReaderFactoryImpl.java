package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.nio.file.Path;

import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link PublicationReaderFactory}.
 */
public class PublicationReaderFactoryImpl implements PublicationReaderFactory {

	public PublicationReaderFactoryImpl() {
	}
	
	@Override
	public PublicationReader createReader(Path path) {
		if (path == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("path"));
		}
		try {
			return new EpubPublicationReader(new ZipContainerLoader(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
