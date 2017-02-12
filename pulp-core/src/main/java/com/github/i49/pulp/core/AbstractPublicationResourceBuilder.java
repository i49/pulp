package com.github.i49.pulp.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;

abstract class AbstractPublicationResourceBuilder implements PublicationResourceBuilder {

	private final String pathname;
	private final XmlService xmlService;
	
	private Supplier<InputStream> source;
	private CoreMediaType mediaType;
	
	AbstractPublicationResourceBuilder(String pathname, XmlService xmlService) {
		this.pathname = pathname;
		this.mediaType = null;
		this.xmlService = xmlService;
	}

	@Override
	public PublicationResourceBuilder ofType(CoreMediaType mediaType) {
		this.mediaType = mediaType;
		return this;
	}

	@Override
	public PublicationResourceBuilder from(Path path) {
		if (path == null) {
			return this;
		}
		URI uri = path.toUri();
		if (Files.isDirectory(path)) {
			uri = uri.resolve(pathname);
		}
		return from(uri);
	}

	@Override
	public PublicationResourceBuilder from(URI uri) {
		if (uri == null) {
			return this;
		}
		return from(()->{
			try {
				return uri.toURL().openStream();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}
	
	@Override
	public PublicationResourceBuilder from(byte[] bytes) {
		if (bytes == null) {
			return this;
		}
		return from(()->new ByteArrayInputStream(bytes));
	}
	
	@Override
	public PublicationResourceBuilder from(Supplier<InputStream> contentProvider) {
		if (contentProvider == null) {
			return this;
		}
		this.source = contentProvider;
		return this;
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
			this.mediaType = CoreMediaTypes.guessMediaType(pathname);
			if (this.mediaType == null) {
				throw new EpubException(Messages.UNKNOWN_MEDIA_TYPE(pathname));
			}
		}
	}
	
	protected PublicationResource createResource(Supplier<InputStream> source) {
		return new BasicPublicationResource(pathname, mediaType, new DeferredContent(source));
	}
	
	protected PublicationResource createXmlDocument(Document document) {
		return new BasicXmlDocument(pathname, mediaType, new DomContent(document));
	}
	
	protected PublicationResource createXmlDocument(Supplier<InputStream> source) {
		return new BasicXmlDocument(pathname, mediaType, new DeferredXmlContent(source));
	}

	protected PublicationResource createXhtmlDocument(Document document) {
		return new BasicXhtmlDocument(pathname, new DomContent(document));
	}
	
	protected PublicationResource createXhtmlDocument(Supplier<InputStream> source) {
		return new BasicXhtmlDocument(pathname, new DeferredXmlContent(source));
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
