package com.github.i49.pulp.core;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;

/**
 * A builder class to build a document carrying bibliographical and structural metadata 
 * about a given publication.
 */
class PackageDocumentBuilder {

	private static final String DEFAULT_NAMESPACE_URI = "http://www.idpf.org/2007/opf";
	private static final String DC_NAMESPACE_URI = "http://purl.org/dc/elements/1.1/";
	
	private static final String VERSION = "3.0";
	private static final String UNIQUE_IDENTIFIER = "publication-id";
	private static final String ID_PREFIX = "item";
	private static final String DEFAULT_TITLE = "unknown title";
	
	private static final DateTimeFormatter ISO8601_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	private Document document;
	private Publication publication;
	private final OffsetDateTime now;
	
	private Map<String, String> identifiers = new HashMap<>();
	private int nextNumber;
	
	/**
	 * Construct this builder.
	 * @param document the XML document of the package document.
	 * @param publication the publication for which a package document will be built.
	 */
	PackageDocumentBuilder(Document document, Publication publication) {
		this.document = document;
		this.publication = publication;
		this.now = OffsetDateTime.now();
		this.nextNumber = 1;
	}
	
	/**
	 * Builds the package document.
	 * @return built package document.
	 */
	Document build() {
		this.document.appendChild(root());
		return this.document;
	}
	
	/**
	 * Creates a package element at the root of the document.
	 * @return created element.
	 */
	private Element root() {
		Element root = document.createElementNS(DEFAULT_NAMESPACE_URI, "package");
		root.setAttribute("version", VERSION);
		root.setAttribute("unique-identifier", UNIQUE_IDENTIFIER);
	
		root.appendChild(metadata());
		root.appendChild(manifest());
		root.appendChild(spine());
		
		return root;
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
		Metadata meta = publication.getMetadata();

		Element e = document.createElementNS(DEFAULT_NAMESPACE_URI, "metadata");
		e.setAttribute("xmlns:dc", DC_NAMESPACE_URI);

		addIdentifier(e, meta);
		addTitles(e, meta);
		addLanguages(e, meta);
		addCreators(e, meta);
		addPublishers(e, meta);
		addDate(e, meta);
		addLastModified(e, meta);

		return e;
	}
	
	private void addIdentifier(Element parent, Metadata metadata) {
		String value = metadata.getIdentifier();
		if (value == null) {
			value = generateIdentifier();
		}
		Element child = createMetadata("dc:identifier", value);
		child.setAttribute("id", UNIQUE_IDENTIFIER);
		parent.appendChild(child);
	}
	
	private void addTitles(Element parent, Metadata metadata) {
		if (metadata.getTitles().isEmpty()) {
			Element child = createMetadata("dc:title", DEFAULT_TITLE);
			parent.appendChild(child);
		} else {
			for (String value: metadata.getTitles()) {
				Element child = createMetadata("dc:title", value);
				parent.appendChild(child);
			}
		}
	}

	private void addLanguages(Element parent, Metadata metadata) {
		if (metadata.getLanguages().isEmpty()) {
			Locale value = Locale.getDefault();
			Element child = createMetadata("dc:language", value.toLanguageTag());
			parent.appendChild(child);
		} else {
			for (Locale value: metadata.getLanguages()) {
				Element child = createMetadata("dc:language", value.toLanguageTag());
				parent.appendChild(child);
			}
		}
	}
	
	private void addCreators(Element parent, Metadata metadata) {
		for (String value: metadata.getCreators()) {
			Element child = createMetadata("dc:creator", value);
			parent.appendChild(child);
		}
	}

	private void addPublishers(Element parent, Metadata metadata) {
		for (String value: metadata.getPublishers()) {
			Element child = createMetadata("dc:publisher", value);
			parent.appendChild(child);
		}
	}
	
	private void addDate(Element parent, Metadata metadata) {
		OffsetDateTime dateTime = metadata.getDate();
		if (dateTime != null) {
			Element child = createMetadata("dc:date", formatDateTime(dateTime));
			parent.appendChild(child);
		}
	}

	private void addLastModified(Element parent, Metadata metadata) {
		OffsetDateTime dateTime = metadata.getLastModified();
		if (dateTime == null) {
			dateTime = now;
		}
		Element child = createMetadata("meta", formatDateTime(dateTime));
		child.setAttribute("property", "dcterms:modified");
		parent.appendChild(child);
	}
	
	private Element createMetadata(String name, String value) {
		Element e = document.createElement(name);
		e.appendChild(document.createTextNode(value));
		return e;
	}
	
	private static String formatDateTime(OffsetDateTime dateTime) {
		OffsetDateTime utc = OffsetDateTime.ofInstant(dateTime.toInstant(), ZoneOffset.UTC);
		return utc.format(ISO8601_FORMATTER);
	}
	
	/**
	 * Creates a manifest element.
	 * @return created manifest element.
	 */
	private Element manifest() {
		Element manifest = document.createElementNS(DEFAULT_NAMESPACE_URI, "manifest");
		for (PublicationResource resource: publication.getAllResources()) {
			manifest.appendChild(item(resource));
		}
		return manifest;
	}
	
	/**
	 * Creates an item element in manifest. 
	 * @param resource the resource for which an item will be created.
	 * @return created item element.
	 */
	private Element item(PublicationResource resource) {
		String id = nextItemId();
		this.identifiers.put(resource.getName(), id);
		
		Element item = document.createElementNS(DEFAULT_NAMESPACE_URI, "item");
		item.setAttribute("id", id);
		item.setAttribute("href", resource.getName().toString());
		item.setAttribute("media-type", resource.getMediaType().toString());
		
		String properties = itemProperties(resource);
		if (properties != null) {
			item.setAttribute("properties", properties);
		}

		return item;
	}
	
	private String itemProperties(PublicationResource resource) {
		List<String> properties = new ArrayList<>();
		if (resource == this.publication.getCoverImage()) {
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
		Element spine = document.createElementNS(DEFAULT_NAMESPACE_URI, "spine");
		for (String name: publication.getContentList()) {
			PublicationResource resource = publication.getResourceByName(name);
			if (resource != null) {
				spine.appendChild(itemref(resource));
			}
		}
		return spine;
	}
	
	/**
	 * Creates an itemref element in spine. 
	 * @param resource the resource to be added to the spine.
	 * @return created itemref element.
	 */
	private Element itemref(PublicationResource resource) {
		Element itemref = document.createElementNS(DEFAULT_NAMESPACE_URI, "itemref");
		String idref = this.identifiers.get(resource.getName());
		itemref.setAttribute("idref", idref);
		if (!resource.isPrimary()) {
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
	
	private static String generateIdentifier() {
		UUID uuid = UUID.randomUUID();
		return "urn:uuid:" + uuid.toString();
	}
}
