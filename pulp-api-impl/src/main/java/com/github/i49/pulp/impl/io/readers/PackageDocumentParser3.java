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

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.publication.EpubException;
import com.github.i49.pulp.api.publication.EpubService;
import com.github.i49.pulp.api.publication.Manifest;
import com.github.i49.pulp.api.publication.PublicationResource;
import com.github.i49.pulp.api.publication.Rendition;
import com.github.i49.pulp.api.publication.Spine.Page;
import com.github.i49.pulp.api.vocabularies.Vocabulary;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.io.containers.PrefixRegistry;
import com.github.i49.pulp.impl.xml.Nodes;

/**
 * Parser of EPUB Package Document version 3.0.
 */
class PackageDocumentParser3 implements PackageDocumentParser {
	
	private final PrefixRegistry prefixRegistry = new PrefixRegistry();
	/*
	 * Map object for mapping item ids to items.
	 */
	private final Map<String, Manifest.Item> items = new HashMap<>();
	
	private String uniqueIdentifier;
	
	private TermRegistry termRegistry;
	private Rendition rendition;
	private RenditionResourceFinder resourceFinder;
	
	@Override
	public void parse(
			Document document,
			Rendition rendition,
			EpubService service,
			RenditionResourceFinder resourceFinder) {
		
		this.termRegistry = service.getPropertyTermRegistry();
		this.rendition = rendition;
		this.resourceFinder = resourceFinder;
		
		parseRoot(document.getDocumentElement());
	}
	
	protected void parseRoot(Element rootElement) {

		assertOn(rootElement)
			.hasNonEmptyAttribute("unique-identifier")
			.contains("metadata", "manifest", "spine");
		
		parseRootAttributes(rootElement);
		
		Iterator<Element> it = Nodes.children(rootElement, NAMESPACE_URI);

		Element element = it.next();
		assertOn(element).hasName("metadata");
	
		MetadataParser metadataParser = createMetadataParser(rendition.getMetadata());
		metadataParser.parse(element);

		element = it.next();
		assertOn(element).hasName("manifest");
		parseManifest(element);

		element = it.next();
		assertOn(element).hasName("spine");
		parseSpine(element);
	}
	
	protected void parseRootAttributes(Element rootElement) {
		this.uniqueIdentifier = rootElement.getAttribute("unique-identifier").trim();
		
		if (rootElement.hasAttribute("prefix")) {
			String prefixes = rootElement.getAttribute("prefix").trim();
			parsePrefixes(prefixes);
		}
	}
	
	protected void parsePrefixes(String prefixes) {
		if (prefixes.isEmpty()) {
			return;
		}
		String[] parts = prefixes.split("\\s+");
		for (int i = 0; i + 1 < parts.length; i += 2) {
			String first = parts[i].trim();
			String second = parts[i + 1].trim();
			if (first.endsWith(":")) {
				String prefix = first.substring(0, first.length() - 1);
				URI uri = URI.create(second);
				if (uri != null) {
					addVocabulary(prefix, uri);
				}
			}
		}
	}
	
	protected void addVocabulary(String prefix, URI uri) {
		Vocabulary v = this.termRegistry.getVocabulary(uri);
		this.prefixRegistry.put(prefix , v);
	}
	
	protected MetadataParser createMetadataParser(Metadata metadata) {
		CurieParser curieParser= new CurieParser(this.prefixRegistry, this.termRegistry);
		return new MetadataParser3(metadata, this.uniqueIdentifier, curieParser);
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