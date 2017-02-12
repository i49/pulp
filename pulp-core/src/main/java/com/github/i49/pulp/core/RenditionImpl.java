package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.Rendition;

public class RenditionImpl implements Rendition {

	private final String prefix;

	private final PublicationResourceManager resourceManager;
	private final PublicationResourceBuilderFactoryImpl resourceBuilderFactory;
	
	private final MetadataImpl metadata;
	
	private final Map<String, Item> manifest;
	private final List<Page> pages;
	
	private Item coverImage;
	
	public RenditionImpl(String prefix, PublicationResourceManager resourceManager) {
		this.prefix = prefix;
		this.resourceManager = resourceManager;
		this.resourceBuilderFactory = new PublicationResourceBuilderFactoryImpl(prefix, resourceManager);
		this.metadata = new MetadataImpl();
		this.manifest = new HashMap<>();
		this.pages = new ArrayList<>();
	}
	
	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	@Override
	public PublicationResourceBuilderFactory getResourceBuilderFactory() {
		return resourceBuilderFactory;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}
	
	@Override
	public Item addResource(PublicationResource resource) {
		if (resource == null) {
			return null;
		} else if (this.resourceManager.containsKey(resource.getName())) {
			throw new IllegalArgumentException("Resource given already exists.");
		}
		
		this.resourceManager.put(resource.getName(), resource);
		
		String relativePath = getRelativePathOf(resource);
		Item item = new ItemImpl(relativePath, resource);
		this.manifest.put(relativePath, item);
		return item;
	}
	
	@Override
	public void removeResource(String path) {
	}
	
	@Override
	public Item getItem(String path) {
		if (path == null) {
			return null;
		}
		return manifest.get(path);
	}
	
	@Override
	public Collection<Item> getItems() {
		return Collections.unmodifiableCollection(manifest.values());
	}
	
	@Override
	public Page createPage(String pathname) {
		Item item = manifest.get(pathname);
		if (item == null) {
			throw new IllegalArgumentException(pathname + " not found");
		}
		return new PageImpl(item);
	}

	@Override
	public List<Page> getPages() {
		return pages;
	}
	
	String getRelativePathOf(PublicationResource resource) {
		String prefix = this.prefix + "/";
		if (!resource.getName().startsWith(prefix)) {
			throw new IllegalArgumentException();
		}
		return resource.getName().substring(prefix.length());
	}

	private class ItemImpl implements Rendition.Item {
		
		private final String path;
		private final PublicationResource resource;
		
		private ItemImpl(String path, PublicationResource resource) {
			this.path = path;
			this.resource = resource;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public PublicationResource getResource() {
			return resource;
		}
		
		@Override
		public boolean isCoverImage() {
			return this == coverImage;
		}
		
		@Override
		public Item asCoverImage() {
			coverImage = this;
			return this;
		}
		
		@Override
		public String toString() {
			return getPath();
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
