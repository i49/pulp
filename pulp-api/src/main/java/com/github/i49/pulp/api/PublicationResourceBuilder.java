package com.github.i49.pulp.api;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * The builder type to build a {@link PublicationResource}.
 * The instances of this type can be obtained through 
 * {@link PublicationResourceBuilderFactory}.
 * 
 */
public interface PublicationResourceBuilder {

	/**
	 * Specifies the media type of the resource to build.
	 * @param mediaType the media type of the resource.
	 * @return this builder.
	 */
	PublicationResourceBuilder ofType(CoreMediaType mediaType);
	
	/**
	 * Specifies the content of the resource with a path.
	 * If the path specified is a directory, the resource pathname is appended to the path.
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
	 * Specifies the supplier of the content for the resource.
	 * @param supplier the supplier of {@link InputStream} that will produce the content.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(Supplier<InputStream> supplier);
	
	PublicationResourceBuilder sourceDir(Path base);
	
	PublicationResourceBuilder sourceDir(URI base);
	
	/**
	 * Builds a {@link PublicationResource}.
	 * The built resource is registered with {@link PublicationResourceBuilderFactory}.
	 * @return built publication resource.
	 */
	PublicationResource build();
}
