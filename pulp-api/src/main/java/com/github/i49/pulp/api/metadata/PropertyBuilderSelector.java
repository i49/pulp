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

import java.time.OffsetDateTime;
import java.util.Locale;

import com.github.i49.pulp.api.vocabulary.Generic;
import com.github.i49.pulp.api.vocabulary.GenericText;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.dc.Contributor;
import com.github.i49.pulp.api.vocabulary.dc.Coverage;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.Description;
import com.github.i49.pulp.api.vocabulary.dc.Format;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;
import com.github.i49.pulp.api.vocabulary.dc.Language;
import com.github.i49.pulp.api.vocabulary.dc.Publisher;
import com.github.i49.pulp.api.vocabulary.dc.Relation;
import com.github.i49.pulp.api.vocabulary.dc.Rights;
import com.github.i49.pulp.api.vocabulary.dc.Source;
import com.github.i49.pulp.api.vocabulary.dc.Subject;
import com.github.i49.pulp.api.vocabulary.dc.Title;
import com.github.i49.pulp.api.vocabulary.dc.Type;
import com.github.i49.pulp.api.vocabulary.dcterms.Modified;

/**
 * Provides various kinds of the property builders.
 */
public interface PropertyBuilderSelector {
	
	Contributor.Builder contributor(String value);

	Coverage.Builder coverage(String value);
	
	Creator.Builder creator(String value);

	Date.Builder date(OffsetDateTime value);
	
	Description.Builder description(String value);

	Format.Builder format(String value);

	/**
	 * Creates a builder for building an identifier property.
	 * The builder generates a random UUID as the value of the property. 
	 * 
	 * @return newly created builder.
	 */
	Identifier.Builder identifier();

	/**
	 * Creates a builder for building an identifier property.
	 * 
	 * @param value the value of the identifier.
	 * @return newly created builder.
	 */
	Identifier.Builder identifier(String value);

	Language.Builder language(Locale value);
	
	Language.Builder language(String value);

	Modified.Builder modified(OffsetDateTime value);
	
	Publisher.Builder publisher(String value);

	Relation.Builder relation(String value);

	Rights.Builder rights(String value);

	Source.Builder source(String value);

	Subject.Builder subject(String value);
	
	Title.Builder title(String value);

	Type.Builder type(String value);
	
	<V> Generic.Builder<V> generic(Term term, V value);

	GenericText.Builder generic(Term term, String value);
}
