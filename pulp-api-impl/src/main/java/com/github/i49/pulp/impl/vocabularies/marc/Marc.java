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

package com.github.i49.pulp.impl.vocabularies.marc;

import com.github.i49.pulp.api.vocabularies.StandardVocabulary;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;

/**
 * Standards for the representation and communication of bibliographic
 * and related information in machine-readable form.
 */
public enum Marc implements Term {
	/**
	 * the relationship between an agent and a resource to be designated in bibliographic records.
	 */
	RELATORS
	;

	@Override
	public Vocabulary getVocabulary() {
		return StandardVocabulary.MARC;
	}

	@Override
	public String localName() {
		return name().toLowerCase();
	}
}
