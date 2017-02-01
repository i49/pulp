package com.github.i49.pulp.api;

import java.util.Collection;

/**
 * A publication that represents a single intellectual or artistic work.
 */
public interface Publication {

	/**
	 * Return the metadata of this publication.
	 * @return the metadata of this publication.
	 */
	Metadata getMetadata();
	
	/**
	 * Returns whether this publication contains the resource specified or not.
	 * @param name the name of the resource.
	 * @return {@code true} if this publication contains the resource, {@code false} otherwise.
	 */
	boolean hasResource(String name);

	/**
	 * Returns the publication resource that has a name with the specified value in this publication.
	 * @param name the name of the resource to be returned.
	 * @return the resource if found in this publication, {@code null} otherwise. 
	 */
	PublicationResource getResourceByName(String name);
	
	/**
	 * Returns all resources contained in this publication.
	 * @return all resources in this publication. The returned collection cannot be modified.
	 */
	Collection<PublicationResource> getAllResources();
	
	/**
	 * Adds a publication resource to this publication.
	 * @param resource the resource to be added to this publication.
	 * @return this publication.
	 */
	Publication addResource(PublicationResource resource);
	
	/**
	 * Removes a publication resource from this publication.
	 * @param resource the resource to be removed from this publication.
	 * @return this publication.
	 */
	Publication removeResource(PublicationResource resource);
	
	/**
	 * Return the spine that binds the all pages composing this publication.
	 * The spine determines the order in which the resources in this publication will be rendered. 
	 * @return the spine of this publication.
	 */
	Spine getSpine();

	/**
	 * Returns a resource that represents a cover image.
	 * @return the cover image resource.
	 */
	PublicationResource getCoverImage();

	/**
	 * Select a publication resource representing a cover image of this publication.
	 * @param name the name of the resource representing a cover image.
	 * @return this publication.
	 */
	Publication setCoverImage(String name);
}
