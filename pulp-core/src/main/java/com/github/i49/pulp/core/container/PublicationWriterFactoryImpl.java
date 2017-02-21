package com.github.i49.pulp.core.container;

import java.io.OutputStream;

import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;

/**
 * An implementation of {@link PublicationWriterFactory}.
 */
public class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	public PublicationWriterFactoryImpl() {
	}
	
	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		try {
			return new Epub3PublicationWriter(stream);
		} catch (Exception e) {
			return null;
		}
	}
}
