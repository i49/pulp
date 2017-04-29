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

import com.github.i49.pulp.api.metadata.Subject;
import com.github.i49.pulp.api.metadata.SubjectAuthority;

/**
 * The default implementation of {@link Subject} property.
 */
class DefaultSubject extends AbstractProperty implements Subject {

	private final String value;
	private final Optional<SubjectAuthority> authority;
	private final Optional<URI> scheme;
	private final Optional<String> code;
	
	public DefaultSubject(String value) {
		assert(value != null);
		this.value = value.trim();
		this.authority = Optional.empty();
		this.scheme = Optional.empty();
		this.code = Optional.empty();
	}

	public DefaultSubject(String value, SubjectAuthority authority, String code) {
		assert(value != null && authority != null && code != null);
		this.value = value.trim();
		this.authority = Optional.of(authority);
		this.scheme = Optional.empty();
		this.code = Optional.of(code.trim());
	}

	public DefaultSubject(String value, URI scheme, String code) {
		assert(value != null && scheme != null && code != null);
		this.value = value.trim();
		this.authority = Optional.empty();
		this.scheme = Optional.of(scheme);
		this.code = Optional.of(code.trim());
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Optional<SubjectAuthority> getAuthority() {
		return authority;
	}

	@Override
	public Optional<URI> getScheme() {
		return scheme;
	}

	@Override
	public Optional<String> getCode() {
		return code;
	}
}
