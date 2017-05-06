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
 * Basic terms generally used as metadata properties. 
 * 
 * @see <a href="http://dublincore.org/documents/dces/">Dublin Core Metadata Element Set, Version 1.1</a>
 */
public enum DublinCore implements Term {
	
	/**
	 *  An entity responsible for making contributions to the resource.
	 * @see Contributor
	 */
	CONTRIBUTOR(PropertyType.RELATOR),

	/** 
	 * The spatial or temporal topic of the resource, 
	 * the spatial applicability of the resource, 
	 * or the jurisdiction under which the resource is relevant.
	 * @see Coverage
	 */
	COVERAGE(PropertyType.TEXT),

	/**
	 * An entity primarily responsible for making the resource.
	 * @see Creator
	 */
	CREATOR(PropertyType.RELATOR),
	
	/**
	 * A point or period of time associated with an event in the lifecycle of the resource.
	 */
	DATE(PropertyType.DATE),
	
	/**
	 * An account of the resource.
	 * @see Description
	 */
	DESCRIPTION(PropertyType.TEXT),

	/** 
	 * The file format, physical medium, or dimensions of the resource. 
	 * @see Format
	 */
	FORMAT(PropertyType.SIMPLE),

	/** 
	 * An unambiguous reference to the resource within a given context.
	 * @see Identifier
	 */
	IDENTIFIER(PropertyType.IDENTIFIER),

	/**
	 * A language of the resource.
	 * @see Language
	 */ 
	LANGUAGE(PropertyType.LANGUAGE),
	
	/**
	 * An entity responsible for making the resource available.
	 * @see Publisher
	 */
	PUBLISHER(PropertyType.RELATOR),
	
	/**
	 * A related resource.
	 * @see Relation
	 */
	RELATION(PropertyType.TEXT),
	
	/**
	 * Information about rights held in and over the resource.
	 * @see Rights
	 */
	RIGHTS(PropertyType.TEXT),
	
	/**
	 * A related resource from which the described resource is derived.
	 * @see Source
	 */
	SOURCE(PropertyType.SOURCE),
	
	/**
	 * The topic of the resource.
	 * @see Subject
	 */
	SUBJECT(PropertyType.SUBJECT),
	
	/**
	 * A name given to the resource.
	 * @see Title
	 */
	TITLE(PropertyType.TITLE),
	
	/**
	 * The nature or genre of the resource.
	 * @see Type
	 */
	TYPE(PropertyType.SIMPLE),
	;

	private final PropertyType type;
	
	private DublinCore(PropertyType type) {
		this.type = type;
	}
			
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyType getType() {
		return type;
	}
	
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
