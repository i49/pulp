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
import java.util.Locale;

/**
 * The factory for producing various kinds of properties of metadata.
 * 
 * <p>The following properties can be created by this type.</p>
 * <ol>
 * <li>{@link Contributor}</li>
 * <li>{@link Coverage}</li>
 * <li>{@link Creator}</li>
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
 * </ol>
 */
public interface PropertyFactory {

	/**
	 * Creates a builder to build an instance of {@link Contributor}.
	 * 
	 * @param name the name of the person or organization.
	 * @return a builder for building {@link Contributor}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	RelatorBuilder<Contributor> getContributorBuilder(String name);

	/**
	 * Creates a builder to build an instance of {@link Creator}.
	 * 
	 * @param name the name of the person or organization.
	 * @return a builder for building {@link Creator}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	RelatorBuilder<Creator> getCreatorBuilder(String name);

	/**
	 * Creates a builder to build an instance of {@link Publisher}.
	 * 
	 * @param name the name of the person or organization.
	 * @return a builder for building {@link Publisher}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	RelatorBuilder<Publisher> getPublisherBuilder(String name);
	
	/**
	 * Creates a builder to build an instance of {@link Title}.
	 * 
	 * @param value the value of the title.
	 * @return a builder for building {@link Title}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	TitleBuilder getTitleBuilder(String value);

	/**
	 * Creates an instance of {@link Contributor}.
	 * 
	 * @param name the name of the person or organization.
	 * @return newly created {@link Contributor}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Contributor newContributor(String name);

	/**
	 * Creates an instance of {@link Contributor} with a specific language.
	 * 
	 * @param name the name of the person or organization.
	 * @param language the language used for the name.
	 * @return newly created {@link Contributor}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Contributor newContributor(String name, Locale language);

	/**
	 * Creates an instance of {@link Coverage}.
	 * 
	 * @param text the text describing the coverage.
	 * @return newly created {@link Coverage}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Coverage newCoverage(String text);

	/**
	 * Creates an instance of {@link Coverage} with the language specified.
	 * 
	 * @param text the text describing the coverage.
	 * @param language the language used for the text.
	 * @return newly created {@link Coverage}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Coverage newCoverage(String text, Locale language);

	/**
	 * Creates an instance of {@link Coverage} with the language and the direction specified.
	 * 
	 * @param text the text describing the coverage.
	 * @param language the language used for the text.
	 * @param direction the direction of the text.
	 * @return newly created {@link Coverage}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Coverage newCoverage(String text, Locale language, Direction direction);
	
	/**
	 * Creates an instance of {@link Creator}.
	 * 
	 * @param name the name of the person or organization.
	 * @return newly created {@link Creator}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Creator newCreator(String name);

	/**
	 * Creates an instance of {@link Creator} with a specific language.
	 * 
	 * @param name the name of the person or organization.
	 * @param language the language used for {@code name}.
	 * @return newly created {@link Creator}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Creator newCreator(String name, Locale language);

	/**
	 * Creates an instance of {@link Description}.
	 * 
	 * @param text the description of the rendition.
	 * @return newly created {@link Description}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Description newDescription(String text);

	/**
	 * Creates an instance of {@link Description} with the language specified.
	 * 
	 * @param text the description of the rendition.
	 * @param language the language of the description. 
	 * @return newly created {@link Description}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Description newDescription(String text, Locale language);

	/**
	 * Creates an instance of {@link Description} with the language and the direction specified.
	 * 
	 * @param text the description of the rendition.
	 * @param language the language of the description. 
	 * @param direction the text direction of the description.
	 * @return newly created {@link Description}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Description newDescription(String text, Locale language, Direction direction);
	
	/**
	 * Creates an instance of {@link Format}.
	 * 
	 * @param value the value representing the format.
	 * @return newly created {@link Format}.
	 * @throws IllegalArgumentException if {@code format} was invalid.
	 */
	Format newFormat(String value);
	
	/**
	 * Creates an instance of {@link Identifier} by generating a random UUID.
	 * 
	 * @return newly created {@link Identifier}.
	 */
	Identifier newIdentifier();

	/**
	 * Creates an instance of {@link Identifier}.
	 * 
	 * @param value the value of the identifier.
	 * @return newly created {@link Identifier}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Identifier newIdentifier(String value);

	/**
	 * Creates an instance of {@link Identifier} with a explicit scheme.
	 * 
	 * @param value the value of the identifier.
	 * @param scheme the scheme used for the identifier.
	 * @return newly created {@link Identifier}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Identifier newIdentifier(String value, IdentifierScheme scheme);

	/**
	 * Creates an instance of {@link Identifier} with a explicit scheme specified by URI.
	 * 
	 * @param value the value of the identifier.
	 * @param schemeURI the URI representing the scheme used for the identifier.
	 * @return newly created {@link Identifier}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Identifier newIdentifier(String value, URI schemeURI);

	/**
	 * Creates an instance of {@link Language}
	 * for the specified IETF BCP 47 language tag string.
	 * 
	 * @param languageTag the language tag defined in IETF BCP 47.
	 * @return newly created {@link Language}.
	 * @throws IllegalArgumentException if {@code languageTag} was {@code null}.
	 * @see <a href="http://www.ietf.org/rfc/bcp/bcp47.txt">Tags for Identifying Languages; Matching of Language Tags.</a>
	 */
	Language newLanguage(String languageTag);
	
