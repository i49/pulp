package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.xml.XmlAssertions.*;

import java.util.Iterator;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.Messages;

abstract class ContainerDocumentParser implements ContainerDocumentProcessor {

	protected final Element rootElement;
	
	protected ContainerDocumentParser(Element rootElement) {
		this.rootElement = rootElement;
	}
	
	public abstract Iterator<Rendition> parseFor(Publication publication);
	
	public static ContainerDocumentParser create(Element rootElement) {
	
		assertOn(rootElement)
			.hasName("container", NAMESPACE_URI)
			.hasNonEmptyAttribute("version");

		ContainerDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("1.0".equals(version)) {
			parser = new ContainerDocumentParser1(rootElement);
		} else {
			throw new EpubException(Messages.XML_DOCUMENT_VERSION_UNSUPPORTED(version));
		}
		return parser;
	}
}
