package com.github.i49.pulp.core;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.XmlDocument;

class BasicXmlDocument extends BasicPublicationResource implements XmlDocument {

	public BasicXmlDocument(String name, CoreMediaType mediaType, XmlContent content) {
		super(name, mediaType, content);
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
