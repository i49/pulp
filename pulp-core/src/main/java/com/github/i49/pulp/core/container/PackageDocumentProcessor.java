package com.github.i49.pulp.core.container;

/**
 * Base type for builder and parser. 
 */

public interface PackageDocumentProcessor {

	static final String NAMESPACE_URI = Vocabulary.PACKAGE_DOCUMENT.getNamespaceURI();
	static final String DC_NAMESPACE_URI = Vocabulary.DUBLIN_CORE.getNamespaceURI();
}
