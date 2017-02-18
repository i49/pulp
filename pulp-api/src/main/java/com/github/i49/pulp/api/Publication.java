package com.github.i49.pulp.api;

/**
 * A publication that represents a single intellectual or artistic work
 * such as book, magazine, newspaper, journal, office document and so on.
 * A publication consists of one or more {@link Rendition}s.
 */
public interface Publication extends Iterable<Rendition> {
	
	/**
	 * Returns the default {@link Rendition} of this publication.
	 * @return the default {@link Rendition} of this publication.
	 */
	Rendition getDefaultRendition();
	
	/**
	 * Returns the {@link PublicationResourceRegistry} that manages publication resources
	 * for this publication.
	 * @return the publication resource registry
	 */
	PublicationResourceRegistry getResourceRegistry();

	/**
	 * Adds a new {@link Rendition} sitting at the default location in the container.
	 * @return created rendition.
	 */
	Rendition addRendition();
	
	/**
	 * Adds a new {@link Rendition} with the location of the Package Document in the container specified.
	 * @param location the location of the Package Document that can be such as "EPUB/package.opf".
	 * @return created rendition.
	 * @exception EpubException if specified {@code location} is invalid.
	 */
	Rendition addRendition(String location);
}
