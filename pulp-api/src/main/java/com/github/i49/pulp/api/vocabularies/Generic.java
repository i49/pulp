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
 * Generic property.
 * 
 * @param <V> the type of the property value.
 */
public interface Generic<V> extends Property<V> {
	
	Optional<String> getScheme();
	
	public static interface Builder<V> extends PropertyBuilder<V, Generic<V>, Builder<V>> {
		
		Builder<V> scheme(String scheme);
	}
}
