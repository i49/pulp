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

import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.Contributor;
import com.github.i49.pulp.api.vocabularies.dc.Coverage;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.Date;
import com.github.i49.pulp.api.vocabularies.dc.Description;
import com.github.i49.pulp.api.vocabularies.dc.Format;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;
import com.github.i49.pulp.api.vocabularies.dc.Publisher;
import com.github.i49.pulp.api.vocabularies.dc.Relation;
import com.github.i49.pulp.api.vocabularies.dc.Rights;
import com.github.i49.pulp.api.vocabularies.dc.Source;
import com.github.i49.pulp.api.vocabularies.dc.Subject;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dc.Type;
import com.github.i49.pulp.api.vocabularies.dcterms.Modified;

/**
 * The type for selecting a property counter.
 */
public interface PropertyCounterSelector {

	/**
	 * Counts the properties of {@link Contributor}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int contributor();
	
	/**
	 * Counts the properties of {@link Coverage}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int coverage();
	
	/**
	 * Counts the properties of {@link Creator}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int creator();
	
	/**
	 * Counts the properties of {@link Date}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int date();
	
	/**
	 * Counts the properties of {@link Description}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int description();
	
	/**
	 * Counts the properties of {@link Format}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int format();
	
	/**
	 * Counts the properties of {@link Identifier}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int identifier();

	/**
	 * Counts the properties of {@link Language}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int language();
	
	/**
	 * Counts the properties of {@link Modified}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int modified();

	/**
	 * Counts the properties of {@link Publisher}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int publisher();

	/**
	 * Counts the properties of {@link Relation}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int relation();
	
	/**
	 * Counts the properties of {@link Rights}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int rights();
	
	/**
	 * Counts the properties of {@link Source}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int source();
	
	/**
	 * Counts the properties of {@link Subject}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int subject();
	
	/**
	 * Counts the properties of {@link Title}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int title();
	
	/**
	 * Counts the properties of {@link Type}.
	 * 
	 * @return the number of the properties in the metadata.
	 */
	int type();
	
	/**
	 * Counts the properties specified by the term.
	 * 
	 * @param term the term of the property to count.
	 * @return the number of the properties in the metadata.
	 * @throws IllegalArgumentException if given {@code term} is {@code null}.
	 */
	int propertyOf(Term term);
}
