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
import com.github.i49.pulp.api.vocabularies.rendering.Flow;
import com.github.i49.pulp.api.vocabularies.rendering.Layout;
import com.github.i49.pulp.api.vocabularies.rendering.Orientation;
import com.github.i49.pulp.api.vocabularies.rendering.Spread;

/**
 * The type for selecting a property finder.
 * <p>
 * If the methods provided by this type have no properties to return,
 * they will return an empty collection instead of returning {@code null}.
 * All of the collections returned by this type are unmodifiable.
 * </p>
 */
public interface PropertyFinderSelector {

	/**
	 * Returns all properties in the metadata.
	 * 
	 * @return the collection containing all properties, never be {@code null}.
	 */
	Collection<Property<?>> all();

	/**
	 * Returns all properties of {@link Contributor}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Contributor> contributor();

	/**
	 * Returns all properties of {@link Coverage}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Coverage> coverage();

	/**
	 * Returns all properties of {@link Creator}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Creator> creator();

	/**
	 * Returns all properties of {@link Date}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Date> date();

	/**
	 * Returns all properties of {@link Description}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Description> description();

	/**
	 * Returns all properties of {@link Format}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Format> format();

	/**
	 * Returns all properties of {@link Identifier}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Identifier> identifier();
	
	/**
	 * Returns all properties of {@link Language}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Language> language();

	/**
	 * Returns all properties of {@link Modified}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Modified> modified();

	/**
	 * Returns all properties of {@link Publisher}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Publisher> publisher();

	/**
	 * Returns all properties of {@link Relation}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Relation> relation();

	/**
	 * Returns all properties of {@link Rights}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Rights> rights();
	
	/**
	 * Returns all properties of {@link Source}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Source> source();

	/**
	 * Returns all properties of {@link Subject}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Subject> subject();

	/**
	 * Returns all properties of {@link Title}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Title> title();

	/**
	 * Returns all properties of {@link Type}.
	 * 
	 * @return the list of the properties of the specified term, never be {@code null}.
	 */
	List<Type> type();
	
	/**
	 * Returns all properties of the specified term.
	 * 
	 * @param term the term of the properties to return.
	 * @return the list of the properties of the specified term.
	 * @throws IllegalArgumentException if given {@code term} is {@code null}.
	 */
	List<Property<?>> propertyOf(Term term);
	
	/**
	 * Returns the type for selecting one of rendering properties.
	 * 
	 * @return the instance of {@link RenderingPropertyFinderSelector}.
	 */
	RenderingPropertyFinderSelector rendering();
	
	/**
	 *  The type for selecting one of rendering property finders.
	 */
	public interface RenderingPropertyFinderSelector {
		
		/**
		 * Returns all properties of {@link Flow}.
		 * 
		 * @return the list of the properties of the specified term, never be {@code null}.
		 */
		List<Flow> flow();
		
		/**
		 * Returns all properties of {@link Layout}.
		 * 
		 * @return the list of the properties of the specified term, never be {@code null}.
		 */
		List<Layout> layout();
		
		/**
		 * Returns all properties of {@link Orientation}.
		 * 
		 * @return the list of the properties of the specified term, never be {@code null}.
		 */
		List<Orientation> orientation();
		
		/**
		 * Returns all properties of {@link Spread}.
		 * 
		 * @return the list of the properties of the specified term, never be {@code null}.
		 */
		List<Spread> spread();
	}
}
