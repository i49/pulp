package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Elements.childElements;
import static com.github.i49.pulp.core.container.Elements.firstChildElement;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.StandardMediaType;

/**
 * Parser of EPUB Container Document version 1.0.
 */
class ContainerDocumentParser1 extends ContainerDocumentParser {

	@Override
	public void parse(Element rootElement) {
		Element rootfiles = firstChildElement(rootElement, NAMESPACE_URI);
		if (rootfiles == null || !"rootfiles".equals(rootfiles.getLocalName())) {
			return;
		}
		for (Element rootfile: childElements(rootfiles, NAMESPACE_URI)) {
			if ("rootfile".equals(rootfile.getLocalName())) {
				parseRootFile(rootfile);
			}
		}
	}
	
	protected void parseRootFile(Element rootfile) {
		String mediaType = rootfile.getAttribute("media-type");
		String expectedMediaType = StandardMediaType.APPLICATION_OEBPS_PACKAGE_XML.toString();
		if (expectedMediaType.equals(mediaType)) {
			String location = rootfile.getAttribute("full-path");
			if (location != null) {
				Rendition rendition = createRendition(location);
				buildRendition(rendition);
			}
		}
	}
}