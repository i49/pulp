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

import java.util.function.Function;

import com.github.i49.pulp.api.metadata.Relator;
import com.github.i49.pulp.api.metadata.RelatorBuilder;

/**
 * The single implementation of {@link RelatorBuilder}.
 * 
 * @param <P> the type of relator property to build.
 */
class DefaultRelatorBuilder<P extends Relator> 
	extends AbstractPropertyBuilder<P, RelatorBuilder<P>> implements RelatorBuilder<P> {

	private final Function<DefaultRelatorBuilder<P>, P> constructor; 
	
	public DefaultRelatorBuilder(String value, Function<DefaultRelatorBuilder<P>, P> constructor) {
		super(value);
		this.constructor = constructor;
	}
	
	@Override
	public P build() {
		return constructor.apply(this);
	}
}
