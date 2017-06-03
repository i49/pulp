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
import com.github.i49.pulp.api.vocabularies.GenericText;
import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.Relator;
import com.github.i49.pulp.api.vocabularies.RelatorRole;
import com.github.i49.pulp.api.vocabularies.StandardVocabulary;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dc.TitleType;
import com.github.i49.pulp.api.vocabularies.dcterms.DublinCoreTerm;
import com.github.i49.pulp.api.vocabularies.rendering.Flow;
import com.github.i49.pulp.api.vocabularies.rendering.Layout;
import com.github.i49.pulp.api.vocabularies.rendering.Orientation;
import com.github.i49.pulp.api.vocabularies.rendering.Rendering;
import com.github.i49.pulp.api.vocabularies.rendering.Spread;
import com.github.i49.pulp.impl.base.Enums;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.vocabularies.epub.MetaPropertyTerm;
import com.github.i49.pulp.impl.vocabularies.marc.Marc;
import com.github.i49.pulp.impl.xml.Nodes;

/**
 * Parser for the metadata defined in EPUB specification 3.0
 */
class MetadataParser3 implements MetadataParser {
	
	private final Metadata metadata;
	private final String uniqueIdentifier;
	private final CurieParser curieParser;

	private static final Logger log = Logger.getLogger(MetadataParser3.class.getName());
	private static final Vocabulary DEFAULT_VOCABULARY = StandardVocabulary.EPUB_META;
	
	private static final Map<Term, BiConsumer<MetadataParser3, MetadataEntry>> handlers;
	
	private static final Function<String, Optional<RelatorRole>> roles = Enums.mapper(RelatorRole.class, RelatorRole::getCode);
	private static final Function<String, Optional<TitleType>> titleTypes = Enums.mapper(TitleType.class, MetadataParser3::getTitleTypeValue);

	private static final Function<String, Optional<Flow>> flows = Enums.mapper(Flow.class, Flow::getValue);
	private static final Function<String, Optional<Layout>> layouts = Enums.mapper(Layout.class, Layout::getValue);
	private static final Function<String, Optional<Orientation>> orientations = Enums.mapper(Orientation.class, Orientation::getValue);
	private static final Function<String, Optional<Spread>> spreads = Enums.mapper(Spread.class, Spread::getValue);
	
	static {
		handlers = new HashMap<Term, BiConsumer<MetadataParser3, MetadataEntry>>(){{
			// Dublin Core
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
			// Dublin Core Term
			put(DublinCoreTerm.MODIFIED, MetadataParser3::handleModified);
			// Rendering properties
			put(Rendering.FLOW, MetadataParser3::handleFlow);
			put(Rendering.LAYOUT, MetadataParser3::handleLayout);
			put(Rendering.ORIENTATION, MetadataParser3::handleOrientation);
			put(Rendering.SPREAD, MetadataParser3::handleSpread);
		}};
	}
	
	MetadataParser3(Metadata metadata, String uniqueIdentifier, CurieParser curieParser) {
		this.metadata = metadata;
		this.uniqueIdentifier = uniqueIdentifier;
		this.curieParser = curieParser.setDefaultVocabulary(DEFAULT_VOCABULARY);
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
		return linkEntries(entries);
	}
	
	private MetadataEntry createEntry(Element element) {
		String namespace = element.getNamespaceURI();
		if (NAMESPACE_URI.equals(namespace)) {
			if ("meta".equals(element.getLocalName())) {
				String name = element.getAttribute("property").trim();
				if (name.isEmpty()) {
					log.warning(Messages.METADATA_META_WITHOUT_PROPERTY_INGNORED());
					return null;
				}
				Optional<Term> term = this.curieParser.parse(name);
				if (term.isPresent()) {
					return new MetadataEntry(term.get(), element);
				}
			}
		} else if (DC_NAMESPACE_URI.equals(namespace)) {
			Optional<Term> term = curieParser.parse(element.getLocalName(), StandardVocabulary.DCMES);
			if (term.isPresent()) {
				return new MetadataEntry(term.get(), element);
			}
		}
		return null;
	}
	
	private List<MetadataEntry> linkEntries(List<MetadataEntry> entries) {
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
	
	private PropertyBuilderSelector add() {
		return metadata.add();
	}
	
	private void add(Optional<? extends Property<?>> p) {
		if (p.isPresent()) {
			this.metadata.add(p.get());
		}
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
				Optional<TitleType> type = findTitleType(refiner.getValue());
				if (type.isPresent()) {
					builder.ofType(type.get());
				} else {
					log.warning(Messages.METADATA_TITLE_TYPE_IGNORED(refiner.getValue()));
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
	
	private void handleFlow(MetadataEntry entry) {
		add(flows.apply(entry.getValue()));
	}

	private void handleLayout(MetadataEntry entry) {
		add(layouts.apply(entry.getValue()));
	}

	private void handleOrientation(MetadataEntry entry) {
		add(orientations.apply(entry.getValue()));
	}
	
	private void handleSpread(MetadataEntry entry) {
		add(spreads.apply(entry.getValue()));
	}

	private void handleGenericProperty(MetadataEntry entry) {
		Term term = entry.getTerm();
		GenericText.Builder b = add().generic(term, entry.getValue());
		b.result();
	}
	
	private <T extends Relator, R extends Relator.Builder<T, R>> 
	void buildRelator(Relator.Builder<T, R> builder, MetadataEntry entry) {
		refineRelator(builder, entry);
		builder.result();
	}
	
	private <T extends Relator, R extends Relator.Builder<T, R>> 
	void refineRelator(Relator.Builder<T, R> builder, MetadataEntry entry) {
		for (MetadataEntry refiner: entry.getRefiners()) {
			Term term = refiner.getTerm();
			if (term == MetaPropertyTerm.FILE_AS) {
				builder.fileAs(refiner.getValue());
			} else if (term == MetaPropertyTerm.ROLE) {
				if (validateRelatorScheme(refiner.getScheme())) {
					Optional<RelatorRole> role = findRole(refiner.getValue());
					if (role.isPresent()) {
						builder.role(role.get());
					} else {
						log.warning(Messages.METADATA_RELATOR_ROLE_IGNORED(refiner.getValue()));
					}
				}
			}
		}
	}
	
	private boolean validateRelatorScheme(String value) {
		if (value == null) {
			return true;
		}
		Optional<Term> term = this.curieParser.parse(value);
		if (!term.isPresent()) {
			return false;
		}
		return term.get() == Marc.RELATORS;
	}

	private static Optional<TitleType> findTitleType(String value) {
		return titleTypes.apply(value);
	}
	
	private static Optional<RelatorRole> findRole(String value) {
		return roles.apply(value);
	}
	
	private static String getTitleTypeValue(TitleType titleType) {
		return titleType.name().toLowerCase();
	}
}