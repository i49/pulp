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

import com.github.i49.pulp.api.vocabulary.Property;
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
 * An interface to list properties in the metadata.
 */
public interface PropertyListerSelector {

	/**
	 * Returns all properties in the metadata.
	 * 
	 * @return a collection containing all properties.
	 */
	Collection<Property> all();

	List<Contributor> contributor();

	List<Coverage> coverage();

	List<Creator> creator();

	List<Date> date();

	List<Description> description();

	List<Format> format();

	List<Identifier> identifier();
	
	List<Language> language();

	List<Modified> modified();

	List<Publisher> publisher();

	List<Relation> relation();

	List<Rights> rights();
	
	List<Source> source();

	List<Subject> subject();

	List<Title> title();

	List<Type> type();
	
	/**
	 * Returns the properties of the specified term.
	 * 
	 * @param term the term of the properties to return.
	 * @return list of properties.
	 */
	List<Property> propertyOf(Term term);
}
