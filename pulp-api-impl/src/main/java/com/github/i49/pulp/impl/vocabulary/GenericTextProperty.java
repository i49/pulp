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

import static com.github.i49.pulp.impl.base.Preconditions.checkNotBlank;

import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.GenericText;
import com.github.i49.pulp.api.vocabulary.Term;

/**
 */
public class GenericTextProperty extends MultiValueTextProperty implements GenericText {

	private final String scheme;
	
	public static GenericText.Builder builder(Term term) {
		return new Builder(term);
	}
	
	protected GenericTextProperty(Builder b) {
		super(b.term, b);
		this.scheme = b.scheme;
	}

	@Override
	public Optional<String> getScheme() {
		return Optional.ofNullable(scheme);
	}

	public static class Builder 
		extends MultiValueTextProperty.Builder<GenericText, GenericText.Builder>
		implements GenericText.Builder {

		private final Term term;
		private String scheme;

		public Builder(Term term) {
			this.term = term;
		}
		
		@Override
		protected GenericText build() {
			return new GenericTextProperty(this);
		}

		@Override
		public GenericText.Builder scheme(String scheme) {
			checkNotBlank(scheme, "scheme");
			this.scheme = scheme;
			return self();
		}
	}
}
