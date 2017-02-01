package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Spine;

/**
 * An implementation class of {@link Spine}.
 */
class SpineImpl implements Spine {
	
	private final List<Page> pages = new ArrayList<>();
	
	/**
	 * Constructs the spine.
	 */
	SpineImpl() {
	}

	@Override
	public Iterator<Page> iterator() {
		return Collections.unmodifiableList(pages).iterator();
	}

	@Override
	public int size() {
		return pages.size();
	}

	@Override
	public boolean isEmpty() {
		return pages.isEmpty();
	}
	
	@Override
	public Page add(PublicationResource resource) {
		Page page = createPage(resource);
		if (page != null) {
			pages.add(createPage(resource));
		}
		return page;
	}

	@Override
	public Page add(int index, PublicationResource resource) {
		Page page = createPage(resource);
		if (page != null) {
			pages.add(index, createPage(resource));
		}
		return page;
	}

	@Override
	public Page get(int index) {
		return pages.get(index);
	}

	@Override
	public Page set(int index, PublicationResource resource) {
		Page page = createPage(resource);
		if (page != null) {
			pages.set(index, createPage(resource));
		}
		return page;
	}

	@Override
	public void clear() {
		pages.clear();
	}
	
	@Override
	public String toString() {
		return pages.toString();
	}
	
	/**
	 * Creates a new page.
	 * @param resource the resource to be assigned to the new page.
	 * @return created page.
	 * @exception NullPointerException if the specified resource is {@code null}.
	 */
	private Page createPage(PublicationResource resource) {
		if (resource == null) {
			throw new NullPointerException();
		}
		return new PageImpl(resource);
	}
	
	/**
	 * An implementation of {@link Spine.Page}.
	 */
	private static class PageImpl implements Spine.Page {
		
		private final PublicationResource resource;
		private boolean linear;
		
		/**
		 * Constructs a new page.
		 * @param resource the resource to be assigned to the page.
		 */
		public PageImpl(PublicationResource resource)  {
			this.resource = resource;
			this.linear = true;
		}

		@Override
		public PublicationResource getResource() {
			return resource;
		}
		
		@Override
		public boolean isLinear() {
			return linear;
		}
		
		@Override
		public Page setLinear(boolean linear) {
			this.linear = linear;
			return this;
		}
		
		@Override
		public String toString() {
			return resource.getName();
		}
	}
}
