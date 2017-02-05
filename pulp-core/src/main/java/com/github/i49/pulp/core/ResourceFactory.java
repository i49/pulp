package com.github.i49.pulp.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.XmlDocument;

public class ResourceFactory {

	private final XmlService xmlService;
	private final Map<String, PublicationResource> resources = new HashMap<>();
	
	private static final EnumSet<CoreMediaType> xmlSet = EnumSet.of(
			CoreMediaType.APPLICAIION_PLS_XML,
			CoreMediaType.APPLICATION_NCX_XML,
			CoreMediaType.APPLICATION_SMIL_XML,
			CoreMediaType.APPLICATION_XHTML_XML,
			CoreMediaType.IMAGE_SVG_XML);
	
	public ResourceFactory(XmlService xmlService) {
		this.xmlService = xmlService;
	}
	
	public Map<String, PublicationResource> getAllResources() {
		return resources;
	}
	
	public boolean hasResource(String fullName) {
		return resources.containsKey(fullName);
	}

	public PublicationResource addResource(String baseName, String localName, Path path) {
		return addResource(baseName, localName, path.toUri());
	}

	public PublicationResource addResource(String baseName, String localName, URI uri) {
		CoreMediaType mediaType = CoreMediaTypes.guessMediaType(localName);
		if (mediaType == null) {
			throw new EpubException("Failed to guess the media type for \"" + localName + "\"");
		}
		return addResource(baseName, localName, mediaType, uri);
	}
	
	public PublicationResource addResource(String baseName, String localName, CoreMediaType mediaType, Path path) {
		return addResource(baseName, localName, mediaType, path.toUri());
	}

	public PublicationResource addResource(String baseName, String localName, CoreMediaType mediaType, URI uri) {
		String fullName = baseName + "/" + localName;
		if (hasResource(fullName)) {
			throw new EpubException("resoruce \"" + fullName + "\" exists");
		}
		PublicationResource resource = createResource(fullName, mediaType, uri);
		if (resource != null) {
			resources.put(fullName, resource);
		}
		return resource;
	}
	
	private PublicationResource createResource(String fullName, CoreMediaType mediaType, URI uri) {
		PublicationResource resource = null;
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			resource = new DeferredXhtmlDocument(fullName, uri);
		} else if (xmlSet.contains(mediaType)) {
			resource = new DeferredXmlDocument(fullName, mediaType, uri);
		} else {
			resource = new DeferredByteArrayResource(fullName, mediaType, uri);
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
