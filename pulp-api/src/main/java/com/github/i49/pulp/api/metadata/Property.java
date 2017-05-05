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

package com.github.i49.pulp.api.metadata;

import java.util.Optional;

/**
 * A property composing the metadata for the given rendition.
 */
public interface Property<V> {

	/**
	 * Returns the term of this property.
	 * 
	 * @return the term of this property.
	 */
	Term getTerm();
	
	/**
	 * Return the type of this property.
	 * 
	 * @return the type of this property.
	 */
	default PropertyType getType() {
		return getTerm().getType();
	}
	
	/**
	 * Returns the value of this property.
	 * 
	 * @return the value of this property.
	 */
	V getValue();
	
	/**
	 * Returns the value of this property as string.
	 * 
	 * @return the string representation of the property value.
	 */
	default String getValueAsString() {
		return getValue().toString();
	}
	
	/**
	 * Returns the normalized form of the value of this property.
	 * By default this method returns an empty {@link Optional} instance.
	 * 
	 * @return the normalized form of the property value.
	 */
	default Optional<String> getNormalizedValue() {
		return Optional.empty();
	}
	
	/**
	 * Assigns a new value to this property.
	 * 
	 * @param value the value to assign.
	 * @return this property.
	 * @throws IllegalArgumentException if {@code value} is invalid.
	 * @throws UnsupportedOperationException if this property is immutable.
	 */
	default Property<V> setValue(V value) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Returns the same result as {@link #getValue()}.
	 * 
	 * @return the same result as {@link #getValue()}.
	 */
	@Override
	String toString();

	/**
	 * A builder for building a property.
	 *
	 * @param <P> the type of the property to build. 
	 */
	public interface Builder<P extends Property> {

		/**
		 * Builds a property.
		 * 
		 * @return a property built.
		 */
		P build();
	}
}
