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

import com.github.i49.pulp.api.vocabulary.Property;
import com.github.i49.pulp.api.vocabulary.PropertyBuilder;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.TypedProperty;

/**
 *
 */
public class DeferredProperty<V> implements Property {
	
	private final Term term;
	private final PropertyBuilder<V, ?, ?> builder;
	private TypedProperty<V> property;

	public static <V> DeferredProperty<V> of(Term term, PropertyBuilder<V, ?, ?> builder) {
		return new DeferredProperty<V>(term, builder);
	}
	
	private DeferredProperty(Term term, PropertyBuilder<V, ?, ?> builder) {
		this.term = term;
		this.builder = builder;
	}

	@Override
	public Term getTerm() {
		return term;
	}

	@Override
	public Object getValue() {
		throw new AssertionError("Unexpected method call");
	}

	@Override
	public String getValueAsString() {
		throw new AssertionError("Unexpected method call");
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		String termName = getTerm().localName();
		b.append("{").append(termName).append(":\"(deferred)\"}");
		return b.toString();
	}
	
	public Property get() {
		if (property == null) {
			property = builder.result();
		}
		return property;
	}
}
