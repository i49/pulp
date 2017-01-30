package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.StreamResource;

/**
 * Basic implementation of {@link StreamResource} interface.
 */
class BasicStreamResource extends AbstractPublicationResource implements StreamResource {

	private static final int PERSIST_BUFFER_SIZE = 128 * 1024;

	private final URI location;
	
	public BasicStreamResource(String name, CoreMediaType mediaType, URI location) {
		super(name, mediaType);
		this.location = location;
	}
	
	@Override
	public void persist(OutputStream stream) throws IOException {
		byte[] buffer = new byte[PERSIST_BUFFER_SIZE];
		try (InputStream in = openStream()) {
			int length = 0;
			while ((length = in.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
		}
	}

	@Override
	public InputStream openStream() throws IOException {
		return this.location.toURL().openStream();
	}
}
