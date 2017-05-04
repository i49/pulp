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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Multiple-value map which maps a key to a list of values.
 * 
 * @param <K> the type of key.
 * @param <V> the type of value.
 */
public interface ListMap<K, V> {
	
	/**
	 * Clears this map.
	 */
	void clear();
	
	boolean containsKey(K key);
	
	List<V> getValues(K key);
	
	Set<K> keySet();
	
	int size();
	
	int size(K key);
	
	Iterator<V> valueIterator();
}
