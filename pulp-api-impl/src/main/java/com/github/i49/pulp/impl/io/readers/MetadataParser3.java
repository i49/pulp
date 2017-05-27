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
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.PropertyBuilderSelector;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.spi.EpubService;
import com.github.i49.pulp.api.vocabularies.GenericText;
import com.github.i49.pulp.api.vocabularies.Relator;
import com.github.i49.pulp.api.vocabularies.StandardVocabulary;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dc.TitleType;
import com.github.i49.pulp.api.vocabularies.dcterms.DublinCoreTerm;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.io.containers.PrefixRegistry;
import com.github.i49.pulp.impl.vocabularies.epub.MetaPropertyTerm;
import com.github.i49.pulp.impl.xml.Nodes;

/**
 * Parser for the metadata defined in EPUB specification 3.0
 */
class MetadataParser3 implements MetadataParser {
	
	private final Metadata metadata;
	private final TermRegistry termRegistry;
	private final PrefixRegistry prefixRegistry;
	private final String uniqueIdentifier;

	private static final Logger log = Logger.getLogger(MetadataParser3.class.getName());
	private static final Vocabulary DEFAULT_VOCABULARY = StandardVocabulary.EPUB_META;
	
	private static final Map<Term, BiConsumer<MetadataParser3, MetadataEntry>> parsers;
	
	static {
		parsers = new HashMap<>();
		parsers.put(DublinCore.CONTRIBUTOR, MetadataParser3::parseContributor);
		parsers.put(DublinCore.COVERAGE, MetadataParser3::parseCoverage);
		parsers.put(DublinCore.CREATOR, MetadataParser3::parseCreator);
		parsers.put(DublinCore.DATE, MetadataParser3::parseDate);
		parsers.put(DublinCore.DESCRIPTION, MetadataParser3::parseDescription);
		parsers.put(DublinCore.FORMAT, MetadataParser3::parseFormat);
		parsers.put(DublinCore.IDENTIFIER, MetadataParser3::parseIdentifier);
		parsers.put(DublinCore.LANGUAGE, MetadataParser3::parseLanguage);
		parsers.put(DublinCore.PUBLISHER, MetadataParser3::parsePublisher);
		parsers.put(DublinCore.RELATION, MetadataParser3::parseRelation);
		parsers.put(DublinCore.RIGHTS, MetadataParser3::parseRights);
		parsers.put(DublinCore.SOURCE, MetadataParser3::parseSource);
		parsers.put(DublinCore.SUBJECT, MetadataParser3::parseSubject);
		parsers.put(DublinCore.TITLE, MetadataParser3::parseTitle);
		parsers.put(DublinCore.TYPE, MetadataParser3::parseType);
		parsers.put(DublinCoreTerm.MODIFIED, MetadataParser3::parseModified);
	}
	
	MetadataParser3(Metadata metadata, EpubService service, PrefixRegistry prefixRegistry, String uniqueIdentifier) {
		this.metadata = metadata;
		this.termRegistry = service.getPropertyTermRegistry();
		this.prefixRegistry = prefixRegistry;
		this.uniqueIdentifier = uniqueIdentifier;
	}
	
	@Override
	public void parse(Element element) {
		for (MetadataEntry entry: fetchMetadataEntries(element)) {
			if (!entry.isRefining()) {
				parseMetadataEntry(entry);
			}
		}
	}
	
	private List<MetadataEntry> fetchMetadataEntries(Element metadata) {
		List<MetadataEntry> entries = new ArrayList<>();
		Iterator<Element> it = Nodes.children(metadata);
		while (it.hasNext()) {
			MetadataEntry entry = createEntry(it.next());
			if (entry != null) {
				entries.add(entry);
			}
		}
		return refineEntries(entries);
	}
	
	private MetadataEntry createEntry(Element element) {
		String namespace = element.getNamespaceURI();
		if (NAMESPACE_URI.equals(namespace)) {
			if ("meta".equals(element.getLocalName())) {
				String name = element.getAttribute("property").trim();
				Term term = findTerm(name);
				if (term != null) {
					return new MetadataEntry(term, element);
				}
			}
		} else if (DC_NAMESPACE_URI.equals(namespace)) {
			Optional<Term> term = termRegistry.findTerm(StandardVocabulary.DCMES, element.getLocalName());
			if (term.isPresent()) {
				return new MetadataEntry(term.get(), element);
			}
		}
		return null;
	}
	
	private List<MetadataEntry> refineEntries(List<MetadataEntry> entries) {
		Map<String, MetadataEntry> map = createEntryMap(entries);
		for (MetadataEntry entry: entries) {
			if (entry.isRefining()) {
				String url = entry.getRefiningTarget();
				if (url.startsWith("#")) {
					MetadataEntry target = map.get(url.substring(1));
					if (target != null) {
						entry.refine(target);
					}
				}
			}
		}
		return entries;
	}
	
