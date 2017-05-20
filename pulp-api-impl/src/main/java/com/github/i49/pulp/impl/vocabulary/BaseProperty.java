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

import static com.github.i49.pulp.impl.base.Preconditions.checkNotNull;

import com.github.i49.pulp.api.vocabulary.PropertyBuilder;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.TypedProperty;

/**
 * The base class of all property classes.
 * 
 * @param <V> the type of the property value.
 */
public class BaseProperty<V> implements TypedProperty<V> {

	private final Term term;
	private final V value;
	
	public BaseProperty(Term term, V value) {
		assert(term != null);
		assert(value != null);
		this.term = term;
		this.value = value;
	}
	
	public BaseProperty(Term term, Builder<V, ?, ?> b) {
		this(term, b.getValue());
	}
	
	@Override
	public Term getTerm() {
		return term;
	}

	@Override
	public V getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		String termName = getTerm().localName();
		b.append("{").append(termName).append(":\"").append(getValueAsString()).append("\"}");
		return b.toString();
	}

	/**
	 * A skeletal implementation of {@link PropertyBuilder}.
	 */
	public static abstract class Builder<V, T extends TypedProperty<V>, R extends PropertyBuilder<V, T, R>> 
		implements PropertyBuilder<V, T, R> {

		private V value;
		// built property. 
		private T property;
		
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
		 * Returns the current value which will be assigned to the property.
		 * 
		 * @return current value of the property.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Builds the property. 
		 * Should be implemented by concrete builder.
		 * 
		 * @return the built property.
		 */
		protected abstract T build();
		
		@SuppressWarnings("unchecked")
		protected final R self() {
			return (R)this;
		}
	}
}
