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

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import com.github.i49.pulp.api.metadata.DublinCore;
import com.github.i49.pulp.api.metadata.IdentifierProperty;
import com.github.i49.pulp.api.metadata.IdentifierScheme;

/**
 * The default implementation of {@link IdentifierProperty}.
 */
class DefaultIdentifierProperty extends AbstractProperty<String> implements IdentifierProperty {
	
	private String value;
	private IdentifierScheme scheme;
	private URI schemeURI;
	
	public static DefaultIdentifierProperty ofRandomUUID() {
		UUID uuid = UUID.randomUUID();
		String value = "urn:uuid:" + uuid.toString();
		return new DefaultIdentifierProperty(value, IdentifierScheme.UUID);
	}

	public DefaultIdentifierProperty(String value) {
		super(DublinCore.IDENTIFIER);
		this.value = value;
		this.scheme = null;
		this.schemeURI = null;
	}

	public DefaultIdentifierProperty(String value, IdentifierScheme scheme) {
		super(DublinCore.IDENTIFIER);
		this.value = value;
		this.scheme = scheme;
		this.schemeURI = null;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public Optional<IdentifierScheme> getScheme() {
		return Optional.ofNullable(scheme);
	}

	@Override
	public Optional<URI> getSchemeURI() {
		return Optional.ofNullable(schemeURI);
	}
	
	@Override
	public IdentifierProperty resetScheme() {
		this.scheme = null;
		this.schemeURI = null;
		return this;
	}
	
	@Override
	public IdentifierProperty setScheme(IdentifierScheme scheme) {
		checkNotNull(scheme, "scheme");
		this.scheme = scheme;
		this.schemeURI = null;
		return this;
	}

	@Override
	public IdentifierProperty setSchemeURI(URI uri) {
		checkNotNull(uri, "uri");
		this.scheme = null;
		this.schemeURI = uri;
		return this;
	}
	
	@Override
	public IdentifierProperty setValue(String value) {
		checkNotBlank(value, "value");
		this.value = value;
		return this;
	}
}
