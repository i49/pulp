package com.github.i49.pulp.core;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.file.Path;

import com.github.i49.pulp.api.ContentSource;
import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;

abstract class AbstractPublicationResourceBuilder implements PublicationResourceBuilder {

	private final URI location;
	private final String localPath;
	
	private ContentSource source;
	private CoreMediaType mediaType;
	
	AbstractPublicationResourceBuilder(URI location, String localPath) {
		this.location = location;
		this.localPath = localPath;
		this.mediaType = null;
	}

	@Override
	public PublicationResourceBuilder ofType(CoreMediaType mediaType) {
		this.mediaType = mediaType;
		return this;
	}

	@Override
	public PublicationResourceBuilder source(Path path) {
		if (path == null) {
			return this;
		}
		return source(path.toUri());
	}

	@Override
	public PublicationResourceBuilder source(URI uri) {
		if (uri == null) {
			return this;
		}
		return source((location)->uri.toURL().openStream());
	}
	
	@Override
	public PublicationResourceBuilder source(byte[] bytes) {
		if (bytes == null) {
			return this;
		}
		return source((location)->new ByteArrayInputStream(bytes));
	}
	
	@Override
	public PublicationResourceBuilder source(ContentSource source) {
		this.source = source;
		return this;
	}
	
	@Override
	public PublicationResourceBuilder sourceDir(Path dir) {
		if (dir == null) {
			return this;
		}
		return sourceDir(dir.toUri());
	}

	@Override
	public PublicationResourceBuilder sourceDir(URI dir) {
		if (dir == null) {
			return this;
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
				throw new EpubException(Messages.UNKNOWN_MEDIA_TYPE(localPath));
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
