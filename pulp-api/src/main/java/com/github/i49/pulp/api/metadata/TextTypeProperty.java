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

import java.util.Locale;
import java.util.Optional;

/**
 * A metadata property that has a human-readable text as its value.
 */
public interface TextTypeProperty extends TypedProperty<String> {

	/**
	 * Returns the language of the text.
	 * 
	 * @return the language of the text.
	 */
	Optional<Locale> getLanguage();
	
	/**
	 * Returns the direction of the text.
	 * 
	 * @return the direction of the text, such as left-to-right and right-to-left.
	 */
	Optional<Direction> getDirection();

	/**
	 * A builder for building a text-type property.
	 * 
	 * @param <P> the type of the property to build.
	 * @param <T> actual builder type which extends this builder.
	 */
	public interface Builder<P extends TextTypeProperty, T extends Builder<P, T>> extends Property.Builder<P> {

		/**
		 * Optionally specifies the language of the text.
		 * 
		 * @param language the language of the text.
		 * @return this builder.
		 * @throws IllegalArgumentException if {@code language} is {@code null}.
		 */
		T language(Locale language);
		
		/**
		 * Optionally specifies the directionality of the text,
		 * such as left-to-right and right-to-left.
		 * 
		 * @param direction the directionality of the text.
		 * @return this builder.
		 * @throws IllegalArgumentException if {@code direction} is {@code null}.
		 */
		T direction(Direction direction);
	}
}
