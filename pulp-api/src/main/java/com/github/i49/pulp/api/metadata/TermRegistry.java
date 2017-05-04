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

import java.net.URI;

/**
 * Property terms registry.
 */
public interface TermRegistry {
	
	boolean containsTerm(Term term);
	
	boolean containsVocabulary(Vocabulary vocabulary);
	
	Term getTerm(Vocabulary vocabulary, String name);
	
	Vocabulary getVocabulary(URI uri);
	
	void registerTerm(Term term);
	
	<T extends Enum<T> & Term> void registerTerms(Class<T> clazz);

	void registerVocabulary(Vocabulary vocabulary);
	
	<T extends Enum<T> & Vocabulary> void registerVocabularies(Class<T> clazz);
}
