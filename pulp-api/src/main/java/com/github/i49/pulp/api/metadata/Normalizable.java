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
 * Property that can have normalized value.
 * 
 * @param <S> the type of the extended interface.
 */
public interface Normalizable<S> {

	/**
	 * Assigns the normalized form of the property value.
	 * 
	 * @param value the normalized value.
	 * @return this property.
	 * @throws IllegalArgumentException if {@code value} is invalid.
	 */
	S fileAs(String value);
	
	/**
	 * Returns the normalized form of the value of this property.
	 * 
	 * @return the normalized form of the property value.
	 */
	Optional<String> getNormalizedValue_();
	
	/**
	 * Clears the normalized value assigned.
	 * 
	 * @return this property.
	 */
	S resetNormalizedValue();
}
