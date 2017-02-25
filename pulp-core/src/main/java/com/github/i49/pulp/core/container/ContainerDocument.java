package com.github.i49.pulp.core.container;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

public class ContainerDocument {

	public static final String DEFAULT_NAMESPACE_URI = "urn:oasis:names:tc:opendocument:xmlns:container";
	
	private final Document document;
	
	private ContainerDocument(Document document) {
		this.document = document;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public List<Element> getRootFiles() {
		List<Element> result = new ArrayList<>();
		Element container = document.getDocumentElement();
		NodeList children = container.getElementsByTagNameNS(DEFAULT_NAMESPACE_URI, "rootfiles");
		if (children.getLength() > 0) {
			Element rootfiles =  (Element)children.item(0);
			children = rootfiles.getElementsByTagNameNS(DEFAULT_NAMESPACE_URI, "rootfile");
			for (int i = 0; i < children.getLength(); i++) {
				result.add((Element)children.item(i));
			}
		}
		return result;
	}
	
	public static ContainerDocument create(Document document) {
		Element root = document.getDocumentElement();
		if (root.getNamespaceURI().equals(DEFAULT_NAMESPACE_URI) && root.getLocalName().equals("container")) {
			return new ContainerDocument(document);
		} else {
			return null;
		}
	}
	
	public static Builder builder(DocumentBuilder documentBuilder) {
		return new Builder(documentBuilder);
	}
	
	/**
	 * A builder class for building {@code ContainerDocument}.
	 */
	public static class Builder {

		private final DocumentBuilder documentBuilder;
		private Publication publication;
		private Document document;
		
		private Builder(DocumentBuilder documentBuilder) {
			this.documentBuilder = documentBuilder;
		}

		public ContainerDocument build(Publication publication) {
			this.publication = publication;
			this.document = this.documentBuilder.newDocument();
			this.document.appendChild(container());
			return new ContainerDocument(this.document);
		}
		
		/**
		 * Creates container element at the document root.
		 * @return created element.
		 */
		private Element container() {
			Element container = document.createElementNS(DEFAULT_NAMESPACE_URI, "container");
			container.setAttribute("version", "1.0");
			container.appendChild(rootfiles());
			return container;
		}
		
		/**
		 * Creates rootfiles element. 
		 * @return created element.
		 */
		private Element rootfiles() {
			Element rootfiles = document.createElementNS(DEFAULT_NAMESPACE_URI, "rootfiles");
			for (Rendition rendition: this.publication) {
				rootfiles.appendChild(rootfile(rendition));
			}
			return rootfiles;
		}
		
		/**
		 * Creates rootfile element. 
		 * @return created element.
		 */
		private Element rootfile(Rendition rendition) {
			Element rootfile = document.createElementNS(DEFAULT_NAMESPACE_URI, "rootfile");
			rootfile.setAttribute("full-path", rendition.getLocation().getPath());
			rootfile.setAttribute("media-type", PackageDocument.MEDIA_TYPE);
			return rootfile;
		}
	}
}
