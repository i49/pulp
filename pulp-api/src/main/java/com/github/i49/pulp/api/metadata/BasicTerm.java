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

import static com.github.i49.pulp.api.metadata.StandardVocabulary.*;

/**
 * Basic terms frequently used as metadata properties. 
 * 
 * @see <a href="http://dublincore.org/documents/dces/">Dublin Core Metadata Element Set, Version 1.1</a>
 * @see <a href="http://dublincore.org/documents/dcmi-terms/">DCMI Metadata Terms</a> 
 */
public enum BasicTerm implements Term {
	
	/**
	 *  An entity responsible for making contributions to the resource.
	 * @see Contributor
	 */
	CONTRIBUTOR(DCMES, "contributor"),

	/** 
	 * The spatial or temporal topic of the resource, 
	 * the spatial applicability of the resource, 
	 * or the jurisdiction under which the resource is relevant.
	 * @see Coverage
	 */
	COVERAGE(DCMES, "coverage"),

	/**
	 * An entity primarily responsible for making the resource.
	 * @see Creator
	 */
	CREATOR(DCMES, "creator"),
	
	/**
	 * A point or period of time associated with an event in the lifecycle of the resource.
	 */
	DATE(DCMES, "date"),
	
	/**
	 * An account of the resource.
	 * @see Description
	 */
	DESCRIPTION(DCMES, "description"),

	/** 
	 * The file format, physical medium, or dimensions of the resource. 
	 * @see Format
	 */
	FORMAT(DCMES, "format"),

	/** 
	 * An unambiguous reference to the resource within a given context.
	 * @see Identifier
	 */
	IDENTIFIER(DCMES, "identifier"),

	/**
	 * A language of the resource.
	 * @see Language
	 */ 
	LANGUAGE(DCMES, "language"),
	
	/**
	 * An entity responsible for making the resource available.
	 * @see Publisher
	 */
	PUBLISHER(DCMES, "publisher"),
	
	/**
	 * A related resource.
	 * @see Relation
	 */
	RELATION(DCMES, "relation"),
	
	/**
	 * Information about rights held in and over the resource.
	 * @see Rights
	 */
	RIGHTS(DCMES, "rights"),
	
	/**
	 * A related resource from which the described resource is derived.
	 * @see Source
	 */
	SOURCE(DCMES, "source"),
	
	/**
	 * The topic of the resource.
	 * @see Subject
	 */
	SUBJECT(DCMES, "subject"),
	
	/**
	 * A name given to the resource.
	 * @see Title
	 */
	TITLE(DCMES, "title"),
	
	/**
	 * The nature or genre of the resource.
	 * @see Type
	 */
	TYPE(DCMES, "type"),
	
	/**
	 * Date on which the resource was changed.
	 */
	MODIFIED(DCTERMS, "modified")
	;

	private final Vocabulary vocabulary;
	private final String name;
	
	private BasicTerm(Vocabulary vocabulary, String name) {
		this.vocabulary = vocabulary;
		this.name = name;
	}
			
	@Override
	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	@Override
	public String getName() {
		return name;
	}
}
