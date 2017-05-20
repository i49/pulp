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

package com.github.i49.pulp.impl.metadata;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.util.Collection;
import java.util.List;

import com.github.i49.pulp.api.metadata.PropertyListerSelector;
import com.github.i49.pulp.api.vocabulary.Property;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.dc.Contributor;
import com.github.i49.pulp.api.vocabulary.dc.Coverage;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.Description;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
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
import com.github.i49.pulp.api.vocabulary.dcterms.DublinCoreTerm;
import com.github.i49.pulp.api.vocabulary.dcterms.Modified;

/**
 * A selector of the object which will process properties.
 */
abstract class AbstractPropertyListerSelector implements PropertyListerSelector {
	
	@Override
	public Collection<Property> all() {
		return processAll();
	}

	@Override
	public List<Contributor> contributor() {
		return process(DublinCore.CONTRIBUTOR);
	}

	@Override
	public List<Coverage> coverage() {
		return process(DublinCore.COVERAGE);
	}

	@Override
	public List<Creator> creator() {
		return process(DublinCore.CREATOR);
	}

	@Override
	public List<Date> date() {
		return process(DublinCore.DATE);
	}

	@Override
	public List<Description> description() {
		return process(DublinCore.DESCRIPTION);
	}

	@Override
	public List<Format> format() {
		return process(DublinCore.FORMAT);
	}

	@Override
	public List<Identifier> identifier() {
		return process(DublinCore.IDENTIFIER);
	}

	@Override
	public List<Language> language() {
		return process(DublinCore.LANGUAGE);
	}

	@Override
	public List<Modified> modified() {
		return process(DublinCoreTerm.MODIFIED);
	}

	@Override
	public List<Publisher> publisher() {
		return process(DublinCore.PUBLISHER);
	}

	@Override
	public List<Relation> relation() {
		return process(DublinCore.RELATION);
	}

	@Override
	public List<Rights> rights() {
		return process(DublinCore.RIGHTS);
	}

	@Override
	public List<Source> source() {
		return process(DublinCore.SOURCE);
	}

	@Override
	public List<Subject> subject() {
		return process(DublinCore.SUBJECT);
	}

	@Override
	public List<Title> title() {
		return process(DublinCore.TITLE);
	}

	@Override
	public List<Type> type() {
		return process(DublinCore.TYPE);
	}
	
	@Override
	public List<Property> propertyOf(Term term) {
		checkNotNull(term, "term");
		return process(term);
	}
	
	/**
	 * Returns the list of the properties to process.
	 *  
	 * @param term the term of the properties to process.
	 * @return the list of the properties of the specific type.
	 */
	@SuppressWarnings("unchecked")
	private <T extends Property> List<T> process(Term term) {
		return (List<T>)processTerm(term);
	}
	
	protected abstract Collection<Property> processAll();
	
	protected abstract List<Property> processTerm(Term term);
}
