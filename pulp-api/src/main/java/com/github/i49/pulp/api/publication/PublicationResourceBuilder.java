/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.api.publication;

import java.net.URI;
import java.nio.file.Path;

/**
 * The builder type to be used to build a {@link PublicationResource}.
 * The instances of this type can be obtained through {@link PublicationResourceBuilderFactory}.
 */
public interface PublicationResourceBuilder {

	/**
	 * Specifies the media type of the resource to build.
	 * 
	 * @param mediaType the media type represented by an instance of {@link MediaType}.
	 * @return this builder.
	 * @throws IllegalArgumentException if {@code mediaType} is {@code null}.
	 */
	PublicationResourceBuilder ofType(MediaType mediaType);
	
	/**
	 * Specifies the media type of the resource to build by a string representation.
	 * 
	 * @param value the string representation of the media type, such as "image/png".
	 * @return this builder.
	 * @throws IllegalArgumentException if {@code value} is illegal or {@code null}.
	 */
	PublicationResourceBuilder ofType(String value);
	
	/**
	 * Specifies that the initial content of the resource should be empty.
	 * 
	 * @return this builder.
	 */
	PublicationResourceBuilder empty();
	
	/**
	 * Specifies the initial content of the resource with a path.
	 * 
	 * @param path the path from which the initial content of the resource will be read.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(Path path);

	/**
	 * Specifies the initial content of the resource with a URI.
	 * 
	 * @param uri the URI from which the initial content of the resource will be read.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(URI uri);
	
	/**
	 * Specifies the initial content of the resource with a byte array.
	 * 
	 * @param bytes the byte array to be used as the initial content of the resource.
	 * @return this builder.
	 */
	PublicationResourceBuilder source(byte[] bytes);
	
	/**
	 * Specifies the initial content of the resource with {@link ContentSource} interface.
	 * 
	 * @param source the content provider for the resource.
	 * @return this builder.
	 * @see ContentSource
	 */
	PublicationResourceBuilder source(ContentSource source);
	
	/**
	 * Builds a {@link PublicationResource}.
	 * 
	 * @return built publication resource.
	 * @throws EpubException if the content source for the resource was not found.
	 */
	PublicationResource build();
}
