package com.github.i49.pulp.api;

/**
 * A publication that represents a single intellectual or artistic work
 * such as book, magazine, newspaper, journal, office document or beyond.
 * A publication consists of one or more {@link Rendition}s.
 */
public interface Publication extends Iterable<Rendition> {
	
	/**
	 * Default location of renditions.
	 */
	static final String DEFAULT_RENDITION_PREFIX = "EPUB";
	
	/**
	 * Returns the default {@link Rendition} of this publication.
	 * @return the default {@link Rendition} of this publication.
	 */
	Rendition getDefaultRendition();
	
	/**
	 * Adds a new {@link Rendition}.
	 * @param prefix the path prefix of the new rendition. if the value is {@code null}, the default prefix is used.
	 * @return created rendition.
	 */
	Rendition addRendition(String prefix);
	
	/**
	 * Returns an instance of {@link PublicationResourceBuilderFactory} for producing {@link PublicationResourceBuilder}.
	 * @param rendition the rendition for which resources will be built.
	 * @return a factory instance.
	 */
	PublicationResourceBuilderFactory getResourceBuilderFactory(Rendition rendition);
}
