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

package com.github.i49.pulp.api.vocabulary;

/**
 * Subject authorities for use in subject metadata.
 */
public enum SubjectAuthority {
	/** The Getty Art and Architecture Taxonomy. */
	AAT,
	/** The main UK subject scheme for the book supply chain. */
	BIC,
	/** The main US subject scheme for the book supply chain. */
	BISAC,
	/** The main subject classification scheme used in China. */
	CLC,
	/** The Dewey Decimal Classification system.*/
	DDC,
	/** The main French subject scheme for the book supply chain. */
	CLIL,
	/** The European multilingual thesaurus. */
	EUROVOC,
	/** IPTC Media Topics classification scheme for the news industry. */
	MEDTOP,
	/** Library of Congress Subject Headings. */
	LCSH,
	/** The main Japanese subject scheme. */
	NDC,
	/** The international subject scheme for the book supply chain. */
	THEMA,
	/** The Universal Decimal Classification system. */
	UDC,
	/** The main German subject scheme for the book supply chain. */
	WGS
	;
}
