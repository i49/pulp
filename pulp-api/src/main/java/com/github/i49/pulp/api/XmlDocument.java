package com.github.i49.pulp.api;

import org.w3c.dom.Document;

/**
 * A publication resource which content is an XML document.
 */
public interface XmlDocument extends PublicationResource {

	/**
	 * Returns the content of this resource as an XML document.
	 * @return an XML document.
	 */
	Document getDocument();
	
	/**
	 * Assigns an XML document to this resource.
	 * @param document the XML document to assign to this resource.
	 */
	void setDocument(Document document);
}
