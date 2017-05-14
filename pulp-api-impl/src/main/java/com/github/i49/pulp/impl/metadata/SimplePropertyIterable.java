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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import com.github.i49.pulp.api.metadata.PropertyIterable;
import com.github.i49.pulp.api.vocabulary.Property;

/**
 * 
 */
public class SimplePropertyIterable<T extends Property> implements PropertyIterable<T> {

	private final Collection<T> collection;
	private final Class<T> propertyType;
	
	public SimplePropertyIterable(Collection<T> collection, Class<T> propertyType) {
		this.collection = collection;
		this.propertyType = propertyType;
	}
	
	@Override
	public T[] asArray() {
		@SuppressWarnings("unchecked")
		T[] array = (T[])Array.newInstance(this.propertyType, this.collection.size());
		this.collection.toArray(array);
		return array;
	}

	@Override
	public Optional<T> first() {
		Iterator<T> it = iterator();
		if (it.hasNext()) {
			return Optional.of(it.next());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return collection.iterator();
	}
}
