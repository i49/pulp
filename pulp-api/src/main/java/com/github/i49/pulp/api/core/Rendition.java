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

package com.github.i49.pulp.api.core;

import java.net.URI;

/**
 * A logical document entity consisting of a set of interrelated resources 
 * representing one rendering of a {@link Publication}. 
 */
public interface Rendition {
	
	/**
	 * Return the publication that contains this rendition.
	 * 
	 * @return the publication containing this rendition, never be {@code null}.
	 */
	Publication getPublication();

	/**
	 * Returns the location of the package document of this rendition,
	 * which is relative to the root directory of the container.
	 * e.g., "EPUB/package.opf".
	 * 
	 * @return the relative URI of the package document, which path component holds the actual location.
	 */
	URI getLocation();
	
	/**
	 * Resolves the given location which is relative to the Package Document of this rendition.
	 * 
	 * @param location the location relative to the Package Document of this rendition. 
	 * @return resolved URI which is relative to the root directory of the EPUB container.
	 * @throws IllegalArgumentException if given {@code location} is {@code null}.
	 */
	URI resolve(String location);

	/**
	 * Return the metadata of this rendition.
	 * 
	 * @return the metadata of this rendition.
	 */
	Metadata getMetadata();
	
	/**
	 * Returns the manifest of this rendition that carries all publication resources
	 * necessary to render this rendition properly. 
	 * 
	 * @return the manifest of this rendition.
	 */
	Manifest getManifest();

	/**
	 * Returns the spine of this rendition that binds the ordered pages of this rendition.
	 * 
	 * @return the spine of this rendition.
	 */
	Spine getSpine();
}
