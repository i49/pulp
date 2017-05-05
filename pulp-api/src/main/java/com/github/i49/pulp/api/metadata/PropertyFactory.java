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
	<V> GenericProperty<V> createGenericProperty(Term term, V value);
	
	/**
	 * Creates a builder to build an instance of {@link Contributor} property.
	 * 
	 * @param name the name of the person or organization.
	 * @return a builder for building {@link Contributor}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Relator.Builder<Contributor> getContributorBuilder(String name);

	/**
	 * Creates a builder to build an instance of {@link Creator} property.
	 * 
	 * @param name the name of the person or organization.
	 * @return a builder for building {@link Creator}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Relator.Builder<Creator> getCreatorBuilder(String name);

	/**
	 * Creates a builder to build an instance of {@link Publisher} property.
	 * 
	 * @param name the name of the person or organization.
	 * @return a builder for building {@link Publisher}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Relator.Builder<Publisher> getPublisherBuilder(String name);
	
	/**
	 * Creates a builder to build an instance of {@link Title} property.
	 * 
	 * @param value the value of the title.
	 * @return a builder for building {@link Title}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Title.Builder getTitleBuilder(String value);

	/**
	 * Creates an instance of {@link Contributor} property.
	 * 
	 * @param name the name of the person or organization.
	 * @return newly created {@link Contributor}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Contributor newContributor(String name);

	/**
	 * Creates an instance of {@link Contributor} property with a specific language.
	 * 
	 * @param name the name of the person or organization.
	 * @param language the language used for the name.
	 * @return newly created {@link Contributor}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Contributor newContributor(String name, Locale language);

	/**
	 * Creates an instance of {@link Coverage} property.
	 * 
	 * @param text the text describing the coverage.
	 * @return newly created {@link Coverage}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Coverage newCoverage(String text);

	/**
	 * Creates an instance of {@link Coverage} property with the language specified.
	 * 
	 * @param text the text describing the coverage.
	 * @param language the language used for the text.
	 * @return newly created {@link Coverage}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Coverage newCoverage(String text, Locale language);

	/**
	 * Creates an instance of {@link Coverage} property with the language and the direction specified.
	 * 
	 * @param text the text describing the coverage.
	 * @param language the language used for the text.
	 * @param direction the direction of the text.
	 * @return newly created {@link Coverage}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Coverage newCoverage(String text, Locale language, Direction direction);
	
	/**
	 * Creates an instance of {@link Creator} property.
	 * 
	 * @param name the name of the person or organization.
	 * @return newly created {@link Creator}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Creator newCreator(String name);

	/**
	 * Creates an instance of {@link Creator} property with a specific language.
	 * 
	 * @param name the name of the person or organization.
	 * @param language the language used for {@code name}.
	 * @return newly created {@link Creator}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Creator newCreator(String name, Locale language);
	
	/**
	 * Creates an instance of {@link Description} property.
	 * 
	 * @param text the description of the rendition.
	 * @return newly created {@link Description}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Description newDescription(String text);

	/**
	 * Creates an instance of {@link Description} property with the language specified.
	 * 
	 * @param text the description of the rendition.
	 * @param language the language of the description. 
	 * @return newly created {@link Description}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Description newDescription(String text, Locale language);

	/**
	 * Creates an instance of {@link Description} property with the language and the direction specified.
	 * 
	 * @param text the description of the rendition.
	 * @param language the language of the description. 
	 * @param direction the text direction of the description.
	 * @return newly created {@link Description}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Description newDescription(String text, Locale language, Direction direction);
	
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
	 * Creates an instance of {@link Publisher} property.
	 * 
	 * @param name the name of the person or organization.
	 * @return newly created {@link Publisher}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Publisher newPublisher(String name);

	/**
	 * Creates an instance of {@link Publisher} property with a specific language.
	 * 
	 * @param name the name of the person or organization.
	 * @param language the language used for {@code name}.
	 * @return newly created {@link Publisher}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Publisher newPublisher(String name, Locale language);

	/**
	 * Creates an instance of {@link Relation} property.
	 * 
	 * @param text the text describing the related resource.
	 * @return newly created {@link Relation}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Relation newRelation(String text);

	/**
	 * Creates an instance of {@link Relation} property with the language specified.
	 * 
	 * @param text the text describing the related resource.
	 * @param language the language used for the text.
	 * @return newly created {@link Relation}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Relation newRelation(String text, Locale language);

	/**
	 * Creates an instance of {@link Relation} property with the language and the direction specified.
	 * 
	 * @param text the text describing the related resource.
	 * @param language the language used for the text.
	 * @param direction the text direction of the text.
	 * @return newly created {@link Relation}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Relation newRelation(String text, Locale language, Direction direction);
	
	/**
	 * Creates an instance of {@link Rights} property.
	 * 
	 * @param text the text describing the rights.
	 * @return newly created {@link Rights}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Rights newRights(String text);

	/**
	 * Creates an instance of {@link Rights} property with the language specified.
	 * 
	 * @param text the text describing the rights.
	 * @param language the language used for the text.
	 * @return newly created {@link Rights}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Rights newRights(String text, Locale language);

	/**
	 * Creates an instance of {@link Rights} property with the language and the direction specified.
	 * 
	 * @param text the text describing the rights.
	 * @param language the language used for the text.
	 * @param direction the text direction of the text.
	 * @return newly created {@link Rights}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Rights newRights(String text, Locale language, Direction direction);
	
	/**
	 * Creates an instance of {@link Source} property.
	 * 
	 * @param value the text describing the source.
	 * @return newly created {@link Source}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Source newSource(String value);
	
	/**
	 * Creates an instance of {@link Source} property with a specific scheme.
	 * 
	 * @param value the text describing the source.
	 * @param scheme the scheme used to represent the source.
	 * @return newly created {@link Source}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Source newSource(String value, String scheme);

	/**
	 * Creates an instance of {@link Subject} property.
	 * 
	 * @param value the text describing the source.
	 * @return newly created {@link Subject}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Subject newSubject(String value);

	/**
	 * Creates an instance of {@link Subject} property with a specific authority.
	 * 
	 * @param value the text describing the source.
	 * @param authority the authority used for selecting the subject.
	 * @param code the subject code specific in the authority. 
	 * @return newly created {@link Subject}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Subject newSubject(String value, SubjectAuthority authority, String code);

	/**
	 * Creates an instance of {@link Subject} property with a specific scheme.
	 * 
	 * @param value the text describing the source.
	 * @param scheme the scheme used for selecting the subject.
	 * @param code the subject code specific in the scheme.
	 * @return newly created {@link Subject}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Subject newSubject(String value, URI scheme, String code);
	
	/**
	 * Creates an instance of {@link Title} property.
	 * 
	 * @param value the value of the title.
	 * @return newly created {@link Title}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Title newTitle(String value);

	/**
	 * Creates an instance of {@link Title} property with a specific language.
	 * 
	 * @param value the value of the title.
	 * @param language the language used for the title.
	 * @return newly created {@link Title}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Title newTitle(String value, Locale language);

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
