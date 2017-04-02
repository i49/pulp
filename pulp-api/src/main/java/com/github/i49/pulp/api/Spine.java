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

package com.github.i49.pulp.api;

/**
 * Spine for binding the ordered pages that compose a rendition.
 */
public interface Spine extends Iterable<Spine.Page> {

	/**
	 * Returns the number of pages bound to this spine.
	 * 
	 * @return the number of pages.
	 */
	int getNumberOfPages();
	
	/**
	 * Returns the page specified by its index.
	 * 
	 * @param index the position of the page, which starts from zero.
	 * @return the page at the specified position.
	 * @throws IndexOutOfBoundsException if the {@code index} is out of range. 
	 */
	Page get(int index);
	
	/**
	 * Creates a new page on this spine and appends it to the list of pages.
	 * 
	 * @param item the manifest item rendered by the page. 
	 * @return created page.
	 * @throws IllegalArgumentException if specified {@code item} is already assigned to a page
	 *            or it is not in the manifest or {@code null}.
	 */
	Page append(Manifest.Item item);
	
	/**
	 * Creates a new page on this spine at the specified location
	 * and shifts the page currently at that position and any subsequent pages backward.
	 * 
	 * @param index the position of the new page, which starts from zero.
	 * @param item the manifest item rendered by the page. 
	 * @return created page.
	 * @throws IllegalArgumentException if specified {@code item} is already assigned to a page
	 *            or it is not in the manifest or {@code null}.
	 * @throws IndexOutOfBoundsException if the {@code index} is out of range. 
	 */
	Page insert(int index, Manifest.Item item);
	
	/**
	 * Removes the page at the specified position from this spine.
	 * 
	 * @param index the position of the page, which starts from zero.
	 * @throws IndexOutOfBoundsException if the {@code index} is out of range. 
	 */
	void remove(int index);

	/**
	 * Moves the page to the specified position in this spine.
	 * 
	 * @param index the current position of the page to move. 
	 * @param newIndex the new position after the page is moved.
	 * @return moved page.
	 * @throws IndexOutOfBoundsException if either {@code index} or {@code newIndex} is out of range. 
	 */
	Page move(int index, int newIndex);
	
	/**
	 * Each page bound to the spine of the rendition.
	 */
	static interface Page {
		
		/**
		 * Returns the manifest item assigned to this page.
		 * 
		 * @return the manifest item assigned to this page.
		 */
		Manifest.Item getItem();
	
		/**
		 * Returns {@code true} if this page can be the starting point in the spine.
		 * 
		 * @return {@code true} if this page can be the starting point, {@code false} otherwise.
		 */
		boolean isLinear();
		
		/**
		 * Sets the linearity of this page, which is {@code true} by default.
		 * 
		 * @param linear {@code true} if this page can be the starting point in the spine, {@code false} otherwise.
		 * @return this page.
		 */
		Page linear(boolean linear);
	}
}
