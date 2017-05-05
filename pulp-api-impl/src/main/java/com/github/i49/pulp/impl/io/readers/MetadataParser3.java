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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.metadata.Contributor;
import com.github.i49.pulp.api.metadata.Creator;
import com.github.i49.pulp.api.metadata.DateProperty;
import com.github.i49.pulp.api.metadata.DublinCoreTerm;
import com.github.i49.pulp.api.metadata.GenericProperty;
import com.github.i49.pulp.api.metadata.IdentifierProperty;
import com.github.i49.pulp.api.metadata.LanguageProperty;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.Publisher;
import com.github.i49.pulp.api.metadata.Relator;
import com.github.i49.pulp.api.metadata.StandardVocabulary;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.metadata.Vocabulary;
import com.github.i49.pulp.api.spi.EpubService;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.io.containers.PrefixRegistry;
import com.github.i49.pulp.impl.xml.Nodes;

/**
 * Parser for the metadata defined in EPUB specification 3.0
 */
class MetadataParser3 implements MetadataParser {
	
	private final Metadata metadata;
	private final TermRegistry termRegistry;
	private final PropertyFactory factory;
	private final PrefixRegistry prefixRegistry;
	private final String uniqueIdentifier;

	private static final Logger log = Logger.getLogger(MetadataParser3.class.getName());
	private static final Vocabulary DEFAULT_VOCABULARY = StandardVocabulary.EPUB_META;
	
	MetadataParser3(Metadata metadata, EpubService service, PrefixRegistry prefixRegistry, String uniqueIdentifier) {
		this.metadata = metadata;
		this.termRegistry = service.getPropertyTermRegistry();
		this.factory = service.createPropertyFactory();
		this.prefixRegistry = prefixRegistry;
		this.uniqueIdentifier = uniqueIdentifier;
	}
	
	@Override
	public void parse(Element element) {
		List<MetadataEntry> entries = fetchMetadataEntries(element);
		for (MetadataEntry entry: entries) {
			if (entry.getVocabulary() == StandardVocabulary.DCMES) {
				parseDublinCoreElement(entry);
			} else {
				parseMetaElement(entry);
			}
		}
	}
	
	protected List<MetadataEntry> fetchMetadataEntries(Element metadata) {
		List<MetadataEntry> entries = new ArrayList<>();
		List<Element> refinements = new ArrayList<>();

		Iterator<Element> it = Nodes.children(metadata);
		while (it.hasNext()) {
			Element child = it.next();
			MetadataEntry entry = null;
			String namespace = child.getNamespaceURI();
			if (NAMESPACE_URI.equals(namespace)) {
				String localName = child.getLocalName();
				if ("meta".equals(localName)) {
					if (child.hasAttribute("refines")) {
						refinements.add(child);
					} else {
						entry = new MetadataEntry(child);
					}
				}
			} else if (DC_NAMESPACE_URI.equals(namespace)) {
				entry = new MetadataEntry(child, StandardVocabulary.DCMES);
			}
			if (entry != null) {
				entries.add(entry);
			}
		}
		
		return refineEntries(entries, refinements);
	}
	
	protected List<MetadataEntry> refineEntries(List<MetadataEntry> entries, List<Element> refinements) {
		Map<String, MetadataEntry> map = createEntryMap(entries);
		for (Element r: refinements) {
			String target = r.getAttribute("refines").trim();
			if (target.startsWith("#")) {
				String id = target.substring(1);
				MetadataEntry entry = map.get(id);
				if (entry != null) {
					entry.addRefinement(r);
				}
			}
		}
		return entries;
	}
	
	protected Map<String, MetadataEntry> createEntryMap(List<MetadataEntry> entries) {
		return entries.stream()
				.filter(MetadataEntry::hasId)
				.collect(Collectors.toMap(MetadataEntry::getId, Function.identity()));
	}
	
