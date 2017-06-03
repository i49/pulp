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
 * Parser for compact URI represented by CURIE syntax.
 */
class CurieParser {

	private static final Logger log = Logger.getLogger(CurieParser.class.getName());
	
	private final PrefixRegistry prefixRegistry;
	private final TermRegistry termRegistry;
	private Vocabulary defaultVocabulary;

	CurieParser(PrefixRegistry prefixRegistry, TermRegistry termRegistry) {
		this.prefixRegistry = prefixRegistry;
		this.termRegistry = termRegistry;
	}
	
	/**
	 * Assigns the default vocabulary used when the prefix part of the CURIE is omitted. 
	 * 
	 * @param vocabulary the default vocabulary.
	 * @return this parser.
	 */
	CurieParser setDefaultVocabulary(Vocabulary vocabulary) {
		this.defaultVocabulary = vocabulary;
		return this;
	}
	
	/**
	 * Parses the compact URI.
	 * 
	 * @param value the compact URI.
	 * @return the term corresponding the given value, may be empty.
	 */
	Optional<Term> parse(String value) {
		assert(value != null);
		String[] parts = value.split(":", 2);
		if (parts.length >= 2) {
			return parse(parts[0], parts[1]);
		} else {
			Term term = getTerm(this.defaultVocabulary, value);
			return Optional.of(term);
		}
	}
	
	Optional<Term> parse(String reference, Vocabulary vocabulary) {
		return termRegistry.findTerm(vocabulary, reference);
	}
	
	private Optional<Term> parse(String prefix, String reference) {
		Optional<Vocabulary> vocabulary = findVocabulary(prefix);
		if (vocabulary.isPresent()) {
			return Optional.of(getTerm(vocabulary.get(), reference));
		}
		return Optional.empty();
	}
	
	private Term getTerm(Vocabulary vocabulary, String reference) {
		return this.termRegistry.getTerm(vocabulary, reference);
	}
	
	private Optional<Vocabulary> findVocabulary(String prefix) {
		Vocabulary vocabulary = this.prefixRegistry.get(prefix);
		if (vocabulary == null) {
			log.warning(Messages.METADATA_PROPERTY_PREFIX_IGNORED(prefix));
		}
		return Optional.ofNullable(vocabulary);
	}
}
