package com.github.i49.pulp.api;

public interface PublicationResourceBuilderFactory {

	PublicationResourceBuilder getBuilder(String path);

	PublicationResourceBuilder getBuilder(String path, CoreMediaType mediaType);
}
