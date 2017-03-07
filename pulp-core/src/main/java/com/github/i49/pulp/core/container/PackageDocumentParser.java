package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.XmlElementAssertion.assertThat;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
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
	public void setRendition(Rendition rendition) {
		this.rendition = rendition;
	}

	/**
	 * Parses a package document.
	 * @param rootElement the root element of the package document.
	 */
	public abstract void parse(Element rootElement);
	
	public static PackageDocumentParser create(Element rootElement) {
		
		assertThat(rootElement)
			.hasName("package", NAMESPACE_URI)
			.hasNonEmptyAttribute("version");

		PackageDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("3.0".equals(version)) {
			parser = new PackageDocumentParser3();
		} else {
			throw new EpubException(Messages.xmlDocumentVersionUnsupported(version));
		}
		return parser;
	}
}
