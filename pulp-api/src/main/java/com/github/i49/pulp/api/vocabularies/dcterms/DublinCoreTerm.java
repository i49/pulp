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

package com.github.i49.pulp.api.vocabularies.dcterms;

import com.github.i49.pulp.api.vocabularies.Generic;
import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.StandardVocabulary;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.Vocabulary;

/**
 * Dublin Core Metadata Terms
 *
 * @see <a href="http://dublincore.org/documents/dcmi-terms/">DCMI Metadata Terms</a> 
 */
public enum DublinCoreTerm implements Term {
	ABSTRACT("abstract"),
	ACCESS_RIGHTS("accessRights"),
	ACCRUAL_METHOD("accrualMethod"),
	ACCRUAL_PERIODICITY("accrualPeriodicity"),
	ACCRUAL_POLICY("accrualPolicy"),
	ALTERNATIVE("alternative"),
	AUDIENCE("audience"),
	AVAILABLE("available"),
	BIBLIOGRAPHIC_CITATION("bibliographicCitation"),
	CONFORMS_TO("conformsTo"),
	CONTRIBUTOR("contributor"),
	COVERAGE("coverage"),
	CREATED("created"),
	CREATOR("creator"),
	DATE("date"),
	DATE_ACCEPTED("dateAccepted"),
	DATE_COPYRIGHTED("dateCopyrighted"),
	DATE_SUBMITTED("dateSubmitted"),
	DESCRIPTION("description"),
	EDUCATION_LEVEL("educationLevel"),
	EXTENT("extent"),
	FORMAT("format"),
	HAS_FORMAT("hasFormat"),
	HAS_PART("hasPart"),
	HAS_VERSION("hasVersion"),
	IDENTIFIER("identifier"),
	INSTRUCTIONAL_METHOD("instructionalMethod"),
	IS_FORMAT_OF("isFormatOf"),
	IS_PART_OF("isPartOf"),
	IS_REFERENCED_BY("isReferencedBy"),
	IS_REPLACED_BY("isReplacedBy"),
	IS_REQUIRED_BY("isRequiredBy"),
	ISSUED("issued"),
	IS_VERSION_OF("isVersionOf"),
	LANGUAGE("language"),
	LICENSE("license"),
	MEDIATOR("mediator"),
	MEDIUM("medium"),
	/**
	 * Date on which the resource was changed.
	 */
	MODIFIED("modified"),
	PROVENANCE("provenance"),
	PUBLISHER("publisher"),
	REFERENCES("references"),
	RELATION("relation"),
	REPLACES("replaces"),
	REQUIRES("requires"),
	RIGHTS("rights"),
	RIGHTS_HOLDER("rightsHolder"),
	SOURCE("source"),
	SPATIAL("spatial"),
	SUBJECT("subject"),
	TABLE_OF_CONTENTS("tableOfContents"),
	TEMPORAL("temporal"),
	TITLE("title"),
	TYPE("type"),
	VALID("valid") 
	;

	private final String name;
	
	private DublinCoreTerm(String name) {
		this.name = name;
	}
	
	@Override
	public Class<? extends Property> getType() {
		if (this == MODIFIED) {
			return Modified.class;
		} else {
			return Generic.class;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vocabulary getVocabulary() {
		return StandardVocabulary.DCTERMS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRepeatable() {
		return this != MODIFIED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String localName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return qualifiedName();
	}
}
