package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.XmlElementAssertion.assertThat;

import java.util.function.Consumer;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

abstract class ContainerDocumentParser implements ContainerDocumentProcessor {

	private Publication publication;
	private Consumer<Rendition> consumer;
	
	public void setPublication(Publication publication, Consumer<Rendition> consumer) {
		this.publication = publication;
		this.consumer = consumer;
	}
	
	public abstract void parse(Element rootElement);
	
	protected Rendition createRendition(String location) {
		return publication.addRendition(location);
	}
	
	protected void buildRendition(Rendition rendition) {
		consumer.accept(rendition);
	}
	
	public static ContainerDocumentParser create(Element rootElement) {
	
		assertThat(rootElement)
			.hasName("container", NAMESPACE_URI)
			.hasNonEmptyAttribute("version");

		ContainerDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("1.0".equals(version)) {
			parser = new ContainerDocumentParser1();
		} else {
			throw new EpubException(Messages.xmlDocumentVersionUnsupported(version));
		}
		return parser;
	}
}
