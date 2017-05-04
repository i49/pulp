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

/**
 * A term in a vocabulary. 
 * The instances of this type are defined as the members of {@link DublinCore} enumeration.
 */
public interface Term {

	/**
	 * Checks if the property of this term is repeatable.
	 * By default this method returns {@code true}.
	 *  
	 * @return {@code true} if the property of this term is repeatable, {@code false} otherwise.
	 */
	default boolean isRepeatable() {
		return true;
	}

	/**
	 * Returns the local name of this term.
	 * 
	 * @return the local name of this term, never be {@code null}.
	 */
	String localName();
	
	/**
	 * Returns the qualified name of this term.
	 * 
	 * @return the qualified name of this term, never be {@code null}.
	 */
	default String qualifiedName() {
		return vocabulary().getURI().resolve(localName()).toString();
	}
	
	/**
	 * Returns the property type of this term.
	 * 
	 * @return the type of the property, never be {@code null}.
	 */
	PropertyType type();

	/**
	 * Returns the vocabulary to which this term belongs.
	 * 
	 * @return the vocabulary to which this term belongs, never be {@code null}.
	 */
	Vocabulary vocabulary();
}
