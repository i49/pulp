package com.github.i49.pulp.core.container;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.StandardMediaType;

/**
 * A builder class for building {@code ContainerDocument}.
 */
public class ContainerDocumentBuilder implements ContainerDocumentProcessor {

	private static final String VERSION = "1.0";
	
	private final DocumentBuilder documentBuilder;
	private Publication publication;
	private Document document;
	
	ContainerDocumentBuilder(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
	}

	public Document build(Publication publication) {
		this.publication = publication;
		this.document = this.documentBuilder.newDocument();
		this.document.appendChild(container());
		return this.document;
	}
	
	/**
	 * Creates container element at the document root.
	 * @return created element.
	 */
	private Element container() {
		Element container = document.createElementNS(NAMESPACE_URI, "container");
		container.setAttribute("version", VERSION);
		container.appendChild(rootfiles());
		return container;
	}
	
	/**
	 * Creates rootfiles element. 
	 * @return created element.
	 */
	private Element rootfiles() {
		Element rootfiles = document.createElementNS(NAMESPACE_URI, "rootfiles");
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
		Element rootfile = document.createElementNS(NAMESPACE_URI, "rootfile");
		rootfile.setAttribute("full-path", rendition.getLocation().getPath());
		rootfile.setAttribute("media-type", StandardMediaType.APPLICATION_OEBPS_PACKAGE_XML.toString());
		return rootfile;
	}
}