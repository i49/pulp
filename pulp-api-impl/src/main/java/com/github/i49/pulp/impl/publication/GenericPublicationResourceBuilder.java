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

package com.github.i49.pulp.impl.publication;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.i49.pulp.api.core.ContentSource;
import com.github.i49.pulp.api.core.CoreMediaType;
import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.MediaType;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.PublicationResourceBuilder;
import com.github.i49.pulp.impl.base.Messages;

/**
 * A generic implementation of {@link PublicationResourceBuilder}
 */
class GenericPublicationResourceBuilder implements PublicationResourceBuilder {

	private final PublicationResourceLocation location;
	private final String localPath;
	private final Path[] sourcePath;
	private final MediaTypeRegistry typeRegistry;
	
	private ContentSource source;
	private MediaType mediaType;
	
	private static final ContentSource EMPTY_SOURCE = EmptyContentSource.getInstance();
	
	/**
	 * Constructs this builder.
	 * 
	 * @param location the resource location relative to the root directory of the container.
	 * @param localPath the user specified path.
	 * @param sourcePath the paths used to find the content source.
	 * @param typeRegistry the media type registry.
	 */
	GenericPublicationResourceBuilder(
			PublicationResourceLocation location, 
			String localPath, 
			Path[] sourcePath,
			MediaTypeRegistry typeRegistry) {
	
		this.location = location;
		this.localPath = localPath;
		this.sourcePath = sourcePath;
		this.typeRegistry = typeRegistry;
		
		this.source = null;
		this.mediaType = null;
	}

	@Override
	public PublicationResourceBuilder ofType(MediaType mediaType) {
		checkNotNull(mediaType, "mediaType");
		this.mediaType = mediaType;
		return this;
	}

	@Override
	public PublicationResourceBuilder ofType(String value) {
		checkNotNull(value, "value");
		this.mediaType = typeRegistry.getMediaType(value);
		return this;
	}
	
	@Override
	public PublicationResourceBuilder empty() {
		return source(EMPTY_SOURCE);
	}
	
	@Override
	public PublicationResourceBuilder source(Path path) {
		checkNotNull(path, "path");
		return source(path.toUri());
	}

	@Override
	public PublicationResourceBuilder source(URI uri) {
		checkNotNull(uri, "uri");
		return source((location)->uri.toURL().openStream());
	}
	
	@Override
	public PublicationResourceBuilder source(byte[] bytes) {
		checkNotNull(bytes, "bytes");
		return source((location)->new ByteArrayInputStream(bytes));
	}
	
	@Override
	public PublicationResourceBuilder source(ContentSource source) {
		checkNotNull(source, "source");
		this.source = source;
		return this;
	}
	
	@Override
	public PublicationResource build() {
		final MediaType mediaType = determineMediaType();
		final ContentSource source = findContentSource();
		
		PublicationResource resource = null;
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			resource = createXhtmlDocument();
		} else if (typeRegistry.checkIfTypeIsXml(mediaType)) {
			resource = createXmlDocument(mediaType);
		} else {
			resource = createResource(mediaType);
		}
		
		resource.setContentSource(source);
		return resource;
	}
	
	@Override
	public String toString() {
		return location.toString();
	}
	
	/**
	 * Determines the final media type of the resource to create.
	 * 
	 * @return determined media type.
	 */
	private MediaType determineMediaType() {
		MediaType mediaType = this.mediaType;
		if (mediaType == null) {
			mediaType = typeRegistry.guessMediaType(localPath);
			if (mediaType == null) {
				throw new EpubException(Messages.MEDIA_TYPE_NOT_DETECTED(localPath));
			}
		}
		return mediaType;
	}
	
	private ContentSource findContentSource() {
		if (this.source != null) {
			return this.source;
		}
		return findContentSourceFromSourcePath();
	}
	
	private ContentSource findContentSourceFromSourcePath() {
		if (this.sourcePath.length == 0) {
			return EMPTY_SOURCE;
		}
		for (Path path: this.sourcePath) {
			Path source = path.resolve(localPath);
			if (Files.exists(source)) {
				return (location)->{
					return Files.newInputStream(source);
				};
			}
		}
		throw new EpubException(Messages.CONTENT_SOURCE_MISSING(location.toURI()));
	}
	
	protected PublicationResource createResource(MediaType mediaType) {
		return new BasicPublicationResource(location, mediaType);
	}
	
	protected PublicationResource createXmlDocument(MediaType mediaType) {
		return new BasicXmlDocument(location, mediaType);
	}
	
	protected PublicationResource createXhtmlDocument() {
		return new BasicXhtmlDocument(location);
	}
}
