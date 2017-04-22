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

package com.github.i49.pulp.impl.metadata;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import com.github.i49.pulp.api.metadata.Identifier;
import com.github.i49.pulp.api.metadata.IdentifierScheme;

/**
 * The single implementation of {@link Identifier}.
 */
class DefaultIdentifier extends AbstractProperty implements Identifier {
	
	private final String value;
	private final Optional<IdentifierScheme> scheme;
	private final Optional<URI> schemeURI;
	
	public static DefaultIdentifier ofRandomUUID() {
		UUID uuid = UUID.randomUUID();
		String value = "urn:uuid:" + uuid.toString();
		return new DefaultIdentifier(value, IdentifierScheme.UUID);
	}

	public DefaultIdentifier(String value) {
		this.value = value;
		this.scheme = Optional.empty();
		this.schemeURI = Optional.empty();
	}

	public DefaultIdentifier(String value, IdentifierScheme scheme) {
		this.value = value;
		this.scheme = Optional.of(scheme);
		this.schemeURI = Optional.empty();
	}

	public DefaultIdentifier(String value, URI schemeURI) {
		this.value = value;
		this.scheme = Optional.empty();
		this.schemeURI = Optional.of(schemeURI);
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public Optional<IdentifierScheme> getScheme() {
		return scheme;
	}

	@Override
	public Optional<URI> getSchemeURI() {
		return schemeURI;
	}
}
