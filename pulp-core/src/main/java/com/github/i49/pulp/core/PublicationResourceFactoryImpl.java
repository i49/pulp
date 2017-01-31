package com.github.i49.pulp.core;

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
import com.github.i49.pulp.api.XmlResource;

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
		CoreMediaType mediaType = CoreMediaTypes.guessMediaType(uri);
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
		if (xmlSet.contains(mediaType)) {
			return new BasicXmlStreamResource(name, mediaType, uri);
		} else {
			return new BasicStreamResource(name, mediaType, uri);
		}
	}
	
	private class BasicXmlStreamResource extends BasicStreamResource implements XmlResource {
		
		private Document document;

		public BasicXmlStreamResource(String name, CoreMediaType mediaType, URI location) {
			super(name, mediaType, location);
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
			try (InputStream stream = openStream()) {
				this.document = xmlService.readDocument(stream);
				return this.document;
			} catch (IOException e) {
				throw new EpubException(e.getMessage(), e);
			}
		}
	}
}
