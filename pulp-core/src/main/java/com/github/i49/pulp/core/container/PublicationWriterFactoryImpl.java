package com.github.i49.pulp.core.container;

import java.io.OutputStream;

import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.core.XmlService;

/**
 * An implementation of {@link PublicationWriterFactory}.
 */
public class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	private final XmlService xmlService;
	
	public PublicationWriterFactoryImpl(XmlService xmlService) {
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
