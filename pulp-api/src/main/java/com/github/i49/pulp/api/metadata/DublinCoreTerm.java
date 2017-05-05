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

/**
 * Dublin Core Metadata Terms
 *
 * @see <a href="http://dublincore.org/documents/dcmi-terms/">DCMI Metadata Terms</a> 
 */
public enum DublinCoreTerm implements Term {
	/**
	 * Date on which the resource was changed.
	 */
	MODIFIED(PropertyType.DATE)
	;

	private final PropertyType type;
	
	private DublinCoreTerm(PropertyType type) {
		this.type = type;
	}
	
	@Override
	public PropertyType getType() {
		return type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vocabulary getVocabulary() {
		return StandardVocabulary.DCTERMS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRepeatable() {
		return this != MODIFIED;
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
	 */
	@Override
	public String toString() {
		return qualifiedName();
	}
}
