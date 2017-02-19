package com.github.i49.pulp.api;

import java.net.URI;

/**
 * A set of publication resources required to render a rendition.
 */
public interface Manifest extends Iterable<Manifest.Item> {
	
	/**
	 * Returns {@code true} if this manifest contains the specified item.
	 * @param item the item to find.
	 * @return {@code true} if this manifest contains the specified item, {@code false} otherwise.
	 */
	boolean contains(Item item);
	
	/**
	 * Returns the specified item in this manifest
	 * @param location the location of the publication resource assigned to the item.
	 * @return the item if this manifest contains the specified resource, {@code null} otherwise.
	 */
	Item find(String location);
	
	/**
	 * Returns the item used as a cover image for the rendition.
	 * @return the item if this manifest has a cover image, {@code null} otherwise.
	 */
	Item getCoverImage();

	/**
	 * Adds a publication resource to this manifest.
	 * If the resource specified already exists in this manifest, 
	 * this method returns the item previously created by the resource.
	 * @param resource the resource to be added, cannot be {@code null}.
	 * @return an item of this manifest.
	 * @exception NullPointerException if {@code resource} is {@code null}.
	 * @exception EpubException if {@code resource} does not exists in the {@link PublicationResourceRegistry}.
	 */
	Item add(PublicationResource resource);
	
	/**
	 * Removes the specified manifest item from this manifest if present. 
	 * @param item the manifest item to be removed from this manifest.
	 */
	void remove(Item item);
	
	/**
	 * An entry of the manifest that refers a publication resource
	 * required by the rendition.
	 */
	static interface Item {
		
		/**
		 * Returns the publication resource referred by this item.
		 * @return the publication resource. 
		 */
		PublicationResource getResource();
		
		/**
		 * Returns the location of the publication resource,
		 * which is an absolute URI or is relative to the root directory of the EPUB container.
		 * @return the location of the publication resource.
		 */
		URI getLocation();

		/**
		 * Returns {@code true} if this item is the cover image of the rendition.
		 * @return {@code true} if this item is the cover image of the rendition, {@code false} otherwise. 
		 */
		boolean isCoverImage();
		
		/**
		 * Uses this item as the cover image of the rendition.
		 * @return this item.
		 */
		Item asCoverImage();
	}
}
