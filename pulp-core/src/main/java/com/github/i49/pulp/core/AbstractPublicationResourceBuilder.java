package com.github.i49.pulp.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.function.Supplier;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;

abstract class AbstractPublicationResourceBuilder implements PublicationResourceBuilder {

	private final URI identifier;
	private final String localPath;
	private final XmlService xmlService;
	
	private Supplier<InputStream> source;
	private CoreMediaType mediaType;
	
	AbstractPublicationResourceBuilder(URI identifier, String localPath, XmlService xmlService) {
		this.identifier = identifier;
		this.localPath = localPath;
		this.mediaType = null;
		this.xmlService = xmlService;
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
		return source(()->{
			try {
				return uri.toURL().openStream();
			} catch (IOException e) {
				throw new EpubException(e.getMessage(), e);
			}
		});
	}
	
	@Override
	public PublicationResourceBuilder source(byte[] bytes) {
		if (bytes == null) {
			return this;
		}
		return source(()->new ByteArrayInputStream(bytes));
	}
	
	@Override
	public PublicationResourceBuilder source(Supplier<InputStream> contentProvider) {
		if (contentProvider == null) {
			return this;
		}
		this.source = contentProvider;
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
			resource = createXhtmlDocument(source);
		} else if (CoreMediaTypes.checkTypeIsXml(mediaType)) {
			resource = createXmlDocument(source);
		} else {
			resource = createResource(source);
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
	
	protected PublicationResource createResource(Supplier<InputStream> source) {
		return new BasicPublicationResource(identifier, mediaType, new DeferredContent(source));
	}
	
	protected PublicationResource createXmlDocument(Document document) {
		return new BasicXmlDocument(identifier, mediaType, new DomContent(document));
	}
	
	protected PublicationResource createXmlDocument(Supplier<InputStream> source) {
		return new BasicXmlDocument(identifier, mediaType, new DeferredXmlContent(source));
	}

	protected PublicationResource createXhtmlDocument(Document document) {
		return new BasicXhtmlDocument(identifier, new DomContent(document));
	}
	
	protected PublicationResource createXhtmlDocument(Supplier<InputStream> source) {
		return new BasicXhtmlDocument(identifier, new DeferredXmlContent(source));
	}

	private static class DeferredContent implements Content {
		
		private final Supplier<InputStream> source;
		
		private DeferredContent(Supplier<InputStream> source) {
			this.source = source;
		}
		
		@Override
		public InputStream openStream() throws IOException {
			return source.get();
		}
	}
	
	private class DomContent implements XmlContent {
		
		private final Document document;
		
		private DomContent(Document document) {
			this.document = document;
		}

		@Override
		public InputStream openStream() throws IOException {
			return getSerialized(getDocument());
		}

		@Override
		public Document getDocument() {
			return document;
		}
	}
	
	private class DeferredXmlContent implements XmlContent {
		
		private final Supplier<InputStream> source;
		private Document document;
		
		private DeferredXmlContent(Supplier<InputStream> source) {
			this.source = source;
		}

		@Override
		public InputStream openStream() throws IOException {
			return getSerialized(getDocument());
		}

		@Override
		public Document getDocument() {
			if (this.document == null) {
				this.document = readDocument();
			}
			return this.document;
		}

		private Document readDocument() {
			try (InputStream in = source.get()) {
				return xmlService.readDocument(in);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
	
	private InputStream getSerialized(Document document) {
		if (document == null) {
			return null;
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			xmlService.writeDocument(out, document);
			out.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
