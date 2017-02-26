package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Elements.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
	
	protected Publication createPublication() {
		return new PublicationImpl();
	}
	
	protected Publication parseContainerDocument() {
		Document document = readXmlDocument(AbstractContainer.CONTAINER_DOCUMENT_LOCATION);
		Element rootElement = document.getDocumentElement();
		ContainerDocumentParser parser = createContainerDocumentParser(rootElement);
		if (parser != null) {
			return parser.parse(rootElement);
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
			parser.parse(rootElement, rendition);
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
	
	/**
	 * Parser of EPUB Container Document version 1.0.
	 */
	private class ContainerDocumentParser1 implements ContainerDocumentParser {

		@Override
		public Publication parse(Element rootElement) {
			Element rootfiles = firstChildElement(rootElement, NAMESPACE_URI);
			if (rootfiles == null || !"rootfiles".equals(rootfiles.getLocalName())) {
				return null;
			}
			Publication publication = createPublication();
			for (Element rootfile: childElements(rootfiles, NAMESPACE_URI)) {
				if ("rootfile".equals(rootfile.getLocalName())) {
					parseRootFile(publication, rootfile);
				}
			}
			return publication;
		}
		
		protected void parseRootFile(Publication publication, Element rootfile) {
			String mediaType = rootfile.getAttribute("media-type");
			String expectedMediaType = StandardMediaType.APPLICATION_OEBPS_PACKAGE_XML.toString();
			if (expectedMediaType.equals(mediaType)) {
				String location = rootfile.getAttribute("full-path");
				if (location != null) {
					Rendition rendition = publication.addRendition(location);
					buildRendition(rendition);
				}
			}
		}
	}
	
	/**
	 * Parser of EPUB Package Document version 3.0.
	 */
	private class PackageDocumentParser3 implements PackageDocumentParser {

		@Override
		public void parse(Element rootElement, Rendition rendition) {
			List<Element> children = childElements(rootElement, NAMESPACE_URI);
			Iterator<Element> it = children.iterator();
			if (!it.hasNext()) {
				return;
			}
			Element element = it.next();
			if (matchElement(element, "metadata", NAMESPACE_URI)) {
				parseMetadata(element);
				if (!it.hasNext()) {
					return;
				}
				element = it.next();
			}
			if (matchElement(element, "manifest", NAMESPACE_URI)) {
				parseManifest(element);
				if (!it.hasNext()) {
					return;
				}
				element = it.next();
			}
			if (matchElement(element, "spine", NAMESPACE_URI)) {
				parseSpine(element);
				if (!it.hasNext()) {
					return;
				}
				element = it.next();
			}
		}
		
		protected void parseMetadata(Element element) {
		}

		protected void parseManifest(Element element) {
		}

		protected void parseSpine(Element element) {
		}
	}
}
