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
import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.Vocabulary;

/**
 * The registry which maintains vocabularies and terms of metadata properties.
 * <p>
 * All instances of {@link Vocabulary} and {@link Term} must be registered with this registry
 * before using them.
 * </p> 
 */
public interface TermRegistry {
	
	/**
	 * Checks if the specified term is already registered with this registry.
	 * 
	 * @param term the term to check.
	 * @return {@code true} if the specified term is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code term} is {@code null}.
	 */
	boolean containsTerm(Term term);
	
	/**
	 * Checks if the term specified by URI and name is already registered with this registry.
	 * 
	 * @param uri the URI which represents the vocabulary of the term to check.
	 * @param name the name of the term within the vocabulary.
	 * @return {@code true} if the specified term is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if any of parameters are {@code null}.
	 */
	boolean containsTerm(URI uri, String name);
	
	/**
	 * Checks if the vocabulary of specified by URI is already registered with this registry.
	 * 
	 * @param uri the URI which represents the vocabulary.
	 * @return {@code true} if the specified vocabulary is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code uri} is {@code null}.
	 */
	boolean containsVocabulary(URI uri);
	
	/**
	 * Checks if the specified vocabulary is already registered with this registry.
	 * 
	 * @param vocabulary the vocabulary to check.
	 * @return {@code true} if the specified vocabulary is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code vocabulary} is {@code null}.
	 */
	boolean containsVocabulary(Vocabulary vocabulary);
	
	/**
	 * Finds the specified vocabulary in this registry.
	 * 
	 * @param uri the URI which represents the vocabulary.
	 * @return found vocabulary.
	 * @throws IllegalArgumentException if {@code uri} is {@code null}.
	 */
	Optional<Vocabulary> findVocabulary(URI uri);

	/**
	 * Finds the specified term in this registry.
	 * 
	 * @param uri the URI which represents the vocabulary.
	 * @param name the name of the term within the vocabulary.
	 * @return found term.
	 * @throws IllegalArgumentException if any parameters are {@code null}.
	 */
	Optional<Term> findTerm(URI uri, String name);

	/**
	 * Finds the specified term in this registry.
	 * 
	 * @param vocabulary the vocabulary to which the term belongs.
	 * @param name the name of the term within the vocabulary.
	 * @return found term.
	 * @throws IllegalArgumentException if any parameters are {@code null}.
	 */
	Optional<Term> findTerm(Vocabulary vocabulary, String name);
	
	/**
	 * Returns the specified term registered with this registry.
	 * If the term is not registered yet, a new term is created and registered with this registry. 
	 *  
	 * @param vocabulary the vocabulary of the term, must be registered with this registry.
	 * @param name the local name of the term.
	 * @return registered term, never be {@code null}.
	 * @throws IllegalArgumentException if any parameters are {@code null}.
	 */
	Term getTerm(Vocabulary vocabulary, String name);
	
	/**
	 * Returns the specified vocabulary registered with this registry.
	 * If the vocabulary is not registered yet, a new vocabulary is created and registered with this registry. 
	 *  
	 * @param uri the URI which represents the vocabulary.
	 * @return registered vocabulary, never be {@code null}.
	 * @throws IllegalArgumentException if {@code uri} is {@code null}.
	 */
	Vocabulary getVocabulary(URI uri);
	
	/**
	 * Registers all terms defined in the enumeration with this registry.
	 * 
	 * @param clazz the enumeration class which contains terms as members.
	 * @param <T> the type of the enumeration.
	 * @throws IllegalArgumentException if {@code clazz} is {@code null}.
	 */
	<T extends Enum<T> & Term> void registerAllTerms(Class<T> clazz);

	/**
	 * Registers all vocabularies defined in the enumeration with this registry.
	 * 
	 * @param clazz the enumeration class which contains vocabularies as members.
	 * @param <T> the type of the enumeration.
	 */
	<T extends Enum<T> & Vocabulary> void registerAllVocabularies(Class<T> clazz);

	/**
	 * Registers the specified term with this registry.
	 * 
	 * @param term the term to register.
	 * @throws IllegalArgumentException if {@code term} is {@code null}.
	 */
	void registerTerm(Term term);
	
	/**
	 * Registers the specified vocabulary with this registry.
	 * 
	 * @param vocabulary the vocabulary to register.
	 * @throws IllegalArgumentException if {@code vocabulary} is {@code null}.
	 */
	void registerVocabulary(Vocabulary vocabulary);
}
