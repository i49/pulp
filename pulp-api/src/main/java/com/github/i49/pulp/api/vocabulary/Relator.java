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

package com.github.i49.pulp.api.vocabulary;

import java.util.Optional;

/**
 *
 */
public interface Relator extends Text {

	Optional<RelatorRole> getRole();
	
	/**
	 * Builder for building an instance of {@link Relator} derived property.
	 *
	 * @param <T> the type of the property to build.
	 * @param <R> the actual builder type.
	 */
	public interface Builder<T extends Relator, R extends Builder<T, R>> 
		extends Text.Builder<T, R>, Multilingual.Builder<R>, Normalizable.Builder<R>  {
		
		R role(RelatorRole role);
	}
}
