package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Message.*;
import static com.github.i49.pulp.core.container.XmlAssertions.*;

import java.util.HashMap;
import java.util.Iterator;
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
import com.github.i49.pulp.core.xml.NominalElement;

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
		Iterator<Element> it = ElementIterator.of(rootElement, NAMESPACE_URI);
	
		if (!it.hasNext()) {
			throw new EpubException(XML_ELEMENT_MISSING.format(new NominalElement("metadata", NAMESPACE_URI)));
		}
		Element element = it.next();
		assertOn(element).hasName("metadata", NAMESPACE_URI);
		parseMetadata(element);

		if (!it.hasNext()) {
			throw new EpubException(XML_ELEMENT_MISSING.format(new NominalElement("manifest", NAMESPACE_URI)));
		}
		element = it.next();
		assertOn(element).hasName("manifest", NAMESPACE_URI);
		parseManifest(element);

		if (!it.hasNext()) {
			throw new EpubException(XML_ELEMENT_MISSING.format(new NominalElement("spine", NAMESPACE_URI)));
		}
		element = it.next();
		assertOn(element).hasName("spine", NAMESPACE_URI);
		parseSpine(element);
	}
	
	protected void parseMetadata(Element element) {
	}

	protected void parseManifest(Element element) {
		PublicationResourceRegistry registry = rendition.getResourceRegistry();
		Manifest manifest = rendition.getManifest();
		Iterator<Element> it = ElementIterator.of(element, NAMESPACE_URI);
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
			if (child.hasAttribute("properties")) {
				addProperties(item, child.getAttribute("properties"));
			}
			items.put(id, item);
		}
	}
	
	protected void addProperties(Manifest.Item item, String properties) {
		for (String value: properties.split("\\s+")) {
			if (value.equals("cover-image")) {
				item.asCoverImage();
			} else if (value.equals("nav")) {
			} else if (value.equals("scripted")) {
				item.scripted(true);
			}
		}
	}
	
	protected void parseSpine(Element element) {
		Spine spine = rendition.getSpine();
		Iterator<Element> it = ElementIterator.of(element, NAMESPACE_URI);
		while (it.hasNext()) {
			Element child = it.next();
			assertOn(child).hasName("itemref", NAMESPACE_URI)
			               .hasNonEmptyAttribute("idref");
			String idref = child.getAttribute("idref");
			Manifest.Item item = items.get(idref);
			if (item == null) {
				throw new EpubException(MANIFEST_ITEM_NOT_FOUND.format(idref));
			}
			Page page = spine.append(item);
			if ("no".equals(child.getAttribute("linear"))) {
				page.linear(false);
			}
		}
	}
}