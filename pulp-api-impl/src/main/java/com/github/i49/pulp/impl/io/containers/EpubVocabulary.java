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

import java.net.URI;

import com.github.i49.pulp.api.vocabulary.Vocabulary;

public enum EpubVocabulary implements Vocabulary {
	
	CONTAINER_DOCUMENT("urn:oasis:names:tc:opendocument:xmlns:container"),
	PACKAGE_DOCUMENT("http://www.idpf.org/2007/opf")
	;
	
	private final URI uri;

	private EpubVocabulary(String uri) {
		this.uri = URI.create(uri);
	}
	
	@Override
	public URI getURI() {
		return uri;
	}
}
