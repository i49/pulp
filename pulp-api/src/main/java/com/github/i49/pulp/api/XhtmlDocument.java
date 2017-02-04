package com.github.i49.pulp.api;

/**
 * A publication resource that provides its contents as a XHTML document.
 */
public interface XhtmlDocument extends XmlDocument {
	
	/**
	 * Returns the title of this document.
	 * @return the title of this document.
	 */
	String getTitle();
}
