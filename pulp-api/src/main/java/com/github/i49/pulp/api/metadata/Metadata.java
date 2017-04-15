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
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * A set of meta information describing a EPUB publication and renditions.
 */
public interface Metadata {
	
	/**
	 * Returns the primary identifier (Unique Identifier) of the rendition.
	 * 
	 * @return the primary identifier.
	 */
	Identifier getPrimaryIdentifier();

	/**
	 * Assigns the primary identifier (Unique Identifier) of the rendition.
	 * 
	 * @param identifier the primary identifier of the rendition.
	 * @throws IllegalArgumentException if {@code identifier} is {@code null}.
	 */
	void setPrimaryIdentifier(Identifier identifier);
	
	/**
	 * Returns the publication date and time of the rendition.
	 * 
	 * @return the publication date and time of the rendition.
	 */
	Optional<OffsetDateTime> getPublicationDate();
	
	/**
	 * Assigns the publication date and time of the rendition.
	 * 
	 * @param dateTime the publication date and time.
	 * @throws IllegalArgumentException if {@code dateTime} is {@code null}.
	 */
	void setPublicationDate(OffsetDateTime dateTime);
	
	/**
	 * Returns the last modification date and time of the rendition.
	 * 
	 * @return the last modification date and time of the rendition.
	 */
	Optional<OffsetDateTime> getLastModificationDate();
	
	/**
	 * Assigns the last modification date and time of the rendition.
	 * 
	 * @param dateTime the last modification date and time.
	 * @throws IllegalArgumentException if {@code dateTime} is {@code null}.
	 */
	void setLastModificationDate(OffsetDateTime dateTime);
	
	/**
	 * Returns the identifiers of the rendition except the primary identifier.
	 * 
	 * @return the list of identifiers.
	 */
	List<Identifier> getAdditionalIdentifiers();
	
	/**
	 * Returns the titles of the rendition.
	 * The first title is the primary one for the rendition.
	 * 
	 * @return the titles of the rendition.
	 */
	List<Title> getTitles();
	
	/**
	 * Returns the languages of the content of the rendition.
	 * 
	 * @return the languages of the content of the rendition.
	 */
	List<Locale> getLanguages();
	
	/**
	 * Returns the creators responsible for the creation of the content of the rendition.
	 * 
	 * @return the creators of the rendition.
	 */
	List<Creator> getCreators();
	
	/**
	 * Returns the contributors that played a secondary role in the creation of the content of the rendition.
	 * 
	 * @return the contributors of the rendition.
	 */
	List<Contributor> getContributors();

	/**
	 * Returns the publishers of the rendition.
	 * 
	 * @return the publishers of the rendition.
	 */
	List<Publisher> getPublishers();
	
	/**
	 * Returns the subjects of the rendition.
	 * 
	 * @return the subjects of the rendition.
	 */
	List<Subject> getSubjects();
	
	/**
	 * Returns the types of the rendition.
	 * 
	 * @return the types of the rendition.
	 */
	List<Type> getTypes();
	
	/**
	 * Returns the formats of the rendition.
	 * 
	 * @return the formats of the rendition.
	 */
	List<Format> getFormats();

	/**
	 * Returns the sources of the rendition.
	 * 
	 * @return the sources of the rendition.
	 */
	List<Source> getSources(); 
	
	/**
	 * Returns the coverages of the rendition.
	 * 
	 * @return the coverages of the rendition.
	 */
	List<Coverage> getCoverages();
	
	/**
	 * Returns the descriptions of the rendition.
	 * 
	 * @return the descriptions of the rendition.
	 */
	List<Description> getDescriptions();

	/**
	 * Returns the related resources of the rendition.
	 * 
	 * @return the related resources of the rendition.
	 */
	List<Relation> getRelations();

	/**
	 * Returns the rights over the rendition.
	 * 
	 * @return the rights over the rendition.
	 */
	List<Rights> getRights();
}
