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
import java.util.function.Function;

/**
 * Utility methods for enumeration types.
 */
public final class Enums {

	private Enums() {
	}
	
	/**
	 * Returns a map for mapping the key to the corresponding enumerator.
	 *  
	 * @param <K> the type of the key. 
	 * @param <E> the type of the enum.
	 * @param type the class representing the enum type.
	 * @param keyMapper the mapper to map each enumerator to its unique key.
	 * @return the created map.
	 */
	public static <K, E extends Enum<E>> Map<K, E> mapTo(Class<E> type, Function<E, K> keyMapper) {
		Map<K, E> map = new HashMap<>();
		for (E value: type.getEnumConstants()) {
			map.put(keyMapper.apply(value), value);
		}
		return map;
	}
}
