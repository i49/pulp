package com.github.i49.pulp.api;

/**
 * Spine for binding the ordered pages that compose a rendition.
 */
public interface Spine extends Iterable<Spine.Page> {

	/**
	 * Returns the number of pages bound to this spine.
	 * @return the number of pages.
	 */
	int getNumberOfPages();
	
	/**
	 * Returns the page specified by its index.
	 * @param index the position of the page, which starts from zero.
	 * @return the page at the specified position.
	 * @exception IndexOutOfBoundsException if the {@code index} is out of range. 
	 */
	Page get(int index);
	
	/**
	 * Creates a new page on this spine and appends it to the list of pages.
	 * @param item the manifest item rendered by the page. 
	 * @return created page.
	 * @exception NullPointerException if specified {@code item} is null.
	 * @exception IllegalArgumentException if specified {@code item} is already assigned to a page
	 *            or it is not in the manifest.
	 */
	Page append(Manifest.Item item);
	
	/**
	 * Creates a new page on this spine at the specified location
	 * and shifts the page currently at that position and any subsequent pages backward.
	 * @param index the position of the new page, which starts from zero.
	 * @param item the manifest item rendered by the page. 
	 * @return created page.
	 * @exception NullPointerException if specified {@code item} is null.
	 * @exception IllegalArgumentException if specified {@code item} is already assigned to a page
	 *            or it is not in the manifest.
	 * @exception IndexOutOfBoundsException if the {@code index} is out of range. 
	 */
	Page insert(int index, Manifest.Item item);
	
	/**
	 * Removes the page at the specified position from this spine.
	 * @param index the position of the page, which starts from zero.
	 * @exception IndexOutOfBoundsException if the {@code index} is out of range. 
	 */
	void remove(int index);

	/**
	 * Moves the page to the specified position in this spine.
	 * @param index the current position of the page to move. 
	 * @param newIndex the new position after the page is moved.
	 * @return moved page.
	 * @exception IndexOutOfBoundsException if either {@code index} or {@code newIndex} is out of range. 
	 */
	Page move(int index, int newIndex);
	
	/**
	 * Each page bound to the spine of the rendition.
	 */
	static interface Page {
		
		/**
		 * Returns the manifest item assigned to this page.
		 * @return the manifest item assigned to this page.
		 */
		Manifest.Item getItem();
	
		/**
		 * Returns {@code true} if this page can be the starting point in the spine.
		 * @return {@code true} if this page can be the starting point, {@code false} otherwise.
		 */
		boolean isLinear();
		
		/**
		 * Sets the linearity of this page, which is {@code true} by default.
		 * @param linear {@code true} if this page can be the starting point in the spine, {@code false} otherwise.
		 * @return this page.
		 */
		Page linear(boolean linear);
	}
}
