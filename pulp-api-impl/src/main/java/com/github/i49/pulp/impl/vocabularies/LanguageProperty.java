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

package com.github.i49.pulp.impl.vocabularies;

import java.util.Locale;

import com.github.i49.pulp.api.vocabularies.PropertyBuilder;
import com.github.i49.pulp.api.vocabularies.TypedProperty;

/**
 * Property which has a value of {@link Locale} type.
 */
public class LanguageProperty extends BaseProperty<Locale> {

	public LanguageProperty(Builder<?, ?> b) {
		super(b);
	}
	
	@Override
	public String getValueAsString() {
		return getValue().toLanguageTag();
	}

	public static abstract class Builder<T extends TypedProperty<Locale>, R extends PropertyBuilder<Locale, T, R>>
		extends BaseProperty.Builder<Locale, T, R> {
	}
}
