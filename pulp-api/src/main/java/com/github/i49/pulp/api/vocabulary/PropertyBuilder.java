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

package com.github.i49.pulp.api.vocabulary;

/**
 *
 */
public interface PropertyBuilder<V, T extends TypedProperty<V>, R extends PropertyBuilder<V, T, R>> {

	/**
	 * Assigns a value to the property.
	 *   
	 * @param value the value of the property.
	 * @return this builder.
	 * @throws IllegalArgumentException if specified {@code value} was invalid one or {@code null}.
	 */
	R value(V value);
	
	/**
	 * Returns the built property.
	 * 
	 * @return the built property.
	 */
	T result();
}
