package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.AuxiliaryResource;
import com.github.i49.pulp.api.ContentDocument;
import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.PublicationResourceFactory;
import com.github.i49.pulp.api.UncheckedSaxException;
import com.github.i49.pulp.api.XhtmlContentDocument;

public class PublicationResourceFactoryImpl implements PublicationResourceFactory {

	private final DocumentBuilder xmlDocumentBuilder;
	
	public PublicationResourceFactoryImpl() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			this.xmlDocumentBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public XhtmlContentDocument createXhtmlContentDocument(String name, URI location) {
		if (name == null || location == null) {
			throw new NullPointerException();
		}
		return new SimpleXhtmlContentDocument(name, location);
	}

	@Override
	public AuxiliaryResource createAuxiliaryResource(String name, CoreMediaType mediaType, URI location) {
		if (name == null || mediaType == null || location == null) {
			throw new NullPointerException();
		}
		return new SimpleAuxiliaryResource(name, mediaType, location);
	}

	private static class SimpleAuxiliaryResource extends AbstractPublicationResource implements AuxiliaryResource {

		private final URI location;

		public SimpleAuxiliaryResource(String name, CoreMediaType mediaType, URI location) {
			super(name, mediaType);
			this.location = location;
		}

		@Override
		public InputStream openOctetStream() throws IOException {
			return this.location.toURL().openStream();
		}
	}
	
	private static abstract class AbstractContentDocument extends SimpleAuxiliaryResource implements ContentDocument {

		private boolean linear;
		
		public AbstractContentDocument(String name, CoreMediaType mediaType, URI location) {
			super(name, mediaType, location);
		}
		
		@Override
		public boolean isLinear() {
			return linear;
		}
		
		@Override
		public void setLinear(boolean linear) {
			this.linear = linear;
		}
	}
	
	private class SimpleXhtmlContentDocument extends AbstractContentDocument implements XhtmlContentDocument {

		public SimpleXhtmlContentDocument(String name, URI location) {
			super(name, CoreMediaType.APPLICATION_XHTML_XML, location);
		}

		@Override
		public Document getDocument() {
			try (InputStream s = openOctetStream()) {
				return xmlDocumentBuilder.parse(s);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			} catch (SAXException e) {
				throw new UncheckedSaxException(e);
			}
		}
	}
}
