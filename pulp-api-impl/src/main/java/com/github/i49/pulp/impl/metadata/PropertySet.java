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

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.github.i49.pulp.api.vocabulary.Property;
import com.github.i49.pulp.api.vocabulary.Term;

/**
 *
 */
public class PropertySet extends AbstractSet<Property> {

	private final Map<Term, List<Property>> map = new HashMap<>();
	
	@Override
	public boolean add(Property e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (contains(e)) {
			return false;
		} 
		Term term = e.getTerm();
		List<Property> list = this.map.get(term);
		if (list == null) {
			list = addPropertyList(term);
		}
		list.add(e);
		return true;
	}
	
	@Override
	public void clear() {
		this.map.clear();
	}
	
	@Override
	public boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException();
		} else if (!(o instanceof Property)) {
			throw new ClassCastException();
		}
		Property p = (Property)o;
		return this.map.containsKey(p.getTerm());
	}
	
	@Override
	public Iterator<Property> iterator() {
		return new ValueIterator<Term, Property>(this.map);
	}
	
	@Override
	public boolean remove(Object o) {
		if (o == null) {
			throw new NullPointerException();
		} else if (!(o instanceof Property)) {
			throw new ClassCastException();
		}
		Property p = (Property)o;
		List<Property> list = this.map.get(p.getTerm());
		if (list == null) {
			return false;
		}
		return list.remove(p);
	}

	@Override
	public int size() {
		int total = 0;
		for (Term term: this.map.keySet()) {
			total +=  this.map.get(term).size();
		}
		return total;
	}
	
	public boolean containsTerm(Term term) {
		assert(term != null);
		return this.map.containsKey(term);
	}

	public Collection<Property> findByTerm(Term term) {
		assert(term != null);
		List<Property> list = this.map.get(term);
		if (list == null) {
			return Collections.emptyList();
		} else {
			return list;
		}
	}
	
	public Collection<Property> removeByTerm(Term term) {
		assert(term != null);
		List<Property> list = this.map.get(term);
		if (list == null) {
			return Collections.emptyList();
		} else {
			this.map.remove(term);
			return list;
		}
	}
	
	private List<Property> addPropertyList(Term term) {
		List<Property> list = new ArrayList<>();
		this.map.put(term, list);
		return list;
	}

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
