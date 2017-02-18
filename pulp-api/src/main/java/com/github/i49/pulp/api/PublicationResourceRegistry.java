package com.github.i49.pulp.api;

/**
 * The registry that manages all publication resources available for a publication. 
 */
public interface PublicationResourceRegistry {

	/**
	 * Returns {@code true} if this registry contains the resource specified. 
	 * @param identifier the identifier of the resource that may be a relative URI.
	 * @return {@code true} if it contains the resource, {@code false} otherwise.
	 */
	boolean contains(String identifier);
	
	/**
	 * Returns {@code true} if this registry contains the resource specified. 
	 * @param resource the resource to find.
	 * @return {@code true} if it contains the resource, {@code false} otherwise.
	 */
	boolean contains(PublicationResource resource);
	
	/**
	 * Returns the resource specified by its identifier.
	 * @param identifier the identifier of the resource that may be a relative URI.
	 * @return the resource if found, {@code null} if not found.
	 */
	PublicationResource get(String identifier);

	/**
	 * Creates a builder for building a resource with the specified identifier.
	 * The built resource is automatically registered with this registry.
	 * @param identifier the identifier of the resource that may be a relative URI.
	 * @return created resource builder.
	 * @exception NullPointerException if {@code identifier} is {@code null}.
	 */
	PublicationResourceBuilder builder(String identifier);
	
	/**
	 * Creates a child registry that resolves relative URIs against the base specified.
	 * @param base the base pathname that is used to resolve relative URIs. 
	 * @return created child registry.
	 */
	PublicationResourceRegistry getChildRegistry(String base);
}
