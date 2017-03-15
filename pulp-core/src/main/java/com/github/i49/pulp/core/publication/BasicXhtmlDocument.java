package com.github.i49.pulp.core.publication;

import java.net.URI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.XhtmlDocument;

class BasicXhtmlDocument extends BasicXmlDocument implements XhtmlDocument {

	public static final String NAMESPACE_URI = "http://www.w3.org/1999/xhtml";
	
	public BasicXhtmlDocument(URI location) {
		super(location, CoreMediaType.APPLICATION_XHTML_XML);
	}

	@Override
	public String getTitle() {
		String title = null;
		Document document = getDocument();
		if (document != null) {
			NodeList nodes = document.getElementsByTagNameNS(NAMESPACE_URI, "title");
			if (nodes.getLength() > 0) {
				Element element = (Element)nodes.item(0);
				title = element.getTextContent();
			}
		}
		return title;
	}
}
