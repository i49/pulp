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

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.github.i49.pulp.api.metadata.BasicTerm;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.ReleaseIdentifier;
import com.github.i49.pulp.api.metadata.Term;

/**
 * The default implementation of {@link Metadata}.
 */
public class DefaultMetadata implements Metadata {
	
	private final Map<Term, List<Property>> terms = new HashMap<>();
	private final ReleaseIdentifier releaseIdentifier;
	private final PropertyFactory propertyFactory;

	/**
	 * Constructs the new metadata.
	 */
	public DefaultMetadata(PropertyFactory propertyFactory) {
		this.releaseIdentifier = new DefaultReleaseIdentifier(this);
		this.propertyFactory = propertyFactory;
	}

	@Override
	public ReleaseIdentifier getReleaseIdentifier() {
		return releaseIdentifier;
	}

	@Override
	public int getNumberOfProperties() {
		int total = 0;
		for (List<Property> list: terms.values()) {
			total += list.size();
		}
		return total;
	}
	
	@Override
	public int getNumberOfProperties(Term term) {
		checkNotNull(term, "term");
		List<Property> list = terms.get(term);
		return (list != null) ? list.size() : 0;
	}
	
	@Override
	public boolean contains(Term term) {
		checkNotNull(term, "term");
		List<Property> list = terms.get(term);
		return (list != null && !list.isEmpty());
	}
	
	@Override
	public Set<Term> getTerms() {
		Set<Term> terms = new HashSet<>();
		for (Term term: this.terms.keySet()) {
			if (!this.terms.get(term).isEmpty()) {
				terms.add(term);
			}
		}
		return terms;
	}
	
	@Override
	public Property get(Term term) {
		checkNotNull(term, "term");
		List<Property> list = terms.get(term);
		if (list == null || list.isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.get(0);
	}
	
	@Override
	public List<Property> getList(Term term) {
		checkNotNull(term, "term");
		List<Property> list = terms.get(term);
		if (list == null) {
			list = addList(term);
		}
		return list;
	}
	
	@Override
	public void clear() {
		this.terms.clear();
	}
	
	@Override
	public boolean add(Property property) {
		checkNotNull(property, "property");
		List<Property> list = getList(property.getTerm());
		if (list.contains(property)) {
			return false;
		}
		return list.add(property);
	}
	
	@Override
	public boolean remove(Property property) {
		checkNotNull(property, "property");
		List<Property> list = terms.get(property.getTerm());
		if (list == null) {
			return false;
		}
		return list.remove(property);
	}
	
	@Override
	public void addMandatory() {
		if (!contains(BasicTerm.IDENTIFIER)) {
			add(getFactory().newIdentifier());
		}
		if (!contains(BasicTerm.TITLE)) {
			add(getFactory().newTitle("untitled"));
		}
		if (!contains(BasicTerm.LANGUAGE)) {
			add(getFactory().newLanguage(Locale.getDefault()));
		}
		if (!contains(BasicTerm.MODIFIED)) {
			OffsetDateTime now = OffsetDateTime.now();
			add(getFactory().newModified(now));
		}
	}
	
	private List<Property> addList(Term term) {
		assert(term != null);
		boolean multiple = !(term == BasicTerm.DATE || term == BasicTerm.MODIFIED);
		List<Property> list = new PropertyList(term, multiple);
		terms.put(term, list);
		return list;
	}
	
	private PropertyFactory getFactory() {
		return propertyFactory;
	}
}
