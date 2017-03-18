package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.xml.XmlAssertions.*;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.Messages;

/**
 * Parser interface for parsing a package document.
 */
abstract class PackageDocumentParser implements PackageDocumentProcessor {
	
	protected final Element rootElement;
	
	protected PackageDocumentParser(Element rootElement) {
		this.rootElement = rootElement;
	}

	/**
	 * Parses a package document.
	 */
	public abstract void parseFor(Rendition rendition, PublicationResourceBuilderFactory builderFactory);
	
	/**
	 * Creates a new parser.
	 * @param rootElement the root element of the document.
	 * @return created parser.
	 */
	public static PackageDocumentParser create(Element rootElement) {
		
		assertOn(rootElement)
			.hasName("package", NAMESPACE_URI)
			.hasNonEmptyAttribute("version");

		PackageDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("3.0".equals(version)) {
			parser = new PackageDocumentParser3(rootElement);
		} else {
			throw new EpubException(Messages.XML_DOCUMENT_VERSION_UNSUPPORTED(version));
		}
		return parser;
	}
}
