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

import java.net.URI;
import java.util.Optional;

import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.PropertyBuilder;

/**
 * An identifier of the resource.
 * This property represents the term of {@link DublinCore#IDENTIFIER}.
 */
public interface Identifier extends Property<String> {

	/**
	 * Returns the scheme of this identifier.
	 * 
	 * @return one of schemes defined in {@link IdentifierScheme}, may be empty.
	 */
	Optional<IdentifierScheme> getScheme();

	/**
	 * Returns the URI representing the scheme of this identifier.
	 * 
	 * @return the scheme URI of this identifier, may be empty.
	 */
	Optional<URI> getSchemeURI();
	
	/**
	 * Checks if this identifier is primary one or not.
	 * Primary identifier can be the identifier part of <i>release identifier</i>.
	 * 
	 * @return {@code true} if this identifier is primary, {@code false} otherwise.
	 */
	boolean isPrimary();

	/**
	 * Builder for building instances of {@link Identifier}.
	 */
	public interface Builder extends PropertyBuilder<String, Identifier, Builder> {
		
		/**
		 * Specifies that the identifier to build is primary one or not.
		 * 
		 * @param primary {@code true} if the identifier is primary, {@code false} otherwise.
		 * @return this builder.
		 */
		Builder primary(boolean primary);
		
		/**
		 * Specifies the scheme of the identifier.
		 * 
		 * @param scheme one of schemes defined in {@link IdentifierScheme}.
		 * @return this builder.
		 * @throws IllegalArgumentException if given {@code scheme} is {@code null}.
		 */
		Builder scheme(IdentifierScheme scheme);
		
		/**
		 * Specifies the scheme of the identifier specified by URI.
		 * 
		 * @param schemeURI the URI representing the scheme.
		 * @return this builder.
		 * @throws IllegalArgumentException if given {@code schemeURI} is {@code null}.
		 */
		Builder scheme(URI schemeURI);
	}
}
