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

import java.util.Collection;
import java.util.List;

import com.github.i49.pulp.api.vocabularies.Property;
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
 * The interface to select a property lister.
 */
public interface PropertyListerSelector {

	/**
	 * Returns all properties in the metadata.
	 * 
	 * @return the collection containing all properties.
	 */
	Collection<Property<?>> all();

	/**
	 * Returns {@link Contributor} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Contributor> contributor();

	/**
	 * Returns {@link Coverage} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Coverage> coverage();

	/**
	 * Returns {@link Creator} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Creator> creator();

	/**
	 * Returns {@link Date} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Date> date();

	/**
	 * Returns {@link Description} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Description> description();

	/**
	 * Returns {@link Format} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Format> format();

	/**
	 * Returns {@link Identifier} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Identifier> identifier();
	
	/**
	 * Returns {@link Language} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Language> language();

	/**
	 * Returns {@link Modified} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Modified> modified();

	/**
	 * Returns {@link Publisher} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Publisher> publisher();

	/**
	 * Returns {@link Relation} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Relation> relation();

	/**
	 * Returns {@link Rights} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Rights> rights();
	
	/**
	 * Returns {@link Source} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Source> source();

	/**
	 * Returns {@link Subject} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Subject> subject();

	/**
	 * Returns {@link Title} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Title> title();

	/**
	 * Returns {@link Type} properties.
	 * 
	 * @return the list of the properties of the same term.
	 */
	List<Type> type();
	
	/**
	 * Returns the properties of the specified term.
	 * 
	 * @param term the term of the properties to return.
	 * @return the list of the properties of the same term.
	 * @throws IllegalArgumentException if given {@code term} is {@code null}.
	 */
	List<Property<?>> propertyOf(Term term);
}
