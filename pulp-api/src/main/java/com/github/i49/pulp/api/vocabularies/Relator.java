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

package com.github.i49.pulp.api.vocabularies;

import java.util.Optional;

/**
 * A person or organization who relates to the resource,
 * such as creator, contributor, and publisher.
 */
public interface Relator extends Text, Multilingual, Normalizable {

	/**
	 * Returns the role of this relator.
	 * 
	 * @return one of roles defined in {@link RelatorRole}, may be empty.
	 */
	Optional<RelatorRole> getRole();
	
	/**
	 * Builder for building instances of types derived from {@link Relator}.
	 *
	 * @param <T> the type of the property to build.
	 * @param <R> the actual builder type.
	 */
	public interface Builder<T extends Relator, R extends Builder<T, R>> 
		extends Text.Builder<T, R>, Multilingual.Builder<R>, Normalizable.Builder<R>  {
		
		/**
		 * Specifies the role of the relator.
		 * 
		 * @param role the role of the relator
		 * @return this builder.
		 * @throws IllegalArgumentException if given {@code role} is {@code null}.
		 */
		R role(RelatorRole role);
	}
}
