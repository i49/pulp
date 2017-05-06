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

import java.util.Optional;

import com.github.i49.pulp.api.metadata.RelatorProperty;
import com.github.i49.pulp.api.metadata.Term;

/**
 * A skeletal implementation of {@link RelatorProperty}.
 */
class DefaultRelatorProperty 
	extends MultilingualNormalizableTextProperty<RelatorProperty>
	implements RelatorProperty {

	private String role;

	/**
	 * Constructs this relator.
	 * 
	 * @param temr the term of this property.
	 * @param name the name of the relator.
	 */
	DefaultRelatorProperty(Term term, String value) {
		super(term, value);
	}
	
	@Override
	public Optional<String> getRole() {
		return Optional.ofNullable(role);
	}
	
	@Override 
	public RelatorProperty resetRole() {
		this.role = null;
		return this;
	}
	
	@Override 
	public RelatorProperty setRole(String role) {
		checkNotNull(role, "role");
		this.role = role;
		return this;
	}
}
