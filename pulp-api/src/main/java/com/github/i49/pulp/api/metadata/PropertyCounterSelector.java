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
 * The interface to select a property counter.
 */
public interface PropertyCounterSelector {

	/**
	 * Counts contributor properties.
	 * 
	 * @return the number of the properties.
	 */
	int contributor();
	
	/**
	 * Counts coverage properties.
	 * 
	 * @return the number of the properties.
	 */
	int coverage();
	
	/**
	 * Counts creator properties.
	 * 
	 * @return the number of the properties.
	 */
	int creator();
	
	/**
	 * Counts date properties.
	 * 
	 * @return the number of the properties.
	 */
	int date();
	
	/**
	 * Counts description properties.
	 * 
	 * @return the number of the properties.
	 */
	int description();
	
	/**
	 * Counts format properties.
	 * 
	 * @return the number of the properties.
	 */
	int format();
	
	/**
	 * Counts identifier properties.
	 * 
	 * @return the number of the properties.
	 */
	int identifier();

	/**
	 * Counts language properties.
	 * 
	 * @return the number of the properties.
	 */
	int language();
	
	/**
	 * Counts modified properties.
	 * 
	 * @return the number of the properties.
	 */
	int modified();

	/**
	 * Counts publisher properties.
	 * 
	 * @return the number of the properties.
	 */
	int publisher();

	/**
	 * Counts relation properties.
	 * 
	 * @return the number of the properties.
	 */
	int relation();
	
	/**
	 * Counts rights properties.
	 * 
	 * @return the number of the properties.
	 */
	int rights();
	
	/**
	 * Counts source properties.
	 * 
	 * @return the number of the properties.
	 */
	int source();
	
	/**
	 * Counts subject properties.
	 * 
	 * @return the number of the properties.
	 */
	int subject();
	
	/**
	 * Counts title properties.
	 * 
	 * @return the number of the properties.
	 */
	int title();
	
	/**
	 * Counts type properties.
	 * 
	 * @return the number of the properties.
	 */
	int type();
	
	/**
	 * Counts the properties specified by the term.
	 * 
	 * @param term the term of the property.
	 * @return the number of the properties.
	 * @throws IllegalArgumentException if given {@code term} is {@code null}.
	 */
	int propertyOf(Term term);
}
