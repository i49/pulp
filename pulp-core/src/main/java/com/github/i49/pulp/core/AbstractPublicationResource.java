package com.github.i49.pulp.core;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
abstract class AbstractPublicationResource implements PublicationResource {

	private final String name;
	private final CoreMediaType mediaType;
	
	protected AbstractPublicationResource(String name, CoreMediaType mediaType) {
		if (name == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.name = name;
		this.mediaType = mediaType;
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
	public String toString() {
		return getName().toString();
	}
}
