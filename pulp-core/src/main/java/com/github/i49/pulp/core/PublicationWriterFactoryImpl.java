package com.github.i49.pulp.core;

import java.io.OutputStream;

import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;

/**
 * An implementation of {@link PublicationWriterFactory}.
 */
class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	private final XmlService xmlService;
	
	PublicationWriterFactoryImpl(XmlService xmlService) {
		this.xmlService = xmlService;
	}
	
	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		try {
			return new Epub3PublicationWriter(stream, this.xmlService);
		} catch (Exception e) {
			return null;
		}
	}
}
