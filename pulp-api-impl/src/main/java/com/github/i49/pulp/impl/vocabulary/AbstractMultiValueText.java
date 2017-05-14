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

package com.github.i49.pulp.impl.vocabulary;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.Multilingual;
import com.github.i49.pulp.api.vocabulary.Normalizable;
import com.github.i49.pulp.api.vocabulary.Text;

/**
 *
 */
abstract class AbstractMultiValueText extends AbstractText implements Multilingual, Normalizable {

	private final Locale alternativeLanguage;
	private final String alternativeValue;
	private final String normalizedValue;
	
	protected AbstractMultiValueText(Builder<?, ?> b) {
		super(b);
		this.alternativeLanguage = b.alternativeLanguage;
		this.alternativeValue = b.alternativeValue;
		this.normalizedValue = b.normalizedValue;
	}

	@Override
	public Optional<Locale> getAlternativeLanguage() {
		return Optional.ofNullable(this.alternativeLanguage);
	}

	@Override
	public Optional<String> getAlternativeValue() {
		return Optional.ofNullable(this.alternativeValue);
	}

	@Override
	public Optional<String> getNormalizedValue() {
		return Optional.ofNullable(this.normalizedValue);
	}
	
	public static abstract class Builder<T extends Text, R extends Text.Builder<T, R> & Multilingual.Builder<R> & Normalizable.Builder<R>> 
		extends AbstractText.Builder<T, R> 
		implements Multilingual.Builder<R>, Normalizable.Builder<R> {

		private Locale alternativeLanguage;
		private String alternativeValue;
		private String normalizedValue;

		@Override
		public R alternativeValue(String value, Locale language) {
			checkNotBlank(value, "value");
			checkNotNull(language, "language");
			this.alternativeValue = value;
			this.alternativeLanguage = language;
			return self();
		}

		@Override
		public R fileAs(String value) {
			checkNotBlank(value, "value");
			this.normalizedValue = value;
			return self();
		}
	}
}
