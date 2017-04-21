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

import com.github.i49.pulp.api.metadata.Direction;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TextProperty;
import com.github.i49.pulp.api.metadata.TextPropertyBuilder;

/**
 * A skeletal implementation of {@link TextProperty}.
 */
abstract class AbstractTextProperty extends AbstractProperty implements TextProperty {
	
	private final Optional<Locale> language;
	private final Optional<Direction> direction;
	
	protected AbstractTextProperty(Term term, String value, Optional<Locale> language, Optional<Direction> direction) {
		super(term, value);
		this.language = language;
		this.direction = direction;
	}
	
	protected AbstractTextProperty(Term term, String value, Builder<?, ?> builder) {
		super(term, value);
		this.language = Optional.of(builder.langauge);
		this.direction = Optional.of(builder.direction);
	}
	
	@Override
	public Optional<Locale> getLanguage() {
		return language;
	}

	@Override
	public Optional<Direction> getDirection() {
		return direction;
	}
	
	abstract static class Builder<P extends TextProperty, T extends TextPropertyBuilder<P, T>> implements TextPropertyBuilder<P, T> {

		private Locale langauge;
		private Direction direction;
		
		@SuppressWarnings("unchecked")
		@Override
		public T language(Locale language) {
			if (language == null) {
				throw new IllegalArgumentException("\"language\" must not be null"); 
			}
			this.langauge = language;
			return (T)this;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T direction(Direction direction) {
			if (direction == null) {
				throw new IllegalArgumentException("\"direction\" must not be null"); 
			}
			this.direction = direction;
			return (T)this;
		}
	}
}

