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
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.PropertyBuilderSelector;
import com.github.i49.pulp.api.metadata.PropertyListerSelector;
import com.github.i49.pulp.api.metadata.PropertyTesterSelector;
import com.github.i49.pulp.api.metadata.ReleaseIdentifier;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dcterms.DublinCoreTerm;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link Metadata}.
 */
public class DefaultMetadata implements Metadata {
	
	private final TermRegistry termRegistry;

	// all properties categorized by terms.
	private final PropertySet properties;
	
	private final PropertyBuilderSelector builder;
	private final PropertyListerSelector finder;
	private final PropertyListerSelector remover;
	private final PropertyTesterSelector tester;

	private final ReleaseIdentifier releaseIdentifier;

	/**
	 * Constructs the new metadata.
	 */
	public DefaultMetadata(TermRegistry termRegistry) {
		this.termRegistry = termRegistry;
		
		this.properties = new PropertySet();
	
		this.builder = new DefaultPropertyBuilderSelector(this.properties);
		this.finder = new PropertyFinderSelector(this.properties);
		this.remover = new PropertyRemoverSelector(this.properties);
		this.tester = new DefaultPropertyTesterSelector(this.properties);
		
		this.releaseIdentifier = new DefaultReleaseIdentifier(this);
	}

	@Override
	public PropertyBuilderSelector add() {
		return builder;
	}
	
	@Override
	public void clear() {
		this.properties.clear();
	}

	@Override
	public PropertyTesterSelector contains() {
		return tester;
	}
	
	@Override
	public PropertyListerSelector find() {
		return finder;
	}

	@Override
	public void fillMissingProperties() {
	}

	@Override
	public ReleaseIdentifier getReleaseIdentifier() {
		return releaseIdentifier;
	}
	
	@Override
	public PropertyListerSelector remove() {
		return remover;
	}
	
	/*
	@Override
	public boolean add(Property property) {
		validate(property, "property");
		List<Property> values = propertyMap.getValues(property.getTerm());
		if (values.contains(property)) {
			return false;
		}
		return values.add(property);
	}

	@Override
	public void clear() {
		this.propertyMap.clear();
	}
	
	@Override
	public boolean contains(Term term) {
		validate(term, "term");
		return propertyMap.containsKey(term);
	}

	@Override
	public void fillMissingProperties() {
	}
	
	@Override
	public Property get(Term term) {
		validate(term, "term");
		if (!propertyMap.containsKey(term)) {
			throw new NoSuchElementException(Messages.METADATA_PROPERTY_NOT_FOUND(term));
		}
		return propertyMap.getValues(term).get(0);
	}

	@Override
	public Iterator<Property> getAllProperties() {
		return propertyMap.valueIterator();
	}
	
	@Override
	public int getNumberOfProperties() {
		return propertyMap.size();
	}
	
	@Override
	public int getNumberOfProperties(Term term) {
		validate(term, "term");
		return propertyMap.size(term);
	}
	
	@Override
	public Set<Term> getTerms() {
		return propertyMap.keySet();
	}
	
	@Override
	public List<Property> getList(Term term) {
		validate(term, "term");
		return propertyMap.getValues(term);
	}
	
	@Override
	public boolean isFilled() {
		return (
			contains(DublinCore.IDENTIFIER) &&
			contains(DublinCore.TITLE) &&
			contains(DublinCore.LANGUAGE) &&
			contains(DublinCoreTerm.MODIFIED)
		);
	}
	
	@Override
	public boolean remove(Property property) {
		validate(property, "property");
		Term term = property.getTerm();
		if (propertyMap.containsKey(term)) {
			return propertyMap.getValues(term).remove(property);
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
	*/
}
