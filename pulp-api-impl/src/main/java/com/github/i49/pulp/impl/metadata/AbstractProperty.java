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

import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TypedProperty;

/**
 * A skeletal implementation of {@link Property}.
 * 
 * @param <V> the type of the property value.
 */
abstract class AbstractProperty<V> implements TypedProperty<V> {

	private final Term term;
	
	protected AbstractProperty(Term term) {
		assert(term != null);
		this.term = term;
	}
	
	@Override
	public Term getTerm() {
		return term;
	}
	
	@Override
	public String toString() {
		return getValueAsString();
	}
}
