package com.github.i49.pulp.api;

import java.net.URI;

/**
 * A factory class for creating various kinds of publication resources.
 */
public interface PublicationResourceFactory {

	XhtmlContentDocument createXhtmlContentDocument(String name, URI location);
	
	AuxiliaryResource createAuxiliaryResource(String name, CoreMediaType mediaType, URI location);
}
