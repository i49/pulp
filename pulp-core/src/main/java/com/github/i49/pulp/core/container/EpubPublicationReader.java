package com.github.i49.pulp.core.container;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.EpubParsingException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.XmlServices;

public class EpubPublicationReader implements PublicationReader {

	private final ReadableContainer container;
	private final Supplier<Publication> supplier;
	private final DocumentBuilder documentBuilder;
	
	private String currentLocation;
	
	public EpubPublicationReader(ReadableContainer loader, Supplier<Publication> supplier) {
		this.container = loader;
		this.supplier = supplier;
		this.documentBuilder = XmlServices.newBuilder();
	}

	@Override
	public Publication read() {
		try {
			return parseContainerDocument();
		} catch (Exception e) {
			throw new EpubParsingException(e.getMessage(), e, currentLocation, container.getPath());
		}
	}

	@Override
	public void close() {
		try {
			this.container.close();
		} catch (IOException e) {
			throw new EpubException(Messages.containerNotCloseable(container.getPath()), e);
		}
	}
	
	protected Publication createPublication() {
		return supplier.get();
	}
	
	protected Publication parseContainerDocument() {
		String location = AbstractContainer.CONTAINER_DOCUMENT_LOCATION;
		Element rootElement = readXmlDocument(location);
		ContainerDocumentParser parser = ContainerDocumentParser.create(rootElement);
		Publication publication = createPublication();
		parser.setPublication(publication, this::buildRendition);
		parser.parse(rootElement);
		return publication;
	}
	
	protected void buildRendition(Rendition rendition) {
		String location = rendition.getLocation().getPath();
		Element rootElement = readXmlDocument(location);
		PackageDocumentParser parser = PackageDocumentParser.create(rootElement);
		parser.setRendition(rendition);
		parser.parse(rootElement);
	}
	
	private Element readXmlDocument(String location) {
		setCurrentLocation(location);
		try (InputStream in = container.openItemToRead(location)) {
			Document document = documentBuilder.parse(in); 
			return document.getDocumentElement();
		} catch (FileNotFoundException e) {
			throw new EpubException(Messages.containerEntryNotFound(location));
		} catch (IOException e) {
			throw new EpubException(Messages.containerEntryNotReadable(location));
		} catch (SAXException e) {
			throw new EpubException(Messages.xmlDocumentNotWellFormed(), e);
		}
	}

	private void setCurrentLocation(String location) {
		this.currentLocation = location;
	}
}
