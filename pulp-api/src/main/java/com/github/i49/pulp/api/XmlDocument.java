package com.github.i49.pulp.api;

import org.w3c.dom.Document;

/**
 * A publication resource that provides its contents as a XML document.
 */
public interface XmlDocument extends PublicationResource {

	/**
	 * Returns the contents of this resource as a XML document.
	 * @return a XML document.
	 */
	Document getDocument();
}
