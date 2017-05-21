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

import com.github.i49.pulp.api.metadata.PropertyCounterSelector;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dcterms.DublinCoreTerm;

/**
 * The default implementation of {@link PropertyCounterSelector}.
 */
class DefaultPropertyCounterSelector implements PropertyCounterSelector {

	private final PropertySet properties;
	
	DefaultPropertyCounterSelector(PropertySet properties) {
		this.properties = properties;
	}

	@Override
	public int contributor() {
		return count(DublinCore.CONTRIBUTOR);
	}

	@Override
	public int coverage() {
		return count(DublinCore.COVERAGE);
	}

	@Override
	public int creator() {
		return count(DublinCore.CREATOR);
	}

	@Override
	public int date() {
		return count(DublinCore.DATE);
	}

	@Override
	public int description() {
		return count(DublinCore.DESCRIPTION);
	}

	@Override
	public int format() {
		return count(DublinCore.FORMAT);
	}

	@Override
	public int identifier() {
		return count(DublinCore.IDENTIFIER);
	}

	@Override
	public int language() {
		return count(DublinCore.LANGUAGE);
	}

	@Override
	public int modified() {
		return count(DublinCoreTerm.MODIFIED);
	}

	@Override
	public int publisher() {
		return count(DublinCore.PUBLISHER);
	}

	@Override
	public int relation() {
		return count(DublinCore.RELATION);
	}

	@Override
	public int rights() {
		return count(DublinCore.RIGHTS);
	}

	@Override
	public int source() {
		return count(DublinCore.SOURCE);
	}

	@Override
	public int subject() {
		return count(DublinCore.SUBJECT);
	}

	@Override
	public int title() {
		return count(DublinCore.TITLE);
	}

	@Override
	public int type() {
		return count(DublinCore.TYPE);
	}

	@Override
	public int propertyOf(Term term) {
		checkNotNull(term, "term");
		return count(term);
	}
	
	/**
	 * Counts the properties of the term.
	 * 
	 * @param term the term of the property.
	 * @return the number of the properties.
	 */
	private int count(Term term) {
		return properties.countByTerm(term);
	}
}
