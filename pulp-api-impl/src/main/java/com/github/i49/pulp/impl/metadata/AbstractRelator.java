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

import java.util.Optional;

import com.github.i49.pulp.api.metadata.Relator;
import com.github.i49.pulp.api.metadata.Representation;
import com.github.i49.pulp.api.metadata.Term;

/**
 * A skeletal implementation of {@link Relator}.
 */
abstract class AbstractRelator<P extends Relator> extends AbstractTextTypeProperty implements Relator {

	private final Optional<String> normalizedValue;
	private final Optional<Representation> alternative;
	private final Optional<String> role;
	
	/**
	 * Constructs this relator.
	 * 
	 * @param builder the builder for building an instance of this class.
	 */
	protected AbstractRelator(Term term, DefaultRelatorBuilder<P> builder) {
		super(term, builder);
		this.normalizedValue = builder.getNormalizedValue();
		this.alternative = builder.getAlternative();
		this.role = builder.getRole();
	}
	
	/**
	 * Constructs this relator with the role specified.
	 * 
	 * @param builder the builder for building an instance of this class.
	 * @param role the role of this relator.
	 */
	protected AbstractRelator(Term term, DefaultRelatorBuilder<P> builder, String role) {
		super(term, builder);
		this.normalizedValue = builder.getNormalizedValue();
		this.alternative = builder.getAlternative();
		this.role = Optional.ofNullable(role);
	}
	
	@Override
	public Optional<String> getNormalizedValue() {
		return normalizedValue;
	}
	
	@Override
	public Optional<Representation> getAlternativeRepresentation() {
		return alternative;
	}
	
	@Override
	public Optional<String> getRole() {
		return role;
	}
}
