package com.github.i49.pulp.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.EnumSet;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceFactory;
import com.github.i49.pulp.api.XmlDocument;

/**
 * An implementation of {@link PublicationResourceFactory}.
 */
class PublicationResourceFactoryImpl implements PublicationResourceFactory {

	private static final EnumSet<CoreMediaType> xmlSet = EnumSet.of(
			CoreMediaType.APPLICAIION_PLS_XML,
			CoreMediaType.APPLICATION_NCX_XML,
			CoreMediaType.APPLICATION_SMIL_XML,
			CoreMediaType.APPLICATION_XHTML_XML,
			CoreMediaType.IMAGE_SVG_XML);

	private final XmlService xmlService;
	
	PublicationResourceFactoryImpl(XmlService xmlService) {
		this.xmlService = xmlService;
	}

	@Override
	public PublicationResource createResource(String name, Path path) {
		if (name == null || path == null) {
			throw new NullPointerException();
		}
		return createResource(name, path.toUri());
	}

	@Override
	public PublicationResource createResource(String name, URI uri) {
		if (name == null || uri == null) {
			throw new NullPointerException();
		}
		CoreMediaType mediaType = CoreMediaTypes.guessMediaType(name);
		if (mediaType == null) {
			return null;
		}
		return createResource(name, mediaType, uri);
	}
	
	@Override
	public PublicationResource createResource(String name, CoreMediaType mediaType, Path path) {
		if (name == null || path == null || mediaType == null) {
			throw new NullPointerException();
		}
		return createResource(name, mediaType, path.toUri());
	}

	@Override
	public PublicationResource createResource(String name, CoreMediaType mediaType, URI uri) {
		if (name == null || uri == null || mediaType == null) {
			throw new NullPointerException();
		}
		PublicationResource resource = null;
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			resource = new DeferredXhtmlDocument(name, uri);
		} else if (xmlSet.contains(mediaType)) {
			resource = new DeferredXmlDocument(name, mediaType, uri);
		} else {
			resource = new DeferredByteArrayResource(name, mediaType, uri);
		}
		return resource;
	}
	
	private class DeferredByteArrayResource extends AbstractByteArrayResource {
		
		private static final int BUFFER_SIZE = 16 * 1024;
		private final URI uri;

		public DeferredByteArrayResource(String name, CoreMediaType mediaType, URI uri) {
			super(name, mediaType);
			this.uri = uri;
		}

		@Override
		public byte[] getByteArray() {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			try (InputStream in = uri.toURL().openStream()) {
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} catch (IOException e) {
				throw new EpubException(e);
			}
			return out.toByteArray();
		}
	}
	
	private class DeferredXmlDocument extends AbstractPublicationResource implements XmlDocument {
		
		private final URI uri;
		private Document document;

		public DeferredXmlDocument(String name, CoreMediaType mediaType, URI uri) {
			super(name, mediaType);
			this.uri = uri;
		}

		@Override
		public void persist(OutputStream stream) throws IOException {
			Document document = getDocument();
			if (document != null) {
				xmlService.writeDocument(stream, document);
			}
		}

		@Override
		public Document getDocument() {
			if (this.document != null) {
				return this.document;
			}
			try (InputStream stream = uri.toURL().openStream()) {
				this.document = xmlService.readDocument(stream);
				return this.document;
			} catch (IOException e) {
				throw new EpubException(e);
			}
		}
	}
	
	private class DeferredXhtmlDocument extends DeferredXmlDocument implements DefaultXhtmlDocument {

		public DeferredXhtmlDocument(String name, URI uri) {
			super(name, CoreMediaType.APPLICATION_XHTML_XML, uri);
		}
	}
}