	/**
	 * Parses one of Dublin Core Metadata Element and add the parsed property.
	 * 
	 * @param entry a child of the metadata element.
	 * @return added property.
	 */
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
		throw new EpubException(Messages.METADATA_DC_ELEMENT_UNKNOWN(localName));
	}
	
	protected Property parseMetaElement(MetadataEntry entry) {
		String name = entry.getElement().getAttribute("property");
		Property p = parseMetaElement(entry, name);
		return p;
	}
	
	protected Property parseMetaElement(MetadataEntry entry, String name) {
		String[] parts = name.split(":", 2);
		String prefix = null;
		String localName = null;
		if (parts.length >= 2) {
			prefix = parts[0];
			localName = parts[1];
		} else {
			localName = name;
		}
		Term term = findTerm(prefix, localName);
		if (term == DublinCoreTerm.MODIFIED) {
			return parseModified(entry);
		} else if (term != null) {
			return parseGenericProperty(entry, term);
		}
		return null;
	}
	
	protected Term findTerm(String prefix, String localName) {
		Vocabulary vocabulary = findVocabulary(prefix);
		if (vocabulary == null) {
			return null;
		}
		return this.termRegistry.getTerm(vocabulary, localName);
	}
	
	protected Vocabulary findVocabulary(String prefix) {
		Vocabulary vocabulary = DEFAULT_VOCABULARY;
		if (prefix != null) {
			vocabulary = this.prefixRegistry.get(prefix);
			if (vocabulary == null) {
				log.warning(Messages.METADATA_PROPERTY_PREFIX_IGNORED(prefix));
			}
		}
		return vocabulary;
	}
	
	protected Property parseContributor(MetadataEntry entry) {
		Relator.Builder<Contributor> b = factory.getContributorBuilder(entry.getValue());
		Property p = buildRelator(b, entry);
		return append(p);
	}

	protected Property parseCoverage(MetadataEntry entry) {
		Property p = factory.newCoverage(entry.getValue());
		return append(p);
	}

	protected Property parseCreator(MetadataEntry entry) {
		Relator.Builder<Creator> b = factory.getCreatorBuilder(entry.getValue());
		Property p = buildRelator(b, entry);
		return append(p);
	}

	protected Property parseDate(MetadataEntry entry) {
		OffsetDateTime dateTime = convertDateTime(entry.getValue());
		Property p = factory.newDate(dateTime);
		return append(p);
	}

	protected Property parseDescription(MetadataEntry entry) {
		Property p = factory.newDescription(entry.getValue());
		return append(p);
	}

	protected Property parseFormat(MetadataEntry entry) {
		Property p = factory.newFormat(entry.getValue());
		return append(p);
	}
	
	protected Property parseIdentifier(MetadataEntry entry) {
		IdentifierProperty p = factory.newIdentifier(entry.getValue());
		if (entry.hasId(this.uniqueIdentifier)) {
			prepend(p);
		} else {
			append(p);
		}
		return p;
	}

	protected Property parseLanguage(MetadataEntry entry) {
		LanguageProperty p = factory.newLanguage(entry.getValue());
		return append(p);
	}

	protected Property parsePublisher(MetadataEntry entry) {
		Relator.Builder<Publisher> b = factory.getPublisherBuilder(entry.getValue());
		Property p = buildRelator(b, entry);
		return append(p);
	}

	protected Property parseRelation(MetadataEntry entry) {
		Property p = factory.newRelation(entry.getValue());
		return append(p);
	}

	protected Property parseRights(MetadataEntry entry) {
		Property p = factory.newRights(entry.getValue());
		return append(p);
	}

	protected Property parseSource(MetadataEntry entry) {
		Property p = factory.newSource(entry.getValue());
		return append(p);
	}

	protected Property parseSubject(MetadataEntry entry) {
		Property p = factory.newSubject(entry.getValue());
		return append(p);
	}
	
	protected Property parseTitle(MetadataEntry entry) {
		Property p = factory.newTitle(entry.getValue());
		return append(p);
	}

	protected Property parseType(MetadataEntry entry) {
		Property p = factory.newType(entry.getValue());
		return append(p);
	}
	
	protected Property parseModified(MetadataEntry entry) {
		OffsetDateTime dateTime = convertDateTime(entry.getValue());
		DateProperty p = factory.newModified(dateTime);
		return append(p);
	}
	
	protected Property parseGenericProperty(MetadataEntry entry, Term term) {
		GenericProperty<String> p = factory.createGenericProperty(term, entry.getValue());
		return append(p);
	}
	
	private Property prepend(Property p) {
		this.metadata.getList(p.getTerm()).add(0, p);
		return p;
	}
	
	private Property append(Property p) {
		this.metadata.add(p);
		return p;
	}

	protected <T extends Relator> T buildRelator(Relator.Builder<T> builder, MetadataEntry entry) {
		for (Element r: entry.getRefinements()) {
			String term = r.getAttribute("property");
			if ("file-as".equals(term)) {
				builder.fileAs(r.getTextContent());
			}
		}
		return builder.build();
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