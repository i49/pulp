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

package com.github.i49.pulp.api.vocabularies.dc;

import java.util.Optional;

import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.PropertyBuilder;

/**
 * A related resource from which the described resource is derived.
 * This property represents the term of {@link DublinCore#SOURCE}.
 */
public interface Source extends Property<String> {

	/**
	 * Returns the scheme of this source.
	 * 
	 * @return the scheme of this source, may be empty.
	 */
	Optional<String> getScheme();

	/**
	 * Builder for building instances of {@link Source}.
	 */
	public interface Builder extends PropertyBuilder<String, Source, Builder> {
		
		/**
		 * Specifies the scheme of the source.
		 * 
		 * @param scheme the scheme of the source.
		 * @return this builder.
		 * @throws IllegalArgumentException if given {@code scheme} is blank.
		 */
		Builder scheme(String scheme);
	}
}
