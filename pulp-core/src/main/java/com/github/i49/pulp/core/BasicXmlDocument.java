package com.github.i49.pulp.core;

import java.net.URI;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.XmlDocument;

class BasicXmlDocument extends BasicPublicationResource implements XmlDocument {

	public BasicXmlDocument(URI location, CoreMediaType mediaType, XmlContent content) {
		super(location, mediaType, content);
	}

	@Override
	public Document getDocument() {
		return getContent().getDocument();
	}
	
	@Override
	protected XmlContent getContent() {
		return (XmlContent)super.getContent();
	}
}
