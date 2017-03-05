package com.github.i49.pulp.api;

import java.net.URI;
import java.nio.file.Path;

/**
 * The builder type to build a {@link PublicationResource}.
 * The instances of this type can be obtained through 
 * {@link PublicationResourceRegistry}.
 */
public interface PublicationResourceBuilder {

	/**
	 * Specifies the media type of the resource to build.
	 * @param mediaType the media type represented by an instance of {@link MediaType}.
	 * @return this builder.
	 */
	PublicationResourceBuilder ofType(MediaType mediaType);
	
	/**
	 * Specifies the media type of the resource to build.
	 * @param value the string representation of the media type.
	 * @return this builder.
	 * @exception IllegalArgumentException if {@code value} is illegal or {@code null}.
	 */
	PublicationResourceBuilder ofType(String value);
	
	/**
	 * Specifies the content of the resource with a path.
	 * @param path the path from which the content will be read.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(Path path);

	/**
	 * Specifies the content of the resource with a URI.
	 * @param uri the URI from which the content will be read.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(URI uri);
	
	/**
	 * Specifies the content of the resource with a byte array.
	 * @param bytes the byte array to be used as the content of the resource.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(byte[] bytes);
	
	/**
	 * Specifies the content source for the resource.
	 * @param source the source of the content.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(ContentSource source);
	
	/**
	 * Specifies the directory containing the content of the resource.
	 * @param dir the path of the directory.
	 * @return this builder.
	 */
	PublicationResourceBuilder sourceDir(Path dir);
	
	/**
	 * Specifies the directory containing the content of the resource.
	 * @param dir the URI of the directory.
	 * @return this builder.
	 */
	PublicationResourceBuilder sourceDir(URI dir);
	
	/**
	 * Builds a {@link PublicationResource}.
	 * The built resource is registered with {@link PublicationResourceRegistry}.
	 * @return built publication resource.
	 */
	PublicationResource build();
}
