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

import java.net.URI;
import java.util.Optional;

/**
 * An identifier associated with the given Rendition, such as a UUID, DOI or ISBN.
 * This property can be used solely for {@link DublinCore#IDENTIFIER}.
 */
public interface IdentifierProperty extends TypedProperty<String> {

	/**
	 * Returns the scheme used for this identifier.
	 * 
	 * @return the scheme used for this identifier.
	 */
	Optional<IdentifierScheme> getScheme();
	
	/**
	 * Returns the URI representing the scheme used for this identifier.
	 * 
	 * @return the URI representing the scheme used for this identifier. 
	 */
	Optional<URI> getSchemeURI();
	
	/**
	 * Clears the scheme assigned.
	 * 
	 * @return this property.
	 */
	IdentifierProperty resetScheme();
	
	/**
	 * Assigns the scheme for this identifier.
	 * 
	 * @param scheme the scheme of this identifier.
	 * @return this property.
	 * @throws IllegalArgumentException if {@code scheme} is {@code null}.
	 */
	IdentifierProperty setScheme(IdentifierScheme scheme);

	/**
	 * Assigns the URI which represents the scheme for this identifier.
	 * 
	 * @param uri the URI of the scheme.
	 * @return this property.
	 * @throws IllegalArgumentException if {@code uri} is {@code null}.
	 */
	IdentifierProperty setSchemeURI(URI uri);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	IdentifierProperty setValue(String value);
}
