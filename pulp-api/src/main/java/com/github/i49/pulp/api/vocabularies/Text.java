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
 * Text type property which can have language and direction.
 */
public interface Text extends Property<String> {

	/**
	 * Returns the direction of the text, such as left-to-right and right-to-left.
	 * 
	 * @return the direction of the text, may be empty.
	 */
	Optional<Direction> getDirection();

	/**
	 * Returns the language of the text.
	 * 
	 * @return the language of the text, may be empty.
	 */	
	Optional<Locale> getLanguage();
	
	/**
	 * Builder for building instances of types derived from {@link Text}.
	 *
	 * @param <T> the type of the property to be built by this builder.
	 * @param <R> the actual builder type.
	 */
	public interface Builder<T extends Text, R extends Builder<T, R>> extends PropertyBuilder<String, T, R> {
	
		/**
		 * Specifies the direction of the text, such as left-to-right and right-to-left.
		 * 
		 * @param direction the direction of the text, cannot be {@code null}.
		 * @return this builder.
		 * @throws IllegalArgumentException if given {@code direction} is {@code null}.
		 */
		R direction(Direction direction);
		
		/**
		 * Specifies the language of the text.
		 * 
		 * @param language the language of the text, cannot be {@code null}.
		 * @return this builder.
		 * @throws IllegalArgumentException if given {@code language} is {@code null}.
		 */
		R language(Locale language);
	}
}
