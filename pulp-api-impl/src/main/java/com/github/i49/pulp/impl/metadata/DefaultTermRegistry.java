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

package com.github.i49.pulp.impl.metadata;

import static com.github.i49.pulp.impl.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.metadata.BasicTerm;
import com.github.i49.pulp.api.metadata.StandardVocabulary;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.metadata.Vocabulary;

/**
 * The default implementation of {@link TermRegistry}.
 */
public class DefaultTermRegistry implements TermRegistry {
	
	private final Map<URI, Vocabulary> vocabularyMap = new HashMap<>();
	private final Map<Vocabulary, Map<String, Term>> termMap = new HashMap<>();
	
	/**
	 * Constructs this registry.
	 * By default, all predefined vocabularies and terms will be picked up
	 * and registered automatically.
	 */
	public DefaultTermRegistry() {
		registerVocabularies(StandardVocabulary.class);
		registerTerms(BasicTerm.class);
	}

	@Override
	public boolean containsTerm(Term term) {
		checkNotNull(term, "term");
		Vocabulary v = term.getVocabulary();
		if (!vocabularyMap.containsValue(v)) {
			return false;
		}
		Map<String, Term> terms = termMap.get(v);
		return terms.containsValue(term);
	}

	@Override
	public boolean containsVocabulary(Vocabulary vocabulary) {
		checkNotNull(vocabulary, "vocabulary");
		return this.vocabularyMap.containsValue(vocabulary);
	}
	
	@Override
	public Term getTerm(Vocabulary vocabulary, String name) {
		checkNotNull(vocabulary, "vocabulary");
		checkNotNull(name, "name");
		if (!containsVocabulary(vocabulary)) {
			// TODO:
			throw new IllegalStateException();
		}
		Map<String, Term> terms = termMap.get(vocabulary);
		Term term = terms.get(name);
		if (term == null) {
			term = createTerm(vocabulary, name);
			doRegisterTerm(term);
		}
		return term;
	}

	@Override
	public Vocabulary getVocabulary(URI uri) {
		checkNotNull(uri, "uri");
		Vocabulary v = this.vocabularyMap.get(uri);
		if (v == null) {
			v = createVocabulary(uri);
			doRegisterVocabulary(v);
		}
		return v;
	}

	@Override
	public void registerTerm(Term term) {
		checkNotNull(term, "term");
		if (containsTerm(term)) {
			// TODO:
			throw new IllegalStateException();
		} else if (containsVocabulary(term.getVocabulary())) {
			doRegisterTerm(term);
		} else {
			// TODO:
			throw new IllegalStateException();
		}
	}

	@Override
	public <T extends Enum<T> & Term> void registerTerms(Class<T> clazz) {
		checkNotNull(clazz, "clazz");
		for (Term t: clazz.getEnumConstants()) {
			registerTerm(t);
		}
	}

	@Override
	public void registerVocabulary(Vocabulary vocabulary) {
		checkNotNull(vocabulary, "vocabulary");
		if (containsVocabulary(vocabulary)) {
			// TODO:
			throw new IllegalStateException();
		} else {
			doRegisterVocabulary(vocabulary);
		}
	}

	@Override
	public <T extends Enum<T> & Vocabulary> void registerVocabularies(Class<T> clazz) {
		checkNotNull(clazz, "clazz");
		for (Vocabulary v: clazz.getEnumConstants()) {
			registerVocabulary(v);
		}
	}
	
	private Term createTerm(Vocabulary vocabulary, String name) {
		return new DynamicTerm(vocabulary, name);
	}
	
	private Vocabulary createVocabulary(URI uri) {
		return new DynamicVocabulary(uri);
	}
	
	private void doRegisterTerm(Term term) {
		assert(term != null);
		Map<String, Term> terms = this.termMap.get(term.getVocabulary());
		assert(terms != null);
		terms.put(term.getName(), term);
	}
	
	private void doRegisterVocabulary(Vocabulary vocabulary) {
		assert(vocabulary != null);
		this.vocabularyMap.put(vocabulary.getURI(), vocabulary);
		this.termMap.put(vocabulary, new HashMap<String, Term>());
	}
	
	/**
	 * Dynamically generated {@link Term}.
	 */
	private static class DynamicTerm implements Term {
		
		private final Vocabulary vocabulary;
		private final String name;
		
		DynamicTerm(Vocabulary vocabulary, String name) {
			assert(vocabulary != null);
			assert(name != null);
			this.vocabulary = vocabulary;
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Vocabulary getVocabulary() {
			return vocabulary;
		}
	}
	
	/**
	 * Dynamically generated {@link Vocabulary}.
	 */
	private static class DynamicVocabulary implements Vocabulary {
		
		private final URI uri;
		
		DynamicVocabulary(URI uri) {
			assert(uri != null);
			this.uri = uri;
		}

		@Override
		public URI getURI() {
			return uri;
		}
		
		@Override
		public String toString() {
			return getURI().toString();
		}
	}
}
