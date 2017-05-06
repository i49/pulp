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

import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.metadata.Multilingual;
import com.github.i49.pulp.api.metadata.Normalizable;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.TextProperty;

/**
 * A skeletal implementation of {@link TextProprety} 
 * that additionally implements {@link Multilingual} and {@link Normalizable}.
 *
 * @param <S> the type of the final descendant type.
 */
class MultilingualNormalizableTextProperty<S extends TextProperty>
	extends DefaultTextProperty<S> implements Multilingual<S>, Normalizable<S> {

	private String normalizedValue;
	private String alternative;
	private Locale alternativeLanguage;
	
	public MultilingualNormalizableTextProperty(Term term, String value) {
		super(term, value);
		this.normalizedValue = null;
		this.alternative = null;
		this.alternativeLanguage = null;
	}
	
	@Override
	public S fileAs(String value) {
		checkNotBlank(value, "value");
		this.normalizedValue = value.trim();
		return self();
	}

	@Override
	public Optional<String> getAlternativeValue() {
		return Optional.ofNullable(this.alternative);
	}

	@Override
	public Optional<Locale> getAlternativeLanguage() {
		return Optional.ofNullable(this.alternativeLanguage);
	}

	@Override
	public Optional<String> getNormalizedValue() {
		return Optional.ofNullable(this.normalizedValue);
	}

	@Override
	public S resetNormalizedValue() {
		this.normalizedValue = null;
		return self();
	}

	@Override
	public S resetAlternativeValue() {
		this.alternative = null;
		this.alternativeLanguage = null;
		return self();
	}

	@Override
	public S setAlternativeValue(String value, Locale language) {
		checkNotBlank(value, "value");
		checkNotNull(language, "language");
		this.alternative = value.trim();
		this.alternativeLanguage = language;
		return self();
	}

	@SuppressWarnings("unchecked")
	private S self() {
		return (S)this;
	}
}
