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

import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.metadata.Direction;
import com.github.i49.pulp.api.metadata.TextTypeProperty;

/**
 * A skeletal implementation of {@link TextTypeProperty}.
 */
abstract class AbstractTextTypeProperty extends AbstractProperty implements TextTypeProperty {
	
	private final String value;
	private final Optional<Locale> language;
	private final Optional<Direction> direction;
	
	protected AbstractTextTypeProperty(String value, Optional<Locale> language, Optional<Direction> direction) {
		this.value = value;
		this.language = language;
		this.direction = direction;
	}
	
	protected AbstractTextTypeProperty(AbstractPropertyBuilder<?, ?> builder) {
		this.value = builder.getValue();
		this.language = builder.getLanguage();
		this.direction = builder.getDirection();
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public Optional<Locale> getLanguage() {
		return language;
	}

	@Override
	public Optional<Direction> getDirection() {
		return direction;
	}
}

