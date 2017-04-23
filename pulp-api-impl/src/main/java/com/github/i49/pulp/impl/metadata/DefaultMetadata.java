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

import static com.github.i49.pulp.impl.Preconditions.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

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
	
	private static final Title DEFAULT_TITLE;
	private static final Language DEFAULT_LANGUAGE;
	
	static {
		DEFAULT_TITLE = DefaultTitle.builder("Untitled").build();
		DEFAULT_LANGUAGE = new DefaultLanguage(Locale.getDefault());
	}

	/**
	 * Constructs this object. 
	 */
	public DefaultMetadata() {
		reset();
	}

	@Override
	public ReleaseIdentifier getReleaseIdentifier() {
		// TODO Auto-generated method stub
		return null;
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
	public Optional<Property> getProperty(Term term) {
		checkNotNull(term, "term");
		List<Property> list = terms.get(term);
		if (list == null || list.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(list.get(0));
		}
	}
	
	@Override
	public List<Property> getProperties(Term term) {
		checkNotNull(term, "term");
		List<Property> list = terms.get(term);
		if (list == null) {
			list = new PropertyList(term);
			terms.put(term, list);
		}
		return list;
	}
	
	@Override
	public void reset() {
		this.terms.clear();
		addProperty(DefaultIdentifier.ofRandomUUID());
		addProperty(DEFAULT_TITLE);
		addProperty(DEFAULT_LANGUAGE);
		addProperty(new DefaultModified(OffsetDateTime.now()));
	}

	private void addProperty(Property property) {
		if (property == null) {
			throw new IllegalArgumentException("\"property\" must not be null");
		}
		Term term = property.getTerm();
		List<Property> list = terms.get(term);
		if (list == null) {
			list = new PropertyList(term);
			terms.put(term, list);
		} else if (list.contains(property)) {
			throw new IllegalStateException();
		}
		list.add(property);
	}
}
