package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
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
		return parseContainerDocument();
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
	
	private Publication parseContainerDocument() {
		Publication publication = new PublicationImpl();
		ContainerDocument document = ContainerDocument.create(readXmlDocument(AbstractContainer.CONTAINER_DOCUMENT_LOCATION));
		for (Element rootFile: document.getRootFiles()) {
			String fullPath = rootFile.getAttribute("full-path");
			String mediaType = rootFile.getAttribute("media-type");
		}
		return publication;
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
