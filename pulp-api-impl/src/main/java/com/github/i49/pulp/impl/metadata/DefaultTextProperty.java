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

import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.metadata.Direction;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TextProperty;

/**
 * A skeletal implementation of {@link TextProperty}.
 * 
 * @param <S> the final descendant interface.
 */
class DefaultTextProperty<S extends TextProperty> extends AbstractProperty<String> implements TextProperty {
	
	private Direction direction;
	private Locale language;
	private String value;
	
	DefaultTextProperty(Term term, String value) {
		super(term);
		assert(value != null);
		this.direction = null;
		this.language = null;
		this.value = value.trim();
	}

	@Override
	public Optional<Direction> getDirection() {
		return Optional.ofNullable(direction);
	}

	@Override
	public Optional<Locale> getLanguage() {
		return Optional.ofNullable(language);
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public S resetDirection() {
		this.direction = null;
		return self();
	}

	@Override
	public S resetLanguage() {
		this.language = null;
		return self();
	}

	@Override
	public S setDirection(Direction direction) {
		checkNotNull(direction, "direction");
		this.direction = direction;
		return self();
	}
	
	@Override
	public S setLanguage(Locale language) {
		checkNotNull(language, "language");
		this.language = language;
		return self();
	}

	@Override
	public S setValue(String value) {
		checkNotBlank(value, "value");
		this.value = value.trim();
		return self();
	}
	
	@SuppressWarnings("unchecked")
	private S self() {
		return (S)this;
	}
}

