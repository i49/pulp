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

/**
 * The factory for producing various kinds of properties of metadata.
 * 
 * <p>The following properties can be instantiated via this interface.</p>
 * <ol>
 * <li>{@link DublinCore#CONTRIBUTOR}</li>
 * <li>{@link DublinCore#COVERAGE}</li>
 * <li>{@link DublinCore#CREATOR}</li>
 * <li>{@link DublinCore#DATE}</li>
 * <li>{@link DublinCore#DESCRIPTION}</li>
 * <li>{@link DublinCore#FORMAT}</li>
 * <li>{@link DublinCore#IDENTIFIER}</li>
 * <li>{@link DublinCore#LANGUAGE}</li>
 * <li>{@link DublinCore#PUBLISHER}</li>
 * <li>{@link DublinCore#RELATION}</li>
 * <li>{@link DublinCore#RIGHTS}</li>
 * <li>{@link DublinCore#SOURCE}</li>
 * <li>{@link DublinCore#SUBJECT}</li>
 * <li>{@link DublinCore#TITLE}</li>
 * <li>{@link DublinCore#TYPE}</li>
 * <li>{@link DublinCoreTerm#MODIFIED}</li>
 * </ol>
 */
public interface PropertyFactory {
	
	/**
	 * Creates an property of {@link DublinCore#DATE}.
	 * 
	 * @param value the publication date and time.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} is {@code null}.
	 */
	DateProperty newDate(OffsetDateTime value);
	
	/**
	 * Creates an instance of generic property.
	 * Please note that The type of the term must be {@link PropertyType#GENERIC}.
	 * 
	 * @param term the term of the property.
	 * @param value the value of the property.
	 * @param <V> the type of the value.
	 * @return newly created property.
	 * @throws IllegalArgumentException if any parameter are {@code null} 
	 * or the type of the term does not match the type of the property to create.
	 * The proper type of the term can be obtained by {@link Term#getType()}.  
	 */
	GenericProperty createGenericProperty(Term term, String value);
	
	/**
	 * Creates an instance of {@link DublinCore#CONTRIBUTOR} property.
	 * 
	 * @param value the name of the person or organization.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	RelatorProperty newContributor(String value);

	/**
	 * Creates an instance of {@link DublinCore#COVERAGE} property.
	 * 
	 * @param text the text describing the coverage.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	TextProperty newCoverage(String text);

	/**
	 * Creates an instance of {@link DublinCore#CREATOR} property.
	 * 
	 * @param value the name of the person or organization.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	RelatorProperty newCreator(String value);

	/**
	 * Creates an instance of {@link DublinCore#DESCRIPTION} property.
	 * 
	 * @param text the description of the rendition.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	TextProperty newDescription(String text);

	/**
	 * Creates an instance of {@link DublinCore#FORMAT}.
	 * 
	 * @param value the value representing the format.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	SimpleProperty newFormat(String value);
	
	/**
	 * Creates an instance of {@link IdentifierProperty} property by generating a random UUID.
	 * 
	 * @return newly created {@link IdentifierProperty}.
	 */
	IdentifierProperty newIdentifier();

	/**
	 * Creates an instance of {@link IdentifierProperty} property.
	 * 
	 * @param value the value of the identifier.
	 * @return newly created {@link IdentifierProperty}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	IdentifierProperty newIdentifier(String value);

	/**
	 * Creates an instance of {@link LanguageProperty} property
	 * for the specified IETF BCP 47 language tag string.
	 * 
	 * @param languageTag the language tag defined in IETF BCP 47, such as "en-US".
	 * @return newly created {@link LanguageProperty}.
	 * @throws IllegalArgumentException if {@code languageTag} was {@code null}.
	 * @throws IllformedLocaleException if {@code languageTag} was ill-formed.
	 * @see <a href="http://www.ietf.org/rfc/bcp/bcp47.txt">Tags for Identifying Languages; Matching of Language Tags.</a>
	 */
	LanguageProperty newLanguage(String languageTag);
	
	/**
	 * Creates an instance of {@link LanguageProperty} property.
	 * 
	 * @param language the value of the language.
	 * @return newly created {@link LanguageProperty}.
	 * @throws IllegalArgumentException if {@code language} was invalid.
	 */
	LanguageProperty newLanguage(Locale language);
	
	/**
	 * Creates an property of {@link DublinCoreTerm#MODIFIED}.
	 * 
	 * @param value the last modification date and time of the publication.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} is {@code null}.
	 */
	DateProperty newModified(OffsetDateTime value);
	
	/**
	 * Creates an instance of {@link DublinCore#PUBLISHER} property.
	 * 
	 * @param value the name of the person or organization.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	RelatorProperty newPublisher(String value);

	/**
	 * Creates an instance of {@link DublinCore#RELATION} property.
	 * 
	 * @param text the text describing the related resource.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	TextProperty newRelation(String text);

	/**
	 * Creates an instance of {@link DublinCore#RIGHTS} property.
	 * 
	 * @param text the text describing the rights.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	TextProperty newRights(String text);

	/**
	 * Creates an instance of {@link DublinCore#SOURCE} property.
	 * 
	 * @param value the text describing the source.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	SourceProperty newSource(String value);
	
	/**
	 * Creates an instance of {@link DublinCore#SUBJECT} property.
	 * 
	 * @param value the text describing the source.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	SubjectProperty newSubject(String value);

	/**
	 * Creates an instance of {@link DublinCore#TITLE} property.
	 * 
	 * @param value the value of the title.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	TitleProperty newTitle(String value);

	/**
	 * Creates an instance of {@link DublinCore#TYPE}.
	 * <p>
	 * The members of {@link PublicationType} are also available as instances.
	 * </p>
	 * 
	 * @param value the value representing the type.
	 * @return newly created property.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	SimpleProperty newType(String value);
}
