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

import java.util.Collection;

import com.github.i49.pulp.api.metadata.PropertyIterable;
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
 *
 */
abstract class AbstractPropertyListerSelector implements PropertyListerSelector {
	
	@Override
	public PropertyIterable<Property> all() {
		return null;
	}

	@Override
	public PropertyIterable<Contributor> contributor() {
		return list(DublinCore.CONTRIBUTOR);
	}

	@Override
	public PropertyIterable<Coverage> coverage() {
		return list(DublinCore.COVERAGE);
	}

	@Override
	public PropertyIterable<Creator> creator() {
		return list(DublinCore.CREATOR);
	}

	@Override
	public PropertyIterable<Date> date() {
		return list(DublinCore.DATE);
	}

	@Override
	public PropertyIterable<Description> description() {
		return list(DublinCore.DESCRIPTION);
	}

	@Override
	public PropertyIterable<Format> format() {
		return list(DublinCore.FORMAT);
	}

	@Override
	public PropertyIterable<Identifier> identifier() {
		return list(DublinCore.IDENTIFIER);
	}

	@Override
	public PropertyIterable<Language> language() {
		return list(DublinCore.LANGUAGE);
	}

	@Override
	public PropertyIterable<Modified> modified() {
		return list(DublinCoreTerm.MODIFIED);
	}

	@Override
	public PropertyIterable<Publisher> publisher() {
		return list(DublinCore.PUBLISHER);
	}

	@Override
	public PropertyIterable<Relation> relation() {
		return list(DublinCore.RELATION);
	}

	@Override
	public PropertyIterable<Rights> rights() {
		return list(DublinCore.RIGHTS);
	}

	@Override
	public PropertyIterable<Source> source() {
		return list(DublinCore.SOURCE);
	}

	@Override
	public PropertyIterable<Subject> subject() {
		return list(DublinCore.SUBJECT);
	}

	@Override
	public PropertyIterable<Title> title() {
		return list(DublinCore.TITLE);
	}

	@Override
	public PropertyIterable<Type> type() {
		return list(DublinCore.TYPE);
	}
	
	private <T extends Property> PropertyIterable<T> list(Term term) {
		@SuppressWarnings("unchecked")
		Collection<T> list = (Collection<T>)listByTerm(term);
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>)term.getType();
		return new SimplePropertyIterable<T>(list, type);
	}
	
	protected abstract Collection<Property> listByTerm(Term term);
}
