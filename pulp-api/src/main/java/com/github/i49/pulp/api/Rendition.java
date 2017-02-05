package com.github.i49.pulp.api;

import java.util.Collection;

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
	
	/**
	 * Returns {@code true} if this rendition contains the item specified.
	 * @param name the name of the rendition item.
	 * @return {@code true} if this rendition contains the item, {@code false} otherwise.
	 */
	boolean hasItem(String name);

	/**
	 * Returns the rendition item that has a name with the specified value in this rendition.
	 * @param name the name of the resource to be returned.
	 * @return the item if found in this rendition, {@code null} otherwise. 
	 */
	Item getItemByName(String name);
	
	/**
	 * Returns all resources contained in this rendition.
	 * @return all resources in this rendition. The returned collection cannot be modified.
	 */
	Collection<Item> getAllItems();
	
	/**
	 * Return the spine that binds the all pages composing this rendition.
	 * The spine determines the order in which the resources in this rendition will be rendered. 
	 * @return the spine of this rendition.
	 */
	Spine getSpine();

	/**
	 * Returns a rendition item that represents a cover image of this rendition.
	 * @return the cover image item if this rendition has, {@code null} if not.
	 */
	Item getCoverImage();
	
	/**
	 * Publication resource that this rendition requires to render contents.
	 */
	public static interface Item {
		
		/**
		 * Returns the name of the item.
		 * @return the name of the item.
		 */
		String getName();
		
		/**
		 * Returns the publication resource assigned to this item.
		 * @return the publication resource.
		 */
		PublicationResource getResource();

		/**
		 * Returns the media type of the resource.
		 * @return the media type.
		 */
		CoreMediaType getMediaType();
	}
}
