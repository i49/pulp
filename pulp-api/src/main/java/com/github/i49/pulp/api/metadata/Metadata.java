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
import java.util.Optional;

/**
 * A set of meta information describing a EPUB publication and renditions.
 */
public interface Metadata {

	ReleaseIdentifier getReleaseIdentifier();
	
	/**
	 * Returns the number of the properties in this metadata.
	 * 
	 * @return the number of the properties in this metadata.
	 */
	int getNumberOfProperties();
	
	/**
	 * Returns the number of the properties of the specified term in this metadata.
	 * 
	 * @param term the term of the properties to count.
	 * @return the number of the properties of the specified term in this metadata.
	 */
	int getNumberOfProperties(Term term);

	/**
	 * Returns one property of the specified term.
	 * If this metadata contains more than one properties of the term,
	 * only the first one will be returned. 
	 * 
	 * @param term the term of the property.
	 * @return the property found in this metadata.
	 */
	Optional<Property> getProperty(Term term);
	
	/**
	 * Returns all properties of the specified term.
	 * 
	 * @param term the term of the property.
	 * @return the properties found in this metadata.
	 */
	List<Property> getProperties(Term term);

	/**
	 * Clears all properties and adds default properties.
	 */
	void reset();
}
