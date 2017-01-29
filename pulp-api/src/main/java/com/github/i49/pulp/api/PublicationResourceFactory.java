package com.github.i49.pulp.api;

import java.net.URI;

public interface PublicationResourceFactory {

	XhtmlContentDocument createXhtmlContentDocument(URI identifier, URI location);
	
	AuxiliaryResource createAuxiliaryResource(URI identifier, CoreMediaType mediaType, URI location);
}
