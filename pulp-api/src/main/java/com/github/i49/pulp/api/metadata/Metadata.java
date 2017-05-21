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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;

/**
 * A set of meta information describing a EPUB publication and its renditions.
 */
public interface Metadata {
	
	PropertyBuilderSelector add();
	
	/**
	 * Clears all properties in this metadata.
	 * All mandatory properties are cleared at the same time.
	 */
	void clear();

	PropertyTesterSelector contains();
	
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
	
	PropertyListerSelector remove();
	
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
	
	/**
	 * Adds the specified property to this metadata if it is not already present.
	 * 
	 * <p>Calling this method is equivalent to the following code.</p>
	 * <pre>{@code
	 * getList(property.getTerm()).add(property);
	 * }</pre>
	 * 
	 * @param property the property to add.
	 * @return {@code true} if this metadata did not already contain the specified property. 
	 * @throws IllegalArgumentException if {@code property} is {@code null}.
	 * @throws IllegalStateException if the maximum number of properties were already added.
	 */
//	boolean add(Property property);

	/**
	 * Checks if this metadata contains any properties of the specific term.
	 * 
	 * @param term the term of the property.
	 * @return {@code true} if this metadata contains any properties of the term, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code term} is {@code null}.
	 */
//	boolean contains(Term term);
	
	/**
	 * Returns the number of the properties of the specific term contained in this metadata.
	 * 
	 * @param term the term of the properties to count, such as {@link DublinCore#TITLE}.
	 * @return the number of the properties of the specific term in this metadata.
	 * @throws IllegalArgumentException if {@code term} is {@code null}.
	 */
//	int getNumberOfProperties(Term term);

	/**
	 * Removes the specified property from this metadata.
	 * 
	 * <p>Calling this method is equivalent to the following code.</p>
	 * <pre>{@code
	 * getList(property.getTerm()).remove(property);
	 * }</pre>
	 * 
	 * @param property the property to remove.
	 * @return {@code true} if this metadata contained the specified property.
	 * @throws IllegalArgumentException if {@code property} is {@code null}.
	 */
//	boolean remove(Property property);
}
