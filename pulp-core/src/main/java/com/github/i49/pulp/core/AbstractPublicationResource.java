package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
class AbstractPublicationResource implements PublicationResource {

	private final String name;
	private final CoreMediaType mediaType;
	private final Content content;
	
	public AbstractPublicationResource(String name, CoreMediaType mediaType, Content content) {
		if (name == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.name = name;
		this.mediaType = mediaType;
		this.content = content;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public CoreMediaType getMediaType() {
		return mediaType;
	}
	
	@Override
	public InputStream openStream() throws IOException {
		return getContent().openStream();
	}

	@Override
	public String toString() {
		return getName().toString();
	}
	
	protected Content getContent() {
		return content;
	}
}
