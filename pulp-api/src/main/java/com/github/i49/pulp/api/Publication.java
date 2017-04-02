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

package com.github.i49.pulp.api;

import java.util.Iterator;
import java.util.Set;

/**
 * A publication that represents a single intellectual or artistic work
 * such as book, magazine, newspaper, journal, office document and so on.
 * A publication consists of one or more {@link Rendition}s.
 */
public interface Publication extends Iterable<Rendition> {
	
	/**
	 * Returns the number of renditions in this publication.
	 * @return the number of renditions in this publication.
	 */
	int getNumberOfRenditions();
	
	/**
	 * Returns the default {@link Rendition} of this publication.
	 * @return the default {@link Rendition} of this publication.
	 */
	Rendition getDefaultRendition();
	
	/**
	 * Adds a new {@link Rendition} sitting at the default location in the container.
	 * @return created rendition.
	 */
	Rendition addRendition();
	
	/**
	 * Adds a new {@link Rendition} with the location of the Package Document in the container specified.
	 * @param location the local location of the Package Document. e.g. "EPUB/package.opf".
	 * @return created rendition.
	 * @throws IllegalArgumentException if specified {@code location} is invalid.
	 */
	Rendition addRendition(String location);
	
	/**
	 * Returns a set containing all resources required by this publication.
	 * @return an immutable set of publication resources. 
	 */
	Set<PublicationResource> getAllResources();
	
	/**
	 * Returns an iterator that can iterate over all renditions in this publication.
	 * @return an iterator over renditions.
	 */
	@Override
	Iterator<Rendition> iterator();
}
