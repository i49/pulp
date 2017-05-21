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

/**
 * The interface to select a property tester.
 * Each method in this interface will return a boolean value 
 * as the result of testing selected properties.
 */
public interface PropertyTesterSelector {

	/**
	 * Tests contributor properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean contributor();
	
	/**
	 * Tests coverage properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean coverage();
	
	/**
	 * Tests creator properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean creator();
	
	/**
	 * Tests date properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean date();
	
	/**
	 * Tests description properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean description();
	
	/**
	 * Tests format properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean format();
	
	/**
	 * Tests identifier properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean identifier();

	/**
	 * Tests language properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean language();
	
	/**
	 * Tests modified properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean modified();

	/**
	 * Tests publisher properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean publisher();

	/**
	 * Tests relation properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean relation();
	
	/**
	 * Tests rights properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean rights();
	
	/**
	 * Tests source properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean source();
	
	/**
	 * Tests subject properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean subject();
	
	/**
	 * Tests title properties.
	 * 
	 * @return the result of the testing.
	 */
	boolean title();
	
	/**
	 * Tests type properties.
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
