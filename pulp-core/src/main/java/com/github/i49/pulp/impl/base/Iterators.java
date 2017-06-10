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
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Provides various kinds of iterators.
 */
public final class Iterators {

	private Iterators() {
	}

	/**
	 * Returns a filtering iterator.
	 * 
	 * @param <E> the type of the element to iterate.
	 * @param iterator the original iterator.
	 * @param predicate the element tester used for filtering.
	 * @return created filtering iterator.
	 */
	public static <E> Iterator<E> filter(Iterator<E> iterator, Predicate<E> predicate) {
		return new FilteringIterator<E>(iterator, predicate);
	}
	
	/**
	 * Returns a unmodifiable iterator.
	 * 
	 * @param <E> the type of the element to iterate.
	 * @param iterator the original iterator.
	 * @return created unmodifiable iterator.
	 */
	public static <E> Iterator<E> unmodifiable(Iterator<E> iterator) {
		return new UnmodifiableIterator<E>(iterator);
	}

	private static class FilteringIterator<E> implements Iterator<E> {
		
		private final Iterator<E> iterator;
		private final Predicate<E> predicate;
		private E next;
		
		FilteringIterator(Iterator<E> iterator, Predicate<E> predicate) {
			this.iterator = iterator;
			this.predicate = predicate;
		}

		@Override
		public boolean hasNext() {
			while (next == null && iterator.hasNext()) {
				E e = iterator.next();
				if (predicate.test(e)) {
					next = e;
					break;
				}
			}
			return next != null;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E next = this.next;
			this.next = null;
			return next;
		}
	}
	
	private static class UnmodifiableIterator<E> implements Iterator<E> {
		
		private final Iterator<E> iterator;

		public UnmodifiableIterator(Iterator<E> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public E next() {
			return iterator.next();
		}
	}
}
