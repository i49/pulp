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

package com.github.i49.pulp.api.metadata;

import static com.github.i49.pulp.api.metadata.StandardVocabulary.*;

/**
 */
public enum BasicTerm implements Term {
	
	CONTRIBUTOR(DCMES, "contributor"),
	COVERAGE(DCMES, "coverage"),
	CREATOR(DCMES, "creator"),
	DATE(DCMES, "date"),
	DESCRIPTION(DCMES, "description"),
	FORMAT(DCMES, "format"),
	IDENTIFIER(DCMES, "identifier"),
	LANGUAGE(DCMES, "language"),
	PUBLISHER(DCMES, "publisher"),
	RELATION(DCMES, "relation"),
	RIGHTS(DCMES, "rights"),
	SOURCE(DCMES, "source"),
	SUBJECT(DCMES, "subject"),
	TITLE(DCMES, "title"),
	TYPE(DCMES, "type"),
	MODIFIED(DCTERMS, "modified")
	;

	private final Vocabulary vocabulary;
	private final String name;
	
	private BasicTerm(Vocabulary vocabulary, String name) {
		this.vocabulary = vocabulary;
		this.name = name;
	}
			
	@Override
	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	@Override
	public String getName() {
		return name;
	}
}
