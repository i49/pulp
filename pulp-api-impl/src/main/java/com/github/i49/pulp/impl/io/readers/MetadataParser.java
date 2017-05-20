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

import com.github.i49.pulp.api.vocabularies.StandardVocabulary;
import com.github.i49.pulp.impl.io.containers.EpubVocabulary;

/**
 * Base parser interface for the metadata defined in EPUB specification
 */
interface MetadataParser {

	static final String NAMESPACE_URI = EpubVocabulary.PACKAGE_DOCUMENT.getURI().toString();
	static final String DC_NAMESPACE_URI = StandardVocabulary.DCMES.getURI().toString();
	
	/**
	 * Parses the metadata element and its descendants.
	 * 
	 * @param element the metadata element.
	 */
	void parse(Element element);
}
