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

package com.github.i49.pulp.impl.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Utility methods for enumeration types.
 */
public final class Enums {

	private Enums() {
	}
	
	/**
	 * Returns a mapper which maps a key to the corresponding enum constant. 
	 * 
	 * @param <K> the type of the key. 
	 * @param <V> the type of the enum.
	 * @param type the class representing the enum V.
	 * @param keyMapper the mapper to map each enum constant to its key.
	 * @return the created mapper.
	 */
	public static <K, V extends Enum<V>> Function<K, Optional<V>> mapper(Class<V> type, Function<V, K> keyMapper) {
		final Map<K, V> map = new HashMap<>();
		for (V value: type.getEnumConstants()) {
			map.put(keyMapper.apply(value), value);
		}
		return (K key)->{
			return Optional.ofNullable(map.get(key));
		};
	}
}
