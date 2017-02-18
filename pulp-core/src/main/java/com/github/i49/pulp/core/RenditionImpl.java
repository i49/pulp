package com.github.i49.pulp.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.Manifest.Item;

/**
 * An implementation of {@link Rendition}.
 */
public class RenditionImpl implements Rendition {

	private final Publication publication;
	private final PublicationResourceRegistry rootRegistry;
	
	private final URI location;
	private final PublicationResourceRegistry localRegistry;
	
	private final MetadataImpl metadata = new MetadataImpl();
	
	private final ManifestImpl manifest = new ManifestImpl();
	private final Map<URI, Page> pages;
	private final List<Page> pageList;
	
	public RenditionImpl(Publication publication, URI location) {
		this.publication = publication;
		this.rootRegistry = publication.getResourceRegistry();
		
		this.location = location;
		this.localRegistry = rootRegistry.getChildRegistry(location.getPath());
		
		this.pages = new HashMap<>();
		this.pageList = new ArrayList<>();
	}
	
	@Override
	public Publication getPublication() {
		return publication;
	}
	
	@Override
	public String getLocation() {
		return location.getPath();
	}
	
	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	@Override
	public PublicationResourceRegistry getResourceRegistry() {
		return localRegistry;
	}
	
	@Override
	public Manifest getManifest() {
		return manifest;
	}
	
	@Override
	public Page page(String pathname) {
		if (pathname == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("pathaname"));
		}
		Page page = this.pages.get(pathname);
		if (page == null) {
			page = createPage(pathname);
		}
		return page;
	}

	@Override
	public List<Page> getPageList() {
		return pageList;
	}
	
	private Page createPage(String location) {
		URI resolved = resolve(location);
		Item item = this.manifest.getItem(location);
		if (item == null) {
			throw new EpubException(Messages.MISSING_PUBLICATION_RESOURCE(resolved));
		}
		Page page = new PageImpl(item);
		this.pages.put(resolved, page);
		return page;
	}
	
	private URI resolve(String pathname) {
		return location.resolve(pathname);
	}
	
	/**
	 * An implementation of {@code Manifest}.
	 */
	private class ManifestImpl implements Manifest {
		
		private final Map<PublicationResource, Item> items = new HashMap<>();
		private Item coverImage;

		@Override
		public Iterator<Item> iterator() {
			return Collections.unmodifiableCollection(items.values()).iterator();
		}

		@Override
		public Item addItem(PublicationResource resource) {
			if (resource == null) {
				return null;
			} else if (!rootRegistry.contains(resource)) {
				throw new EpubException(Messages.INVALID_RESOURCE(resource.getLocation()));
			}
			Item item = items.get(resource);
			if (item == null) {
				item = new ItemImpl(resource);
				items.put(resource, item);
			}
			return item;
		}

		@Override
		public void removeItem(Item item) {
			if (item == null) {
				return;
			}
			items.remove(item.getResource());
		}

		@Override
		public Item getItem(String location) {
			if (location == null) {
				return null;
			}
			PublicationResource resource = localRegistry.get(location);
			if (resource == null) {
				return null;
			}
			return items.get(resource);
		}

		@Override
		public Item getCoverImage() {
			return coverImage;
		}
	}
	
	/**
	 * An implementation of {@link Manifest.Item}.
	 */
	private class ItemImpl implements Manifest.Item {
		
		private final PublicationResource resource;
		
		private ItemImpl(PublicationResource resource) {
			this.resource = resource;
		}

		@Override
		public PublicationResource getResource() {
			return resource;
		}

		@Override
		public URI getLocation() {
			return resource.getLocation();
		}
		
		@Override
		public boolean isCoverImage() {
			return this == manifest.coverImage;
		}
		
		@Override
		public Item asCoverImage() {
			manifest.coverImage = this;
			return this;
		}
		
		@Override
		public String toString() {
			return getLocation().toString();
		}
	}
	
	private static class PageImpl implements Rendition.Page {

		private final Item item;
		private boolean linear;
		
		private PageImpl(Item item)  {
			this.item = item;
			this.linear = true;
		}
	
		@Override
		public Item getItem() {
			return item;
		}

		@Override
		public boolean isLinear() {
			return linear;
		}

		@Override
		public PageImpl linear(boolean linear) {
			this.linear = linear;
			return this;
		}

		@Override
		public String toString() {
			return getItem().toString();
		}
	}
}
