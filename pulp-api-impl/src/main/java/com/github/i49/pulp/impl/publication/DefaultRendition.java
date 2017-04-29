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

package com.github.i49.pulp.impl.publication;

import static com.github.i49.pulp.impl.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.core.Spine;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.core.Manifest.Item;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link Rendition}.
 */
public class DefaultRendition implements Rendition {

	/* publication owing this rendition */
	private final Publication publication;
	
	/* the location of the package document */
	private final PublicationResourceLocation location;
	private final PublicationResourceRegistry registry;
	
	/* the metadata of this rendition */
	private final Metadata metadata;

	private final DefaultManifest manifest = new DefaultManifest();
	private final DefaultSpine spine = new DefaultSpine();
	
	/**
	 * Constructs this rendition.
	 * 
	 * @param publication the publication that owns this rendition.
	 * @param location the location of the package document.
	 * @param registry the registry that maintains all publication resources.
	 * @param metadata the metadata of this rendition.
	 */
	public DefaultRendition(
			Publication publication, 
			PublicationResourceLocation location, 
			PublicationResourceRegistry registry,
			Metadata metadata) {
		
		this.publication = publication;
		this.location = location;
		this.registry = registry;
		this.metadata = metadata;
	}
	
	@Override
	public Publication getPublication() {
		return publication;
	}
	
	@Override
	public URI getLocation() {
		return location.toURI();
	}
	
	@Override
	public URI resolve(String location) {
		checkNotNull(location, "location");
		return this.location.resolve(location);
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
	
	/**
	 * Returns a URI relative to the location of the package document.
	 * @param location the URI relative to the container root.
	 * @return a relative URI.
	 */
	private URI relativize(URI location) {
		return this.location.relativize(location);
	}
	
	/**
	 * The default implementation of {@code Manifest}.
	 */
	private class DefaultManifest implements Manifest {
		
		private final Map<PublicationResource, Item> resourceItemMap = new HashMap<>();
		// cover image of the rendition
		private Item coverImage;
		// navigation document of the rendition
		private Item navigationDocument;

		@Override
		public int getNumberOfItems() {
			return resourceItemMap.size();
		}
	
		@Override
		public boolean contains(Item item) {
			checkNotNull(item, "item");
			return resourceItemMap.containsValue(item);
		}
		
		@Override
		public boolean contains(String location) {
			checkNotNull(location, "location");
			URI resolved = resolve(location);
			if (!registry.contains(resolved)) {
				return false;
			}
			PublicationResource resource = registry.get(resolved);
			return resourceItemMap.containsKey(resource);
		}
		
		@Override
		public Item get(String location) {
			checkNotNull(location, "location");
			Item item = null;
			PublicationResource resource = registry.get(resolve(location));
			if (resource != null) {
				item = resourceItemMap.get(resource);
			}
			if (item == null) {
				throw new NoSuchElementException(Messages.MANIFEST_ITEM_MISSING(location));
			}
			return item;
		}
		
		@Override
		public Item add(PublicationResource resource) {
			checkNotNull(resource, "resource");
			if (resourceItemMap.containsKey(resource)) {
				throw new EpubException(Messages.RESOURCE_ALREADY_EXISTS_IN_MANIFEST(resource.getLocation()));
			}
			registry.register(resource);
			return addNewItem(resource);
		}

		@Override
		public boolean remove(Item item) {
			checkNotNull(item, "item");
			if (!resourceItemMap.containsValue(item)) {
				return false;
			}
			PublicationResource resource = item.getResource();
			resourceItemMap.remove(resource);
			registry.unregister(resource);
			return true;
		}

		@Override
		public Iterator<Item> iterator() {
			return Collections.unmodifiableCollection(resourceItemMap.values()).iterator();
		}
		
		@Override
		public boolean hasCoverImage() {
			return coverImage != null;	
		}
		
		@Override
		public Item getCoverImage() {
			if (hasCoverImage()) {
				return coverImage;
			} else {
				throw new NoSuchElementException();
			}
		}
		
		@Override
		public boolean hasNavigationDocument() {
			return navigationDocument != null;
		}
		
		@Override
		public Item getNavigationDocument() {
			if (hasNavigationDocument()) {
				return navigationDocument;
			} else {
				throw new NoSuchElementException();
			}
		}

		private Item addNewItem(PublicationResource resource) {
			URI location = relativize(resource.getLocation());
			Item item = new DefaultItem(location, resource);
			resourceItemMap.put(resource, item);
			return item;
		}
	}
	
	/**
	 * The default implementation of {@link Manifest.Item}.
	 */
	private class DefaultItem implements Manifest.Item {
		
		private final URI location;
		private final PublicationResource resource;
		private boolean scripted;
		
		/**
		 * Constructs this item.
		 * @param location the location of this item.
		 * @param resource the publication resource referenced by this item.
		 */
		private DefaultItem(URI location, PublicationResource resource) {
			this.location = location;
			this.resource = resource;
			this.scripted = false;
		}

		@Override
		public PublicationResource getResource() {
			return resource;
		}

		@Override
		public URI getLocation() {
			return location;
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
	 * The default implementation of {@link Spine}.
	 */
	private class DefaultSpine implements Spine {
		
		private final List<Spine.Page> pages = new ArrayList<>();
		private final Map<Manifest.Item, Spine.Page> itemPageMap = new HashMap<>();


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
			checkNotNull(item, "item");
			validateItem(item);
			Page page = new DefaultPage(item);
			pages.add(page);
			itemPageMap.put(item, page);
			return page;
		}

		@Override
		public Page insert(int index, Item item) {
			checkNotNull(item, "item");
			validateItem(item);
			Page page = new DefaultPage(item);
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
		
		@Override
		public Iterator<Page> iterator() {
			return Collections.unmodifiableList(pages).iterator();
		}

		private void validateItem(Item item) {
			if (!manifest.contains(item)) {
				throw new IllegalArgumentException(Messages.MANIFEST_ITEM_MISSING(item.getLocation().toString()));
			} else if (itemPageMap.containsKey(item)) {
				throw new IllegalArgumentException(Messages.MANIFEST_ITEM_ALREADY_EXISTS_IN_SPINE(item.getLocation()));
			}
		}
	}
	
	/**
	 * The default implementation of {@link Spine.Page}.
	 */
	private static class DefaultPage implements Spine.Page {

		private final Item item;
		private boolean linear;
		
		private DefaultPage(Item item)  {
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
		public DefaultPage linear(boolean linear) {
			this.linear = linear;
			return this;
		}

		@Override
		public String toString() {
			return getItem().toString();
		}
	}
}
