package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
class BasicPublicationResource implements PublicationResource {

	private final URI location;
	private final CoreMediaType mediaType;
	private final Content content;
	
	public BasicPublicationResource(URI location, CoreMediaType mediaType, Content content) {
		if (location == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.location = location;
		this.mediaType = mediaType;
		this.content = content;
	}

	@Override
	public URI getLocation() {
		return location;
	}
	
	@Override
	public CoreMediaType getMediaType() {
		return mediaType;
	}
	
	@Override
	public InputStream openContent() throws IOException {
		try {
			return getContent().openStream();
		} catch (Exception e) {
			// TODO:
			throw new EpubException("", e);
		}
	}

	@Override
	public String toString() {
		return getLocation().toString();
	}
	
	protected Content getContent() {
		return content;
	}
}
