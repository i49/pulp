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

package com.github.i49.pulp.api.vocabularies.dc;

import com.github.i49.pulp.api.vocabularies.StandardVocabulary;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;

/**
 * Terms defined in Dublin Core Metadata Element Set,
 * each implementing {@link Term}.
 * 
 * @see <a href="http://dublincore.org/documents/dces/">Dublin Core Metadata Element Set, Version 1.1</a>
 */
public enum DublinCore implements Term {
	
	/**
	 *  An entity responsible for making contributions to the resource.
	 */
	CONTRIBUTOR,

	/** 
	 * The spatial or temporal topic of the resource, 
	 * the spatial applicability of the resource, 
	 * or the jurisdiction under which the resource is relevant.
	 */
	COVERAGE,

	/**
	 * An entity primarily responsible for making the resource.
	 */
	CREATOR,
	
	/**
	 * A point or period of time associated with an event in the lifecycle of the resource.
	 */
	DATE,
	
	/**
	 * An account of the resource.
	 */
	DESCRIPTION,

	/** 
	 * The file format, physical medium, or dimensions of the resource. 
	 */
	FORMAT,

	/** 
	 * An unambiguous reference to the resource within a given context.
	 */
	IDENTIFIER,

	/**
	 * A language of the resource.
	 */ 
	LANGUAGE,
	
	/**
	 * An entity responsible for making the resource available.
	 */
	PUBLISHER,
	
	/**
	 * A related resource.
	 */
	RELATION,
	
	/**
	 * Information about rights held in and over the resource.
	 */
	RIGHTS,
	
	/**
	 * A related resource from which the described resource is derived.
	 */
	SOURCE,
	
	/**
	 * The topic of the resource.
	 */
	SUBJECT,
	
	/**
	 * A name given to the resource.
	 */
	TITLE,
	
	/**
	 * The nature or genre of the resource.
	 */
	TYPE
	;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vocabulary getVocabulary() {
		return StandardVocabulary.DCMES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRepeatable() {
		return this != DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String localName() {
		return name().toLowerCase();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return qualifiedName();
	}
}
