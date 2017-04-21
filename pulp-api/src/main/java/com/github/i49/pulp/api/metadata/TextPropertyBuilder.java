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

/**
 * A builder for a text property.
 * 
 * @param <P> the type of the property to build.
 * @param <T> actual builder type.
 */
public interface TextPropertyBuilder<P extends TextProperty, T extends TextPropertyBuilder<P, T>> extends PropertyBuilder<P> {

	/**
	 * Specifies the language of the text.
	 * 
	 * @param language the language of the text.
	 * @return this builder.
	 * @throws IllegalArgumentException if {@code language} is {@code null}.
	 */
	T language(Locale language);
	
	/**
	 * Specifies the directionality of the text.
	 * 
	 * @param direction the directionality of the text.
	 * @return this builder.
	 * @throws IllegalArgumentException if {@code direction} is {@code null}.
	 */
	T direction(Direction direction);
}
