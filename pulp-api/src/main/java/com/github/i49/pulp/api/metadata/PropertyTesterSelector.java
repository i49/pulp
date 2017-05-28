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
 * The type for selecting a property tester.
 * <p>
 * Each method provided by this type will return a boolean value 
 * as the result of testing the selected property type.
 * When used for existence testing, each method returns {@code true}
 * if the properties of the selected term are contained in the metadata,
 * {@code false} otherwise. 
 * </p>
 */
public interface PropertyTesterSelector {

	/**
	 * Tests the properties of {@link Contributor}.
	 * 
	 * @return the result of the testing.
	 */
	boolean contributor();
	
	/**
	 * Tests the properties of {@link Coverage}.
	 * 
	 * @return the result of the testing.
	 */
	boolean coverage();
	
	/**
	 * Tests the properties of {@link Creator}.
	 * 
	 * @return the result of the testing.
	 */
	boolean creator();
	
	/**
	 * Tests the properties of {@link Date}.
	 * 
	 * @return the result of the testing.
	 */
	boolean date();
	
	/**
	 * Tests the properties of {@link Description}.
	 * 
	 * @return the result of the testing.
	 */
	boolean description();
	
	/**
	 * Tests the properties of {@link Format}.
	 * 
	 * @return the result of the testing.
	 */
	boolean format();
	
	/**
	 * Tests the properties of {@link Identifier}.
	 * 
	 * @return the result of the testing.
	 */
	boolean identifier();

	/**
	 * Tests the properties of {@link Language}.
	 * 
	 * @return the result of the testing.
	 */
	boolean language();
	
	/**
	 * Tests the properties of {@link Modified}.
	 * 
	 * @return the result of the testing.
	 */
	boolean modified();

	/**
	 * Tests the properties of {@link Publisher}.
	 * 
	 * @return the result of the testing.
	 */
	boolean publisher();

	/**
	 * Tests the properties of {@link Relation}.
	 * 
	 * @return the result of the testing.
	 */
	boolean relation();
	
	/**
	 * Tests the properties of {@link Rights}.
	 * 
	 * @return the result of the testing.
	 */
	boolean rights();
	
	/**
	 * Tests the properties of {@link Source}.
	 * 
	 * @return the result of the testing.
	 */
	boolean source();
	
	/**
	 * Tests the properties of {@link Subject}.
	 * 
	 * @return the result of the testing.
	 */
	boolean subject();
	
	/**
	 * Tests the properties of {@link Title}.
	 * 
	 * @return the result of the testing.
	 */
	boolean title();
	
	/**
	 * Tests the properties of {@link Type}.
	 * 
	 * @return the result of the testing.
	 */
	boolean type();
	
	/**
	 * Tests the properties specified by the term.
	 * 
	 * @param term the term of the property.
	 * @return the result of the testing.
	 * @throws IllegalArgumentException if given {@code term} is {@code null}.
	 */
	boolean propertyOf(Term term);
}
