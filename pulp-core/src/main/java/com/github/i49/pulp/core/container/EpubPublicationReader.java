package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Elements.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.XmlServices;

public class EpubPublicationReader implements PublicationReader {

	private final ReadableContainer container;
	private final Supplier<Publication> supplier;
	private final DocumentBuilder documentBuilder;
	
	public EpubPublicationReader(ReadableContainer loader, Supplier<Publication> supplier) {
		this.container = loader;
		this.supplier = supplier;
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
	
	protected Publication parseContainerDocument() {
		Document document = readXmlDocument(AbstractContainer.CONTAINER_DOCUMENT_LOCATION);
		Element rootElement = document.getDocumentElement();
		ContainerDocumentParser parser = createContainerDocumentParser(rootElement);
		if (parser != null) {
			Publication publication = supplier.get();
			parser.setPublication(publication, this::buildRendition);
			parser.parse(rootElement);
			return publication;
		} else {
			return null;
		}
	}
	
	protected ContainerDocumentParser createContainerDocumentParser(Element rootElement) {
		if (!matchElement(rootElement, "container" , ContainerDocumentParser.NAMESPACE_URI)) {
			return null;
		}
		ContainerDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("1.0".equals(version)) {
			parser = new ContainerDocumentParser1();
		}
		return parser;
	}
	
	protected void buildRendition(Rendition rendition) {
		Document document = readXmlDocument(rendition.getLocation().getPath());
		Element rootElement = document.getDocumentElement();
		PackageDocumentParser parser = createPackageDocumentParser(rootElement);
		if (parser != null) {
			parser.setRendition(rendition);
			parser.parse(rootElement);
		}
	}
	
	protected PackageDocumentParser createPackageDocumentParser(Element rootElement) {
		if (!matchElement(rootElement, "package" , PackageDocumentParser.NAMESPACE_URI)) {
			return null;
		}
		PackageDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("3.0".equals(version)) {
			parser = new PackageDocumentParser3();
		}
		return parser;
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
