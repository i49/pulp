package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation of {@link Rendition}.
 */
public class RenditionImpl implements Rendition {

	private final String prefix;

	private final PublicationResourceBuilderFactoryImpl resourceBuilderFactory;
	
	private final MetadataImpl metadata;
	
	private final Map<String, Item> manifest;
	private final Map<String, Page> pages;
	private final List<Page> pageList;
	
	private Item coverImage;
	
	public RenditionImpl(String prefix, PublicationResourceBuilderFactoryImpl factory) {
		this.prefix = prefix;
		this.resourceBuilderFactory = factory;
		this.metadata = new MetadataImpl();
		this.manifest = new HashMap<>();
		this.pages = new HashMap<>();
		this.pageList = new ArrayList<>();
	}
	
	@Override
	public String getPrefix() {
		return prefix;
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
	public Item require(PublicationResource resource) {
		if (resource == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("resource"));
		}
		String pathname = resource.getPathname();
		if (resourceBuilderFactory.getBuilt(pathname) != resource) {
			throw new EpubException(Messages.INVALID_RESOURCE(pathname));
		}
		Item item = getItem(pathname);
		if (item == null) {
			item = createItem(resource);
		}
		return item;
	}
	
	@Override
	public void unrequire(String pathname) {
		if (pathname == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("pathname"));
		}
		Item item = getItem(pathname);
		if (item != null) {
			this.manifest.remove(pathname);
		}
	}
	
	@Override
	public Item getItem(String pathname) {
		if (pathname == null) {
			return null;
		}
		return manifest.get(pathname);
	}
	
	@Override
	public Collection<Item> getItems() {
		return Collections.unmodifiableCollection(manifest.values());
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
	
	private Item createItem(PublicationResource resource) {
		String pathname = resource.getPathname();
		Item item = new ItemImpl(resource);
		this.manifest.put(pathname, item);
		return item;
	}
	
	private Page createPage(String pathname) {
		Item item = this.manifest.get(pathname);
		if (item == null) {
			throw new EpubException(Messages.MISSING_PUBLICATION_RESOURCE(pathname));
		}
		Page page = new PageImpl(item);
		this.pages.put(pathname, page);
		return page;
	}

	/**
	 * An implementation of {@link Rendition.Item}.
	 */
	private class ItemImpl implements Rendition.Item {
		
		private final PublicationResource resource;
		
		private ItemImpl(PublicationResource resource) {
			this.resource = resource;
		}

		@Override
		public String getPathname() {
			return resource.getPathname();
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
			return getPathname();
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
