package com.github.i49.pulp.core;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;

public class PublicationResourceBuilderFactoryImpl implements PublicationResourceBuilderFactory {
	
	private final String prefix;
	private final PublicationResourceManager resourceManager;
	
	PublicationResourceBuilderFactoryImpl(String prefix, PublicationResourceManager resourceManager) {
		if (prefix.endsWith("/")) {
			this.prefix = prefix;
		} else {
			this.prefix = prefix + "/";
		}
		this.resourceManager = resourceManager;
	}

	@Override
	public PublicationResourceBuilder getBuilder(String path) {
		CoreMediaType mediaType = CoreMediaTypes.guessMediaType(path);
		if (mediaType == null) {
			throw new EpubException("Unknown media type.");
		}
		return getBuilder(path, mediaType);
	}

	@Override
	public PublicationResourceBuilder getBuilder(String path, CoreMediaType mediaType) {
		if (resourceManager.containsKey(path)) {
			throw new EpubException("Resource already exists.");
		}
		return new PublicationResourceBuilderImpl(resolve(path), mediaType, this.resourceManager.getXmlService());
	}
	
	private String resolve(String path) {
		return prefix + path;
	}
}
