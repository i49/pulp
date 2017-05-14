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

package com.github.i49.pulp.impl.vocabulary;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import com.github.i49.pulp.api.vocabulary.PropertyBuilder;
import com.github.i49.pulp.api.vocabulary.TypedProperty;

/**
 * A skeletal implementation of {@link PropertyBuilder}.
 */
public abstract class AbstractPropertyBuilder<V, T extends TypedProperty<V>, R extends PropertyBuilder<V, T, R>> 
	implements PropertyBuilder<V, T, R> {

	private V value;
	// built property. 
	private T property;
	
	public V getValue() {
		return value;
	}

	@Override
	public R value(V value) {
		checkNotNull(value, "value");
		this.value = value;
		return self();
	}
	
	@Override
	public T result() {
		if (property == null) {
			property = build();
		}
		return property;
	}
	
	/**
	 * Builds the property. Should be implemented by concrete builder.
	 * 
	 * @return the built property.
	 */
	protected abstract T build();
	
	@SuppressWarnings("unchecked")
	protected R self() {
		return (R)this;
	}
}
