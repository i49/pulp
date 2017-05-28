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

import java.util.Locale;
import java.util.Optional;

/**
 * Property that can have an alternative value in different language.
 * Please note that only single value can be assigned as an alternative representation.
 */
public interface Multilingual {

	/**
	 * Returns the language of the alternative representation.
	 * 
	 * @return the language of the alternative representation, may be empty.
	 */
	Optional<Locale> getAlternativeLanguage();

	/**
	 * Returns the alternative representation of the property value.
	 * 
	 * @return the alternative representation of the property value, may be empty.
	 */
	Optional<String> getAlternativeValue();

	/**
	 * Builder for building instances of type which implements {@link Multilingual}.
	 *
	 * @param <R> the actual builder type.
	 */
	public interface Builder<R extends Builder<R>> {
		
		/**
		 * Specifies the alternative representation of this property.
		 * 
		 * @param value the alternative representation of the main value. 
		 * @param language the language of the alternative representation.
		 * @return this builder.
		 * @throws IllegalArgumentException if one or more parameters are invalid.
		 */
		R alternativeValue(String value, Locale language);
	}
}
