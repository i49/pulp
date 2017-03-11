package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.XmlAssertions.*;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.Spine;
import com.github.i49.pulp.api.Spine.Page;
import com.github.i49.pulp.core.xml.ElementIterator;

/**
 * Parser of EPUB Package Document version 3.0.
 */
class PackageDocumentParser3 extends PackageDocumentParser {
	
	private Rendition rendition;
	private final Map<String, Manifest.Item> items = new HashMap<>();

	public PackageDocumentParser3(Element rootElement) {
		super(rootElement);
	}

	@Override
	public void parseFor(Rendition rendition) {
		this.rendition = rendition;
		ElementIterator it = new ElementIterator(rootElement, NAMESPACE_URI);
		if (!it.hasNext()) {
			return;
		}
		Element element = it.next();
		assertOn(element).hasName("metadata", NAMESPACE_URI);
		parseMetadata(element);

		element = it.next();
		assertOn(element).hasName("manifest", NAMESPACE_URI);
		parseManifest(element);
		element = it.next();

		assertOn(element).hasName("spine", NAMESPACE_URI);
		parseSpine(element);
	}
	
	protected void parseMetadata(Element element) {
	}

	protected void parseManifest(Element element) {
		PublicationResourceRegistry registry = rendition.getResourceRegistry();
		Manifest manifest = rendition.getManifest();
		ElementIterator it = new ElementIterator(element, NAMESPACE_URI);
		while (it.hasNext()) {
			Element child = it.next();
			assertOn(child).hasName("item", NAMESPACE_URI)
			               .hasNonEmptyAttribute("id")
			               .hasNonEmptyAttribute("href")
			               .hasNonEmptyAttribute("media-type");
			String id = child.getAttribute("id");
			String location = child.getAttribute("href");
			String mediaType = child.getAttribute("media-type");
			PublicationResource resource = registry.builder(location).ofType(mediaType).build();
			Manifest.Item item = manifest.add(resource);
			items.put(id, item);
		}
	}
	
	protected void parseSpine(Element element) {
		Spine spine = rendition.getSpine();
		ElementIterator it = new ElementIterator(element, NAMESPACE_URI);
		while (it.hasNext()) {
			Element child = it.next();
			assertOn(child).hasName("itemref", NAMESPACE_URI)
			               .hasNonEmptyAttribute("idref");
			String idref = child.getAttribute("idref");
			Manifest.Item item = items.get(idref);
			if (item == null) {
				// TODO
				throw new EpubException("");
			}
			Page page = spine.append(item);
			if ("no".equals(child.getAttribute("linear"))) {
				page.linear(false);
			}
		}
	}
}