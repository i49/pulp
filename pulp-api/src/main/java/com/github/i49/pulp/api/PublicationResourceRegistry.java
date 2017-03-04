package com.github.i49.pulp.api;

import java.util.Optional;
import java.util.NoSuchElementException;

/**
 * The registry that manages all publication resources available for a publication. 
 */
public interface PublicationResourceRegistry {

	/**
	 * Returns {@code true} if this registry contains the resource specified. 
	 * @param location the location of the resource that may be a relative URI.
	 * @return {@code true} if it contains the resource, {@code false} otherwise.
	 * @exception NullPointerException if {@code location} is {@code null}.
	 */
	boolean contains(String location);
	
	/**
	 * Returns {@code true} if this registry contains the resource specified. 
	 * @param resource the resource to find.
	 * @return {@code true} if it contains the resource, {@code false} otherwise.
	 * @exception NullPointerException if {@code resource} is {@code null}.
	 */
	boolean contains(PublicationResource resource);
	
	/**
	 * Returns the resource specified by its location.
	 * @param location the location of the resource that may be a relative URI, cannot be {@code null}.
	 * @return found resource, never be {@code null}.
	 * @exception NullPointerException if specified {@code location} is {@code null}.
	 * @exception NoSuchElementException if this registry does not contains the resource.
	 */
	PublicationResource get(String location);

	/**
	 * Finds the resource specified by its location in this registry.
	 * @param location the location of the resource that may be a relative URI, cannot be {@code null}.
	 * @return the resource if this registry contains it, {@code null} otherwise.
	 * @exception NullPointerException if specified {@code location} is {@code null}.
	 */
	Optional<PublicationResource> find(String location);
	
	/**
	 * Returns the number of resources in this registry.
	 * @return the number of resources.
	 */
	int getNumberOfResources();
	
	/**
	 * Creates a builder for building a publication resource at the specified location.
	 * The built resource is automatically registered with this registry.
	 * @param location the location of the resource that may be a relative URI.
	 * @return created resource builder, never be {@code null}.
	 * @exception NullPointerException if {@code location} is {@code null}.
	 * @exception IllegalArgumentException if resource to build already exists in this registry.
	 */
	PublicationResourceBuilder builder(String location);
}
