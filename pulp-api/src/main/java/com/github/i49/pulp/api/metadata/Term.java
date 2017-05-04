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

import java.net.URI;

/**
 * A term in a vocabulary. 
 * The instances of this type are defined as the members of {@link DublinCore} enumeration.
 */
public interface Term {

	/**
	 * Returns the name of this term.
	 * 
	 * @return the name of this term.
	 */
	String getName();
	
	/**
	 * Returns the URI of this term.
	 * 
	 * @return the URI of this term.
	 */
	default URI getURI() {
		return getVocabulary().getURI().resolve(getName());
	}

	/**
	 * Returns the vocabulary to which this term belongs.
	 * 
	 * @return the vocabulary to which this term belongs.
	 */
	Vocabulary getVocabulary();

	/**
	 * Checks if the property of this term is repeatable.
	 * By default this method returns {@code true}.
	 *  
	 * @return {@code true} if the property of this term is repeatable, {@code false} otherwise.
	 */
	default boolean isRepeatable() {
		return true;
	}
}
