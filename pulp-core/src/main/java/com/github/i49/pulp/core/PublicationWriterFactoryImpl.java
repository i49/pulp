package com.github.i49.pulp.core;

import java.io.OutputStream;

import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;

public class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		try {
			return new Epub3PublicationWriter(stream);
		} catch (Exception e) {
			return null;
		}
	}
}
