package com.github.i49.pulp.api;

import java.util.Collection;
import java.util.List;

/**
 * A logical document entity consisting of a set of interrelated resources 
 * representing one rendering of a {@link Publication}. 
 */
public interface Rendition {
	
	/**
	 * Return the metadata of this rendition.
	 * @return the metadata of this rendition.
	 */
	Metadata getMetadata();

	PublicationResourceBuilderFactory getResourceBuilderFactory();
	
	String getPrefix();

	Item addResource(PublicationResource resource);
	
	void removeResource(String path);
	
	Item getItem(String path);
	
	Collection<Item> getItems();
	
	/**
	 * Creates a new page.
	 * @param path the pathname of the item used by the new page.
	 * @return created page.
	 */
	Page createPage(String path);
	
	/**
	 * Returns the ordered list of the pages bound to the spine of this rendition.
	 * @return the ordered list of the pages.
	 */
	List<Page> getPages();
	
	/**
	 * A resource required by this rendition. 
	 */
	public static interface Item {
		
		/**
		 * Returns the pathname of this item.
		 * @return the pathname of this item.
		 */
		String getPath();
		
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
		 */
		Item asCoverImage();
	}
	
	/**
	 * A page bound to the spine of this rendition.
	 */
	public static interface Page {
		
		/**
		 * Returns the resource item of this page.
		 * @return the resource item of this page.
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
		 */
		Page linear(boolean linear);
	};
}
