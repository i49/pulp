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

import java.util.Optional;

import com.github.i49.pulp.api.metadata.SourceProperty;
import com.github.i49.pulp.api.metadata.Term;

/**
 * The default implementation of {@link SourceProperty}.
 */
class DefaultSourceProperty extends AbstractProperty<String> implements SourceProperty {

	private String value;
	private String scheme;
	
	public DefaultSourceProperty(Term term, String value) {
		super(term);
		assert(value != null);
		this.value = value.trim();
		this.scheme = null;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public Optional<String> getScheme() {
		return Optional.ofNullable(scheme);
	}

	@Override
	public DefaultSourceProperty resetScheme() {
		this.scheme = null;
		return this;
	}

	@Override
	public DefaultSourceProperty setScheme(String scheme) {
		checkNotBlank(scheme, "scheme");
		this.scheme = scheme.trim();
		return this;
	}

	@Override
	public DefaultSourceProperty setValue(String value) {
		checkNotBlank(value, "value");
		this.value = value.trim();
		return this;
	}
}
