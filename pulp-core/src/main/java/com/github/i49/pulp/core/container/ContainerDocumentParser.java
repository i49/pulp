package com.github.i49.pulp.core.container;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.Publication;

public interface ContainerDocumentParser extends ContainerDocumentProcessor {

	Publication parse(Element rootElement);
}
