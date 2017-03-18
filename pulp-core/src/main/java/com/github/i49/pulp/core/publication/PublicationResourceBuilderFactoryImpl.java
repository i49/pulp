package com.github.i49.pulp.core.publication;

import java.net.URI;

import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;

public class PublicationResourceBuilderFactoryImpl implements PublicationResourceBuilderFactory {
	
	private final URI baseURI;
	private final MediaTypeRegistry typeRegistry;
	
	public PublicationResourceBuilderFactoryImpl(URI baseURI, MediaTypeRegistry typeRegistry) {
		this.baseURI = baseURI;
		this.typeRegistry = typeRegistry;
	}

	@Override
	public PublicationResourceBuilder newBuilder(String location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		PublicationResourceLocation resolved = resolve(location);
		return new GenericPublicationResourceBuilder(resolved, location, this.typeRegistry);
	}
	
	private PublicationResourceLocation resolve(String location) {
		URI uri = this.baseURI.resolve(location); 
		return PublicationResourceLocation.of(uri);
	}
}
