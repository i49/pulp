package com.github.i49.pulp.core;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation class of {@link Publication}.
 */
class PublicationImpl implements Publication {

	private final RenditionImpl defaultRendition;
	
	/**
	 * Constructs the publication.
	 */
	PublicationImpl() {
		this.defaultRendition = new RenditionImpl();
	}
	
	@Override
	public Rendition getDefaultRendition() {
		return defaultRendition;
	}
}