	/**
	 * Creates an instance of {@link Language}.
	 * 
	 * @param language the value of the language.
	 * @return newly created {@link Language}.
	 * @throws IllegalArgumentException if {@code language} was invalid.
	 */
	Language newLanguage(Locale language);
	
	/**
	 * Creates an instance of {@link Publisher}.
	 * 
	 * @param name the name of the person or organization.
	 * @return newly created {@link Publisher}.
	 * @throws IllegalArgumentException if {@code name} was invalid.
	 */
	Publisher newPublisher(String name);

	/**
	 * Creates an instance of {@link Publisher} with a specific language.
	 * 
	 * @param name the name of the person or organization.
	 * @param language the language used for {@code name}.
	 * @return newly created {@link Publisher}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Publisher newPublisher(String name, Locale language);

	/**
	 * Creates an instance of {@link Relation}.
	 * 
	 * @param text the text describing the related resource.
	 * @return newly created {@link Relation}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Relation newRelation(String text);

	/**
	 * Creates an instance of {@link Relation} with the language specified.
	 * 
	 * @param text the text describing the related resource.
	 * @param language the language used for the text.
	 * @return newly created {@link Relation}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Relation newRelation(String text, Locale language);

	/**
	 * Creates an instance of {@link Relation}  with the language and the direction specified.
	 * 
	 * @param text the text describing the related resource.
	 * @param language the language used for the text.
	 * @param direction the text direction of the text.
	 * @return newly created {@link Relation}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Relation newRelation(String text, Locale language, Direction direction);
	
	/**
	 * Creates an instance of {@link Rights}.
	 * 
	 * @param text the text describing the rights.
	 * @return newly created {@link Rights}.
	 * @throws IllegalArgumentException if {@code text} was invalid.
	 */
	Rights newRights(String text);

	/**
	 * Creates an instance of {@link Rights} with the language specified.
	 * 
	 * @param text the text describing the rights.
	 * @param language the language used for the text.
	 * @return newly created {@link Rights}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Rights newRights(String text, Locale language);

	/**
	 * Creates an instance of {@link Rights} with the language and the direction specified.
	 * 
	 * @param text the text describing the rights.
	 * @param language the language used for the text.
	 * @param direction the text direction of the text.
	 * @return newly created {@link Rights}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Rights newRights(String text, Locale language, Direction direction);
	
	/**
	 * Creates an instance of {@link Source}.
	 * 
	 * @param value the text describing the source.
	 * @return newly created {@link Source}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Source newSource(String value);
	
	/**
	 * Creates an instance of {@link Source} with a specific scheme.
	 * 
	 * @param value the text describing the source.
	 * @param scheme the scheme used to represent the source.
	 * @return newly created {@link Source}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Source newSource(String value, String scheme);

	/**
	 * Creates an instance of {@link Subject}.
	 * 
	 * @param value the text describing the source.
	 * @return newly created {@link Subject}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Subject newSubject(String value);

	/**
	 * Creates an instance of {@link Subject} with a specific authority.
	 * 
	 * @param value the text describing the source.
	 * @param authority the authority used for selecting the subject.
	 * @param code the subject code specific in the authority. 
	 * @return newly created {@link Subject}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Subject newSubject(String value, SubjectAuthority authority, String code);

	/**
	 * Creates an instance of {@link Subject} with a specific scheme.
	 * 
	 * @param value the text describing the source.
	 * @param scheme the scheme used for selecting the subject.
	 * @param code the subject code specific in the scheme.
	 * @return newly created {@link Subject}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Subject newSubject(String value, URI scheme, String code);
	
	/**
	 * Creates an instance of {@link Title}.
	 * 
	 * @param value the value of the title.
	 * @return newly created {@link Title}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Title newTitle(String value);

	/**
	 * Creates an instance of {@link Title} with a specific language.
	 * 
	 * @param value the value of the title.
	 * @param language the language used for the title.
	 * @return newly created {@link Title}.
	 * @throws IllegalArgumentException if one of arguments was invalid.
	 */
	Title newTitle(String value, Locale language);

	/**
	 * Creates an instance of {@link Type}.
	 * <p>
	 * The members of {@link PublicationType} are also available.
	 * </p>
	 * 
	 * @param value the value representing the type.
	 * @return newly created {@link Type}.
	 * @throws IllegalArgumentException if {@code value} was invalid.
	 */
	Type newType(String value);
}
