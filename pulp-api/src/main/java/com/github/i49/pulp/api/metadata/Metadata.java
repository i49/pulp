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
 * A set of meta information describing a EPUB publication and its renditions.
 */
public interface Metadata {
	
	/**
	 * Adds a property to be selected by term.
	 *  
	 * @return the selector of the properties.
	 */
	PropertyBuilderSelector add();

	/**
	 * Adds the specified property to this metadata if it is not already present.
	 * 
	 * @param property the property to add.
	 * @return {@code true} if this metadata did not already contain the specified property. 
	 * @throws IllegalArgumentException if {@code property} is {@code null}.
	 * @throws IllegalStateException if the maximum number of properties allowed were already added.
	 */
	boolean add(Property<?> property);
	
	/**
	 * Clears all properties in this metadata.
	 * All mandatory properties are cleared at the same time.
	 */
	void clear();

	/**
	 * Tests the existence of the property to be selected by term.
	 * 
	 * @return the selector of the properties.
	 */
	PropertyTesterSelector contains();
	
	/**
	 * Counts properties to be selected by term.
	 *  
	 * @return the selector of the properties.
	 */
	PropertyCounterSelector count();
	
	/**
	 * Finds properties to be selected by term.
	 *  
	 * @return the selector of the properties.
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
	 * Returns the <i>release identifier</i> of the publication
	 * that uniquely identifies a specific version of it.
	 * 
	 * <p>Release identifier of the publication consists of the following two properties:</p>
	 * <ul>
	 * <li>identifier of the publication</li>
	 * <li>last modification time of the publication</li>
	 * </ul>
	 * 
	 * @return the release identifier of the publication, never be {@code null}.
	 */
	ReleaseIdentifier getReleaseIdentifier();
	
	/**
	 * Checks if this metadata contains all required properties.
	 * 
	 * @return {@code true} if all required properties exist in this metadata, {@code false} otherwise.
	 */
	boolean isFilled();
	
	/**
	 * Removes properties to be selected by term.
	 *  
	 * @return the selector of the properties.
	 */
	PropertyListerSelector remove();
	
	/**
	 * Removes the specified property from this metadata.
	 * 
	 * @param property the property to remove.
	 * @return {@code true} if this metadata contained the specified property.
	 * @throws IllegalArgumentException if {@code property} is {@code null}.
	 */
	boolean remove(Property<?> property);
	
	/**
	 * Returns the total number of the properties contained in this metadata.
	 * 
	 * @return the total number of the properties in this metadata.
	 */
	int size();
	
	/**
	 * Returns all terms that have at least one property in this metadata.
	 * 
	 * @return the set of terms, never be {@code null}.
	 */
	Set<Term> termSet();
}
