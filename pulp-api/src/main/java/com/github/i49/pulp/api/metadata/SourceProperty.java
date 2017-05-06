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
 * A related resource from which the described resource is derived.
 * This property can be used for {@link DublinCore#SOURCE} and others.
 */
public interface SourceProperty extends TypedProperty<String> {

	/**
	 * Returns the scheme used for describing this source.
	 * 
	 * @return the scheme used for describing this source.
	 */
	Optional<String> getScheme();

	/**
	 * Clears the assigned scheme of this source.
	 * 
	 * @return this property.
	 */
	SourceProperty resetScheme();

	/**
	 * Assigns a new scheme to this source.
	 * 
	 * @param scheme the scheme of the source.
	 * @return this property.
	 * @throws IllegalArgumentException if {@code scheme} is invalid.
	 */
	SourceProperty setScheme(String scheme);

	/**
	 * {@inheritDoc}
	 */
	@Override
	SourceProperty setValue(String value);
}
