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

import com.github.i49.pulp.api.metadata.Creator;
import com.github.i49.pulp.api.metadata.RelatorBuilder;

/**
 * The single implementation of {@link Creator}.
 */
class DefaultCreator extends AbstractRelator<Creator> implements Creator {

	/**
	 * Creates a builder for building an instance of this class.
	 * 
	 * @param name the name of the creator.
	 * @return created builder.
	 */
	public static RelatorBuilder<Creator> builder(String name) {
		return new DefaultRelatorBuilder<Creator>(name, DefaultCreator::new);
	}
	
	private DefaultCreator(DefaultRelatorBuilder<Creator> builder) {
		super(builder);
	}
}
