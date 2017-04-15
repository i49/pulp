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
 * 
 * @see <a href="http://dublincore.org/documents/dces/#identifier">Dublin Core Metadata Element Set, Version 1.1</a> 
 */
public interface Identifier extends Property {
	
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
}
