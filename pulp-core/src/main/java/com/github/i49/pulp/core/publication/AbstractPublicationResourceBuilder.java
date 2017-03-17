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

abstract class AbstractPublicationResourceBuilder implements PublicationResourceBuilder {

	private final PublicationResourceLocation location;
	private final String localPath;
	private final MediaTypeRegistry typeRegistry;
	
	private ContentSource source;
	private MediaType mediaType;
	
	AbstractPublicationResourceBuilder(PublicationResourceLocation location, String localPath, MediaTypeRegistry typeRegistry) {
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
		updateMediaType();
		PublicationResource resource = null;
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			resource = createXhtmlDocument();
		} else if (CoreMediaTypes.checkTypeIsXml(mediaType)) {
			resource = createXmlDocument();
		} else {
			resource = createResource();
		}
		if (source != null) {
			resource.setContentSource(source);
		}
		addResource(resource);
		return resource;
	}
	
	abstract protected void addResource(PublicationResource resource);
	
	private void updateMediaType() {
		if (this.mediaType == null) {
			this.mediaType = CoreMediaTypes.guessMediaType(localPath);
			if (this.mediaType == null) {
				throw new EpubException(Messages.MEDIA_TYPE_NOT_DETECTED(localPath));
			}
		}
	}
	
	protected PublicationResource createResource() {
		return new BasicPublicationResource(location, mediaType);
	}
	
	protected PublicationResource createXmlDocument() {
		return new BasicXmlDocument(location, mediaType);
	}
	
	protected PublicationResource createXhtmlDocument() {
		return new BasicXhtmlDocument(location);
	}
}
