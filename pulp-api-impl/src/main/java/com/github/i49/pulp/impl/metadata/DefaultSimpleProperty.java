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

import com.github.i49.pulp.api.metadata.SimpleProperty;
import com.github.i49.pulp.api.metadata.Term;

/**
 * The default implementation of {@link SimpleProperty}.
 */
class DefaultSimpleProperty extends AbstractProperty<String> implements SimpleProperty {

	private String value;
	
	DefaultSimpleProperty(Term term, String value) {
		super(term);
		assert(value != null);
		this.value = value.trim();
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public DefaultSimpleProperty setValue(String value) {
		checkNotBlank(value, "value");
		this.value = value.trim();
		return this;
	}
}
