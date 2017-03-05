package com.github.i49.pulp.core.container;

import java.util.function.Consumer;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

abstract class ContainerDocumentParser implements ContainerDocumentProcessor {

	private Publication publication;
	private Consumer<Rendition> consumer;
	
	void setPublication(Publication publication, Consumer<Rendition> consumer) {
		this.publication = publication;
		this.consumer = consumer;
	}
	
	abstract void parse(Element rootElement);
	
	protected Rendition createRendition(String location) {
		return publication.addRendition(location);
	}
	
	protected void buildRendition(Rendition rendition) {
		consumer.accept(rendition);
	}
}
