package com.github.i49.pulp.core.publication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.github.i49.pulp.api.ContentSource;
import com.github.i49.pulp.api.MediaType;
import com.github.i49.pulp.api.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
class BasicPublicationResource implements PublicationResource {

	private static final int BUFFER_SIZE = 64 * 1024;

	private final URI location;
	private final MediaType mediaType;
	private ContentSource source;
	
	public BasicPublicationResource(URI location, MediaType mediaType) {
		if (location == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.location = location;
		this.mediaType = mediaType;
	}

	@Override
	public URI getLocation() {
		return location;
	}
	
	@Override
	public MediaType getMediaType() {
		return mediaType;
	}
	
	@Override
	public ContentSource getContentSource() {
		return source;
	}
	
	@Override
	public void setContentSource(ContentSource source) {
		this.source = source;
	}
	
	@Override
	public InputStream openContent() throws IOException {
		if (this.source == null) {
			throw new IllegalStateException();
		}
		return this.source.openSource(getLocation());
	}
	
	@Override
	public byte[] getContent() throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		try (InputStream in = openContent(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			return out.toByteArray();
		}
	}

	@Override
	public String toString() {
		return getLocation().toString();
	}
}
