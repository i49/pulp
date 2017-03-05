package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Elements.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Spine;
import com.github.i49.pulp.api.Spine.Page;

/**
 * Parser of EPUB Package Document version 3.0.
 */
class PackageDocumentParser3 extends PackageDocumentParser {
	
	private final Map<String, Manifest.Item> items = new HashMap<>();

	@Override
	public void parse(Element rootElement) {
		List<Element> children = childElements(rootElement, NAMESPACE_URI);
		Iterator<Element> it = children.iterator();
		if (!it.hasNext()) {
			return;
		}
		Element element = it.next();
		if (matchElement(element, "metadata", NAMESPACE_URI)) {
			parseMetadata(element);
			if (!it.hasNext()) {
				return;
			}
			element = it.next();
		}
		if (matchElement(element, "manifest", NAMESPACE_URI)) {
			parseManifest(element);
			if (!it.hasNext()) {
				return;
			}
			element = it.next();
		}
		if (matchElement(element, "spine", NAMESPACE_URI)) {
			parseSpine(element);
			if (!it.hasNext()) {
				return;
			}
			element = it.next();
		}
	}
	
	protected void parseMetadata(Element element) {
	}

	protected void parseManifest(Element element) {
		PublicationResourceRegistry registry = rendition.getResourceRegistry();
		Manifest manifest = rendition.getManifest();
		for (Element child: childElements(element, NAMESPACE_URI)) {
			if (!child.getLocalName().equals("item")) {
				// TODO
				throw new EpubException("");
			}
			String id = getMandatoryAttribute(child, "id");
			String location = getMandatoryAttribute(child, "href");
			String mediaType = getMandatoryAttribute(child, "media-type");
			PublicationResource resource = registry.builder(location).ofType(mediaType).build();
			Manifest.Item item = manifest.add(resource);
			items.put(id, item);
		}
	}
	
	protected void parseSpine(Element element) {
		Spine spine = rendition.getSpine();
		for (Element child: childElements(element, NAMESPACE_URI)) {
			if (!child.getLocalName().equals("itemref")) {
				// TODO
				throw new EpubException("");
			}
			String idref = getMandatoryAttribute(child, "idref");
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
	
	private String getMandatoryAttribute(Element element, String name) {
		String value = element.getAttribute(name);
		if (value == null || value.isEmpty()) {
			// TODO
			throw new EpubException("");
		}
		return value;
	}
}