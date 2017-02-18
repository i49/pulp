package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
class BasicPublicationResource implements PublicationResource {

	private final URI identifier;
	private final CoreMediaType mediaType;
	private final Content content;
	
	public BasicPublicationResource(URI identifier, CoreMediaType mediaType, Content content) {
		if (identifier == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.identifier = identifier;
		this.mediaType = mediaType;
		this.content = content;
	}

	@Override
	public URI getIdentifier() {
		return identifier;
	}
	
	@Override
	public CoreMediaType getMediaType() {
		return mediaType;
	}
	
	@Override
	public InputStream openContent() throws IOException {
		return getContent().openStream();
	}

	@Override
	public String toString() {
		return getIdentifier().toString();
	}
	
	protected Content getContent() {
		return content;
	}
}
