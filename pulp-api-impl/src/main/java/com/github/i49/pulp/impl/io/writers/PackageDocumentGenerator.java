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

package com.github.i49.pulp.impl.io.writers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.core.Spine;
import com.github.i49.pulp.api.metadata.DublinCore;
import com.github.i49.pulp.api.metadata.DublinCoreTerm;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.impl.io.containers.PackageDocumentProcessor;

/**
 * A generator class to generate a document which carries bibliographical 
 * and structural metadata about a given rendition.
 */
class PackageDocumentGenerator implements PackageDocumentProcessor {

	private static final String VERSION = "3.0";
	private static final String UNIQUE_IDENTIFIER = "pub-id";
	private static final String ID_PREFIX = "item";
	
	private final DocumentBuilder documentBuilder;
	private Rendition rendition;
	private URI packageBase;

	private Document document;
	private Map<Manifest.Item, String> itemIds = new HashMap<>();
	private int nextNumber;
	
	/**
	 * Construct this generator.
	 */
	PackageDocumentGenerator(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
		this.nextNumber = 1;
	}
	
	/**
	 * Generates a package document.
	 * 
	 * @param rendition the rendition to be written.
	 * @return generated package document.
	 */
	Document generateDocument(Rendition rendition) {
		
		this.rendition = rendition;
		this.packageBase = rendition.getLocation().resolve(".");

		document = this.documentBuilder.newDocument();

		Element root = document.createElementNS(NAMESPACE_URI, "package");
		root.setAttribute("version", VERSION);
		root.setAttribute("unique-identifier", UNIQUE_IDENTIFIER);
	
		root.appendChild(metadata());
		root.appendChild(manifest());
		root.appendChild(spine());
		
		document.appendChild(root);
		return document;
	}
	
	/**
	 * Creates a metadata element.
	 * <p>The following metadata must be included.</p>
	 * <ul>
	 * <li>identifier</li>
	 * <li>title</li>
	 * <li>language</li>
	 * </ul>
	 * @return created metadata element.
	 */
	private Element metadata() {
		Metadata meta = rendition.getMetadata();

		Element e = document.createElementNS(NAMESPACE_URI, "metadata");
		e.setAttribute("xmlns:dc", DC_NAMESPACE_URI);

		addIdentifiers(e, meta);
		addTitles(e, meta);
		addLanguages(e, meta);
		addCreators(e, meta);
		addContributors(e, meta);
		addPublishers(e, meta);
		addDate(e, meta);
		addLastModified(e, meta);

		return e;
	}
	
	private void addIdentifiers(Element parent, Metadata metadata) {
		boolean first = true;
		for (Property p: metadata.getList(DublinCore.IDENTIFIER)) {
			Element e = createMetadataEntry("dc:identifier", p.getValueAsString());
			if (first) {
				first = false;
				e.setAttribute("id", UNIQUE_IDENTIFIER);
				parent.appendChild(e);
			}
		}
	}
	
	private void addTitles(Element parent, Metadata metadata) {
		for (Property p: metadata.getList(DublinCore.TITLE)) {
			Element e = createMetadataEntry("dc:title", p.getValueAsString());
			parent.appendChild(e);
		}
	}

	private void addLanguages(Element parent, Metadata metadata) {
		for (Property p: metadata.getList(DublinCore.LANGUAGE)) {
			Element e = createMetadataEntry("dc:language", p.getValueAsString());
			parent.appendChild(e);
		}
	}
	
	private void addCreators(Element parent, Metadata metadata) {
		for (Property p: metadata.getList(DublinCore.CREATOR)) {
			Element e = createMetadataEntry("dc:creator", p.getValueAsString());
			parent.appendChild(e);
		}
	}

	private void addContributors(Element parent, Metadata metadata) {
		for (Property p: metadata.getList(DublinCore.CONTRIBUTOR)) {
			Element e = createMetadataEntry("dc:contributor", p.getValueAsString());
			parent.appendChild(e);
		}
	}
	
	private void addPublishers(Element parent, Metadata metadata) {
		for (Property p: metadata.getList(DublinCore.PUBLISHER)) {
			Element e = createMetadataEntry("dc:publisher", p.getValueAsString());
			parent.appendChild(e);
		}
	}
	
	private void addDate(Element parent, Metadata metadata) {
		if (metadata.contains(DublinCore.DATE)) {
			Property p = metadata.get(DublinCore.DATE);
			Element e = createMetadataEntry("dc:date", p.getValueAsString());
			parent.appendChild(e);
		}
	}

	private void addLastModified(Element parent, Metadata metadata) {
		Property p = metadata.get(DublinCoreTerm.MODIFIED);
		Element e = createMetadataEntry("meta", p.getValueAsString());
		e.setAttribute("property", "dcterms:modified");
		parent.appendChild(e);
	}
	
	private Element createMetadataEntry(String name, String value) {
		Element e = document.createElement(name);
		e.appendChild(document.createTextNode(value));
		return e;
	}
	
	/**
	 * Creates a manifest element.
	 * @return created manifest element.
	 */
	private Element manifest() {
		Element manifest = document.createElementNS(NAMESPACE_URI, "manifest");
		for (Manifest.Item item: sortItems(rendition.getManifest())) {
			manifest.appendChild(item(item));
		}
		return manifest;
	}
	
	private Iterable<Manifest.Item> sortItems(Manifest manifest) {
		List<Manifest.Item> items = new ArrayList<>();
		for (Manifest.Item item: manifest) {
			items.add(item);
		}
		Collections.sort(items, (x, y)->x.getLocation().compareTo(y.getLocation()));
		return items;
	}
	
	/**
	 * Creates an item element in manifest. 
	 * @param resource the resource for which an item will be created.
	 * @return created item element.
	 */
	private Element item(Manifest.Item item) {
		String id = nextItemId();
		this.itemIds.put(item, id);
		
		URI href = this.packageBase.relativize(item.getLocation()); 
		
		Element element = document.createElementNS(NAMESPACE_URI, "item");
		element.setAttribute("id", id);
		element.setAttribute("href", href.toString());
		element.setAttribute("media-type", item.getResource().getMediaType().toString());
		
		String properties = itemProperties(item);
		if (properties != null) {
			element.setAttribute("properties", properties);
		}

		return element;
	}
	
	private String itemProperties(Manifest.Item item) {
		List<String> properties = new ArrayList<>();
		if (item.isCoverImage()) {
			properties.add("cover-image");
		}
		if (properties.isEmpty()) {
			return null;
		}
		return properties.stream().collect(Collectors.joining(" "));
	}

	/**
	 * Creates a spine element.
	 * @return created spine element.
	 */
	private Element spine() {
		Element spine = document.createElementNS(NAMESPACE_URI, "spine");
		for (Spine.Page page: rendition.getSpine()) {
			spine.appendChild(itemref(page));
		}
		return spine;
	}
	
	/**
	 * Creates an itemref element in spine. 
	 * @param page the page to be added to the spine.
	 * @return created itemref element.
	 */
	private Element itemref(Spine.Page page) {
		Element itemref = document.createElementNS(NAMESPACE_URI, "itemref");
		String idref = this.itemIds.get(page.getItem());
		itemref.setAttribute("idref", idref);
		if (!page.isLinear()) {
			itemref.setAttribute("linear", "no");
		}
		return itemref;
	}
	
	/**
	 * Generates a next id.
	 * @return generated id.
	 */
	private String nextItemId() {
		return ID_PREFIX + this.nextNumber++;		
	}
}
