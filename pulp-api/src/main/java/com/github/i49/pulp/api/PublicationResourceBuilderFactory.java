package com.github.i49.pulp.api;

/**
 * Factory type to be used to produce {@link PublicationResourceBuilder}s.
 */
public interface PublicationResourceBuilderFactory {

	/**
	 * Creates a builder for building a publication resource at the specified location.
	 * @param location the location of the resource that may be a relative URI.
	 * @return created resource builder, never be {@code null}.
	 * @throws IllegalArgumentException if {@code location} is {@code null}.
	 */
	PublicationResourceBuilder newBuilder(String location);
}
