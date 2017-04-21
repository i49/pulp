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

import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.metadata.BasicTerm;
import com.github.i49.pulp.api.metadata.Representation;
import com.github.i49.pulp.api.metadata.Title;
import com.github.i49.pulp.api.metadata.TitleBuilder;

/**
 * An implementation of {@link Title} property.
 */
class TitleImpl extends AbstractTextProperty implements Title {
	
	private final String normalizedValue;
	private final Optional<Representation> alternative;

	static TitleBuilder builder(String value) {
		return new Builder(value);
	}
	
	private TitleImpl(Builder builder) {
		super(BasicTerm.TITLE, builder.value, builder);
		this.normalizedValue = builder.normalizedValue;
		this.alternative = Optional.of(builder.alternative);
	}

	@Override
	public String getNormalizedValue() {
		return normalizedValue;
	}
	
	@Override
	public Optional<Representation> getAlternativeRepresentation() {
		return alternative;
	}
	
	private static class Builder extends AbstractTextProperty.Builder<Title, TitleBuilder> implements TitleBuilder {

		private final String value;
		private String normalizedValue;
		private Representation alternative;
		
		Builder(String value) {
			this.value = value;
		}
		
		@Override
		public TitleBuilder fileAs(String value) {
			if (value == null) {
				throw new IllegalArgumentException("\"value\" must not be null"); 
			}
			this.normalizedValue = value;
			return this;
		}

		@Override
		public TitleBuilder alternative(String value, Locale language) {
			if (value == null) {
				throw new IllegalArgumentException("\"value\" must not be null"); 
			}
			if (language == null) {
				throw new IllegalArgumentException("\"language\" must not be null"); 
			}
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Title build() {
			return new TitleImpl(this);
		}
	}
}
