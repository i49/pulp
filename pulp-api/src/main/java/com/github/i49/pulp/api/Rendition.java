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
	 * Returns {@code true} if this rendition contains the resource specified.
	 * @param name the name of the resource.
	 * @return {@code true} if this rendition contains the resource, {@code false} otherwise.
	 */
	boolean hasResource(String name);

	/**
	 * Returns the publication resource that has a name with the specified value in this rendition.
	 * @param name the name of the resource to be returned.
	 * @return the resource if found in this rendition, {@code null} otherwise. 
	 */
	PublicationResource getResourceByName(String name);
	
	/**
	 * Returns all resources contained in this rendition.
	 * @return all resources in this rendition. The returned collection cannot be modified.
	 */
	Collection<PublicationResource> getAllResources();
	
	/**
	 * Adds a publication resource to this rendition.
	 * @param resource the resource to be added to this rendition.
	 * @return this rendition.
	 */
	Rendition addResource(PublicationResource resource);
	
	/**
	 * Removes a publication resource from this rendition.
	 * @param resource the resource to be removed from this rendition.
	 * @return this rendition.
	 */
	Rendition removeResource(PublicationResource resource);
	
	/**
	 * Return the spine that binds the all pages composing this rendition.
	 * The spine determines the order in which the resources in this rendition will be rendered. 
	 * @return the spine of this rendition.
	 */
	Spine getSpine();

	/**
	 * Returns a resource that represents a cover image of this rendition.
	 * @return the cover image resource.
	 */
	PublicationResource getCoverImage();

	/**
	 * Select a publication resource representing a cover image of this rendition.
	 * @param name the name of the resource representing a cover image.
	 * @return this rendition.
	 */
	Rendition setCoverImage(String name);
}
