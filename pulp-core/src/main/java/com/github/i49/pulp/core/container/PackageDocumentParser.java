package com.github.i49.pulp.core.container;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.Rendition;

/**
 * Parser interface for parsing a package document.
 */
public interface PackageDocumentParser extends PackageDocumentProcessor {

	/**
	 * Parses a package document.
	 * @param rootElement the root element of the package document.
	 * @param rendition the rendition to be built.
	 */
	void parse(Element rootElement, Rendition rendition);
}
