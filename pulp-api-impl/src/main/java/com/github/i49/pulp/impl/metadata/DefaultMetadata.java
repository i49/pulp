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
import java.util.Locale;
import java.util.Set;

import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.PropertyBuilderSelector;
import com.github.i49.pulp.api.metadata.PropertyCounterSelector;
import com.github.i49.pulp.api.metadata.PropertyFinderSelector;
import com.github.i49.pulp.api.metadata.PropertyTesterSelector;
import com.github.i49.pulp.api.metadata.ReleaseIdentifier;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link Metadata}.
 */
public class DefaultMetadata implements Metadata {
	
	private final TermRegistry termRegistry;

	// all properties categorized by terms.
	private final PropertySet properties;
	
	private final PropertyBuilderSelector builder;
	private final PropertyFinderSelector finder;
	private final PropertyFinderSelector remover;
	private final PropertyTesterSelector tester;
	private final PropertyCounterSelector counter;

	private final ReleaseIdentifier releaseIdentifier;
	
	/**
	 * Constructs the new metadata.
	 * 
	 * @param termRegistry the registry of all terms.
	 */
	public DefaultMetadata(TermRegistry termRegistry) {
		this.termRegistry = termRegistry;
		
		this.properties = new PropertySet();
	
		this.builder = new DefaultPropertyBuilderSelector(this.properties);
		this.finder = new DefaultPropertyFinderSelector(this.properties);
		this.remover = new DefaultPropertyRemoverSelector(this.properties);
		this.tester = new DefaultPropertyTesterSelector(this.properties);
		this.counter = new DefaultPropertyCounterSelector(this.properties);
		
		this.releaseIdentifier = new DefaultReleaseIdentifier(this);
	}

	@Override
	public PropertyBuilderSelector add() {
		return builder;
	}
	
	@Override
	public boolean add(Property<?> property) {
		validate(property, "property");
		return properties.add(property);
	}
	
	@Override
	public void clear() {
		properties.clear();
	}

	@Override
	public PropertyTesterSelector contains() {
		return tester;
	}
	
	@Override
	public PropertyCounterSelector count() {
		return counter;
	}
	
	@Override
	public PropertyFinderSelector find() {
		return finder;
	}

	@Override
	public void fillMissingProperties() {
		PropertyTesterSelector tester = contains();
		if (!tester.identifier()) {
			add().identifier();
		}
		if (!tester.title()) {
			add().title(Messages.METADATA_TITLE_DEFAULT());
		}
		if (!tester.language()) {
			add().language(Locale.getDefault());
		}
		if (!tester.modified()) {
			add().modified(OffsetDateTime.now());
		}
	}

	@Override
	public ReleaseIdentifier getReleaseIdentifier() {
		return releaseIdentifier;
	}
	
	@Override
	public boolean isFilled() {
		PropertyTesterSelector t = contains();
		return t.identifier() && t.title() && t.language() && t.modified();
	}
	
	@Override
	public PropertyFinderSelector remove() {
		return remover;
	}
	
	@Override
	public boolean remove(Property<?> property) {
		validate(property, "property");
		return properties.remove(property);
	}

	@Override
	public int size() {
		return properties.size();
	}
	
	@Override
	public Set<Term> termSet() {
		return properties.termSet();
	}

	private void validate(Property<?> property, String name) {
		checkNotNull(property, name);
		Term term = property.getTerm();
		if (!termRegistry.containsTerm(term)) {
			// TODO
			throw new IllegalArgumentException();
		}
	}
}
