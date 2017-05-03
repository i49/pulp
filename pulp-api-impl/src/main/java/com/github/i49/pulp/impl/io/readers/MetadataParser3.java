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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.metadata.Contributor;
import com.github.i49.pulp.api.metadata.Creator;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.Publisher;
import com.github.i49.pulp.api.metadata.Relator;
import com.github.i49.pulp.api.metadata.StandardVocabulary;
import com.github.i49.pulp.impl.xml.Nodes;

/**
 * Parser for the metadata defined in EPUB specification 3.0
 */
class MetadataParser3 implements MetadataParser {
	
	private final Metadata metadata;
	private final PropertyFactory factory;
	
	MetadataParser3(Metadata metadata, PropertyFactory factory) {
		this.metadata = metadata;
		this.factory = factory;
	}
	
	@Override
	public void parse(Element element) {
		List<MetadataEntry> entries = fetchMetadataEntries(element);
		for (MetadataEntry entry: entries) {
			Property p = null;
			if (entry.getVocabulary() == StandardVocabulary.DCMES) {
				p = parseDublinCoreElement(entry);
			} else {
				p = parseMetaElement(entry);
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
				String localName = child.getLocalName();
				if ("meta".equals(localName)) {
					if (child.hasAttribute("refines")) {
					} else {
						entry = new MetadataEntry(child);
					}
				}
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
	
	protected Property parseDublinCoreElement(MetadataEntry entry) {
		String localName = entry.getElement().getLocalName();
		switch (localName) {
		case "contributor":
			return parseContributor(entry);
		case "coverage":
			return parseCoverage(entry);
		case "creator":
			return parseCreator(entry);
		case "date":
			return parseDate(entry);
		case "description":
			return parseDescription(entry);
		case "format":
			return parseFormat(entry);
		case "identifier":
			return parseIdentifier(entry);
		case "language":
			return parseLanguage(entry);
		case "publisher":
			return parsePublisher(entry);
		case "relation":
			return parseRelation(entry);
		case "rights":
			return parseRights(entry);
		case "source":
			return parseSource(entry);
		case "subject":
			return parseSubject(entry);
		case "title":
			return parseTitle(entry);
		case "type":
			return parseType(entry);
		}
		throw new EpubException("Unknown Dublin Core element.");
	}
	
	protected Property parseMetaElement(MetadataEntry entry) {
		String name = entry.getElement().getAttribute("property");
		String[] parts = name.split(":");
		String prefix = null;
		String localName = name;
		if (parts.length >= 2) {
			prefix = parts[0];
			localName = parts[1];
		}
		if ("dcterms".equals(prefix)) {
			if ("modified".equals(localName)) {
				return parseModified(entry);
			}
		}
		return null;
	}
	
	protected Property parseContributor(MetadataEntry entry) {
		Relator.Builder<Contributor> b = factory.getContributorBuilder(entry.getValue());
		return b.build();
	}

	protected Property parseCoverage(MetadataEntry entry) {
		Property p = factory.newCoverage(entry.getValue());
		return p;
	}

	protected Property parseCreator(MetadataEntry entry) {
		Relator.Builder<Creator> b = factory.getCreatorBuilder(entry.getValue());
		return b.build();
	}

	protected Property parseDate(MetadataEntry entry) {
		OffsetDateTime dateTime = convertDateTime(entry.getValue());
		return factory.newDate(dateTime);
	}

	protected Property parseDescription(MetadataEntry entry) {
		Property p = factory.newDescription(entry.getValue());
		return p;
	}

	protected Property parseFormat(MetadataEntry entry) {
		Property p = factory.newFormat(entry.getValue());
		return p;
	}
	
	protected Property parseIdentifier(MetadataEntry entry) {
		Property p = factory.newIdentifier(entry.getValue());
		return p;
	}

	protected Property parseLanguage(MetadataEntry entry) {
		return factory.newLanguage(entry.getValue());
	}

	protected Property parsePublisher(MetadataEntry entry) {
		Relator.Builder<Publisher> b = factory.getPublisherBuilder(entry.getValue());
		return b.build();
	}

	protected Property parseRelation(MetadataEntry entry) {
		Property p = factory.newRelation(entry.getValue());
		return p;
	}

	protected Property parseRights(MetadataEntry entry) {
		Property p = factory.newRights(entry.getValue());
		return p;
	}

	protected Property parseSource(MetadataEntry entry) {
		Property p = factory.newSource(entry.getValue());
		return p;
	}

	protected Property parseSubject(MetadataEntry entry) {
		Property p = factory.newSubject(entry.getValue());
		return p;
	}
	
	protected Property parseTitle(MetadataEntry entry) {
		Property p = factory.newTitle(entry.getValue());
		return p;
	}

	protected Property parseType(MetadataEntry entry) {
		Property p = factory.newType(entry.getValue());
		return p;
	}
	
	protected Property parseModified(MetadataEntry entry) {
		OffsetDateTime dateTime = convertDateTime(entry.getValue());
		return factory.newModified(dateTime);
	}
	
	private static OffsetDateTime convertDateTime(String value) {
		OffsetDateTime dateTime = null;
		try {
			dateTime = OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		} catch (DateTimeParseException e) {
			LocalDate date = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
			LocalTime time = LocalTime.of(0, 0);
			dateTime = OffsetDateTime.of(date, time, ZoneOffset.UTC);
		}
		return dateTime;
	}
}