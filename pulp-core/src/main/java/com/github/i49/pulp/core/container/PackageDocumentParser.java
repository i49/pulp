package com.github.i49.pulp.core.container;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.Rendition;

/**
 * Parser interface for parsing a package document.
 */
abstract class PackageDocumentParser implements PackageDocumentProcessor {
	
	protected Rendition rendition;
	
	/**
	 * Assigns the rendition to build while parsing the document.
	 * @param rendition the rendition to be built.
	 */
	void setRendition(Rendition rendition) {
		this.rendition = rendition;
	}

	/**
	 * Parses a package document.
	 * @param rootElement the root element of the package document.
	 */
	abstract void parse(Element rootElement);
}
