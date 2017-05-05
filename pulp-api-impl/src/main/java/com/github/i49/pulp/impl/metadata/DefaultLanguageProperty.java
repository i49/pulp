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

import static com.github.i49.pulp.impl.base.Preconditions.checkNotNull;

import java.util.Locale;

import com.github.i49.pulp.api.metadata.DublinCore;
import com.github.i49.pulp.api.metadata.LanguageProperty;

/**
 * The default implementation of {@link LanguageProperty} property.
 */
class DefaultLanguageProperty extends AbstractProperty<Locale> implements LanguageProperty {

	private Locale value;
	
	public DefaultLanguageProperty(Locale value) {
		super(DublinCore.LANGUAGE);
		assert(value != null);
		this.value = value;
	}

	@Override
	public Locale getValue() {
		return value;
	}

	@Override
	public String getValueAsString() {
		return value.toLanguageTag();
	}

	@Override
	public LanguageProperty setValue(Locale value) {
		checkNotNull(value, "value");
		this.value = value;
		return this;
	}
}
