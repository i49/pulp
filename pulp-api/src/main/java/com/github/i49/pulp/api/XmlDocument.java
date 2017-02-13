package com.github.i49.pulp.api;

import org.w3c.dom.Document;

/**
 * A publication resource which content is an XML document.
 */
public interface XmlDocument extends PublicationResource {

	/**
	 * Returns an XML document as the content of this resource.
	 * @return an XML document.
	 */
	Document getDocument();
}
