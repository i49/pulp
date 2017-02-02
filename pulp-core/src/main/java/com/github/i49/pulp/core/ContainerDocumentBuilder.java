package com.github.i49.pulp.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class ContainerDocumentBuilder {
	
	private static final String DEFAULT_NAMESPACE_URI = "urn:oasis:names:tc:opendocument:xmlns:container";
	
	private final Document documemt;
	
	ContainerDocumentBuilder(Document document) {
		this.documemt = document;
	}
	
	Document build(String packageDir) {
		Element root = documemt.createElementNS(DEFAULT_NAMESPACE_URI, "container");
		root.setAttribute("version", "1.0");
		documemt.appendChild(root);
		
		Element rootfiles = documemt.createElementNS(DEFAULT_NAMESPACE_URI, "rootfiles");
		root.appendChild(rootfiles);
		
		Element rootfile = documemt.createElementNS(DEFAULT_NAMESPACE_URI, "rootfile");
		rootfile.setAttribute("full-path", packageDir + "package.opf");
		rootfile.setAttribute("media-type", "application/oebps-package+xml");
		rootfiles.appendChild(rootfile);
		
		return documemt;
	}
}
