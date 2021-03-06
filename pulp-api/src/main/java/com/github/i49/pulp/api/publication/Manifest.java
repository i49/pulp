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

package com.github.i49.pulp.api.publication;

import java.net.URI;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A set of publication resources required to render a rendition.
 */
public interface Manifest extends Iterable<Manifest.Item> {

	/**
	 * Returns the number of the items in this manifest.
	 * 
	 * @return the number of the items.
	 */
	int getNumberOfItems();

	/**
	 * Checks if this manifest contains the specified item.
	 * 
	 * @param item the item to be checked. cannot be {@code null}.
	 * @return {@code true} if this manifest contains the specified item, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code item} is {@code null}.
	 */
	boolean contains(Item item);
	
	/**
	 * Checks if this manifest contains the item at the specified location.
	 * 
	 * @param location the item location relative to the publication document, cannot be {@code null}.
	 * @return {@code true} if this manifest contains the specified item, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code location} is {@code null}.
	 */
	boolean contains(String location);
	
	/**
	 * Returns the specified item in this manifest
	 * 
	 * @param location the item location relative to the publication document, cannot be {@code null}.
	 * @return the item found.
	 * @throws IllegalArgumentException if {@code location} is {@code null}.
	 * @throws NoSuchElementException if this manifest does not contain the specified resource.
	 */
	Item get(String location);
	
	/**
	 * Adds a publication resource to this manifest.
	 * If the resource specified already exists in this manifest, 
	 * this method returns the item previously created by the resource.
	 * 
	 * @param resource the resource to be added, cannot be {@code null}.
	 * @return an item of this manifest.
	 * @throws IllegalArgumentException if {@code resource} is {@code null}.
	 * @throws EpubException if specified {@code resource} already added to this manifest,
	 *                       or other resource exists at the location in the publication.
	 */
	Item add(PublicationResource resource);
	
	/**
	 * Removes the specified manifest item from this manifest if present. 
	 * 
	 * @param item the manifest item to be removed from this manifest, cannot be {@code null}.
	 * @return {@code true} if this manifest contained the specified item.
	 * @throws IllegalArgumentException if {@code item} is {@code null}.
	 */
	boolean remove(Item item);
	
	/**
	 * Returns an iterator that can iterate over all items in this manifest.
	 * 
	 * @return an iterator over instances of {@link Item}.
	 */
	@Override
	Iterator<Item> iterator();
	
	/**
	 * Returns whether this manifest has the cover image or not.
	 * 
	 * @return {@code true} if this manifest has the cover image, {@code false} otherwise.
	 */
	boolean hasCoverImage();
	
	/**
	 * Returns the item used as the cover image for the rendition.
	 * This method can be called safely in case that {@link #hasCoverImage()} returns {@code true}.
	 * 
	 * @return the {@link Item} used as the cover image.
	 * @throws NoSuchElementException if this manifest does not have the cover image.
	 * @see #hasCoverImage()
	 */
	Item getCoverImage();

	/**
	 * Returns whether this manifest has the navigation document or not.
	 * 
	 * @return {@code true} if this manifest has the navigation document, {@code false} otherwise.
	 */
	boolean hasNavigationDocument();
	
	/**
	 * Returns the item used as the navigation document for the rendition.
	 * This method can be called safely in case that {@link #hasNavigationDocument()} returns {@code true}.
	 * 
	 * @return the {@link Item} used as the navigation document.
	 * @throws NoSuchElementException if this manifest does not have the navigation document.
	 * @see #hasNavigationDocument()
	 */
	Item getNavigationDocument();

	/**
	 * An entry of the manifest that refers a publication resource
	 * required by the rendition.
	 */
	static interface Item {
		
		/**
		 * Returns the publication resource referenced by this item.
		 * 
		 * @return the publication resource. 
		 */
		PublicationResource getResource();
		
		/**
		 * Returns the location of this item that is an absolute URI or relative to the rendition.
		 * 
		 * @return the location of this item.
		 */
		URI getLocation();

		/**
		 * Returns {@code true} if this item is the cover image of the rendition.
		 * 
		 * @return {@code true} if this item is the cover image, {@code false} otherwise. 
		 */
		boolean isCoverImage();
		
		/**
		 * Marks this item as the cover image of the rendition.
		 * At most one item can be specified as a cover image.
		 * 
		 * @return this item.
		 */
		Item asCoverImage();
		
		/**
		 * Returns {@code true} if this item is the navigation document of the rendition.
		 * 
		 * @return {@code true} if this item is the navigation document, {@code false} otherwise. 
		 */
		boolean isNavigation();
		
		/**
		 * Marks this item as the navigation document of the rendition.
		 * At most one item can be specified as a navigation document.
		 * 
		 * @return this item.
		 */
		Item asNavigation();
		
		/**
		 * Returns {@code true} if this item is marked as scripted.
		 * 
		 * @return {@code true} if this item is marked as scripted, {@code false} otherwise.
		 */
		boolean isScripted();
		
		/**
		 * Marks this item as scripted.
		 * 
		 * @param scripted {@code true} if this item is scripted, or {@code false} if not scripted.
		 * @return this item.
		 */
		Item scripted(boolean scripted);
	}
}
