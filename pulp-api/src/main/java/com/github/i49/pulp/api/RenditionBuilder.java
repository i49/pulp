package com.github.i49.pulp.api;

import java.net.URI;
import java.nio.file.Path;

public interface RenditionBuilder {
	
	RenditionBuilder addResource(String name, Path path);

	RenditionBuilder addResource(String name, URI uri);
	
	RenditionBuilder addResource(String name, CoreMediaType mediaType, Path path);

	RenditionBuilder addResource(String name, CoreMediaType mediaType, URI uri);
	
	RenditionBuilder selectCoverImage(String name);

	void addPage(String name);

	void addPage(String name, boolean linear);
}
