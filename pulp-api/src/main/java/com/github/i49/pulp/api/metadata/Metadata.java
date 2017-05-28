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

import java.util.Set;

import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.Term;

/**
 * A set of meta information describing an EPUB publication and its renditions.
 */
public interface Metadata {
	
	/**
	 * Adds a new property of the specific term, which can be selected by a returned selector.
	 *  
	 * @return the selector of the property builders, never be {@code null}.
	 */
	PropertyBuilderSelector add();

	/**
	 * Adds the specified property to this metadata if it is not already present.
	 * 
	 * @param property the property to add to this metadata.
	 * @return {@code true} if this metadata did not already contain the specified property, {@code false} otherwise. 
	 * @throws IllegalArgumentException if given {@code property} is {@code null}.
	 * @throws IllegalStateException if the maximum number of properties allowed for the term were already added.
	 */
	boolean add(Property<?> property);
	
	/**
	 * Clears all properties in this metadata.
	 * All required properties will be cleared also.
	 */
	void clear();

	/**
	 * Tests the existence of the property of the specific term, which can be selected by a returned selector.
	 * 
	 * @return the selector of the property testers, never be {@code null}.
	 */
	PropertyTesterSelector contains();
	
	/**
	 * Counts properties of the specific term, which can be selected by a returned selector.
	 *  
	 * @return the selector of the property counters, never be {@code null}.
	 */
	PropertyCounterSelector count();
	
	/**
	 * Finds all properties of the specific term, which can be selected by a returned selector.
	 *  
	 * @return the selector of the property listers, never be {@code null}.
	 */
	PropertyListerSelector find();
	
	/**
	 * Adds the required properties to this metadata if one or more of them are missing.
	 * 
	 * <p>The following properties may be added.</p>
	 * <dl>
	 * <dt>identifier</dt>
	 * <dd>A UUID is randomly generated.</dd>
	 * <dt>title</dt>
	 * <dd>A default title is added.</dd>
	 * <dt>language</dt>
	 * <dd>The language of the current locale is added.</dd>
	 * <dt>modified</dt>
	 * <dd>Current time is added.</dd>
	 * </dl>
	 */
	void fillMissingProperties();

	/**
	 * Returns the <i>release identifier</i> of the rendition
	 * that uniquely identifies a specific version of it.
	 * 
	 * <p>Release identifier of the rendition consists of the following two properties:</p>
	 * <ul>
	 * <li>identifier of the rendition</li>
	 * <li>last modification time of the rendition</li>
	 * </ul>
	 * 
	 * @return the release identifier of the rendition, never be {@code null}.
	 */
	ReleaseIdentifier getReleaseIdentifier();
	
	/**
	 * Checks if this metadata contains all required properties.
	 * <p>This method returns {@code true} if all of the following properties exist
	 * in this metadata.</p>
	 * <ul>
	 * <li>identifier</li>
	 * <li>title</li>
	 * <li>language</li>
	 * <li>modified</li>
	 * </ul>
	 * 
	 * @return {@code true} if all required properties exist in this metadata, {@code false} otherwise.
	 */
	boolean isFilled();
	
	/**
	 * Removes all properties of the specific term, which can be selected by a returned selector.
	 *  
	 * @return the selector of the property listers, never be {@code null}.
	 */
	PropertyListerSelector remove();
	
	/**
	 * Removes the specified property from this metadata.
	 * 
	 * @param property the property to remove from this metadata.
	 * @return {@code true} if this metadata contained the specified property, {@code false} otherwise.
	 * @throws IllegalArgumentException if given {@code property} is {@code null}.
	 */
	boolean remove(Property<?> property);
	
	/**
	 * Returns the total number of the properties currently contained in this metadata.
	 * 
	 * @return the total number of the properties in this metadata, which will be zero or positive integer.
	 */
	int size();
	
	/**
	 * Returns all unique terms that have at least one property in this metadata.
	 * 
	 * @return the set of terms, never be {@code null}.
	 */
	Set<Term> termSet();
}
