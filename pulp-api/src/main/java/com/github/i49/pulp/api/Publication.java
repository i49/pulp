package com.github.i49.pulp.api;

/**
 * A publication that represents a single intellectual or artistic work
 * such as book, magazine, newspaper, journal, office document or beyond.
 * A publication consists of one or more {@link Rendition}s.
 */
public interface Publication extends Iterable<Rendition> {
	
	/**
	 * Returns the default {@link Rendition} of this publication.
	 * @return the default {@link Rendition} of this publication.
	 */
	Rendition getDefaultRendition();
	
	Rendition addRendition(String prefix);
}
