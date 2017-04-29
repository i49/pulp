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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A publication that represents a single intellectual or artistic work
 * such as book, magazine, newspaper, journal, office document and so on.
 * A publication consists of one or more {@link Rendition}s.
 */
public interface Publication extends Iterable<Rendition> {
	
	/**
	 * Returns the number of renditions that consists of this publication.
	 * 
	 * @return the number of renditions in this publication.
	 */
	int getNumberOfRenditions();
	
	/**
	 * Returns the default {@link Rendition} of this publication.
	 * 
	 * @return the default {@link Rendition} of this publication.
	 */
	Rendition getDefaultRendition();
	
	/**
	 * Adds a new {@link Rendition} sitting at the default location in the container.
	 * 
	 * @return created rendition.
	 */
	Rendition addRendition();
	
	/**
	 * Adds a new {@link Rendition} with the location of the Package Document in the container specified.
	 * 
	 * @param location the local location of the Package Document. e.g. "EPUB/package.opf".
	 * @return created rendition.
	 * @throws IllegalArgumentException if specified {@code location} is invalid or {@code null}.
	 */
	Rendition addRendition(String location);
	
	/**
	 * Returns an iterator that can iterate over all renditions in this publication.
	 * 
	 * @return an iterator over renditions.
	 */
	@Override
	Iterator<Rendition> iterator();

	/**
	 * Returns a set containing all resources required by this publication.
	 * 
	 * @return an immutable set of publication resources. 
	 */
	Set<PublicationResource> getAllResources();
	
	/**
	 * Returns whether this publication contains a resource at the specified location or not. 
	 * 
	 * @param location the path relative to the root directory of the container such as "EPUB/chapter1.xhtml",  
	 *                 this must be path-rootless form.  
	 * @return {@code true} if this publication contains the resource at the specified location, {@code false} otherwise.
	 * @throws IllegalArgumentException if given {@code location} is {@code null} or invalid.
	 */
	boolean containsResource(String location);
	
	/**
	 * Returns the publication resource at the specified location in this publication. 
	 * This method can be called safely in case that {@link #containsResource(String)} returns {@code true}.
	 * 
	 * @param location the path relative to the root directory of the container such as "EPUB/chapter1.xhtml",
	 *                 this must be path-rootless form.  
	 * @return the {@link PublicationResource} found at the specified location, never be {@code null}. 
	 * @throws IllegalArgumentException if given {@code location} is {@code null} or invalid.
	 * @throws NoSuchElementException if this publication does not contain any resource at the specified location.
	 * @see #containsResource(String)
	 */
	PublicationResource getResource(String location);
}
