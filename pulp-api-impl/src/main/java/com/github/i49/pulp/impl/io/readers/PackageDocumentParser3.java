/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.impl.io.readers;

import static com.github.i49.pulp.impl.xml.XmlAssertions.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.core.Spine.Page;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.xml.Nodes;

/**
 * Parser of EPUB Package Document version 3.0.
 */
class PackageDocumentParser3 implements PackageDocumentParser {
	
	/*
	 * Map object for mapping item ids to items.
	 */
	private final Map<String, Manifest.Item> items = new HashMap<>();
	
	private Rendition rendition;
	private RenditionResourceFinder resourceFinder;

	@Override
	public void parse(
			Document document,
			Rendition rendition,
			PropertyFactory propertyFactory,
			RenditionResourceFinder resourceFinder) {
		
		this.rendition = rendition;
		this.resourceFinder = resourceFinder;
		
		Element rootElement = document.getDocumentElement();
		assertOn(rootElement).contains("metadata", "manifest", "spine");
		
		Iterator<Element> it = Nodes.children(rootElement, NAMESPACE_URI);

		Element element = it.next();
		assertOn(element).hasName("metadata");
		createMetadataParser(rendition.getMetadata(), propertyFactory).parse(element);

		element = it.next();
		assertOn(element).hasName("manifest");
		parseManifest(element);

		element = it.next();
		assertOn(element).hasName("spine");
		parseSpine(element);
	}
	
	protected MetadataParser createMetadataParser(Metadata metadata, PropertyFactory factory) {
		return new MetadataParser3(metadata, factory);
	}
	
	protected void parseManifest(Element element) {
		Iterator<Element> it = Nodes.children(element, NAMESPACE_URI);
		while (it.hasNext()) {
			Element child = it.next();
			assertOn(child).hasName("item")
			               .hasNonEmptyAttribute("id")
			               .hasNonEmptyAttribute("href")
			               .hasNonEmptyAttribute("media-type");
			String id = child.getAttribute("id");
			String href = child.getAttribute("href");
			String mediaType = child.getAttribute("media-type");
			Manifest.Item item = addManifestItem(href, mediaType);
			if (child.hasAttribute("properties")) {
				addProperties(item, child.getAttribute("properties"));
			}
			registerItemWithMap(id, item);
		}
	}
	
	protected Manifest.Item addManifestItem(String href, String mediaType) {
		PublicationResource resource = this.resourceFinder.findResource(href, mediaType);
		return this.rendition.getManifest().add(resource);
	}
	
	protected void addProperties(Manifest.Item item, String properties) {
		for (String value: properties.split("\\s+")) {
			if (value.equals("cover-image")) {
				item.asCoverImage();
			} else if (value.equals("nav")) {
				item.asNavigation();
			} else if (value.equals("scripted")) {
				item.scripted(true);
			}
		}
	}
	
	protected void parseSpine(Element element) {
		Iterator<Element> it = Nodes.children(element, NAMESPACE_URI);
		while (it.hasNext()) {
			Element child = it.next();
			assertOn(child).hasName("itemref")
			               .hasNonEmptyAttribute("idref");
			String idref = child.getAttribute("idref");
			Manifest.Item item = items.get(idref);
			if (item == null) {
				throw new EpubException(Messages.MANIFEST_ITEM_ID_MISSING(idref));
			}
			Page page = this.rendition.getSpine().append(item);
			if ("no".equals(child.getAttribute("linear"))) {
				page.linear(false);
			}
		}
	}
	
	private void registerItemWithMap(String id, Manifest.Item item) {
		if (items.containsKey(id)) {
			throw new EpubException(Messages.MANIFEST_ITEM_ID_DUPLICATED(id));
		} else {
			items.put(id, item);
		}
	}
}