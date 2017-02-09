package com.github.i49.pulp.api;

import java.net.URI;
import java.nio.file.Path;

/**
 * A builder of a publication.
 */
public interface PublicationBuilder {

	void startRendition();
	
	void startRendition(String prefix);
	
	void endRendition();
	
	void addResource(String name, Path path);

	void addResource(String name, URI uri);
	
	void addResource(String name, CoreMediaType mediaType, Path path);

	void addResource(String name, CoreMediaType mediaType, URI uri);
	
	void selectCoverImage(String name);

	void addPage(String name);

	void addPage(String name, boolean linear);

	Publication build();
}
