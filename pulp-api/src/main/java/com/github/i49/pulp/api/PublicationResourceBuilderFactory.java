package com.github.i49.pulp.api;

/**
 * The factory to be used to produce instances of {@link PublicationResourceBuilder}.
 */
public interface PublicationResourceBuilderFactory {

	/**
	 * Creates a builder for building a publication resource located at the specified location.
	 * @param location the location of the publication resource.
	 * @return created resource builder, never be {@code null}.
	 * @throws IllegalArgumentException if {@code location} is {@code null}.
	 * @throws EpubException if {@code location} is an invalid location.
	 */
	PublicationResourceBuilder newBuilder(String location);
}
