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

import com.github.i49.pulp.api.metadata.Direction;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.PropertyBuilder;
import com.github.i49.pulp.api.metadata.Representation;

/**
 * A skeletal implementation of {@link PropertyBuilder}.
 *
 * @param <P> the type of the property to build.
 * @param <T> actual builder type.
 */
abstract class AbstractPropertyBuilder<P extends Property, T extends PropertyBuilder<P>> 
	implements PropertyBuilder<P> {

	private final String value;
	private Locale language;
	private Direction direction;
	private String normalizedValue;
	private Representation alternative;
	private String role;
	
	protected AbstractPropertyBuilder(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public Locale getLanguage() {
		return language;
	}

	public Direction getDirection() {
		return direction;
	}

	public String getNormalizedValue() {
		return normalizedValue;
	}

	public Representation getAlternative() {
		return alternative;
	}
	
	public String getRole() {
		return role;
	}

	public T language(Locale language) {
		if (language == null) {
			throw new IllegalArgumentException("\"language\" must not be null"); 
		}
		this.language = language;
		return self();
	}

	public T direction(Direction direction) {
		if (direction == null) {
			throw new IllegalArgumentException("\"direction\" must not be null"); 
		}
		this.direction = direction;
		return self();
	}
	
	public T fileAs(String value) {
		if (value == null) {
			throw new IllegalArgumentException("\"value\" must not be null"); 
		}
		this.normalizedValue = value;
		return self();
	}

	public T alternative(String value, Locale language) {
		if (value == null) {
			throw new IllegalArgumentException("\"value\" must not be null"); 
		}
		if (language == null) {
			throw new IllegalArgumentException("\"language\" must not be null"); 
		}
		this.alternative = new DefaultRepresentation(value, language);
		return self();
	}
	
	public T role(String role) {
		if (role == null) {
			throw new IllegalArgumentException("\"role\" must not be null"); 
		}
		this.role = role;
		return self();
	}
	
	@SuppressWarnings("unchecked")
	private T self() {
		return (T)this;
	}
}
