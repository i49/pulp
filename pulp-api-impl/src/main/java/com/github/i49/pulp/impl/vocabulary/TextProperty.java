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

import static com.github.i49.pulp.impl.base.Preconditions.checkNotNull;

import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.Direction;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.Text;

/**
 *
 */
public class TextProperty extends StringProperty implements Text {

	private final Direction direction;
	private final Locale language;
	
	public TextProperty(Term term, Builder<?, ?> b) {
		super(term, b);
		this.direction = b.direction;
		this.language = b.language;
	}

	@Override
	public Optional<Direction> getDirection() {
		return Optional.ofNullable(direction);
	}

	@Override
	public Optional<Locale> getLanguage() {
		return Optional.ofNullable(language);
	}
	
	public static abstract class Builder<T extends Text, R extends Text.Builder<T, R>> 
		extends StringProperty.Builder<T, R> implements Text.Builder<T, R> {

		private Direction direction;
		private Locale language;
		
		@Override
		public R direction(Direction direction) {
			checkNotNull(direction, "direction");
			this.direction = direction;
			return self();
		}

		@Override
		public R language(Locale language) {
			checkNotNull(language, "language");
			this.language = language;
			return self();
		}
	}
}
