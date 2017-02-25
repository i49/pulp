package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.PublicationImpl;
import com.github.i49.pulp.core.XmlServices;

public class EpubPublicationReader implements PublicationReader {

	private final ReadableContainer container;
	private final DocumentBuilder documentBuilder;
	
	public EpubPublicationReader(ReadableContainer loader) {
		this.container = loader;
		this.documentBuilder = XmlServices.newBuilder();
	}

	@Override
	public Publication read() {
		Publication publication = createPublication();
		addRenditions(publication);
		return publication;
	}

	@Override
	public void close() {
		try {
			this.container.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected Publication createPublication() {
		return new PublicationImpl();
	}
	
	private void addRenditions(Publication publication) {
		Document document = readXmlDocument(AbstractContainer.CONTAINER_DOCUMENT_LOCATION);
		ContainerDocument container = ContainerDocument.create(document);
		container.addRenditions(publication);
		for (Rendition rendition: publication) {
			populateRendition(rendition);
		}
	}
	
	private void populateRendition(Rendition rendition) {
		Document document = readXmlDocument(rendition.getLocation().getPath());
	}
	
	private Document readXmlDocument(String location) {
		try (InputStream in = container.openItemToRead(location)) {
			return documentBuilder.parse(in);
		} catch (IOException e) {
			// TODO
			return null;
		} catch (SAXException e) {
			// TODO
			return null;
		}
	}
}
