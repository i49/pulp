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

package com.github.i49.pulp.api.vocabularies.rendering;

import com.github.i49.pulp.api.vocabularies.StandardVocabulary;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;

/**
 * All terms provided by EPUB Package Rendering Metadata.
 */
public enum Rendering implements Term {
	/**
	 * Specifies how reading systems should handle content overflow.
	 */
	FLOW,
	/**
	 * Specifies whether the given rendition is reflowable or pre-paginated.
	 */
	LAYOUT,
	/**
	 * Specifies the orientation of the given rendition.
	 */
	ORIENTATION,
	/**
	 * Specifies synthetic spread behavior for the given rendition.
	 */
	SPREAD
	;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vocabulary getVocabulary() {
		return StandardVocabulary.EPUB_RENDITION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String localName() {
		return name().toLowerCase();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@code false} always.
	 */
	@Override
	public boolean isRepeatable() {
		return false;
	}
}
