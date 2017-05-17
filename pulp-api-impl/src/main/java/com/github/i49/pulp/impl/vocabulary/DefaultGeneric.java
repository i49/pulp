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

import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.Generic;
import com.github.i49.pulp.api.vocabulary.Term;

/**
 *
 */
public class DefaultGeneric<V> extends BaseProperty<V> implements Generic<V> {

	private final String scheme;

	public DefaultGeneric(Builder<V> b) {
		super(b);
		this.scheme = b.scheme;
	}

	@Override
	public Optional<String> getScheme() {
		return Optional.ofNullable(scheme);
	}
	
	public static class Builder<V> 
		extends BaseProperty.Builder<V, Generic<V>, Generic.Builder<V>>
		implements Generic.Builder<V> {
		
		private final Term term;
		private String scheme;
		
		public Builder(Term term) {
			this.term = term;
		}
		
		@Override
		public Term getTerm() {
			return term;
		}

		@Override
		protected Generic<V> build() {
			return new DefaultGeneric<V>(this);
		}

		@Override
		public Generic.Builder<V> scheme(String scheme) {
			checkNotBlank(scheme, "scheme");
			this.scheme = scheme;
			return self();
		}
	}
}
