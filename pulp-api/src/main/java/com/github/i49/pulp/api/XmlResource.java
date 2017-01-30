package com.github.i49.pulp.api;

import org.w3c.dom.Document;

/**
 * A publication resource that provides XML document.
 */
public interface XmlResource extends PublicationResource {

	/**
	 * Returns a XML document representing this resource.
	 * @return a XML document.
	 */
	Document getDocument();
}
