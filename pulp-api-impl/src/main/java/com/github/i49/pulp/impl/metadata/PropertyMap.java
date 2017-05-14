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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.vocabulary.Property;
import com.github.i49.pulp.api.vocabulary.Term;

/**
 *
 */
class PropertyMap {
	
	private final Map<Term, List<Property>> map = new HashMap<>();

	public PropertyMap() {
	}
	
	public boolean contains(Term term) {
		if (term == null) {
			return false;
		}
		List<Property> list = map.get(term);
		return list != null && !list.isEmpty();
	}
	
	public Collection<Property> find(Term term) {
		List<Property> list = map.get(term);
		if (list == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList(list);
		}
	}

	public Collection<Property> remove(Term term) {
		if (term == null) {
			return Collections.emptyList();
		}
		List<Property> list = map.get(term);
		if (list == null) {
			return Collections.emptyList();
		} else {
			map.remove(term);
			return Collections.unmodifiableList(list);
		}
	}
}
