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
import java.util.IllformedLocaleException;
import java.util.Locale;

import com.github.i49.pulp.api.vocabularies.Generic;
import com.github.i49.pulp.api.vocabularies.GenericText;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.Contributor;
import com.github.i49.pulp.api.vocabularies.dc.Coverage;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.Date;
import com.github.i49.pulp.api.vocabularies.dc.Description;
import com.github.i49.pulp.api.vocabularies.dc.Format;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;
import com.github.i49.pulp.api.vocabularies.dc.Publisher;
import com.github.i49.pulp.api.vocabularies.dc.Relation;
import com.github.i49.pulp.api.vocabularies.dc.Rights;
import com.github.i49.pulp.api.vocabularies.dc.Source;
import com.github.i49.pulp.api.vocabularies.dc.Subject;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dc.Type;
import com.github.i49.pulp.api.vocabularies.dcterms.Modified;

/**
 * The type for selecting a property builder.
 * 
 * <p>The following properties can be built with the builders this interface provides.</p>
 * <h3>Dublin Core Metadata Element Set</h3>
 * <ul>
 * <li>{@link Contributor}</li>
 * <li>{@link Coverage}</li>
 * <li>{@link Creator}</li>
 * <li>{@link Date}</li>
 * <li>{@link Description}</li>
 * <li>{@link Format}</li>
 * <li>{@link Identifier}</li>
 * <li>{@link Language}</li>
 * <li>{@link Publisher}</li>
 * <li>{@link Relation}</li>
 * <li>{@link Rights}</li>
 * <li>{@link Source}</li>
 * <li>{@link Subject}</li>
 * <li>{@link Title}</li>
 * <li>{@link Type}</li>
 * </ul>
 * <h3>DCMI Metadata Terms</h3>
 * <ul>
 * <li>{@link Modified}</li>
 * </ul>
 * <h3>Others</h3>
 * <ul>
 * <li>{@link Generic}</li>
 * <li>{@link GenericText}</li>
 * </ul>
 */
public interface PropertyBuilderSelector {
	
	/**
	 * Creates a builder for building an instance of {@link Contributor}.
	 * 
	 * @param value the text describing the coverage.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Contributor.Builder contributor(String value);

	/**
	 * Creates a builder for building an instance of {@link Coverage}.
	 * 
	 * @param value the value of the property.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Coverage.Builder coverage(String value);
	
	/**
	 * Creates a builder for building an instance of {@link Creator}.
	 * 
	 * @param value the name of the person or organization.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Creator.Builder creator(String value);

	/**
	 * Creates a builder for building an instance of {@link Date}.
	 * 
	 * @param value the publication date.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Date.Builder date(OffsetDateTime value);

	/**
	 * Creates a builder for building an instance of {@link Date}.
	 * 
	 * @param value date and time in ISO 8601 format.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Date.Builder date(String value);
	
	/**
	 * Creates a builder for building an instance of {@link Description}.
	 * 
	 * @param value the description of the rendition.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Description.Builder description(String value);

	/**
	 * Creates a builder for building an instance of {@link Format}.
	 * 
	 * @param value the value representing the format, such as "application/epub+zip".
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Format.Builder format(String value);

	/**
	 * Creates a builder for building an instance of {@link Identifier}.
	 * The value of the property will be a UUID generated randomly.
	 * 
	 * @return newly created builder.
	 */
	Identifier.Builder identifier();

	/**
	 * Creates a builder for building an instance of {@link Identifier}.
	 * 
	 * @param value the value of the identifier.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Identifier.Builder identifier(String value);

	/**
	 * Creates a builder for building an instance of {@link Language}.
	 * The value of the property is specified by a {@link Locale} value.
	 * 
	 * @param value the value of the language, such as {@link Locale#ENGLISH}.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Language.Builder language(Locale value);
	
	/**
	 * Creates a builder for building an instance of {@link Language}.
	 * The value of the property is specified by a language tag.
	 * 
	 * @param value the language tag defined in IETF BCP 47, such as "en-US".
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 * @throws IllformedLocaleException if given {@code value} is ill-formed.
	 * @see <a href="http://www.ietf.org/rfc/bcp/bcp47.txt">Tags for Identifying Languages; Matching of Language Tags.</a>
	 */
	Language.Builder language(String value);

	/**
	 * Creates a builder for building an instance of {@link Modified}.
	 * 
	 * @param value the last modification date of the publication.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Modified.Builder modified(OffsetDateTime value);

	/**
	 * Creates a builder for building an instance of {@link Modified}.
	 * 
	 * @param value date and time in ISO 8601 format.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Modified.Builder modified(String value);
	
	/**
	 * Creates a builder for building an instance of {@link Publisher}.
	 * 
	 * @param value the name of the person or organization.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Publisher.Builder publisher(String value);

	/**
	 * Creates a builder for building an instance of {@link Relation}.
	 * 
	 * @param value the text describing the related resource.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Relation.Builder relation(String value);

	/**
	 * Creates a builder for building an instance of {@link Rights}.
	 * 
	 * @param value the text describing the rights.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Rights.Builder rights(String value);

	/**
	 * Creates a builder for building an instance of {@link Source}.
	 * 
	 * @param value the text describing the source of the publication.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Source.Builder source(String value);

	/**
	 * Creates a builder for building an instance of {@link Subject}.
	 * 
	 * @param value the value representing the subject of the publication.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Subject.Builder subject(String value);
	
	/**
	 * Creates a builder for building an instance of {@link Title}.
	 * 
	 * @param value the value of the title.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Title.Builder title(String value);

	/**
	 * Creates a builder for building an instance of {@link Type}.
	 * 
	 * @param value the value representing the type of the publication.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if given {@code value} is invalid.
	 */
	Type.Builder type(String value);
	
	/**
	 * Creates a builder for building an instance of {@link Generic}.
	 * 
	 * @param <V> the type of the property value.
	 * @param term the term of the property.
	 * @param value the value of the property.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if one or more parameters are invalid.
	 */
	<V> Generic.Builder<V> generic(Term term, V value);

	/**
	 * Creates a builder for building an instance of {@link GenericText}
	 * which has a property value of string type.
	 * 
	 * @param term the term of the property.
	 * @param value the value of the property.
	 * @return newly created builder.
	 * @throws IllegalArgumentException if one or more parameters are invalid.
	 */
	GenericText.Builder generic(Term term, String value);
}