	private Map<String, MetadataEntry> createEntryMap(List<MetadataEntry> entries) {
		return entries.stream()
				.filter(MetadataEntry::hasId)
				.collect(Collectors.toMap(MetadataEntry::getId, Function.identity()));
	}

	private void parseMetadataEntry(MetadataEntry entry) {
		Term term = entry.getTerm();
		BiConsumer<MetadataParser3, MetadataEntry> consumer = parsers.get(term);
		if (consumer != null) {
			consumer.accept(this, entry);
		} else {
			parseGenericProperty(entry);
		}
	}
	
	private Term findTerm(String name) {
		String[] parts = name.split(":", 2);
		String prefix = null;
		String localName = null;
		if (parts.length >= 2) {
			prefix = parts[0];
			localName = parts[1];
		} else {
			localName = name;
		}
		return findTerm(prefix, localName);
	}
	
	private Term findTerm(String prefix, String localName) {
		Vocabulary vocabulary = findVocabulary(prefix);
		if (vocabulary == null) {
			return null;
		}
		return this.termRegistry.getTerm(vocabulary, localName);
	}
	
	private Vocabulary findVocabulary(String prefix) {
		Vocabulary vocabulary = DEFAULT_VOCABULARY;
		if (prefix != null) {
			vocabulary = this.prefixRegistry.get(prefix);
			if (vocabulary == null) {
				log.warning(Messages.METADATA_PROPERTY_PREFIX_IGNORED(prefix));
			}
		}
		return vocabulary;
	}

	private PropertyBuilderSelector add() {
		return metadata.add();
	}
	
	private void parseContributor(MetadataEntry entry) {
		buildRelator(add().contributor(entry.getValue()), entry);
	}

	private void parseCoverage(MetadataEntry entry) {
		add().coverage(entry.getValue()).result();
	}

	private void parseCreator(MetadataEntry entry) {
		buildRelator(add().creator(entry.getValue()), entry);
	}

	private void parseDate(MetadataEntry entry) {
		OffsetDateTime dateTime = convertDateTime(entry.getValue());
		add().date(dateTime).result();
	}

	private void parseDescription(MetadataEntry entry) {
		add().description(entry.getValue()).result();
	}

	private void parseFormat(MetadataEntry entry) {
		add().format(entry.getValue()).result();
	}
	
	private void parseIdentifier(MetadataEntry entry) {
		Identifier.Builder b = add().identifier(entry.getValue());
		if (entry.hasId() && entry.getId().equals(this.uniqueIdentifier)) {
			//prepend(p);
		} else {
			//append(p);
		}
		b.result();
	}

	private void parseLanguage(MetadataEntry entry) {
		add().language(entry.getValue()).result();
	}

	private void parsePublisher(MetadataEntry entry) {
		buildRelator(add().publisher(entry.getValue()), entry);
	}

	private void parseRelation(MetadataEntry entry) {
		add().relation(entry.getValue()).result();
	}

	private void parseRights(MetadataEntry entry) {
		add().rights(entry.getValue()).result();
	}

	private void parseSource(MetadataEntry entry) {
		add().source(entry.getValue()).result();
	}

	private void parseSubject(MetadataEntry entry) {
		add().subject(entry.getValue()).result();
	}
	
	private void parseTitle(MetadataEntry entry) {
		Title.Builder builder = add().title(entry.getValue());
		for (MetadataEntry refiner: entry.getRefiners()) {
			Term term = refiner.getTerm();
			if (term == MetaPropertyTerm.DISPLAY_SEQ) {
				builder.displayOrder(Integer.parseInt(refiner.getValue()));
			} else if (term == MetaPropertyTerm.FILE_AS) {
				builder.fileAs(refiner.getValue());
			} else if (term == MetaPropertyTerm.TITLE_TYPE) {
				TitleType type = TitleType.valueOf(refiner.getValue().toUpperCase());
				builder.ofType(type);
			}
		}
		builder.result();
	}

	private void parseType(MetadataEntry entry) {
		add().type(entry.getValue()).result();
	}
	
	private void parseModified(MetadataEntry entry) {
		OffsetDateTime dateTime = convertDateTime(entry.getValue());
		add().modified(dateTime).result();
	}
	
	private void parseGenericProperty(MetadataEntry entry) {
		Term term = entry.getTerm();
		GenericText.Builder b = add().generic(term, entry.getValue());
		b.result();
	}
	
	private <T extends Relator, R extends Relator.Builder<T, R>> 
	T buildRelator(Relator.Builder<T, R> builder, MetadataEntry entry) {
		for (MetadataEntry refiner: entry.getRefiners()) {
			Term term = refiner.getTerm();
			if (term == MetaPropertyTerm.FILE_AS) {
				builder.fileAs(refiner.getValue());
			}
		}
		return builder.result();
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