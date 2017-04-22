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

package com.github.i49.pulp.core.metadata;

import java.util.Optional;

import com.github.i49.pulp.api.metadata.Representation;
import com.github.i49.pulp.api.metadata.Title;
import com.github.i49.pulp.api.metadata.TitleBuilder;

/**
 * The single implementation of {@link Title} property.
 */
class DefaultTitle extends AbstractTextProperty implements Title {
	
	private final String normalizedValue;
	private final Optional<Representation> alternative;

	static TitleBuilder builder(String value) {
		return new Builder(value);
	}
	
	private DefaultTitle(Builder builder) {
		super(builder);
		this.normalizedValue = builder.getNormalizedValue();
		this.alternative = Optional.ofNullable(builder.getAlternative());
	}

	@Override
	public String getNormalizedValue() {
		if (normalizedValue == null) {
			return getValue();
		}
		return normalizedValue;
	}
	
	@Override
	public Optional<Representation> getAlternativeRepresentation() {
		return alternative;
	}
	
	private static class Builder extends AbstractPropertyBuilder<Title, TitleBuilder> implements TitleBuilder {

		private Builder(String value) {
			super(value);
		}
		
		@Override
		public Title build() {
			return new DefaultTitle(this);
		}
	}
}
