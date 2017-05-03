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

import org.w3c.dom.Element;

import com.github.i49.pulp.api.metadata.Vocabulary;

/**
 * Metadata entry.
 */
class MetadataEntry {

	private final Element element;
	private final Vocabulary vocabulary;
	
	MetadataEntry(Element element) {
		this.element = element;
		this.vocabulary = null;
	}

	MetadataEntry(Element element, Vocabulary vocabulary) {
		this.element = element;
		this.vocabulary = vocabulary;
	}
	
	Element getElement() {
		return element;
	}
	
	
	/**
	 * Returns the value of this entry as a string.
	 * 
	 * @return the value of this entry.
	 */
	String getValue() {
		return getElement().getTextContent();
	}
	
	Vocabulary getVocabulary() {
		return vocabulary;
	}
	
	@Override
	public String toString() {
		return element.getTagName();
	}
}
