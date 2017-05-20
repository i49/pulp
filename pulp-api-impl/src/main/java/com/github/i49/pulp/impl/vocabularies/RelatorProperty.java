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

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.util.Optional;

import com.github.i49.pulp.api.vocabularies.Relator;
import com.github.i49.pulp.api.vocabularies.RelatorRole;

/**
 *
 */
public class RelatorProperty extends MultiValueTextProperty implements Relator {

	private final RelatorRole role;
	
	public RelatorProperty(Builder<?, ?> b) {
		super(b);
		this.role = b.role;
	}

	@Override
	public Optional<RelatorRole> getRole() {
		return Optional.ofNullable(role);
	}

	public static abstract class Builder<T extends Relator, R extends Relator.Builder<T, R>> 
		extends MultiValueTextProperty.Builder<T, R> implements Relator.Builder<T, R> {

		private RelatorRole role;
		
		@Override
		public R role(RelatorRole role) {
			checkNotNull(role, "role");
			this.role = role;
			return self();
		}
	}
}
