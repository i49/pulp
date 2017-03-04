package com.github.i49.pulp.core;

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
	public String toString() {
		return getLocation().toString();
	}
}
