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
 * A map for mapping keys to enum constants.
 */
public class ReverseEnumMap<K, V extends Enum<V>> {

	/**
	 * Creates an instance of this class.
	 * 
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @param type the class of the enum.
	 * @param keyMapper the mapper which generates a key for each enum constant.
	 * @return newly created map.
	 */
	public static <K, V extends Enum<V>> ReverseEnumMap<K, V> to(Class<V> type, Function<V, K> keyMapper) {
		Map<K, V> map = new HashMap<>();
		for (V value: type.getEnumConstants()) {
			map.put(keyMapper.apply(value), value);
		}
		return new ReverseEnumMap<K, V>(map);
	}
	
	private final Map<K, V> map;
	
	private ReverseEnumMap(Map<K, V> map) {
		this.map = map;
	}
	
	public Optional<V> get(K key) {
		return Optional.ofNullable(map.get(key));
	}
}
