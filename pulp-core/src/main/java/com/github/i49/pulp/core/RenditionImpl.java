package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.Spine;
import com.github.i49.pulp.api.Spine.Page;

/**
 * An implementation class of {@link Rendition}.
 */
class RenditionImpl implements Rendition {

	private final Metadata metadata = new MetadataImpl();

	private final Map<String, PublicationResource> resourceMap = new HashMap<>();
	private final SpineImpl spine = new SpineImpl();
	
	private PublicationResource coverImage;
	
	RenditionImpl() {
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}
	
	@Override
	public boolean hasResource(String name) {
		return this.resourceMap.containsKey(name);
	}
	
	@Override
	public PublicationResource getResourceByName(String name) {
		if (name == null) {
			return null;
		}
		return resourceMap.get(name);
	}
	
	@Override
	public Collection<PublicationResource> getAllResources() {
		return Collections.unmodifiableCollection(this.resourceMap.values());
	}
	
	@Override
	public Rendition addResource(PublicationResource resource) {
		if (resource != null) {
			PublicationResource previous = this.resourceMap.put(resource.getName(), resource);
			if (previous != null) {
				cleanUpResource(previous);
			}
		}
		return this;
	}
	
	@Override
	public Rendition removeResource(PublicationResource resource) {
		if (resource != null) {
			PublicationResource previous = this.resourceMap.remove(resource.getName());
			if (previous != null) {
				cleanUpResource(previous);
			}
		}
		return this;
	}
	
	@Override
	public Spine getSpine() {
		return spine;
	}

	@Override
	public PublicationResource getCoverImage() {
		return coverImage;
	}

	@Override
	public Rendition setCoverImage(String name) {
		if (name == null) {
			this.coverImage = null;
		} else {
			this.coverImage = getResourceByName(name);
		}
		return this;
	}
	
	private void cleanUpResource(PublicationResource resource) {
		if (this.coverImage == resource) {
			this.coverImage = null;
		}
	}

	/**
	 * An implementation class of {@link Spine}.
	 */
	private class SpineImpl implements Spine {
		
		private final List<Page> pages = new ArrayList<>();
		
		/**
		 * Constructs the spine.
		 */
		private SpineImpl() {
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
		 * @exception IllegalArgumentException if the specified resource is not found in the rendition.
		 */
		private Page createPage(PublicationResource resource) {
			if (resource == null) {
				throw new NullPointerException();
			} else if (!resourceMap.containsValue(resource)) {
				throw new IllegalArgumentException("resource is not found in the rendition.");
			}
			
			return new PageImpl(resource);
		}
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
		private PageImpl(PublicationResource resource)  {
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
