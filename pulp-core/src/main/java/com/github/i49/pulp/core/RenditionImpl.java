package com.github.i49.pulp.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation of {@link Rendition}.
 */
public class RenditionImpl implements Rendition {

	private final Publication publication;
	private final PublicationResourceRegistry rootRegistry;
	
	private final URI packagePath;
	private final PublicationResourceRegistry localRegistry;
	
	private final MetadataImpl metadata = new MetadataImpl();
	
	private final Map<URI, Item> manifest;
	private final Map<URI, Page> pages;
	private final List<Page> pageList;
	
	private Item coverImage;
	
	public RenditionImpl(Publication publication, String packagePath) {
		this.publication = publication;
		this.rootRegistry = publication.getResourceRegistry();
		
		this.packagePath = URI.create(packagePath);
		this.localRegistry = rootRegistry.getChildRegistry(packagePath);
		
		this.manifest = new HashMap<>();
		this.pages = new HashMap<>();
		this.pageList = new ArrayList<>();
	}
	
	@Override
	public Publication getPublication() {
		return publication;
	}
	
	@Override
	public String getPackageDocumentPath() {
		return packagePath.getPath();
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
	public Item require(PublicationResource resource) {
		if (resource == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("resource"));
		}
		if (!rootRegistry.contains(resource)) {
			throw new EpubException(Messages.INVALID_RESOURCE(resource.getLocation()));
		}
		URI identifier = resource.getLocation();
		Item item = manifest.get(identifier);
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
		Item item = new ItemImpl(resource);
		this.manifest.put(resource.getLocation(), item);
		return item;
	}
	
	private Page createPage(String identifier) {
		URI resolved = resolve(identifier);
		Item item = this.manifest.get(resolved);
		if (item == null) {
			throw new EpubException(Messages.MISSING_PUBLICATION_RESOURCE(resolved));
		}
		Page page = new PageImpl(item);
		this.pages.put(resolved, page);
		return page;
	}
	
	private URI resolve(String pathname) {
		return packagePath.resolve(pathname);
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
		public URI getLocation() {
			return resource.getLocation();
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
