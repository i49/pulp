package com.github.i49.pulp.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;

public class RenditionImpl implements Rendition {

	@SuppressWarnings("unused")
	private final String prefix;

	private final MetadataImpl metadata = new MetadataImpl();
	private final Map<String, Rendition.Item> items;
	private List<Rendition.Page> pages;
	private Item coverImage;
	
	public RenditionImpl(String prefix, Map<String, ItemImpl> items, List<PageImpl> pages) {
		this.prefix = prefix;
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
	
	void setCoverImage(Item coverImage) {
		this.coverImage = coverImage;
	}

	static class ItemImpl implements Rendition.Item {

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

	static class PageImpl implements Rendition.Page {

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
