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

import com.github.i49.pulp.api.metadata.PropertyTesterSelector;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
import com.github.i49.pulp.api.vocabulary.dcterms.DublinCoreTerm;

/**
 *
 */
class DefaultPropertyTesterSelector implements PropertyTesterSelector {

	private final PropertySet properties;
	
	DefaultPropertyTesterSelector(PropertySet properties) {
		this.properties = properties;
	}

	@Override
	public boolean contributor() {
		return test(DublinCore.CONTRIBUTOR);
	}

	@Override
	public boolean coverage() {
		return test(DublinCore.COVERAGE);
	}

	@Override
	public boolean creator() {
		return test(DublinCore.CREATOR);
	}

	@Override
	public boolean date() {
		return test(DublinCore.DATE);
	}

	@Override
	public boolean description() {
		return test(DublinCore.DESCRIPTION);
	}

	@Override
	public boolean format() {
		return test(DublinCore.FORMAT);
	}

	@Override
	public boolean identifier() {
		return test(DublinCore.IDENTIFIER);
	}

	@Override
	public boolean language() {
		return test(DublinCore.LANGUAGE);
	}

	@Override
	public boolean modified() {
		return test(DublinCoreTerm.MODIFIED);
	}

	@Override
	public boolean publisher() {
		return test(DublinCore.PUBLISHER);
	}

	@Override
	public boolean relation() {
		return test(DublinCore.RELATION);
	}

	@Override
	public boolean rights() {
		return test(DublinCore.RIGHTS);
	}

	@Override
	public boolean source() {
		return test(DublinCore.SOURCE);
	}

	@Override
	public boolean subject() {
		return test(DublinCore.SUBJECT);
	}

	@Override
	public boolean title() {
		return test(DublinCore.TITLE);
	}

	@Override
	public boolean type() {
		return test(DublinCore.TYPE);
	}

	private boolean test(Term term) {
		return properties.containsTerm(term);
	}
}
