package com.github.i49.pulp.core;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.RenditionBuilder;

public class RenditionBuilderImpl implements RenditionBuilder {

	private final String baseName;
	private final Map<String, ItemImpl> items;
	private Rendition.Item coverImage;
	private final List<PageImpl> pages;

	private final ResourceFactory resourceFactory;
	
	public RenditionBuilderImpl(String baseName, ResourceFactory resourceFactory) {
		this.baseName = baseName;
		this.resourceFactory = resourceFactory;
		this.items = new HashMap<>();
		this.pages = new ArrayList<>();
	}
	
	@Override
	public RenditionBuilder addResource(String name, Path path) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, path);
		return addItem(name, resource);
	}

	@Override
	public RenditionBuilder addResource(String name, URI uri) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, uri);
		return addItem(name, resource);
	}

	@Override
	public RenditionBuilder addResource(String name, CoreMediaType mediaType, Path path) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, mediaType, path);
		return addItem(name, resource);
	}

	@Override
	public RenditionBuilder addResource(String name, CoreMediaType mediaType, URI uri) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, mediaType, uri);
		return addItem(name, resource);
	}
	
	@Override
	public RenditionBuilder selectCoverImage(String name) {
		Rendition.Item item = items.get(name);
		if (item == null) {
			throw new IllegalArgumentException("Item \"" + name + "\" is not found in the rendition.");
		}
		this.coverImage = item;
		return this;
	}

	@Override
	public void addPage(String name) {
		addPage(name, true);
	}

	@Override
	public void addPage(String name, boolean linear) {
		ItemImpl item = items.get(name);
		if (item == null) {
			throw new IllegalArgumentException("Item \"" + name + "\" is not found in the rendition.");
		}
		pages.add(new PageImpl(item, linear));
	}
	
	public Rendition build() {
		RenditionImpl rendition = new RenditionImpl(this.baseName, this.items, this.pages);
		rendition.coverImage = this.coverImage;
		return rendition;
	}
	
	private RenditionBuilder addItem(String name, PublicationResource resource) {
		this.items.put(name, new ItemImpl(name, resource));
		return this;
	}
	
	private static class RenditionImpl implements Rendition {
		
		private final String baseName;
		private final MetadataImpl metadata = new MetadataImpl();
		private final Map<String, Rendition.Item> items;
		private List<Rendition.Page> pages;
		private Item coverImage;
		
		public RenditionImpl(String baseName, Map<String, ItemImpl> items, List<PageImpl> pages) {
			this.baseName = baseName;
			this.items = Collections.unmodifiableMap(items);
			this.pages = Collections.unmodifiableList(pages);
		}

		@Override
		public Metadata getMetadata() {
			return metadata;
		}

		@Override
		public boolean hasItem(String name) {
			if (name == null) {
				return false;
			}
			return items.containsKey(name);
		}

		@Override
		public Item getItemByName(String name) {
			if (name == null) {
				return null;
			}
			return items.get(name);
		}

		@Override
		public Collection<Item> getAllItems() {
			return items.values();
		}

		@Override
		public Item getCoverImage() {
			return coverImage;
		}

		@Override
		public List<Page> getPages() {
			return pages;
		}
	}
	
	private static class ItemImpl implements Rendition.Item {

		private final String name;
		private final PublicationResource resource;
		
		public ItemImpl(String name, PublicationResource resource) {
			this.name = name;
			this.resource = resource;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public PublicationResource getResource() {
			return resource;
		}

		@Override
		public CoreMediaType getMediaType() {
			return getResource().getMediaType();
		}
	}
	
	private static class PageImpl implements Rendition.Page {

		private final ItemImpl item;
		private final boolean linear;
		
		public PageImpl(ItemImpl item, boolean linear) {
			this.item = item;
			this.linear = linear;
		}
	
		@Override
		public String getName() {
			return item.getName();
		}

		@Override
		public PublicationResource getResource() {
			return item.getResource();
		}

		@Override
		public boolean isLinear() {
			return linear;
		}

		@Override
		public String toString() {
			return getName();
		}
	}
}
