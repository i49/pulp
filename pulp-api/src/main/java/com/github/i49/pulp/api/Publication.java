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
	
	PublicationResourceRegistry getResourceRegistry();

	/**
	 * Adds a new {@link Rendition} sitting at the default location in the container.
	 * @return created rendition.
	 */
	Rendition addRendition();
	
	/**
	 * Adds a new {@link Rendition} with the location of the Package Document in the container specified.
	 * @param packagePath the path to the Package Document of the new rendition that can be such as "EPUB/package.opf".
	 * @return created rendition.
	 */
	Rendition addRendition(String packagePath);
}
