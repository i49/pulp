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

import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;

/**
 * The registry which maintains vocabularies and terms available for metadata properties.
 * <p>
 * All instances of {@link Vocabulary} and {@link Term} must be registered with this registry
 * before using them.
 * All vocabularies and terms provided by the API are registered automatically
 * when this registry is instantiated and no explicit registrations are needed.
 * </p> 
 */
public interface TermRegistry {
	
	/**
	 * Checks if the given term is already registered with this registry.
	 * 
	 * @param term the term to check.
	 * @return {@code true} if the specified term is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if given {@code term} is {@code null}.
	 */
	boolean containsTerm(Term term);
	
	/**
	 * Checks if the term which has the specified vocabulary URI and the name is already registered with this registry.
	 * 
	 * @param uri the URI which represents the vocabulary of the term to check.
	 * @param name the name of the term within the vocabulary.
	 * @return {@code true} if the specified term is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if one or more parameters are {@code null}.
	 */
	boolean containsTerm(URI uri, String name);
	
	/**
	 * Checks if the vocabulary which has the specified URI is already registered with this registry.
	 * 
	 * @param uri the URI which represents the vocabulary.
	 * @return {@code true} if the specified vocabulary is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if given {@code uri} is {@code null}.
	 */
	boolean containsVocabulary(URI uri);
	
	/**
	 * Checks if the given vocabulary is already registered with this registry.
	 * 
	 * @param vocabulary the vocabulary to check.
	 * @return {@code true} if the specified vocabulary is already registered, {@code false} otherwise.
	 * @throws IllegalArgumentException if given {@code vocabulary} is {@code null}.
	 */
	boolean containsVocabulary(Vocabulary vocabulary);
	
	/**
	 * Finds the vocabulary which has the specified URI in this registry.
	 * 
	 * @param uri the URI which represents the vocabulary.
	 * @return found vocabulary, may be empty if not found.
	 * @throws IllegalArgumentException if given {@code uri} is {@code null}.
	 */
	Optional<Vocabulary> findVocabulary(URI uri);

	/**
	 * Finds the term which has the specified vocabulary URI and the name in this registry.
	 * 
	 * @param uri the URI which represents the vocabulary.
	 * @param name the name of the term within the vocabulary.
	 * @return found term, may be empty if not found.
	 * @throws IllegalArgumentException if one or more parameters are {@code null}.
	 */
	Optional<Term> findTerm(URI uri, String name);

	/**
	 * Finds the term which has the specified vocabulary and the name in this registry.
	 * 
	 * @param vocabulary the vocabulary to which the term belongs.
	 * @param name the name of the term within the vocabulary.
	 * @return found term, may be empty if not found.
	 * @throws IllegalArgumentException if one or more parameters are {@code null}.
	 */
	Optional<Term> findTerm(Vocabulary vocabulary, String name);
	
	/**
	 * Returns the specified term registered with this registry.
	 * If the term is not registered yet, a new term is created and registered with this registry. 
	 *  
	 * @param vocabulary the vocabulary of the term, must be registered with this registry.
	 * @param name the local name of the term.
	 * @return registered term, never be {@code null}.
	 * @throws IllegalArgumentException if one or more parameters are {@code null}.
	 */
	Term getTerm(Vocabulary vocabulary, String name);
	
	/**
	 * Returns the specified vocabulary registered with this registry.
	 * If the vocabulary is not registered yet, a new vocabulary is created and registered with this registry. 
	 *  
	 * @param uri the URI which represents the vocabulary.
	 * @return registered vocabulary, never be {@code null}.
	 * @throws IllegalArgumentException if given {@code uri} is {@code null}.
	 */
	Vocabulary getVocabulary(URI uri);
	
	/**
	 * Registers all terms defined in the specified enumeration with this registry.
	 * 
	 * @param <T> the type of the enumeration.
	 * @param type the enumeration class which contains terms as its members.
	 * @throws IllegalArgumentException if given {@code type} is {@code null}.
	 */
	<T extends Enum<T> & Term> void registerAllTerms(Class<T> type);

	/**
	 * Registers all vocabularies defined in the specified enumeration with this registry.
	 * 
	 * @param <T> the type of the enumeration.
	 * @param type the enumeration class which contains vocabularies as its members.
	 * @throws IllegalArgumentException if given {@code type} is {@code null}.
	 */
	<T extends Enum<T> & Vocabulary> void registerAllVocabularies(Class<T> type);

	/**
	 * Registers the specified term with this registry.
	 * 
	 * @param term the term to register.
	 * @throws IllegalArgumentException if given {@code term} is {@code null}.
	 * @throws IllegalStateException if given {@code term} is already registered.
	 */
	void registerTerm(Term term);
	
	/**
	 * Registers the specified vocabulary with this registry.
	 * 
	 * @param vocabulary the vocabulary to register.
	 * @throws IllegalArgumentException if given {@code vocabulary} is {@code null}.
	 * @throws IllegalStateException if given {@code vocabulary} is already registered.
	 */
	void registerVocabulary(Vocabulary vocabulary);
}
