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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.core.Spine.Page;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.StandardVocabulary;
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
	private Metadata metadata;

	private PropertyFactory propertyFactory;
	private RenditionResourceFinder resourceFinder;

	@Override
	public void parse(
			Document document,
			Rendition rendition,
			PropertyFactory propertyFactory,
			RenditionResourceFinder resourceFinder) {
		
		this.rendition = rendition;
		this.metadata = rendition.getMetadata();
		this.propertyFactory = propertyFactory;
		this.resourceFinder = resourceFinder;
		
		Element rootElement = document.getDocumentElement();
		assertOn(rootElement).contains("metadata", "manifest", "spine");
		
		Iterator<Element> it = Nodes.children(rootElement, NAMESPACE_URI);

		Element element = it.next();
		assertOn(element).hasName("metadata");
		parseMetadata(element);

		element = it.next();
		assertOn(element).hasName("manifest");
		parseManifest(element);

		element = it.next();
		assertOn(element).hasName("spine");
		parseSpine(element);
	}
	
	protected void parseMetadata(Element element) {
		List<MetadataEntry> entries = fetchMetadataEntries(element);
		for (MetadataEntry entry: entries) {
			Property p = null;
			if (entry.getVocabulary() == StandardVocabulary.DCMES) {
				p = parseDublinCoreProperty(entry);
			}
			if (p != null) {
				this.metadata.add(p);
			}
		}
	}
	
	protected List<MetadataEntry> fetchMetadataEntries(Element metadata) {
		Map<String, MetadataEntry> map = new HashMap<>();
		List<MetadataEntry> entries = new ArrayList<>();

		Iterator<Element> it = Nodes.children(metadata);
		while (it.hasNext()) {
			Element child = it.next();
			MetadataEntry entry = null;
			if (DC_NAMESPACE_URI.equals(child.getNamespaceURI())) {
				entry = new MetadataEntry(child, StandardVocabulary.DCMES);
			} else if (NAMESPACE_URI.equals(child.getNamespaceURI())) {
				entry = new MetadataEntry(child);
			}
			if (entry != null) {
				entries.add(entry);
				String id = child.getAttribute("id");
				if (id != null) {
					map.put(id, entry);
				}
			}
		}
		
		return entries;
	}

	protected Property parseDublinCoreProperty(MetadataEntry entry) {
		String localName = entry.getElement().getLocalName();
		switch (localName) {
		case "identifier":
			return parseIdentifier(entry);
		case "title":
			return parseTitle(entry);
		case "language":
			return parseLanguage(entry);
		}
		return null;
		//throw new EpubException("Unknown Dublin Core element.");
	}
	
	protected Property parseIdentifier(MetadataEntry entry) {
		Property p = propertyFactory.newIdentifier(entry.getValue());
		return p;
	}

	protected Property parseTitle(MetadataEntry entry) {
		Property p = propertyFactory.newTitle(entry.getValue());
		return p;
	}

	protected Property parseLanguage(MetadataEntry entry) {
		return propertyFactory.newLanguage(entry.getValue());
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