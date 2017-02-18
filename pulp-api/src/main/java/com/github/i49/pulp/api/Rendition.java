package com.github.i49.pulp.api;

import java.util.List;

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
	 * Returns the location to the Package Document of this rendition.
	 * @return the location to the Package Document of this rendition.
	 */
	String getLocation();

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
	 * Returns the manifest that carries a set of required publication resources.
	 * @return the manifest of this rendition.
	 */
	Manifest getManifest();
	
	/**
	 * Returns the page of the given pathname if it exists, or newly created page if it does not exist.
	 * @param pathname the pathname of the item assigned to the new page.
	 * @return specified page.
	 * @exception EpubException if the specified item does not exist.
	 */
	Page page(String pathname);
	
	/**
	 * Returns the ordered list of the pages presented to the readers of this rendition.
	 * @return the ordered list of the pages.
	 */
	List<Page> getPageList();
	
	/**
	 * A page bound to the spine of this rendition.
	 */
	public static interface Page {
		
		/**
		 * Returns the item of this page.
		 * @return the item of this page.
		 */
		Manifest.Item getItem();
		
		/**
		 * Returns {@code true} if this page is considered primary.
		 * @return {@code true} if this page is considered primary, {@code false} if considered auxiliary.
		 */
		boolean isLinear();
		
		/**
		 * Sets the linearity of this page.
		 * @param linear {@code true} if this page is considered primary, {@code false} otherwise.
		 * @return this page.
		 */
		Page linear(boolean linear);
	}
}
