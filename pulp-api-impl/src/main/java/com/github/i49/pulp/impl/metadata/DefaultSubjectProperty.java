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

import com.github.i49.pulp.api.metadata.SubjectProperty;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.SubjectAuthority;

/**
 * The default implementation of {@link SubjectProperty}.
 */
class DefaultSubjectProperty extends AbstractProperty<String> implements SubjectProperty {

	private String value;
	
	private SubjectAuthority authority;
	private URI scheme;
	private String code;
	
	public DefaultSubjectProperty(Term term, String value) {
		super(term);
		assert(value != null);
		this.value = value.trim();
		this.authority = null;
		this.scheme = null;
		this.code = null;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Optional<SubjectAuthority> getAuthority() {
		return Optional.ofNullable(authority);
	}

	@Override
	public Optional<URI> getScheme() {
		return Optional.ofNullable(scheme);
	}

	@Override
	public Optional<String> getCode() {
		return Optional.ofNullable(code);
	}
	
	@Override
	public SubjectProperty resetCode() {
		this.authority = null;
		this.scheme = null;
		this.code = null;
		return this;
	}
	
	@Override
	public SubjectProperty setCode(SubjectAuthority authority, String code) {
		checkNotNull(authority, "authority");
		checkNotBlank(code, "code");
		this.authority = authority;
		this.code = code;
		return this;
	}

	@Override
	public SubjectProperty setCode(URI scheme, String code) {
		checkNotNull(scheme, "scheme");
		checkNotBlank(code, "code");
		this.scheme = scheme;
		this.code = code;
		return this;
	}

	@Override
	public SubjectProperty setValue(String value) {
		checkNotBlank(value, "value");
		this.value = value.trim();
		return this;
	}
}
