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

package com.github.i49.pulp.api.vocabularies;

import java.net.URI;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dcterms.DublinCoreTerm;

/**
 * Default and reserved vocabularies for metadata terms.
 */
public enum StandardVocabulary implements Vocabulary {

	/** 
	 * Dublin Core Metadata Element Set.
	 * The terms of this vocabulary are predefined in {@link DublinCore} enumeration.
	 */
	DCMES("http://purl.org/dc/elements/1.1/"),
	
	/** 
	 * DCMI Metadata Terms.
	 * The terms of this vocabulary are predefined in {@link DublinCoreTerm} enumeration.
	 */
	DCTERMS("http://purl.org/dc/terms/"),
	
	EPUB_A11Y("http://www.idpf.org/epub/vocab/package/a11y/#"),
	
	/**
	 * The vocabulary defined by EPUB Media Overlays specification.
	 */
	EPUB_MEDIA("http://www.idpf.org/epub/vocab/overlays/#"),
	
	/**
	 * EPUB Meta Properties Vocabulary.
	 */
	EPUB_META("http://idpf.org/epub/vocab/package/meta/#"),
	
	/**
	 * EPUB Package Rendering Vocabulary.
	 */
	EPUB_RENDITION("http://www.idpf.org/vocab/rendition/#"),
	
	EPUB_SC("http://idpf.org/epub/vocab/sc/#"),
	
	/**
	 * A vocabulary commonly used by libraries for bibliographic metadata.
	 */
	MARC("http://id.loc.gov/vocabulary/"),
	
	/**
	 * A vocabulary used for book supply chain metadata.
	 */
	ONIX("http://www.editeur.org/ONIX/book/codelists/current.html#"),
	
	SCHEMA("http://schema.org/"),
	
	/** 
	 * Defines W3C XML Schema data types.
	 */
	XSD("http://www.w3.org/2001/XMLSchema#")
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
