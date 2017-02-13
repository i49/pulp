package com.github.i49.pulp.api;

/**
 * The  factory type to create a {@link PublicationResourceBuilder}
 * that can be used to build a {@link PublicationResource}.
 */
public interface PublicationResourceBuilderFactory {

	/**
	 * Creates a {@link PublicationResourceBuilder} that will build a {@link PublicationResource}.
	 * @param pathname the pathname of the publication resource to be built. cannot be {@code null}.
	 * @return created builder.
	 * @exception NullPointerException if given {@code pathname} is {@code null}. 
	 * @exception EpubException if the publication resource with given {@code pathname} is already built. 
	 */
	PublicationResourceBuilder builder(String pathname);
}
