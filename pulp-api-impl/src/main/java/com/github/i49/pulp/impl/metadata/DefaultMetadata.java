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
import com.github.i49.pulp.api.metadata.Language;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.ReleaseIdentifier;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.Title;

/**
 * The default implementation of {@link Metadata}.
 */
public class DefaultMetadata implements Metadata {
	
	private final Map<Term, List<Property>> terms = new HashMap<>();
	private final ReleaseIdentifier releaseIdentifier;

	private static final Title DEFAULT_TITLE;
	private static final Language DEFAULT_LANGUAGE;
	
	static {
		DEFAULT_TITLE = DefaultTitle.builder("Untitled").build();
		DEFAULT_LANGUAGE = new DefaultLanguage(Locale.getDefault());
	}

	/**
	 * Constructs the new metadata.
	 */
	public DefaultMetadata() {
		releaseIdentifier = new DefaultReleaseIdentifier(this);
		reset();
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
	public void reset() {
		this.terms.clear();
		addDefaultLists();
		add(DefaultIdentifier.ofRandomUUID());
		add(DEFAULT_TITLE);
		add(DEFAULT_LANGUAGE);
		add(new DefaultModified(OffsetDateTime.now()));
	}
	
	@Override
	public void set(Property property) {
		checkNotNull(property, "property");
		List<Property> list = getList(property.getTerm());
		if (list.isEmpty()) {
			list.add(property);
		} else {
			list.set(0, property);
		}
		while (list.size() > 1) {
			list.remove(1);
		}
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
	
	private void addDefaultLists() {
		addList(BasicTerm.IDENTIFIER, true, true);
		addList(BasicTerm.TITLE, true, true);
		addList(BasicTerm.LANGUAGE, true, true);
		// Only one property is allowed for Modified and Date.
		addList(BasicTerm.MODIFIED, true, false);
		addList(BasicTerm.DATE, false, false);
	}
	
	private List<Property> addList(Term term) {
		return addList(term, false, true);
	}
	
	private List<Property> addList(Term term, boolean required, boolean multiple) {
		List<Property> list = new PropertyList(term, required, multiple);
		terms.put(term, list);
		return list;
	}
}
