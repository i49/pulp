package com.github.i49.pulp.core.container;

import org.w3c.dom.Document;

public class PackageDocument {

	public static final String MEDIA_TYPE = "application/oebps-package+xml";
	public static final String DEFAULT_NAMESPACE_URI = "http://www.idpf.org/2007/opf";
	public static final String DC_NAMESPACE_URI = "http://purl.org/dc/elements/1.1/";
	
	private final Document document;
	
	private PackageDocument(Document document) {
		this.document = document;
	}
	
	public Document getDocument() {
		return document;
	}
	
	private static PackageDocument create(Document document) {
		validate(document);
		return new PackageDocument(document);
	}
	
	private static void validate(Document document) {
	}
}
