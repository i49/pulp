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

package com.github.i49.pulp.impl.io.containers;

import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.metadata.StandardVocabulary;
import com.github.i49.pulp.api.metadata.Vocabulary;

/**
 * The registry of prefixes.
 */
public class PrefixRegistry {
	
	private final Map<String, Vocabulary> prefixes = new HashMap<>();
	
	private static final Map<String, Vocabulary> RESERVED_PREFIXES = new HashMap<>();
	
	static {
		// Adds all reserved prefixes for package metadata.
		add("a11y", StandardVocabulary.EPUB_A11Y);
		add("dcterms", StandardVocabulary.DCTERMS);
		add("epubsc", StandardVocabulary.EPUB_SC);
		add("marc", StandardVocabulary.MARC);
		add("media", StandardVocabulary.EPUB_MEDIA);
		add("onix", StandardVocabulary.ONIX);
		add("rendition", StandardVocabulary.EPUB_RENDITION);
		add("schema", StandardVocabulary.SCHEMA);
		add("xsd", StandardVocabulary.XSD);
	}

	public void put(String prefix, Vocabulary vocabulary) {
		assert(prefix != null);
		assert(vocabulary != null);
		this.prefixes.put(prefix.trim(), vocabulary);
	}
	
	public Vocabulary get(String prefix) {
		assert(prefix != null);
		Vocabulary v = RESERVED_PREFIXES.get(prefix);
		if (v == null) {
			v = this.prefixes.get(prefix);
		}
		return v;
	}
	
	private static void add(String prefix, Vocabulary v) {
		RESERVED_PREFIXES.put(prefix, v);
	}
}
