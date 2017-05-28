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

import java.util.Optional;

/**
 * Property that can have a normalized form of the property value.
 */
public interface Normalizable {

	/**
	 * Returns the normalized form of the value of this property.
	 * 
	 * @return the normalized form of the property value, may be empty.
	 */
	Optional<String> getNormalizedValue();
	
	/**
	 * Builder for building instances of type which implements {@link Normalizable}.
	 *
	 * @param <R> the actual builder type.
	 */
	public interface Builder<R extends Builder<R>> {
		
		/**
		 * Specifies the normalized form of the property value.
		 * 
		 * @param value the normalized property value.
		 * @return this builder.
		 * @throws IllegalArgumentException if given {@code value} is {@code null}.
		 */
		R fileAs(String value);
	}
}
