package com.github.i49.pulp.core.container;

public enum Vocabulary {
	
	DUBLIN_CORE("http://purl.org/dc/elements/1.1/"),
	CONTAINER_DOCUMENT("urn:oasis:names:tc:opendocument:xmlns:container"),
	PACKAGE_DOCUMENT("http://www.idpf.org/2007/opf")
	;
	
	private final String uri;

	private Vocabulary(String uri) {
		this.uri = uri;
	}
	
	public String getNamespaceURI() {
		return uri;
	}
}
