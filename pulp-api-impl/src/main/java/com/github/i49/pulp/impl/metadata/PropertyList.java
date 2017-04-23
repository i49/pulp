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

import java.util.AbstractList;
import java.util.ArrayList;

import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.api.metadata.Term;

/**
 * The list containing homogeneous properties. 
 */
class PropertyList extends AbstractList<Property> {
	
	private final ArrayList<Property> delegate = new ArrayList<>();
	private final Term term;
	private final int minSize;
	private final int maxSize;
	
	public PropertyList(Term term) {
		this(term, 0, Integer.MAX_VALUE);
	}

	public PropertyList(Term term, int minSize) {
		this(term, minSize, Integer.MAX_VALUE);
	}

	/**
	 * Constructs this list.
	 * 
	 * @param term the term of the property this list can contain.
	 * @param minSize the minimum size of this list.
	 * @param maxSize the maximum size of this list.
	 */
	public PropertyList(Term term, int minSize, int maxSize) {
		this.term = term;
		this.minSize = minSize;
		this.maxSize = maxSize;
	}

	@Override
	public Property get(int index) {
		return delegate.get(index);
	}

	@Override
	public int size() {
		return delegate.size();
	}
	
	@Override
	public Property set(int index, Property element) {
		validateElement(element);
		return delegate.set(index, element);
	}

	@Override
	public void add(int index, Property element) {
		validateElement(element);
		delegate.add(index, element);
	}

	@Override
	public Property remove(int index) {
		if (size() <= minSize) {
			throw new IllegalStateException("List must have at least " + minSize + " element(s).");
		}
		return delegate.remove(index);
	}

	private void validateElement(Property element) {
		if (element == null) {
			throw new NullPointerException("Property must not be null.");
		} else if (element.getTerm() != this.term) {
			throw new IllegalArgumentException("Unexpected term " + term + ".");
		} else if (contains(element)) {
			throw new IllegalArgumentException("Element already exists.");
		}
	}
}
