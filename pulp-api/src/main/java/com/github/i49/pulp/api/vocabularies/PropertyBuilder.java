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

package com.github.i49.pulp.api.vocabularies;

/**
 * The base type of all types of property builders.
 * 
 * @param <V> the type of the property value.
 * @param <T> the type of the property.
 * @param <R> the type of the derived property builder producing the final property.
 */
public interface PropertyBuilder<V, T extends Property<V>, R extends PropertyBuilder<V, T, R>> {

	/**
	 * Returns the term of the property to build.
	 * 
	 * @return the term of the property.
	 */
	Term getTerm();
	
	/**
	 * Specifies a value of the property.
	 *   
	 * @param value the value of the property.
	 * @return this builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	R value(V value);
	
	/**
	 * Returns the built property.
	 * 
	 * @return the built property.
	 */
	T result();
}
