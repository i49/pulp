package com.github.i49.pulp.api;

import java.net.URI;

/**
 * A set of publication resources required to render a rendition.
 */
public interface Manifest extends Iterable<Manifest.Item> {
	
	/**
	 * Adds a resource to this manifest.
	 * @param resource the resource to be added.
	 * @return an item of this manifest.
	 */
	Item addItem(PublicationResource resource);
	
	/**
	 * Removes a resource from this manifest. 
	 * @param resource the resource to be removed.
	 */
	void removeItem(Item item);
	
	/**
	 * Returns the specified item in this manifest
	 * @param location the location of the resource.
	 * @return the item if it is found in this manifest, or {@code null} if not found.
	 */
	Item getItem(String location);
	
	/**
	 * Returns the item used as a cover image.
	 * @return the item if this manifest has the cover image, or {@code null} if not found.
	 */
	Item getCoverImage();

	/**
	 * An entry of the manifest that refers a publication resource.
	 */
	static interface Item {
		
		/**
		 * Returns the publication resource referred by this item.
		 * @return the publication resource. 
		 */
		PublicationResource getResource();
		
		/**
		 * Returns the location of the publication resource.
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
