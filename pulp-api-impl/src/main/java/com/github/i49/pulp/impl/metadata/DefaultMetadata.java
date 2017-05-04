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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;

import com.github.i49.pulp.api.metadata.BasicTerm;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.ReleaseIdentifier;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.impl.base.AbstractListMap;
import com.github.i49.pulp.impl.base.ListMap;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link Metadata}.
 */
public class DefaultMetadata implements Metadata {
	
	private final TermRegistry termRegistry;
	private final PropertyFactory propertyFactory;

	// all properties categorized by terms.
	private final ListMap<Term, Property> terms = new PropertyListMap();
	private final ReleaseIdentifier releaseIdentifier;

	/**
	 * Constructs the new metadata.
	 */
	public DefaultMetadata(TermRegistry termRegistry, PropertyFactory propertyFactory) {
		this.termRegistry = termRegistry;
		this.propertyFactory = propertyFactory;
		this.releaseIdentifier = new DefaultReleaseIdentifier(this);
	}

	@Override
	public boolean add(Property property) {
		validate(property, "property");
		List<Property> values = terms.getValues(property.getTerm());
		if (values.contains(property)) {
			return false;
		}
		return values.add(property);
	}

	@Override
	public void clear() {
		this.terms.clear();
	}
	
	@Override
	public boolean contains(Term term) {
		validate(term, "term");
		return terms.containsKey(term);
	}

	@Override
	public void fillMissingProperties() {
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
	
	@Override
	public Property get(Term term) {
		validate(term, "term");
		if (!terms.containsKey(term)) {
			throw new NoSuchElementException(Messages.METADATA_PROPERTY_NOT_FOUND(term));
		}
		return terms.getValues(term).get(0);
	}

	@Override
	public Iterator<Property> getAllProperties() {
		return terms.valueIterator();
	}
	
	@Override
	public int getNumberOfProperties() {
		return terms.size();
	}
	
	@Override
	public int getNumberOfProperties(Term term) {
		validate(term, "term");
		return terms.size(term);
	}
	
	@Override
	public ReleaseIdentifier getReleaseIdentifier() {
		return releaseIdentifier;
	}

	@Override
	public Set<Term> getTerms() {
		return terms.keySet();
	}
	
	@Override
	public List<Property> getList(Term term) {
		validate(term, "term");
		return terms.getValues(term);
	}
	
	@Override
	public boolean isFilled() {
		return (
			contains(BasicTerm.IDENTIFIER) &&
			contains(BasicTerm.TITLE) &&
			contains(BasicTerm.LANGUAGE) &&
			contains(BasicTerm.MODIFIED)
		);
	}
	
	@Override
	public boolean remove(Property property) {
		validate(property, "property");
		Term term = property.getTerm();
		if (terms.containsKey(term)) {
			return terms.getValues(term).remove(property);
		} else {
			return false;
		}
	}
	
	private PropertyFactory getFactory() {
		return propertyFactory;
	}
	
	private void validate(Property property, String name) {
		checkNotNull(property, name);
		Term term = property.getTerm();
		if (!termRegistry.containsTerm(term)) {
			// TODO
			throw new IllegalArgumentException();
		}
	}
	
	private void validate(Term term, String name) {
		checkNotNull(term, name);
		if (!termRegistry.containsTerm(term)) {
			// TODO
			throw new IllegalArgumentException();
		}
	}
	
	private static class PropertyListMap extends AbstractListMap<Term, Property> {

		@Override
		protected List<Property> createList(Term term) {
			return new PropertyList(term, term.isRepeatable());
		}
	}
}
