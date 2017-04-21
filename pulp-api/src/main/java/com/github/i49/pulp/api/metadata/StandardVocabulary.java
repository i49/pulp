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
 * Default and reserved vocabularies for metadata.
 */
public enum StandardVocabulary implements Vocabulary {

	/**  EPUB Meta Properties Vocabulary */ 
	EPUB_META("http://idpf.org/epub/vocab/package/meta/#"),
	/* Dublin Core Metadata Element Set */
	DCMES("http://purl.org/dc/elements/1.1/"),
	/* DCMI Metadata Terms */
	DCTERMS("http://purl.org/dc/terms/")
	;
	
	private final URI uri;
	
	private StandardVocabulary(String uri) {
		this.uri = URI.create(uri);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URI getURI() {
		return uri;
	}
}
