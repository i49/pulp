package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
class BasicPublicationResource implements PublicationResource {

	private final String pathname;
	private final CoreMediaType mediaType;
	private final Content content;
	
	public BasicPublicationResource(String pathname, CoreMediaType mediaType, Content content) {
		if (pathname == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.pathname = pathname;
		this.mediaType = mediaType;
		this.content = content;
	}

	@Override
	public String getPathname() {
		return pathname;
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
		return getPathname().toString();
	}
	
	protected Content getContent() {
		return content;
	}
}
