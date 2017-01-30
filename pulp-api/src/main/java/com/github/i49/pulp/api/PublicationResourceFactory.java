package com.github.i49.pulp.api;

import java.net.URI;
import java.nio.file.Path;

/**
 * A factory interface for creating various kinds of publication resources.
 */
public interface PublicationResourceFactory {
	
	/**
	 * Creates a publication resource that contributes the rendering of a publication.
	 * The media type of the resource will be estimated with the file extension.
	 * @param name the name of the publication resource.
	 * @param path the path where the publication resource resides.  
	 * @return created publication resource.
	 * @exception NullPointerException thrown if one or more parameters are {@code null}.
	 */
	PublicationResource createResource(String name, Path path);

	/**
	 * Creates a publication resource that contributes the rendering of a publication.
	 * The media type of the resource will be estimated with the file extension.
	 * @param name the name of the publication resource.
	 * @param uri the URL where the publication resource resides.  
	 * @return created publication resource.
	 * @exception NullPointerException thrown if one or more parameters are {@code null}.
	 */
	PublicationResource createResource(String name, URI uri);
	
	/**
	 * Creates a publication resource that contributes the rendering of a publication.
	 * @param name the name of the publication resource.
	 * @param mediaType the media type of the resource.
	 * @param path the path where the publication resource resides.  
	 * @return created publication resource.
	 * @exception NullPointerException thrown if one or more parameters are {@code null}.
	 */
	PublicationResource createResource(String name, CoreMediaType mediaType, Path path);

	/**
	 * Creates a publication resource that contributes the rendering of a publication.
	 * @param name the name of the publication resource.
	 * @param mediaType the media type of the resource.
	 * @param uri the URL where the publication resource resides.  
	 * @return created publication resource.
	 * @exception NullPointerException thrown if one or more parameters are {@code null}.
	 */
	PublicationResource createResource(String name, CoreMediaType mediaType, URI uri);
}
