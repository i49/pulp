package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.ContentSource;
import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.EpubParsingException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.spi.EpubServiceProvider;
import com.github.i49.pulp.core.Messages;
import com.github.i49.pulp.core.xml.XmlServices;

public class EpubPublicationReader implements PublicationReader {

	private final ReadableContainer container;
	private final EpubServiceProvider provider;
	private final DocumentBuilder documentBuilder;
	
	private String currentLocation;
	
	public EpubPublicationReader(ReadableContainer loader, EpubServiceProvider provider) {
		this.container = loader;
		this.provider = provider;
		this.documentBuilder = XmlServices.newBuilder();
	}

	@Override
	public Publication read() {
		try {
			return parseAll();
		} catch (Exception e) {
			throw new EpubParsingException(e.getMessage(), e, currentLocation, container.getPath());
		}
	}

	@Override
	public void close() {
		try {
			this.container.close();
		} catch (IOException e) {
			throw new EpubException(Messages.CONTAINER_NOT_CLOSEABLE(container.getPath()), e);
		}
	}
	
	protected Publication parseAll() throws IOException, SAXException {
		Publication publication = createPublication();
		Iterator<Rendition> it = parseContainerDocument(publication);
		while (it.hasNext()) {
			buildRendition(it.next());
		}
		updateContentSource(publication);
		return publication;
	}
	
	protected Publication createPublication() {
		return provider.createPublication();
	}
	
	protected PublicationResourceBuilderFactory createResourceBuilderFactory(URI location) {
		return provider.createResourceBuilderFactory(location);
	}
	
	protected Iterator<Rendition> parseContainerDocument(Publication publication) throws IOException, SAXException {
		String location = AbstractContainer.CONTAINER_DOCUMENT_LOCATION;
		Element rootElement = readXmlDocument(location);
		ContainerDocumentParser parser = ContainerDocumentParser.create(rootElement);
		return parser.parseFor(publication);
	}
	
	protected void buildRendition(Rendition rendition) throws IOException, SAXException {
		String location = rendition.getLocation().getPath();
		Element rootElement = readXmlDocument(location);
		PackageDocumentParser parser = PackageDocumentParser.create(rootElement);
		parser.parseFor(rendition, createResourceBuilderFactory(rendition.getLocation()));
	}
	
	protected void updateContentSource(Publication publication) {
		ContentSource source = this.container.getContentSource();
		for (PublicationResource resource: publication.getAllResources()) {
			resource.setContentSource(source);
		}
	}
	
	private Element readXmlDocument(String location) throws IOException, SAXException {
		setCurrentLocation(location);
		try (InputStream in = container.openItemToRead(location)) {
			Document document = documentBuilder.parse(in); 
			document.setDocumentURI(location);
			return document.getDocumentElement();
		}
	}

	private void setCurrentLocation(String location) {
		this.currentLocation = location;
	}
}
