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

import com.github.i49.pulp.api.metadata.Representation;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TitleProperty;

/**
 * The default implementation of {@link TitleProperty}.
 */
class DefaultTitleProperty extends DefaultTextProperty<TitleProperty> implements TitleProperty {
	
	private String normalizedValue;
	
	private Optional<Representation> alternative;

	DefaultTitleProperty(Term term, String value) {
		super(term, value);
		alternative = Optional.empty();
	}
	
	@Override
	public TitleProperty fileAs(String value) {
		checkNotBlank(value, "value");
		this.normalizedValue = value.trim();
		return this;
	}

	@Override
	public Optional<String> getNormalizedValue() {
		return Optional.ofNullable(this.normalizedValue);
	}
	
	@Override
	public Optional<Representation> getAlternativeRepresentation() {
		return alternative;
	}

	@Override
	public TitleProperty resetNormalizedValue() {
		this.normalizedValue = null;
		return this;
	}
}
