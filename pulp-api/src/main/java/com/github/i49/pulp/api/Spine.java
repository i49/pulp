package com.github.i49.pulp.api;

import java.util.Iterator;

/**
 * The spine where the pages are gathered and bound.
 * One spine belongs to one {@link Rendition}. 
 */
public interface Spine extends Iterable<Spine.Page> {

	/**
	 * A page bound to the spine.
	 */
	public interface Page {
		
		String getName();
		
		/**
		 * Returns the resource assigned to this page.
		 * @return the resource assigned to this page.
		 */
		PublicationResource getResource();
		
		/**
		 * Returns {@code true} if this page is considered primary.
		 * @return {@code true} if this page is considered primary, {@code false} if considered auxiliary.
		 */
		boolean isLinear();
	};
	
	/**
	 * Returns an iterator over the pages in this spine from the front to the back order.
	 * 
	 * <p>{@link Iterator#remove} is not supported in the returned iterator.</p>
	 * @return an iterator over the pages in this spine.
	 */
	Iterator<Page> iterator();
	
	/**
	 * Returns the number of pages bound to this spine.
	 * @return the number of pages.
	 */
	int size();
	
	/**
	 * Returns {@code true} if this spine contains no pages.
	 * 
	 * @return {@code true} if this spine contains no pages, {@code false} otherwise.
	 */
	boolean isEmpty();
}
