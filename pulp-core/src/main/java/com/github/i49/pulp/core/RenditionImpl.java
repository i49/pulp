package com.github.i49.pulp.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.Spine;
import com.github.i49.pulp.api.Manifest.Item;

/**
 * An implementation of {@link Rendition}.
 */
public class RenditionImpl implements Rendition {

	/* publication owing this rendition */
	private final Publication publication;
	
	private final URI location;
	private final PublicationResourceRegistry registry;
	
	private final MetadataImpl metadata = new MetadataImpl();

	private final ManifestImpl manifest = new ManifestImpl();
	private final SpineImpl spine = new SpineImpl();
	
	public RenditionImpl(Publication publication, URI location, PublicationResourceRegistry registry) {
		this.publication = publication;
		this.location = location;
		this.registry = registry;
	}
	
	@Override
	public Publication getPublication() {
		return publication;
	}
	
	@Override
	public URI getLocation() {
		return location;
	}
	
	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	@Override
	public PublicationResourceRegistry getResourceRegistry() {
		return registry;
	}
	
	@Override
	public Manifest getManifest() {
		return manifest;
	}
	
	@Override
	public Spine getSpine() {
		return spine;
	}
	
	private URI resolve(String location) {
		return this.location.resolve(location);
	}
	
	/**
	 * An implementation of {@code Manifest}.
	 */
	private class ManifestImpl implements Manifest {
		
		private final Map<PublicationResource, Item> items = new HashMap<>();
		/* cover image of the rendition */
		private Item coverImage;

		@Override
		public Iterator<Item> iterator() {
			return Collections.unmodifiableCollection(items.values()).iterator();
		}

		@Override
		public boolean contains(Item item) {
			if (item == null) {
				return false;
			}
			return items.containsValue(item);
		}
		
		@Override
		public Item get(String location) {
			if (location == null) {
				throw new NullPointerException(Messages.NULL_PARAMETER("location"));
			}
			Item item = null;
			PublicationResource resource = registry.get(location);
			if (resource != null) {
				item = items.get(resource);
			}
			if (item == null) {
				URI resolved = resolve(location);
				throw new NoSuchElementException(Messages.MISSING_PUBLICATION_RESOURCE(resolved));
			}
			return item;
		}
		
		@Override
		public Optional<Item> find(String location) {
			if (location == null) {
				throw new NullPointerException(Messages.NULL_PARAMETER("location"));
			}
			Item item = null;
			PublicationResource resource = registry.get(location);
			if (resource != null) {
				item = items.get(resource);
			}
			return Optional.ofNullable(item);
		}

		@Override
		public Optional<Item> findCoverImage() {
			return Optional.ofNullable(coverImage);
		}

		@Override
		public int getNumberOfItems() {
			return items.size();
		}
	
		@Override
		public Item add(PublicationResource resource) {
			validateResource(resource);
			Item item = items.get(resource);
			if (item == null) {
				item = new ItemImpl(resource);
				items.put(resource, item);
			}
			return item;
		}

		@Override
		public void remove(Item item) {
			if (item == null) {
				throw new NullPointerException(Messages.NULL_PARAMETER("item"));
			}
			items.remove(item.getResource());
		}
		
		private void validateResource(PublicationResource resource) {
			if (resource == null) {
				throw new NullPointerException(Messages.NULL_PARAMETER("resource"));
			} else if (!registry.contains(resource)) {
				throw new EpubException(Messages.MISSING_PUBLICATION_RESOURCE(resource.getLocation()));
			}
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
	
	/**
	 * An implementation of {@link Spine}.
	 */
	private class SpineImpl implements Spine {
		
		private final List<Spine.Page> pages = new ArrayList<>();
		private final Map<Manifest.Item, Spine.Page> itemPageMap = new HashMap<>();

		@Override
		public Iterator<Page> iterator() {
			return Collections.unmodifiableList(pages).iterator();
		}

		@Override
		public int getNumberOfPages() {
			return pages.size();
		}
		
		@Override
		public Page get(int index) {
			return pages.get(index);
		}

		@Override
		public Page append(Item item) {
			validateItem(item);
			Page page = new PageImpl(item);
			pages.add(page);
			itemPageMap.put(item, page);
			return page;
		}

		@Override
		public Page insert(int index, Manifest.Item item) {
			validateItem(item);
			Page page = new PageImpl(item);
			pages.add(index, page);
			itemPageMap.put(item, page);
			return page;
		}
	
		@Override
		public void remove(int index) {
			Page page = pages.remove(index);
			itemPageMap.remove(page.getItem());
		}

		@Override
		public Page move(int index, int newIndex) {
			Page page = pages.remove(index);
			pages.add(newIndex, page);
			return page;
		}
		
		private void validateItem(Item item) {
			if (item == null) {
				throw new NullPointerException(Messages.NULL_PARAMETER("item"));
			} else if (!manifest.contains(item)) {
				throw new IllegalArgumentException(Messages.MISSING_MANIFEST_ITEM(item.getLocation()));
			} else if (itemPageMap.containsKey(item)) {
				throw new IllegalArgumentException(Messages.DUPLICATE_MANIFEST_ITEM(item.getLocation()));
			}
		}
	}
	
	/**
	 * An implementation of {@link Spine.Page}.
	 */
	private static class PageImpl implements Spine.Page {

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
