package com.github.i49.pulp.api;

import java.net.URI;

/**
 * A logical document entity consisting of a set of interrelated resources 
 * representing one rendering of a {@link Publication}. 
 */
public interface Rendition {
	
	/**
	 * Return the publication that this rendition belongs to.
	 * @return the publication.
	 */
	Publication getPublication();

	/**
	 * Returns the location of the Package Document for this rendition,
	 * which is relative to the root directory of the EPUB container.
	 * e.g., "EPUB/package.opf".
	 * @return the relative URI of the Package Document, the path component of which has the location.
	 */
	URI getLocation();

	/**
	 * Return the metadata of this rendition.
	 * @return the metadata of this rendition.
	 */
	Metadata getMetadata();
	
	/**
	 * Returns the publication resource registry that resolves relative paths 
	 * against the location of this rendition.
	 * @return the publication resource registry.
	 */
	PublicationResourceRegistry getResourceRegistry();

	/**
	 * Returns the manifest of this rendition that carries all publication resources
	 * required to render the rendition. 
	 * @return the manifest of this rendition.
	 */
	Manifest getManifest();

	/**
	 * Returns the spine of this rendition that binds the ordered pages of the rendition.
	 * @return the spine of this rendition.
	 */
	Spine getSpine();
}
