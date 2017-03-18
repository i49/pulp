/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.core.publication;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.Spine;
import com.github.i49.pulp.api.Manifest.Item;
import com.github.i49.pulp.core.Messages;

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
		// cover image of the rendition
		private Item coverImage;
		// navigation document of the rendition
		private Item navigationDocument;

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
				throw new IllegalArgumentException("location is null");
			}
			URI resolved = resolve(location);
			Item item = null;
			PublicationResource resource = registry.get(resolved);
			if (resource != null) {
				item = items.get(resource);
			}
			if (item == null) {
				throw new NoSuchElementException(Messages.MANIFEST_ITEM_MISSING(location));
			}
			return item;
		}
		
		@Override
		public Optional<Item> find(String location) {
			if (location == null) {
				throw new IllegalArgumentException("location is null");
			}
			URI resolved = resolve(location);
			Item item = null;
			PublicationResource resource = registry.get(resolved);
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
		public Optional<Item> findNavigationDocument() {
			return Optional.ofNullable(navigationDocument);
		}

		@Override
		public int getNumberOfItems() {
			return items.size();
		}
	
		@Override
		public Item add(PublicationResource resource) {
			if (resource == null) {
				throw new IllegalArgumentException("resource is null");
			}
			registry.register(resource);
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
				throw new IllegalArgumentException("item is null");
			}
			items.remove(item.getResource());
		}
	}
	
	/**
	 * An implementation of {@link Manifest.Item}.
	 */
	private class ItemImpl implements Manifest.Item {
		
		private final PublicationResource resource;
		private boolean scripted;
		
		private ItemImpl(PublicationResource resource) {
			this.resource = resource;
			this.scripted = false;
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
		public boolean isNavigation() {
			return this == manifest.navigationDocument;
		}
		
		@Override
		public Item asNavigation() {
			manifest.navigationDocument = this;
			return this;
		}
		
		@Override
		public boolean isScripted() {
			return scripted;
		}
		
		@Override
		public Item scripted(boolean scripted) {
			this.scripted = scripted;
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
				throw new IllegalArgumentException("item is null");
			} else if (!manifest.contains(item)) {
				throw new IllegalArgumentException(Messages.MANIFEST_ITEM_MISSING(item.getLocation().toString()));
			} else if (itemPageMap.containsKey(item)) {
				throw new IllegalArgumentException(Messages.MANIFEST_ITEM_DUPLICATE(item.getLocation()));
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