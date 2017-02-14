package com.github.i49.pulp.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A logical document entity consisting of a set of interrelated resources 
 * representing one rendering of a {@link Publication}. 
 */
public interface Rendition {

	/**
	 * Returns the prefix of this rendition.
	 * The prefix is used as the base path of the rendition when persisted.
	 * @return the prefix of this rendition.
	 */
	String getPrefix();

	/**
	 * Return the metadata of this rendition.
	 * @return the metadata of this rendition.
	 */
	Metadata getMetadata();
	
	/**
	 * Returns all resources available for this rendition.
	 * @return all resources available for this rendition.
	 */
	Map<String, PublicationResource> getAvailableResources();

	/**
	 * Adds a resource required for this rendition.
	 * @param resource the required resource for this rendition.
	 * @return the item referring the required resource. 
	 */
	Item require(PublicationResource resource);
	
	/**
	 * Removes a resource from this rendition. 
	 * @param pathname the pathname of the resource to be removed.
	 */
	void unrequire(String pathname);
	
	Item getItem(String pathname);
	
	/**
	 * Returns all items in this rendition.
	 * @return the collection containing all items, cannot be modified.
	 */
	Collection<Item> getItems();
	
	/**
	 * Returns the page of the given pathname if it exists, or newly created page if it does not exist.
	 * @param pathname the pathname of the item assigned to the new page.
	 * @return specified page.
	 * @exception EpubException if the specified item does not exist.
	 */
	Page page(String pathname);
	
	/**
	 * Returns the ordered list of the pages presented to the readers of this rendition.
	 * @return the ordered list of the pages.
	 */
	List<Page> getPageList();
	
	/**
	 * A resource required by this rendition. 
	 */
	public static interface Item {
		
		/**
		 * Returns the pathname of this item.
		 * @return the pathname of this item.
		 */
		String getPathname();
		
		/**
		 * Returns the publication resource that this item refers to.
		 * @return the publication resource that this item refers to. 
		 */
		PublicationResource getResource();
		
		/**
		 * Returns {@code true} if this item is the cover image of this rendition.
		 * @return {@code true} if this item is the cover image of this rendition, {@code false} otherwise. 
		 */
		boolean isCoverImage();
		
		/**
		 * Uses this item as the cover image of this rendition.
		 * @return this item.
		 */
		Item asCoverImage();
	}
	
	/**
	 * A page bound to the spine of this rendition.
	 */
	public static interface Page {
		
		/**
		 * Returns the item of this page.
		 * @return the item of this page.
		 */
		Item getItem();
		
		/**
		 * Returns {@code true} if this page is considered primary.
		 * @return {@code true} if this page is considered primary, {@code false} if considered auxiliary.
		 */
		boolean isLinear();
		
		/**
		 * Sets the linearity of this page.
		 * @param linear {@code true} if this page is considered primary, {@code false} otherwise.
		 * @return this page.
		 */
		Page linear(boolean linear);
	}
}
