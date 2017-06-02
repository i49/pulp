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

import java.util.Optional;
import java.util.logging.Logger;

import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.io.containers.PrefixRegistry;

/**
 * Property data type parser.
 */
class PropertyParser {

	private static final Logger log = Logger.getLogger(PropertyParser.class.getName());
	
	private final PrefixRegistry prefixRegistry;
	private final TermRegistry termRegistry;

	PropertyParser(PrefixRegistry prefixRegistry, TermRegistry termRegistry) {
		this.prefixRegistry = prefixRegistry;
		this.termRegistry = termRegistry;
	}
	
	Optional<Term> parse(String value, Vocabulary defaultVocabulary) {
		if (value == null || value.isEmpty()) {
			return Optional.empty();
		}
		String[] parts = value.split(":", 2);
		if (parts.length >= 2) {
			return parse(parts[0], parts[1]);
		} else {
			return Optional.of(getTerm(defaultVocabulary, value));
		}
	}
	
	Optional<Term> findTerm(Vocabulary vocabulary, String name) {
		return termRegistry.findTerm(vocabulary, name);
	}
	
	private Optional<Term> parse(String prefix, String localName) {
		Optional<Vocabulary> vocabulary = findVocabulary(prefix);
		if (vocabulary.isPresent()) {
			return Optional.of(getTerm(vocabulary.get(), localName));
		}
		return Optional.empty();
	}
	
	private Term getTerm(Vocabulary vocabulary, String localName) {
		return this.termRegistry.getTerm(vocabulary, localName);
	}
	
	private Optional<Vocabulary> findVocabulary(String prefix) {
		Vocabulary vocabulary = this.prefixRegistry.get(prefix);
		if (vocabulary == null) {
			log.warning(Messages.METADATA_PROPERTY_PREFIX_IGNORED(prefix));
		}
		return Optional.ofNullable(vocabulary);
	}
}
