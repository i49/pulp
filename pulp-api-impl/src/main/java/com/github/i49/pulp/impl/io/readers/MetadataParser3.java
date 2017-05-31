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
	
	private static final Map<Term, BiConsumer<MetadataParser3, MetadataEntry>> handlers;
	private static final Map<String, TitleType> titleTypes;
	
	static {
		handlers = new HashMap<Term, BiConsumer<MetadataParser3, MetadataEntry>>(){{
			put(DublinCore.CONTRIBUTOR, MetadataParser3::handleContributor);
			put(DublinCore.COVERAGE, MetadataParser3::handleCoverage);
			put(DublinCore.CREATOR, MetadataParser3::handleCreator);
			put(DublinCore.DATE, MetadataParser3::handleDate);
			put(DublinCore.DESCRIPTION, MetadataParser3::handleDescription);
			put(DublinCore.FORMAT, MetadataParser3::handleFormat);
			put(DublinCore.IDENTIFIER, MetadataParser3::handleIdentifier);
			put(DublinCore.LANGUAGE, MetadataParser3::handleLanguage);
			put(DublinCore.PUBLISHER, MetadataParser3::handlePublisher);
			put(DublinCore.RELATION, MetadataParser3::handleRelation);
			put(DublinCore.RIGHTS, MetadataParser3::handleRights);
			put(DublinCore.SOURCE, MetadataParser3::handleSource);
			put(DublinCore.SUBJECT, MetadataParser3::handleSubject);
			put(DublinCore.TITLE, MetadataParser3::handleTitle);
			put(DublinCore.TYPE, MetadataParser3::handleType);
			put(DublinCoreTerm.MODIFIED, MetadataParser3::handleModified);
		}};

		titleTypes = new HashMap<>();
		for (TitleType t: TitleType.values()) {
			titleTypes.put(t.name().toLowerCase(), t);
		}
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
				handleMetadataEntry(entry);
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

	private void handleMetadataEntry(MetadataEntry entry) {
		Term term = entry.getTerm();
		BiConsumer<MetadataParser3, MetadataEntry> consumer = handlers.get(term);
		if (consumer != null) {
			consumer.accept(this, entry);
		} else {
			handleGenericProperty(entry);
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
	
	private void handleContributor(MetadataEntry entry) {
		buildRelator(add().contributor(entry.getValue()), entry);
	}

	private void handleCoverage(MetadataEntry entry) {
		add().coverage(entry.getValue()).result();
	}

	private void handleCreator(MetadataEntry entry) {
		buildRelator(add().creator(entry.getValue()), entry);
	}

	private void handleDate(MetadataEntry entry) {
		add().date(entry.getValue()).result();
	}

	private void handleDescription(MetadataEntry entry) {
		add().description(entry.getValue()).result();
	}

	private void handleFormat(MetadataEntry entry) {
		add().format(entry.getValue()).result();
	}
	
	private void handleIdentifier(MetadataEntry entry) {
		Identifier.Builder b = add().identifier(entry.getValue());
		b.primary(entry.hasId() && entry.getId().equals(this.uniqueIdentifier));
		b.result();
	}

	private void handleLanguage(MetadataEntry entry) {
		add().language(entry.getValue()).result();
	}

	private void handlePublisher(MetadataEntry entry) {
		buildRelator(add().publisher(entry.getValue()), entry);
	}

	private void handleRelation(MetadataEntry entry) {
		add().relation(entry.getValue()).result();
	}

	private void handleRights(MetadataEntry entry) {
		add().rights(entry.getValue()).result();
	}

	private void handleSource(MetadataEntry entry) {
		add().source(entry.getValue()).result();
	}

	private void handleSubject(MetadataEntry entry) {
		add().subject(entry.getValue()).result();
	}
	
	private void handleTitle(MetadataEntry entry) {
		Title.Builder builder = add().title(entry.getValue());
		for (MetadataEntry refiner: entry.getRefiners()) {
			Term term = refiner.getTerm();
			if (term == MetaPropertyTerm.DISPLAY_SEQ) {
				builder.displayOrder(Integer.parseInt(refiner.getValue()));
			} else if (term == MetaPropertyTerm.FILE_AS) {
				builder.fileAs(refiner.getValue());
			} else if (term == MetaPropertyTerm.TITLE_TYPE) {
				TitleType type = findTitleType(refiner.getValue());
				if (type != null) {
					builder.ofType(type);
				} else {
					log.warning(Messages.METADATA_TITLE_TYPE_UNKNOWN(refiner.getValue()));
				}
			}
		}
		builder.result();
	}

	private void handleType(MetadataEntry entry) {
		add().type(entry.getValue()).result();
	}
	
	private void handleModified(MetadataEntry entry) {
		add().modified(entry.getValue()).result();
	}
	
	private void handleGenericProperty(MetadataEntry entry) {
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
			// TODO: role
		}
		return builder.result();
	}

	private static TitleType findTitleType(String value) {
		return titleTypes.get(value);
	}
}