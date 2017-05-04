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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * An skeletal implementation of {@link ListMap}.
 * 
 * @param <K> the type of key.
 * @param <V> the type of value.
 */
public abstract class AbstractListMap<K, V> implements ListMap<K, V> {

	private final Map<K, List<V>> map = new HashMap<>();

	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public boolean containsKey(K key) {
		List<V> list = map.get(key);
		return list != null && !list.isEmpty();
	}
	
	@Override
	public List<V> getValues(K key) {
		List<V> values = map.get(key);
		if (values == null) {
			values = createList(key);
			map.put(key, values);
		}
		return values;
	}
	
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	
	@Override
	public int size() {
		int sum = 0;
		for (K key: keySet()) {
			sum += size(key);
		}
		return sum;
	}
	
	@Override
	public int size(K key) {
		List<V> values = map.get(key);
		return (values != null) ? values.size() : 0;
	}
	
	@Override
	public Iterator<V> valueIterator() {
		return new ValueIterator<K, V>(this.map);
	}
	
	/**
	 * Creates a new list which will accommodate added values.
	 * 
	 * @param key the key for the values to be added.
	 * @return newly created list;
	 */
	protected abstract List<V> createList(K key);
	
	/**
	 * Iterator over values.
	 *
	 * @param <K> the type of key.
	 * @param <V> the type of value.
	 */
	private static class ValueIterator<K, V> implements Iterator<V> {
		
		private final Map<K, List<V>> map;
		private final Iterator<K> keyIterator;
		private Iterator<V> iterator;
		
		ValueIterator(Map<K, List<V>> map) {
			this.map = map;
			this.keyIterator = map.keySet().iterator();
		}

		@Override
		public boolean hasNext() {
			while (iterator == null || !iterator.hasNext()) {
				iterator = nextIterator();
				if (iterator == null) {
					return false;
				}
			}
			return true;
		}

		@Override
		public V next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return iterator.next();
		}
		
		private Iterator<V> nextIterator() {
			if (!keyIterator.hasNext()) {
				return null;
			}
			K key = keyIterator.next();
			List<V> list = map.get(key);
			return (list != null) ? list.iterator() : null;
		}
	}
}
