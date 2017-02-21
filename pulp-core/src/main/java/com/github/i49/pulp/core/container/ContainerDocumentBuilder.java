package com.github.i49.pulp.core.container;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

class ContainerDocumentBuilder {
	
	private static final String DEFAULT_NAMESPACE_URI = "urn:oasis:names:tc:opendocument:xmlns:container";
	private static final String PACKAGE_DOCUMENT_MEDIA_TYPE = "application/oebps-package+xml";
	
	private final DocumentBuilder documentBuilder;
	private Publication publication;
	
	private Document document;
	
	public ContainerDocumentBuilder(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
	}
	
	public ContainerDocumentBuilder publication(Publication publication) {
		this.publication = publication;
		return this;
	}
	
	public Document build() {
		document = documentBuilder.newDocument();
		addRootElement();
		return document;
	}
	
	private Element addRootElement() {
		Element container = document.createElementNS(DEFAULT_NAMESPACE_URI, "container");
		container.setAttribute("version", "1.0");
		document.appendChild(container);
		container.appendChild(addRootfiles());
		return container;
	}
	
	private Element addRootfiles() {
		Element rootfiles = document.createElementNS(DEFAULT_NAMESPACE_URI, "rootfiles");
		for (Rendition rendition: this.publication) {
			rootfiles.appendChild(addRootfile(rendition));
		}
		return rootfiles;
	}
	
	private Element addRootfile(Rendition rendition) {
		Element rootfile = document.createElementNS(DEFAULT_NAMESPACE_URI, "rootfile");
		rootfile.setAttribute("full-path", rendition.getLocation().getPath());
		rootfile.setAttribute("media-type", PACKAGE_DOCUMENT_MEDIA_TYPE);
		return rootfile;
	}
}
