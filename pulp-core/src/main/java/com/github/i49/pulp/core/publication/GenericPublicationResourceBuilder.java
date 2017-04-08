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

package com.github.i49.pulp.core.publication;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.file.Path;

import com.github.i49.pulp.api.ContentSource;
import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.MediaType;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.core.Messages;

class GenericPublicationResourceBuilder implements PublicationResourceBuilder {

	private final PublicationResourceLocation location;
	private final String localPath;
	private final MediaTypeRegistry typeRegistry;
	
	private ContentSource source;
	private MediaType mediaType;
	
	GenericPublicationResourceBuilder(PublicationResourceLocation location, String localPath, MediaTypeRegistry typeRegistry) {
		this.location = location;
		this.localPath = localPath;
		this.typeRegistry = typeRegistry;
		
		this.mediaType = null;
	}

	@Override
	public PublicationResourceBuilder ofType(MediaType mediaType) {
		if (mediaType == null) {
			throw new IllegalArgumentException("mediaType is null");
		}
		this.mediaType = mediaType;
		return this;
	}

	@Override
	public PublicationResourceBuilder ofType(String value) {
		if (value == null) {
			throw new IllegalArgumentException("value is null");
		}
		this.mediaType = typeRegistry.getMediaType(value);
		return this;
	}
	
	@Override
	public PublicationResourceBuilder source(Path path) {
		if (path == null) {
			throw new IllegalArgumentException("path is null");
		}
		return source(path.toUri());
	}

	@Override
	public PublicationResourceBuilder source(URI uri) {
		if (uri == null) {
			throw new IllegalArgumentException("uri is null");
		}
		return source((location)->uri.toURL().openStream());
	}
	
	@Override
	public PublicationResourceBuilder source(byte[] bytes) {
		if (bytes == null) {
			throw new IllegalArgumentException("bytes is null");
		}
		return source((location)->new ByteArrayInputStream(bytes));
	}
	
	@Override
	public PublicationResourceBuilder source(ContentSource source) {
		if (source == null) {
			throw new IllegalArgumentException("source is null");
		}
		this.source = source;
		return this;
	}
	
	@Override
	public PublicationResourceBuilder sourceDir(Path dir) {
		if (dir == null) {
			throw new IllegalArgumentException("dir is null");
		}
		return sourceDir(dir.toUri());
	}

	@Override
	public PublicationResourceBuilder sourceDir(URI dir) {
		if (dir == null) {
			throw new IllegalArgumentException("dir is null");
		}
		return source(dir.resolve(localPath));
	}
	
	@Override
	public PublicationResource build() {
		final MediaType mediaType = determineMediaType();
		PublicationResource resource = null;
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			resource = createXhtmlDocument();
		} else if (typeRegistry.checkIfTypeIsXml(mediaType)) {
			resource = createXmlDocument(mediaType);
		} else {
			resource = createResource(mediaType);
		}
		if (source != null) {
			resource.setContentSource(source);
		}
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
