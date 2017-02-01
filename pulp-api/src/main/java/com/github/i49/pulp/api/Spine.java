package com.github.i49.pulp.api;

import java.util.Iterator;

/**
 * The Spine where the pages are gathered and bound.
 * An instance of this class will be owned by a {@link Publication} instance. 
 */
public interface Spine extends Iterable<Spine.Page> {

	/**
	 * A page bound to the spine.
	 */
	public interface Page {
		
		/**
		 * Returns the resource assigned to this page.
		 * @return the resource assigned to this page.
		 */
		PublicationResource getResource();
		
		/**
		 * Returns {@code true} if this page is considered primary.
		 * @return {@code true} if this page is considered primary, {@code false} if this page is considered auxiliary.
		 */
		boolean isLinear();
		
		/**
		 * Assigns the value of the linear property that determines whether this spine
		 * is primary or not.  
		 * @param linear {@code true} if this page is primary, {@code false} if auxiliary.
		 * @return this page.
		 */
		Page setLinear(boolean linear);
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
	
	/**
	 * Creates a new page and appends it to the end of this spine.
	 * 
	 * @param resource the resource to be assigned to the new page.
	 * @return created page.
	 * @exception NullPointerException if the specified resource is {@code null}.
	 * @exception IllegalArgumentException if the specified resource is not valid one.  
	 */
	Page add(PublicationResource resource);
	
	/**
	 * Creates a new page and inserts it at the specified position in this spine.
	 *  
	 * @param index the index at which the new page to be inserted.
	 * @param resource the resource to be assigned to the new page.
	 * @return created page.
	 * @exception IllegalArgumentException if the specified resource is not valid one.  
	 * @exception NullPointerException if the specified resource is {@code null}.
	 */
	Page add(int index, PublicationResource resource);

	/**
	 * Returns the page at the specified position in this spine.
	 * 
	 * @param index the index of the page to be returned.
	 * @return the page at the specified position in this spine.
	 * @exception IndexOutOfBoundsException if the index specified is out of range.
	 */
	Page get(int index);
	
	/**
	 * Creates a new page and replaces the page at the specified position with the new one.
	 * 
	 * @param index the index of the page to be replaced.
	 * @param resource the resource to be assigned to the new page.
	 * @return created page.
	 * @exception NullPointerException if the specified resource is {@code null}.
	 * @exception IndexOutOfBoundsException if the index specified is out of range.
	 * @exception IllegalArgumentException if the specified resource is not valid one.  
	 */
	Page set(int index, PublicationResource resource);

	/**
	 * Removes all of the pages in this spine. 
	 * The spine will be empty after the invocation of this method.
	 */
	void clear();
}
